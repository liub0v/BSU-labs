#include <iostream>
#include <fstream>
#include <vector>
#include<set>
#include<algorithm>
using namespace std;

ifstream in("input.in");
ofstream out("output.out");

int n;
bool dicotyledonous;
int quantity1 = 0;
int quantity2 = 0;
vector<vector<int>> graph;
vector<int> color;
vector<int> comonent;
vector<int> group1;
vector<int> group2;
vector<int> used;
vector<vector<int>> components;
multiset < pair<pair<int, int>, pair<int, int>>, greater<pair<pair<int, int>, pair<int, int>>>> s;
vector<int> result;

void dfs(int v)
{
	for (int u = 0; u < (int)graph[v].size(); ++u)
		if (color[graph[v][u]] == 0)
		{
			color[graph[v][u]] = 3 - color[v];
			dfs(graph[v][u]);
		}
		else if (color[graph[v][u]] == color[v])
			dicotyledonous = false;
}
bool isDicotyledonous() {
	color.resize(n + 1, 0);
	dicotyledonous = true;
	int counter_componets = 0;
	for (int i = 1; i <= n; ++i) {

		if (color[i] == 0)
		{
			counter_componets++;
			color[i] = 1;
			dfs(i);
		}
	}
	return dicotyledonous;
}
void dfsCopm(int v) {
	for (int u = 0; u < (int)graph[v].size(); ++u)
	{
		if (comonent[graph[v][u]] == 0)
		{
			comonent[graph[v][u]] = 3 - comonent[v];
			used[graph[v][u]] = true;
			dfsCopm(graph[v][u]);
		}
	}
}
void componentization() {
	comonent.resize(n + 1, 0);
	used.resize(n + 1, 0);
	for (int i = 1; i <= n; ++i) {
		if (!used[i]) {
			if (comonent[i] == 0){
				used[i] = true;
				comonent[i] = 1;
				dfsCopm(i);
				components.push_back(comonent);
				comonent.clear();
				comonent.resize(n + 1, 0);
			}
		}
	}
}


int main() {
	
	/*for (int i = 0; i < 13; i++) {
		for (int i = 0; i < 13; i++)
			out << 1 << " ";
		out << endl;
	}*/
	in >> n;
	graph.resize(n + 1, vector<int>());
	int temp = 0;
	for (int i = 1; i < n + 1; i++) {
		for (int j = 1; j < n + 1; j++) {
			in >> temp;
			if (temp == 0 && i != j) {
				graph[i].push_back(j);
			}
		}
	}
	/*for (int i = 1; i < n + 1; i++) {
		out << endl << i << ": ";
		for (int j = 0; j < graph[i].size(); j++) {

			out << graph[i][j] << " ";

		}
	}*/
	
	if (!isDicotyledonous()) {
		out << "NO";
		return 0;
	}
	//components
	componentization();
	/*out << "components:\n";
	for (int i = 0; i < components.size(); i++) {
		out << (i+1) << ":";
		for (int j = 1; j < components[i].size(); j++) {

			out << components[i][j] << " ";

		}
		out << endl;
	}*/
	//s
	for (int i = 0; i < (int)components.size(); ++i)
	{
		int gr1 = 0;
		int gr2 = 0;
		for (int j = 1; j < (int)components[i].size(); j++)
		{
			if (components[i][j] == 1) {
				gr1++;
			}
			else if (components[i][j] == 2) {
				gr2++;
			}
			
		}
		int index = i + 1;
		if (gr1 >= gr2) {
			s.insert({ {gr1,index},{gr2,-index} });
		}
		else {

			s.insert({ {gr2,-index},{gr1,index} });
		}
		
	}
	/*int cntr = 1;
	out << "s:\n";
	for (auto i = s.begin(); i != s.end(); i++) {
		out <<cntr++<<": " << (*i).first.first << " "
			<< (*i).first.second << " "
			<< (*i).second.first << " "
			<< (*i).second.second << endl;

	}*/
	//quantity and group
	for (auto i = s.begin(); i != s.end(); i++)
	{

		if (quantity1 <= quantity2) {
			group1.push_back((*i).first.second);
			group2.push_back((*i).second.second);
			quantity1 += (*i).first.first;
			quantity2 += (*i).second.first;
		}
		else {
			group2.push_back((*i).first.second);
			group1.push_back((*i).second.second);
			quantity2 += (*i).first.first;
			quantity1 += (*i).second.first;
		}

	}
	/*out << "group1:";
	for (int i = 0; i < group1.size(); i++) {
		out << group1[i] << " ";
	}
	out << endl << quantity1 << endl;
	out << "group2:";
	for (int i = 0; i < group2.size(); i++) {
		out << group2[i] << " ";
	}
	out << endl << quantity2 << endl;*/
	if (quantity1 > 2 * quantity2 || quantity2 > 2 * quantity1) {
		out << "NO";
		return 0;
	}
	if (quantity1 >= quantity2) {

		for (int i = 0; i < (int)group1.size(); i++) {
			int type = 0;
			if (group1[i] > 0) {
				type = 1;
			}
			else {
				type = 2;
			}
			int index = abs(group1[i]) - 1;
			for (int j = 0; j < (int)components[index].size(); j++) {
				if (components[index][j] == type) {
					result.push_back(j);
				}
			}
		}

	}
	else {
		for (int i = 0; i < (int)group2.size(); i++) {
			int type = 0;
			if (group2[i] > 0) {
				type = 1;
			}
			else {
				type = 2;
			}
			int index = abs(group2[i]) - 1;
			for (int j = 0; j < (int)components[index].size(); j++) {
				if (components[index][j] == type) {
					result.push_back(j);
				}
			}
		}

	}
	sort(result.begin(), result.end());
	out << "YES" << endl;
	for (int i : result) {
		out << i << " ";
	}
	
	
	return 0;
}