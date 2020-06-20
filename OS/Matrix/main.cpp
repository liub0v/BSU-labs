#include<iostream>
#include <iomanip>
#include <fstream>
#include <thread>
#include <vector>
using namespace std;

typedef vector<vector<double >> matrix;

ofstream out("output.txt");
ifstream in("input.txt");

matrix C;
matrix A;
matrix B;

void break1(int i1, int j1, int k) {
    int s1 = A.size(), s2 = B[0].size();
    for (int i = i1 * s1 / k; i < (i1 + 1) * s1 / k; i++) {
        for (int j = j1 * s2 / k; j < (j1 + 1) * s2 / k; j++) {
            for (int l = 0; l < A[0].size(); l++) {
                C[i][j] += A[i][l] * B[l][j];
            }
        }
    }
}

void break2(int i1, int j1, int k) {
    int s1 = A.size(), s2 = B[0].size();
    for (int j = j1 * s2 / k; j < (j1 + 1) * s2 / k; j++) {
        for (int i = i1 * s1 / k; i < (i1 + 1) * s1 / k; i++) {
            for (int m = 0; m < A[0].size(); m++) {
                C[i][j] += A[i][m] * B[m][j];
            }
        }
    }
}

void break3(int count, int i1, int j1, int k) {
    int s1 = A.size(), s2 = B[0].size(), s3 = A[0].size();
    for (int i = i1 * s1 / count; i < (i1 + 1) * s1 / count; i++) {
        for (int j = j1 * s2 / count; j < (j1 + 1) * s2 / count; j++) {
            for (int m = k * s3 / count; m < (k + 1) * s3 / count; m++) {
                C[i][j] += A[i][m] * B[m][j];
            }
        }
    }
}

void print() {
    for (const vector<double> &i : C) {
        for (double j : i)
            out << setw(3) << j << " ";
        out << endl;
    }
    C.clear();
}

int main() {
    int threads_count;
    in >> threads_count;
    int n, m, k;
    in >> n >> m >> k;
    A.resize(n, vector<double>(m, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++)
            in >> A[i][j];
    }
    B.resize(m, vector<double>(k, 0));
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < k; j++)
            in >> B[i][j];
    }
    vector<thread> threads(threads_count);

    C.resize(A.size(), vector<double>(B[0].size()));
    auto start = chrono::steady_clock::now();
    for (int i = 0; i < A.size(); i++)
        for (int j = 0; j < B[0].size(); j++)
            for (int t = 0; t < A[0].size(); t++)
                C[i][j] += A[i][t] * B[t][j];
    auto end = chrono::steady_clock::now();
    out << "Ordinary multiplication: " << chrono::duration_cast<chrono::microseconds>(end - start).count() << " mcs"
        << endl;
    print();

    C.resize(A.size(), vector<double>(B[0].size()));
    start = chrono::steady_clock::now();
    for (int i = 0; i < threads_count; i++) {
        for (int j = 0; j < threads_count; j++) {
            threads[j] = thread(break1, i, j, threads_count);
            threads[j].join();
        }
    }
    end = chrono::steady_clock::now();
    out << "Break1: " << chrono::duration_cast<chrono::microseconds>(end - start).count() << " mcs" << endl;
    print();

    threads.clear();
    threads.resize(threads_count);
    C.resize(A.size(), vector<double>(B[0].size()));
    start = chrono::steady_clock::now();
    for (int i = 0; i < threads_count; i++) {
        for (int j = 0; j < threads_count; j++) {
            threads[j] = thread(break2, i, j, threads_count);
            threads[j].join();

        }
    }
    end = chrono::steady_clock::now();
    out << "Break2: " << chrono::duration_cast<chrono::microseconds>(end - start).count() << " mcs" << endl;
    print();

    threads.clear();
    threads.resize(threads_count);
    C.resize(A.size(), vector<double>(B[0].size()));
    start = chrono::steady_clock::now();
    for (int i = 0; i < threads_count; i++) {
        for (int j = 0; j < threads_count; j++) {
            for (int k = 0; k < threads_count; k++) {
                threads[k] = thread(break3, threads_count, i, j, k);
            }
            for (int k = 0; k < threads_count; k++) {
                threads[k].join();
            }
        }
    }
    end = chrono::steady_clock::now();
    out << "Break3: " << chrono::duration_cast<chrono::microseconds>(end - start).count() << " mcs" << endl;
    print();

    return 0;
}
