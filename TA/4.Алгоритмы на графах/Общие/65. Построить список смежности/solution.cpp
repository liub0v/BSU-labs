#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int main()
{
	int x, y;
	ifstream in("input.txt");
	int n = 0;//вершины
	int m = 0;//ребра
	int counter = 1;
	ofstream out;
	out.open("output.txt");
	in >> n >> m;
	vector<vector<int>> vec(n + 1,vector<int>(1));
	while (counter <= m)
	{
		in >> x >> y;
		vec[x][0]++;
		vec[y][0]++;
		vec[x].push_back(y);
		vec[y].push_back(x);
		counter++;
	}

	for (int i = 1; i <= n; i++)
	{
		for (int j : vec[i])
			out << j << " ";
		out << endl;

	}
	return 0;
	
}