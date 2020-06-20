#include<iostream>
#include<fstream>
#include<vector>
#include<algorithm>
#include <set>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

typedef long long big;

int n;
big min_fine = 0;
big start_time = 0;
big fine = 0;
vector<bool> used;
vector<int> arrival_time;
vector<int> lead_time;
vector<vector<int>> result;
vector<int> tasks;

void brute_force(int task, big start_time, big fine, vector<int> tasks, vector<bool> used) {

	used[task] = true;
	tasks.push_back(task);

	if (start_time < arrival_time[task]) {
		start_time = arrival_time[task];
	}
	fine += start_time - arrival_time[task];
	start_time += lead_time[task];

	big temp_fine = fine;
	for (int i = 0; i < n; i++) {
		if (start_time > arrival_time[i] && used[i] == false) {
			temp_fine += start_time - arrival_time[i];
		}
	}
	int size = tasks.size();
	bool continue_ = (fine <= min_fine && temp_fine <= min_fine);
	if (continue_ && size < n) {

		for (int i = 0; i < n; i++) {
			if (used[i] == false) {
				brute_force(i, start_time, fine, tasks, used);
			}
		}
	}
	else if (continue_ && min_fine == fine) {

		result.push_back(tasks);
	}
	else if (continue_ && min_fine > fine) {
		result.clear();
		min_fine = fine;
		result.push_back(tasks);
	}

}


int main() {

	in >> n;
	arrival_time.resize(n + 1, 0);
	lead_time.resize(n + 1, 0);

	set <pair<pair<int, int>, int>> s;


	for (int i = 0; i < n; i++) {
		in >> arrival_time[i] >> lead_time[i];
		s.insert({ { arrival_time[i],lead_time[i] },i });
	}
	for (auto i = s.begin(); i != s.end(); ++i) {
		int task = (*i).second;
		if (start_time < arrival_time[task]) {
			start_time = arrival_time[task];
		}
		min_fine += start_time - arrival_time[task];
		start_time += lead_time[task];
	}

	for (int i = 0; i < n; i++) {
		used.clear();
		used.resize(n, false);
		tasks.clear();
		start_time = 0;
		fine = 0;
		brute_force(i, start_time, fine, tasks, used);

	}

	out << min_fine << endl;
	for (int i = 0; i < (int)result.size() - 1; i++) {
		for (int j = 0; j < n - 1; j++) {
			out << result[i][j]+1 << " ";
		}
		out << result[i][n - 1]+1 << endl;
	}
	for (int j = 0; j < n - 1; j++) {
		out << result[result.size() - 1][j]+1 << " ";
	}
	out << result[result.size() - 1][n - 1]+1;
	return 0;
}