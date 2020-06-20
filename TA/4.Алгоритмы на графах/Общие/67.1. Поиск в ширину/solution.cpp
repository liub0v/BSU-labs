#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int main()
{
	int x;
	ifstream in("input.txt");
	ofstream out("output.txt");
	int n = 0;//вершины
	int counter = 1;
	in >> n;
	vector<vector<int>> vec(n, vector<int>());
	vector<int> result(n);
	vector<bool> isVisited(n);
	queue<int> q;
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			in >> x;
			if (x == 1)
				vec[i].push_back(j+1);
		}
	}
	for (int i = 0; i < n; i++)
	{
		if (!isVisited[i])
		{
			q.push(i);
			isVisited[i] = true;
			result[i] = counter++;
			while (!q.empty())
			{
				;
				for (int j : vec[q.front()])
				{
					if (!isVisited[j-1])
					{
						q.push(j-1);
						result[j-1] = counter++;
						isVisited[j-1] = true;
					}
				}
		
				q.pop();
			}
		}
	}
	for (int i : result)
		out << i << " ";
	return 0;
}