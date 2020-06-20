#include <iostream>
#include <vector>
#include <algorithm>
#include <list>
#include <fstream>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");
int main() {

	int n = 0;
	float x, y = 0;
	int sh = 0;
	in >> n;
	list<int> intersections;
	for (int i = 0; i < n; i++)
	{
		in >> x >> y >> sh; 

		intersections.push_back(sh);

	}
	
	while (!intersections.empty() || intersections.size() != 1) {
		bool trigger = false;
		int temp1 = 0;
		for (auto iter = intersections.begin(); iter != intersections.end(); iter++)
		{
			
			if(*iter==temp1){
				
				intersections.erase(prev(iter));
				temp1 = *iter;
				advance(iter, 1);
				intersections.erase(prev(iter));
				trigger = true;
				break;
			}
			else {
				temp1 = *iter;
			}
			
		}
		if (intersections.size() == 0 || intersections.size() == 1) {
			out << "Yes";
			break;
		}
		if (trigger==false) {
			out << "No";
			break;
		}
	}
	

	return 0;
}