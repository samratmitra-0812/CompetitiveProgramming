package com.cp.dsalgo;

import java.util.HashSet;

class Node {
	
	int number;
	int value;
	HashSet<Node> adjSet = new HashSet<Node>();
	Tree tree;
	
	Node parent;
	int subtreeSize;
	int height;
	
	public Node(int number, Tree tree) {
		this(number, Integer.MIN_VALUE, tree);
	}
	
	public Node(int number, int value, Tree tree) {
		this.number = number;
		this.value = value;
	}
	
	public boolean isLeaf() {
		if (tree.size() == 1) return true;
		return !isRoot() && adjSet.size() == 1;
	}
	
	public boolean isRoot() {
		return this.number == tree.root.number;
	}
	
	public void addAdjacentNode(Node node) {
		adjSet.add(node);
	}
	
	public int countChildren() {
		if (isRoot()) return adjSet.size();
		return adjSet.size() - 1;
	}
}
