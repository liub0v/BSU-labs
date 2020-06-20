#include <iostream>
#include <vector>
#include <fstream>
#include <queue>
#include <set>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");


int main() {

	int n=0;
	in >> n;
	vector<int> duration(n+1,0);
	vector<int> deadline(n+1,0);
	for (int i = 1; i < n+1; i++) {
		in >> duration[i] >> deadline[i];
	}
	
	int m=0;
	in >> m;
	int v, u;
	vector<set<int>> graph(n+1, set<int>());
	vector<vector<int>> ancestors(n + 1, vector<int>());
	for (int i = 0; i < m; i++) {
		in >> v >> u;
		
		graph[v].insert(u);
		ancestors[u].push_back(v);
	}
	set<pair<int, pair<int, int>>> N;
	for (int i = 1; i < n + 1; i++) {
		N.insert(make_pair(graph[i].size(), make_pair(-deadline[i],i)));
	 }
	
	set<int> S;
	for (int i = 1; i < n + 1; i++) {
		S.insert(i);
	}
	int p = 0;
	for (int i = 1; i < n + 1; i++) {
		p += duration[i];
	}
	vector<int> result(n + 1, 0);
	int maxFine = 0;
	int indexMaxFine = 0;
	int c = 0;
	for (int k = n; k > 0; k--) {
		int job = 0;
		if (N.begin()->first == 0) {
			job = N.begin()->second.second;
		}
		S.erase(job);
		N.erase(N.begin());
		result[k] = job;
		
		c += duration[job];
		int  fine = 0;
		int zero = 0;
		fine = max(zero, p - deadline[job]);
		if (fine > maxFine) {
			maxFine = fine;
			indexMaxFine = job;
		}
		p -= duration[job];
		for (int i = 0; i < ancestors[job].size(); i++) {
			int index = ancestors[job][i];
			int  size = graph[index].size();
			pair<int, pair<int, int>> pair1 = { size, {-deadline[index] ,index} };
			auto search = N.find(pair1);
			N.erase(search);
			graph[index].erase(job);
			size-=1;
			pair<int, pair<int, int>> pair2 = { size, {-deadline[index] ,index} };
			N.insert(pair2); 
		}

	}
	if (indexMaxFine == 0) {
		indexMaxFine = n;
	}
	out << indexMaxFine << " " << maxFine << "\n";
	for (int i = 1; i < n; i++) {
		out << result[i] << "\n";
	}
	out << result[n];
	in.close();
	out.close();

}