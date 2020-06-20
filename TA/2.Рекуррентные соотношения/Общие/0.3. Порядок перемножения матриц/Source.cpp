#include <iostream>
#include <fstream>
#include <algorithm>
using namespace std;


int** result;
int* sizes;


int number_of_operations(int s) {

	for(int i=1;i<s;i++)
		for (int j = 0; j < s-i; j++) {
			int m = j + i;
			result[j][m] = 2147483647;
			for (int k = j; k < m; k++) {
				result[j][m] = min(result[j][m],
					result[j][k] + result[k + 1][m]+ sizes[j] * sizes[k + 1] * sizes[m + 1]);
			}
		}
	return result[0][s-1];
}


void main() {

	ifstream in("input.txt");
	ofstream out("output.txt");
	int temp = 0;
	int s = 0;
	in >> s;
	result = new int* [s];
	for (int i = 0; i < s; i++) {
		result[i] = new int[s];
	}
	for (int i = 0; i < s; i++)
		for (int j = 0; j < s; j++)
			result[i][j] = 0;
	sizes = new int[s+1];
	for (int i=0; i < s+1; i++) {
		in >> sizes[i] >> temp;
	}
	sizes[s] = temp;
	/*for (int i=0; i < s+1; i++) {
		out << sizes[i]<<" ";
	}*/

	out << number_of_operations(s);
	

}