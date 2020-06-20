#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <iomanip>
#include<ctime>
using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

//vector<vector<int>>linetree;
int** linetree;
int* indices;
int* sequence;
int k = 1;
int n1;
bool comp1(const pair<int, int>& a, const pair<int, int>& b) {
	if (a.second != b.second)
		return a.second < b.second;
	else
		return a.first > b.first;
}

int get_max(int index, int v, int tl, int tr, int l, int r) {

	if (l <= tl && tr <= r) {
		return linetree[index][v];
	}
	if (tr < l || r < tl) {
		return 0;
	}
	int tm = (tl + tr) / 2;
	return max(get_max(index, v * 2, tl, tm, l, r),
		get_max(index, v * 2 + 1, tm + 1, tr, l, r));
}


void update(int index, int v, int val) {
	linetree[index][v] = max(linetree[index][v], val);
	v /= 2;
	while (v) {
		int was = linetree[index][v];
		linetree[index][v] = max(linetree[index][2 * v], linetree[index][2 * v + 1]);
		if (was == linetree[index][v]) break;
		v /= 2;
	}
}

void get_indices(int n) {
	vector<pair<int, int>> temp;
	for (int i = 0; i < n; i++) {
		temp.push_back(make_pair(i, sequence[i]));
	}
	sort(temp.begin(), temp.end(), comp1);

	k = 0;
	indices[0] = 0;
	for (int i = 1; i < n; i++) {
		if (temp[i].first == temp[i - 1].first) indices[temp[i].first] = k;
		else indices[temp[i].first] = ++k;
	}
	n1 = n;
	n = k;
	k = 1;
}

int func(int n, int m) {


	//vector<int> indices(n, 0);
	indices = new int[n];
	for (int i = 0; i < n; i++) {
		indices[i] = 0;
	}
	get_indices(n);

	while (k < n)
		k *= 2;
	n = k;

	//linetree.resize(m + 1, vector<int>(4*n, 0));
	linetree = new int* [m + 1];
	for (int i = 0; i < m + 1; i++) {
		linetree[i] = new int[2 * n];
	}
	for (int i = 0; i < m + 1; i++) {
		for (int j = 0; j < 2 * n; j++) {
			linetree[i][j] = 0;
		}
	}

	int max1 = 0, max2 = 0;
	for (int i = 0; i < n1; i++) {

		for (int j = m; j >= 0; j--) {
			if (j == 0) {
				max1 = 0;
				max2 = get_max(j, 1, 0, n - 1, 0, indices[i] - 1);
			}
			else {
				max1 = get_max(j - 1, 1, 0, n - 1, indices[i], n - 1);
				max2 = get_max(j, 1, 0, n - 1, 0, indices[i] - 1);
			}
			update(j, n + indices[i], max(max1, max2) + 1);
		}
	}

	return get_max(m, 1, 0, n - 1, 0, n - 1);

}


int main() {
	int n = 0;
	int m = 0;
	in >> n;
	//vector<int> sequence(n, 0);

	sequence = new int[n];

	for (int i = 0; i < n; i++) {

		in >> sequence[i];


	}
	out << func(n, m);

	return 0;
}