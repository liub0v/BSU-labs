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
class Tree{
public:
	Tree():Root(0){}
	~Tree() {
		DestroyNode(Root);
	}
	void Insert(int x);
	void PreOrderTraversal(Node *node);
	void printPreOrderTraversal();

private:
	static void DestroyNode(Node *node){
		if (node){
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
	int x = 0;
	while (in >> x)
	{
		tree.Insert(x);
	}
	tree.printPreOrderTraversal();


}