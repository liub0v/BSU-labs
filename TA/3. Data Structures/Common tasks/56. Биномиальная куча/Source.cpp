#include <iostream>
#include <fstream>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

int main() {

	long long n = 0;
	int index = 0;
	bool trigger = false;
	in >> n;
	while (n)
	{
		if ((n % 2)) {
			out << index << endl;
			trigger = true;
		}
		index++;
		n = n / 2;
	}
	if (trigger == false) {
		out << -1;
	}

	return 0;
}
