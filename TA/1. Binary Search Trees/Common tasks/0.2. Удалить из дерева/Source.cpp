#include <iostream>
#include <fstream>

using namespace std;

ifstream in("input.txt");
ofstream out("output.txt");

class Node {
public:
	Node(int key) : Key(key) {}

	int Key;
	Node* Left = nullptr;
	Node* Right = nullptr;

};
class Tree {
public:
	Tree() :Root(0) {}
	~Tree() {
		DestroyNode(Root);
	}
	void Insert(int x);
	void PreOrderTraversal(Node* node);
	void printPreOrderTraversal();
	Node* deleteKey(Node* node, int x);
	Node* findMin(Node* node);
	void DeleteKey(int x);
	

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
Node* Tree::deleteKey(Node* node, int x){
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
	else if (node->Right == nullptr){
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

void Tree:: DeleteKey(int x) {
	Root = deleteKey(Root, x);
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
void Tree::printPreOrderTraversal()
{
	PreOrderTraversal(Root);
}



void main()
{
	Tree tree;
	int Key = 0;
	int x = 0;
	in >> Key;
	while (in >> x)
	{
		tree.Insert(x);
	}
	tree.DeleteKey(Key);
	tree.printPreOrderTraversal();


}