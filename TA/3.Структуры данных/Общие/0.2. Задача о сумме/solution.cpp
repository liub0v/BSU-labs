#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include<algorithm>
using namespace std;

//Дерево отрезков

//ifstream in("input.txt");
//ofstream out("output.txt");

long long findSum(vector<long long>& t, int v, int tl, int tr,int l, int r) {
	
	if (l == tl && r == tr) {
		return t[v];
	}
	int m = (tl + tr) / 2;
	if (r <= m) {
		return findSum(t, 2 * v, tl, m, l, r);
	}
	if (m <= l) {
		return  findSum( t,2 * v+1, m, tr, l, r);
	}
	return (
		findSum( t,2 * v, tl, m, l, m)+
		findSum( t,2 * v + 1, m, tr, m, r) 
		);
}


void add(vector<long long>& t,int v, int tl, int tr ,int index, int x) {
	if (tr == tl + 1) {
		t[v] += x;
		return;
	}
	int m = (tl + tr) / 2;
	if (index < m) {
		add(t, 2 * v, tl, m, index, x);
	}else add(t,2 * v+1, m, tr, index, x);
	t[v] = t[2 * v] + t[2 * v + 1];
	
}

void build(vector<long long>& t,vector<int> &a, int v,int tl, int tr ) {

	if (tr == tl + 1) {
		t[v] = a[tl];
	}
	else {
		int m = (tl + tr) / 2;
		build(t,a,  2 * v, tl, m);
		build(t,a,  2 * v + 1, m, tr);
		t[v] = t[2 * v] + t[2 * v + 1];
	}
}
int main()
{
	int n = 0;
	int q = 0;
	string str = "";
	int index = 0;
	int x = 0;
	int l = 0;
	int r = 0;
	cin >> n;
	vector<int> a(n, 0);
	vector<long long> t(4 * n, 0);
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	build(t,a, 1, 0, n);
	cin >> q;
	for (int i = 0; i < q; i++) {
		cin >> str;
		if (str == "FindSum") {
			cin >> l;
			cin >> r;
			cout << findSum(t,1, 0, n, l, r) << endl;
		}
		else if (str == "Add") {
			cin >> index;
			cin >> x;
			add(t,1, 0, n, index, x);
		}
	}
	
	return 0;
}