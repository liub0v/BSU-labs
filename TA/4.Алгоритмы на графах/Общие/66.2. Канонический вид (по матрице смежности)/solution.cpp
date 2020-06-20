#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int main()
{
	int x, y;
	ifstream in("input.txt");
	ofstream out("output.txt");
	int n = 0;//вершины
	//int m = 0;//ребра
	int counter = 1;
	in >> n;
	vector<vector<int>> vec(n + 1, vector<int>(n + 1, 0));
	vector<int> p(n);
	while (counter <= n)
	{
		for (int i = 0; i < n; i++)
		{
			in >> vec[counter][i];
		}
		counter++;
	}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 0; j <= n; j++)
		{
			if (vec[i][j] == 1)
				p[j] = i;
		}
	}

	
	for (int i : p)
		out << i << " ";
	return 0;

}