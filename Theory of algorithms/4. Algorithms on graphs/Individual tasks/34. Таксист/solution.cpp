#include <iostream>
#include <fstream>
#include <vector>
#include <numeric>
#include <queue>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");
int n;
void pathLength(int u,vector<int> &length, vector<vector<pair<int, int>>> &graph) {

	priority_queue< pair<int, int>, vector< pair<int, int> >, greater< pair<int, int> > > queue;

	for (int i = 1; i < n + 1; i++) {
		length[i] = numeric_limits<int>::max();
	}

	length[u] = 0;
	queue.push(make_pair(0, u));

	while (!queue.empty()) {

		pair<int, int> u = queue.top();
		queue.pop();

		if (u.first > length[u.second]) continue;

		for (int i = 0; i < graph[u.second].size(); i++) {
			int v = graph[u.second][i].second, len = graph[u.second][i].first;
			if (length[v] > length[u.second] + len) {
				length[v] = length[u.second] + len;
				queue.push(make_pair(length[v], v));
			}
		}
	}
}

int main() {

	int m, k;
	in >> n >> m >> k;
	int a1, a2, l;
	vector<vector<pair<int, int>>> graph(n + 1, vector <pair<int, int>>());
	vector<vector<pair<int, int>>> tgraph(n + 1, vector <pair<int, int>>());
	vector<pair<int, int>> temp(m+1);
	vector<int> tempL(m + 1,0);
	vector<int> pathLength_1N(n + 1, 0);
	vector<int> pathLength_N1(n + 1, 0);
	vector<int> roads;
	
	for (int i = 0; i < m; i++) {
		in >> a1 >> a2 >> l;
		graph[a1].push_back(make_pair(l, a2));
		tgraph[a2].push_back(make_pair(l, a1));
		temp[i + 1] = { a1,a2 };
		tempL[i + 1] = l;
	}
	
	pathLength(1, pathLength_1N, graph);
	int shortcutLength = pathLength_1N[n];
	pathLength(n, pathLength_N1, tgraph);
	
	
	for (int i = 1; i < m + 1; i++) {
		
		if (pathLength_1N[temp[i].first] + pathLength_N1[temp[i].second] + tempL[i] <= shortcutLength + k
			&& pathLength_1N[temp[i].first] != numeric_limits<int>::max()
			&& pathLength_N1[temp[i].second] != numeric_limits<int>::max()) {
			roads.push_back(i);
		}
	}
	out << roads.size() << endl;
	for (int i = 0; i < roads.size(); i++) {
		out << roads[i] << endl;
	}
	return 0;
}