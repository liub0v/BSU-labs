#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;

void main()
{
	ifstream in("input.txt");
	ofstream out;
	out.open("output.txt");
	int key = 0;
	long long result = 0;
	set<int> keys;
	while (in >> key)
	{
		keys.insert(key);
	}
	for (int i : keys)
	{
		result += i;
	}
	out << result;
	out.close();

}