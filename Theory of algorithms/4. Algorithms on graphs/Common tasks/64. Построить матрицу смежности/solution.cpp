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
	in >> n >> m;
	vector<vector<int>> vec(n + 1, vector<int>(n + 1, 0));
	while (counter <= m)
	{
		in >> x >> y;
		vec[x][y] = 1;
		vec[y][x] = 1;
		counter++;
	}
	
	ofstream out;
	out.open("output.txt");
	
	for (int i = 1; i <= n; i++)
	{
		int size = vec[i].size();
		for (int j = 1; j <size; j++)
			out << vec[i][j] << " ";
		out << endl;

	}
	return 0;
	
}