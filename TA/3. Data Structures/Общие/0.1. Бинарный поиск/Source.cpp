#include<iostream>
#include <math.h>
using namespace std;


bool Binary_Search(int a[], int x) {

	int l = 0;
	int r = _msize(a)/sizeof(int);
	int m = 0;

	while (l < r) {
		m = floor((l + r) / 2);
		if (x == a[m]) {
			return true;
		}
		else if(x < a[m]) {
			r = m;
		}
		else {
			l = m + 1;
		}
	}
	return false;
}

int Lower_Bound(int a[], int x) {
	int l = 0;
	int r= _msize(a) / sizeof(int);
	int m = 0;
	while (l < r) {
		m=floor((l + r) / 2);
		if (x <= a[m])
			r = m;
		else {
			l = m + 1;
		}
	}
	return l;

}
int Upper_Bound(int a[], int x) {
	int l = 0;
	int r = _msize(a) / sizeof(int);
	int m = 0;
	if (x == a[r-1])
		return r;
	while (l < r) {
		m = floor((l + r) / 2);
		if (x < a[m])
			r = m;
		else {
			l = m + 1;
		}
	}
	return l;
}

void main() {

	int size = 0;
	int k = 0;
	int b = 0;
	int l = 0;
	int r = 0;
	int x = 0;
	cin >> size;
	int *numbers=new int[size];
	for (int i = 0; i < size; i++) {

		cin >> numbers[i];
	}
	cin >> k;
	for (int i = 0; i < k; i++) {

		cin >> x;
		b = Binary_Search(numbers, x);
		l = Lower_Bound(numbers, x);
		r = Upper_Bound(numbers, x);
		
		cout << b << " " << l << " " << r << endl;
	}

	delete[]numbers;
}