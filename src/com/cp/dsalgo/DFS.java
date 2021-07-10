package com.cp.dsalgo;

import java.util.Stack;

public class DFS {
	
	Tree tree;
	
	public DFS(Tree tree) {
		this.tree =tree;
	}
	
	public void perform() {
		perform(tree.root, tree.root);
	}
	
	void perform(Node node, Node parent) {
		
		for (Node adjNode : node.adjSet) {
	        if (adjNode != parent)
	        	perform(adjNode, node);
	    }
	}
	
	void performIterative(Node node, Node parent) {
		
		Stack<Node> stack = new Stack<Node>();
		
		stack.push(node);
		node.parent = parent;
		
		while (!stack.isEmpty()) {
			
			Node currentNode = stack.pop();
			
			// do stuff here
			System.out.println(currentNode.number);
			
			for (Node adjNode : currentNode.adjSet) {
				adjNode.parent = currentNode;
		        if (adjNode != currentNode.parent)
		        	stack.push(adjNode);
		    }
		}
	}
	
	public static void main(String[] args) {
		
		Tree tree = new Tree(0);
		
		tree.addEdge(0, 1);
		tree.addEdge(0, 2);
		tree.addEdge(1, 3);
		tree.addEdge(1, 4);
		tree.addEdge(2, 5);
		tree.addEdge(2, 6);
		
		DFS dfs = new DFS(tree);
		
		dfs.performIterative(tree.root, tree.root);
	}

}
