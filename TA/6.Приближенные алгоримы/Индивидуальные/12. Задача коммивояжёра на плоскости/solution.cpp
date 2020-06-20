#include <iostream>
#include <vector>
#include <queue>
#include <fstream>
#include<iomanip>
#include <cmath>
using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");
int n;
vector<vector<double>> g;
vector<pair<double, double>> coord;
vector<vector<int>> mst;
vector<int> h;
vector<bool> used_;

double d(int u, int v) {

	return sqrt(pow(coord[u].first - coord[v].first, 2) +
		pow(coord[u].second - coord[v].second, 2));
}
void dfs(int v) {
	h.push_back(v);
	used_[v] = true;
	for (int u : mst[v]) {
		
		if (!used_[u]) {
			dfs(u);	
		}
	}
}
int main() {
	in >> n;
	int x, y;
	g.resize(n, vector<double>(n, 0));
	used_.resize(n, false);
	for (int i = 0; i < n; i++) {
		in >> x >> y;
		coord.push_back({ x,y });
	}
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			
			g[i][j] = d(i, j);

		}
	}
	out << fixed;
	out << setprecision(3);
	/*for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {

			
			out<<g[i][j] << " ";

		}
		out << endl;
	}*/
	//mst
	vector<bool> used(n, false);
	
	vector<pair<pair<int,int>,double>> dist(n, 
		{ {-1,-1}, numeric_limits<double>::max() });
	mst.resize(n, vector<int>());
	dist[0].second = 0;
	for (int i = 0; i < n; ++i) {
		double min_dist = numeric_limits<int>::max();
		int u = -1;
		for (int j = 0; j < n; ++j) {
			if (!used[j] &&  dist[j].second < min_dist) {
				min_dist = dist[j].second;
				u = j;
			}
		}
		used[u] = true;
		for (int v = 0; v < n; ++v) {
			//dist[v] = min((double)dist[v], g[u][v]);
			if (g[u][v] < dist[v].second) {
				dist[v].second = g[u][v];
				if(dist[v].second==0){

					int u1= dist[v].first.second;
					int u2= dist[v].first.first;

					if (u1 != -1 && u2 != -1) {
						mst[u1].push_back(u2);
						mst[u2].push_back(u1);
					}
				}
				dist[v].first.second = v;
				dist[v].first.first = u;
				
			}
		}
		/*out << " dist" << i << ": " << endl;
		for (int i = 0; i < n; i++) {
			out << "{" <<dist[i].first.first << ","
				<< dist[i].first.second << "} "
				<< dist[i].second << endl;
		}*/
	
	}

	/*for (int i = 0; i < n; i++) {
		out << i << ": ";
		for (int j = 0; j < (int)mst[i].size(); j++) {
			out << mst[i][j] << " ";
		}
		out << endl;
	}*/
	double sum;
	double min_sum= numeric_limits<double>::max();
	vector<int> min_h;
	min_h = h;
	for (int i = 0; i < n; i++) {

		dfs(i);
		for (int i = 0; i < n; i++) {
			used_[i] = false;
		}
		sum = 0;
		for (int i = 0; i < n; i++) {
			if (i + 1 == n) {
				sum += g[h[n - 1]][h[0]];
			}
			else {
				sum += g[h[i]][h[i + 1]];
			}
		}
		if (sum < min_sum) {
			min_sum = sum;
			min_h = h;
		}
		h.clear();
	}
	out << min_sum << endl;
	for (int i = 0; i < n; i++) {
		out << min_h[i]+1 << " ";
	}

	return 0;
}