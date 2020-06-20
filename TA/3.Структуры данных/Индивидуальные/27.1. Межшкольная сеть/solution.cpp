#include<iostream>
#include<fstream>
#include<vector>
#include <queue>
using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

int n;

bool bfs(vector<vector<int>> g, int s,int t) {
	if (s == t)
		return false;
	queue<int> q;
	q.push(s);
	vector<bool> used(n,false);
	used[s] = true;
	while (!q.empty()) {
		int v = q.front();
		q.pop();
		for (int i = 0; i < n; i++) {
			if (!used[i] && g[v][i]) {
				used[i] = true;
				q.push(i);
			}
		}
	}
	return used[t];
}
void dominantSet(vector<vector<int>> &graph, vector<int> &dominant_set) {
	vector<int> used(n, false);
	for (int s = 0; s < n; s++) {
		if (used[s])
			continue;
		for (int t = 0; t < n;t++) {
			if (bfs(graph, s, t)) {
				used[t] = true;
				dominant_set.erase(remove(dominant_set.begin(), dominant_set.end(), t), dominant_set.end());
			}
		}
		if (!used[s]) 
			dominant_set.push_back(s);
	}
}

int main() {

	int temp;
	in >> n;
	vector<vector<int>> graph(n, vector <int>(n, 0));
	vector<vector<int>> transposed_graph(n, vector<int>(n));
	vector<int> ds1;
	vector<int> ds2;
	
	for (int i = 0; i < n; i++) {

		while (in >> temp) {
			if (!temp)
				break;
			graph[i][temp - 1] = 1;
		}
	}
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			transposed_graph[i][j] = graph[j][i];
		}
	}
	dominantSet(graph, ds1);
	out << ds1.size() << endl;
	dominantSet(transposed_graph, ds2);
	if (ds1.size() == 1 && ds2.size() == 1
		&& ds1[0] == ds2[0])
		out<< 0;
	else out << max(ds1.size(), ds2.size());
	
	return 0;
}