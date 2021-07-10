package com.cp.dsalgo;

import java.util.*;

public class Tree {
	
	Node root;;
	HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
	
	public Tree(Node root) {
		this.root = root;
	}
	
	public Tree(int rootNodeNumber) {
		root = getOrCreateNode(rootNodeNumber);
	}
	
	private Node getOrCreateNode(int nodeNumber) {
		Node node = nodes.get(nodeNumber);
		if (node == null) {
			node = new Node(nodeNumber, this);
			nodes.put(nodeNumber, node);
		}
		
		return node;
	}
	
	public void addEdge(int u, int v) {
		Node node1 = getOrCreateNode(u);
		Node node2 = getOrCreateNode(v);
		
		node1.addAdjacentNode(node2);
		node2.addAdjacentNode(node1);
	}
	
	Node getNode(int number) {
		return nodes.get(number);
	}
	
	public int size() {
		return nodes.size();
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	
}
