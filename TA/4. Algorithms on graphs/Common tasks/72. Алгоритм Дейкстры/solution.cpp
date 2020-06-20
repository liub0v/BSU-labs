#include <iostream>
#include <fstream>
#include <vector>
#include <set>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");
typedef long long big; 
int main() {
	big n, m;
	in >> n >> m;
	big v, u, w;
	vector < vector < pair<big, big> > > g(n);
	while (m) {
		in >> v >> u >> w;
		g[v - 1].push_back(make_pair(u - 1, w));
		g[u - 1].push_back(make_pair(v - 1, w));
		m--;
	}
	
	vector<big> d(n, numeric_limits<big>::max());
	vector<bool> used(n);

	d[0] = 0;
	set<pair<big, big>> q;
	q.insert(make_pair(0, 0));

	while (!q.empty()) {
		set<pair<big, big>>::iterator it = q.begin();
		int v = (*it).second;
		q.erase(it);
		used[v] = true;
		for (auto i : g[v]) {
			int to = i.first,
				len = i.second;
			if (!used[to] && d[v] + len < d[to]) {
				q.erase(make_pair(d[to], to));
				d[to] = d[v] + len;
				q.insert(make_pair(d[to], to));
			}
		}
	}
	out << d[n - 1];
}