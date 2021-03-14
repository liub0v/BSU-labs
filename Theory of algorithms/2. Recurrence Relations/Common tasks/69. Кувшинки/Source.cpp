#include <iostream>
#include <algorithm>
#include <fstream>
#include <vector>
using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");


int func(int n,vector<int> mosquitoes) {

	vector<long long> way(n + 3, -1);
	way[1] = mosquitoes[1];
	way[3] = mosquitoes[1];
	way[3] += mosquitoes[3];
	for (int i = 3; i < n + 1; i++) {
		way[i] = max(way[i - 2], way[i - 3]) + mosquitoes[i];
	}
	return way[n];
}

int main()
{
	int n = 0;
	in >> n;
	vector<int> mosquitoes(n+3,0);
	for (int i = 1; i < n + 1; i++) {
		in >> mosquitoes[i];
		//out << mosquitoes[i]<<" ";
	}
	out << func(n,mosquitoes);
}