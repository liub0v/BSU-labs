#include <iostream>
#include <fstream>
#include <vector>
#include <list>

using namespace std;

int main()
{
	int x, y;
	ifstream in("input.txt");
	int n = 0;//вершины
	int counter = 1;
	ofstream out;
	out.open("output.txt");
	in >> n;
	vector<int> p(n);
	while (counter <= n - 1)
	{
		in >> x >> y;
		p[y-1] = x;
		counter++;
	}

	for (int j : p)
		out << j << " ";

	return 0;
}