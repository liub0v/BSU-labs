#include<iostream>
#include<vector>
#include <fstream>
#include <algorithm>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

int main() {

	int n = 0;
	int counter = 0;
	in >> n;
	vector<int> sequence(n+1);
	for (int i = 1; i < n+1; i++) {
		in >> sequence[i];
	}

	for (int i = 1; i < n/2+1; i++) {
		if (sequence[i] <= sequence[2 * i]){
			if (2 * i + 1 > n) {
				continue;
			}
			else if ((sequence[i] <= sequence[2 * i + 1])) {
				continue;
			}
			else {
				out << "No";
				return 0;
			}
		}
		else {
			out << "No";
			return 0;
		}
		
	}
	out << "Yes";


	return 0;
}
