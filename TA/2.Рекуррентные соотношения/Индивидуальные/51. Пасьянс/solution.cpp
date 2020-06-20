#include<iostream>
#include<fstream>
#include<vector>
#include<algorithm>


using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

int get_min(vector<int> &sequence,int n) {
	vector<vector<int>> matrix(n+1, vector<int>(n+1, numeric_limits<int>::max()));
	
	for (int i = 1; i < n+1; i++) {
		for (int j = i; j>=1; j--) {
			if(i==j)
				matrix[i][j] = 0;
			for (int k = j; k < i; k++) {
				matrix[j][i] = min(matrix[j][i],
					matrix[j][k] + matrix[k + 1][i] + abs(sequence[i] - sequence[k]));
			}

		}
	}
	return matrix[1][n];
	
}

int main() {

	int n = 0;
	int index = 0;
	in >> n;
	vector<int> sequence(n+1, 0);
	for (int i = 1; i < n+1 ; i++) {
		in >> index;
		sequence[index] = i;
	}
	out << get_min(sequence, n);

	return 0;
}