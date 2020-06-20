#include <iostream>
#include <vector>
#include <algorithm>
#include <ctime>

using namespace std;

void main() {

	int n = 0;
	int index3 = 0;
	cin >> n;
	vector<int> sequence(n + 1, -1);
	vector<int> vResult;
	vector<int> A(n + 1, 0);
	for (int i = 1; i < n + 1; i++) {
		cin >> A[i];
	
	}
	vector<int> temp(n + 1, 0);

	
	
	int result = 0;
	for (int i = n; i >= 1; i--) {
		int index = i;
		int max = 0;
		if (i == n) {
			temp[n] = 1;
			//sequence[n] = 1;
		}
		else {
			for (int j = i + 1; j < n + 1; j++) {
				if (A[i] != 0) {
					if (A[j] % A[i] == 0) {
						if (temp[j] > max) {
							max = temp[j];
							index = j;
							sequence[i] = j;
						}
					}
				}
			}
			temp[i] = temp[index] + 1;
		}
	}
	/*for (int i = 1; i < n + 1; i++) {
		cout << temp[i]<<" ";
	}*/
	for (int i = 1; i < n + 1; i++) {
		if (temp[i] > result) {
			result = temp[i];
			index3 = i;
		}
	}
	cout << result << endl;
	/*for(int i = 1; i < n + 1; i++) {
		cout << sequence[i] << " ";
	}*/

	while (index3!=-1)
	{
		vResult.push_back(index3);
		index3 = sequence[index3];

	}
	for (int i = 0; i < vResult.size(); i++) {
		cout << vResult[i] << " ";
	}
	
}