#include <iostream>
#include <fstream>
#include <vector>

using namespace std;
int top=1;
void dfs(vector<vector<int>> vec, int v, vector<int> &points)
{
	for (int j = 0; j < vec[v].size(); j++)
	{
		if (vec[v][j] == 1 && points[j] == 0)
		{
			points[j] = top++;
			dfs(vec, j, points);
		}
	}
}
int main()
{
	int x, y;
	ifstream in("input.txt");
	ofstream out("output.txt");
	int n = 0;
	int counter = 0;
	in >> n;
	vector<vector<int>> vec(n, vector<int>(n, 0));
	vector<int> result(n);
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			in>>vec[i][j];
		}
	}
	for (int i = 0; i < n; i++)
	{
		if (result[i]==0)
		{
			result[i] = top++;
			dfs(vec, i, result);
		}
	}
	for (int i : result)
		out << i << " ";
	return 0;
}