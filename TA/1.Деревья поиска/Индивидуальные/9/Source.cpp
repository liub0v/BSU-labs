#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

ifstream in("in.txt");
ofstream out("out.txt");
int min2 = -1;
int min1 = -1;
int counter1 = 0;
int counter2 = 0;
int counter3 = 0;
int counter4 = 0;

class Node {
public:
	Node(int key) : Key(key) {}

	int Key;
	Node* Left = nullptr;
	Node* Right = nullptr;
	bool isMSL = false;
	int hight = 0;

	//int getHight();
	int semipath_length();
	//void is_MSL();
};
class Tree {
public:

	int max = 0;
	Tree() :Root(0) {}
	~Tree() {
		DestroyNode(Root);
	}
	void Insert(int x);
	void InOrderTraversal_FindMin2(Node* node);
	void printPreOrderTraversal();
	void PostOrderTraversal_FindMSL(Node* root);
	void printPostOrderTraversal();
	Node* deleteKey(Node* node, int x);
	Node* findMin(Node* node);
	void DeleteKey(int x);
	Node* length_MSL(Node* node, int max);
	void PreOrderTraversal(Node* root);
	void is_MSL(Node* node);
private:
	static void DestroyNode(Node* node) {
		if (node) {
			DestroyNode(node->Left);
			DestroyNode(node->Right);
			delete node;
		}
	}
private:
	Node* Root;
};


void Tree::Insert(int x) {
	Node** cur = &Root;
	while (*cur) {
		Node& node = **cur;
		if (x < node.Key) {
			cur = &node.Left;
		}
		else if (x > node.Key) {
			cur = &node.Right;
		}
		else {
			return;
		}
	}
	*cur = new Node(x);
}
Node* Tree::deleteKey(Node* node, int x) {
	if (node == nullptr)
		return nullptr;
	if (x < node->Key) {
		node->Left = deleteKey(node->Left, x);
		return node;
	}
	else if (x > node->Key) {
		node->Right = deleteKey(node->Right, x);
		return node;
	}

	if (node->Left == nullptr)
		return node->Right;
	else if (node->Right == nullptr) {
		return node->Left;
	}
	else {
		int minKey = findMin(node->Right)->Key;
		node->Key = minKey;
		node->Right = deleteKey(node->Right, minKey);
		return node;
	}


}
Node* Tree::findMin(Node* node)
{
	if (node->Left != nullptr) {
		return findMin(node->Left);
	}
	else {
		return node;
	}
}
void Tree::DeleteKey(int x) {
	Root = deleteKey(Root, x);
}

void Tree::PostOrderTraversal_FindMSL(Node* root)
{
	if (root == nullptr)
	{
		return;
	}
	PostOrderTraversal_FindMSL(root->Left);
	PostOrderTraversal_FindMSL(root->Right);
	//out << root->getHight()<<" "<<root->Key<<" "<<root->semipath_length()<<"\n ";
	if (root->semipath_length() > max) {
		max = root->semipath_length();
	}
}

void getHight(Node* node) {
	if (!node->Left && !node->Right) {
		node->hight = 0;
		return;
	}
	if (!node->Right) {
		getHight(node->Left);
		node->hight = node->Left->hight + 1;
		return;
	}
	if (!node->Left) {
		getHight(node->Right);
		node->hight = node->Right->hight + 1;
		return;
	}
	getHight(node->Left);
	getHight(node->Right);
	node->hight = max(node->Left->hight, node->Right->hight) + 1;
}


int Node::semipath_length() {
	if (this->Left || this->Right)
	{
		if (this->Left == nullptr)
			return this->Right->hight + 1;
		else if (this->Right == nullptr)
			return this->Left->hight + 1;
		else
			return this->Right->hight + this->Left->hight + 2;
	}
	else return 0;
}

Node* Tree::length_MSL(Node* node, int max) {

	if (node->semipath_length() == max)
		return node;
	else return nullptr;


}

void Tree::printPostOrderTraversal()
{
	getHight(Root);
	PostOrderTraversal_FindMSL(Root);
}

void Tree::printPreOrderTraversal()
{
	is_MSL(Root);
	InOrderTraversal_FindMin2(Root);
	this->DeleteKey(min2);
	PreOrderTraversal(Root);
}

void Tree::PreOrderTraversal(Node* root)
{
	if (root == nullptr)
	{
		return;
	}
	out << root->Key << "\n";
	PreOrderTraversal(root->Left);
	PreOrderTraversal(root->Right);
}

void Tree::InOrderTraversal_FindMin2(Node* root)
{
	if (root == nullptr)
	{
		return;
	}
	InOrderTraversal_FindMin2(root->Left);
	if (root->isMSL == true)
	{
		counter1++;
	}
	if (counter1 == 2) {
		min2 = root->Key;
		return;
	}
	InOrderTraversal_FindMin2(root->Right);
}

void Tree::is_MSL(Node* root) {
	if (root == nullptr)
	{
		return;
	}
	Node* msl_root = length_MSL(root, max);
	if (msl_root) {
		if (counter2 > 1)
			return;
		counter2++;
		msl_root->isMSL = true;
		if (msl_root->Left) {
			msl_root->Left->isMSL = true;
			is_MSL(msl_root->Left);
		}
		else if (msl_root->Right) {
			msl_root->Right->isMSL = true;
			is_MSL(msl_root->Right);
		}
	}
	else {
		if (root->Left && root->Right)
			if (root->Left->hight >= root->Right->hight) {
				root->Left->isMSL = true;
				counter3++;
				is_MSL(root->Left);
			}
			else
			{
				root->Right->isMSL = true;
				counter4++;
				if (counter3 > 1 || counter4 > 1)
					return;
				is_MSL(root->Right);
			}
		else if (root->Left) {
			counter3++;
			root->Left->isMSL = true;
			is_MSL(root->Left);
		}
		else if (root->Right) {
			counter4++;
			root->Right->isMSL = true;
			if (counter3 > 1 || counter4 > 1)
				return;
			is_MSL(root->Right);
		}
		else
			return;
	}
}


void main()
{
	Tree tree;
	int x = 0;
	while (in >> x) {
		tree.Insert(x);
	}
	tree.printPostOrderTraversal();
	tree.printPreOrderTraversal();
}