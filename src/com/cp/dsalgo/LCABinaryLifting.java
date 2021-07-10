package com.cp.dsalgo;

import java.util.Stack;

public class LCABinaryLifting {
	
	Tree tree;
	int[][] ancestorArray;
	int[] visitTime;
	int[] exitTime;
	int time;
	int log2N;
	
	LCABinaryLifting(Tree tree) {
		this.tree = tree;
		preprocess();
	}
	
	void preprocess() {
		visitTime = new int[tree.size() + 1];
		exitTime = new int[tree.size() + 1];
		log2N = ceilLog2(tree.size());
		ancestorArray = new int[tree.size() + 1][log2N + 1];
		
		int rootNumber = tree.root.number;
		
		dfs(rootNumber, rootNumber);
	}
	
	void dfs(int node, int parent) {
		visitTime[node] = ++time;
		
		ancestorArray[node][0] = parent;   // 2^0 th ancestor of the node is is its parent;
		
		for (int i = 1; i <= log2N; i++)
			ancestorArray[node][i] = ancestorArray[ancestorArray[node][i-1]][i-1];
		
		Node currentNode = tree.getNode(node);
		for (Node adjNode : currentNode.adjSet) {
	        if (adjNode.number != parent)
	            dfs(adjNode.number, currentNode.number);
	    }
		
		exitTime[node] = ++time;
	}
	
	void dfsIterative(Node node, Node parent) {
		
		Stack<Node> stack = new Stack<Node>();
		
		stack.push(node);
		
		ancestorArray[node.number][0] = parent.number;
		
		while(!stack.isEmpty()) {
			
			Node currentNode = stack.pop();
			
			for (int i = 1; i <= log2N; i++)
    			ancestorArray[currentNode.number][i] = ancestorArray[ancestorArray[currentNode.number][i-1]][i-1];
			
			for (Node adjNode : currentNode.adjSet) {
    	        if (adjNode != currentNode.parent) {
    	        	ancestorArray[adjNode.number][0] = currentNode.number;
    	        	stack.push(adjNode);
    	        }
    	    }
		}
	}
	
	// Return true if u is the ancestor of v
	public boolean is_ancestor(int u, int v) {
	    return visitTime[u] <= visitTime[v] && exitTime[u] >= exitTime[v];
	}
	
	// Return true if u is the ancestor of v
	public boolean is_ancestor(Node u, Node v) {
	    return is_ancestor(u.number, v.number);
	}
	
	int lcaNumber(int u, int v) {
		if (u == v)
			return u;
		
	    if (is_ancestor(u, v))
	        return u;
	    
	    if (is_ancestor(v, u))
	        return v;
	    
	    for (int i = log2N; i >= 0; --i) {
	        if (!is_ancestor(ancestorArray[u][i], v))
	            u = ancestorArray[u][i];
	    }
	    
	    return ancestorArray[u][0];
	}
	
	int ceilLog2(int n) {
		return (int)Math.ceil(Math.log10(n)/Math.log10(2));
	}
	
	public static void main(String[] args) {
		
		Tree tree = new Tree(0);
		tree.addEdge(0, 1);
		tree.addEdge(0, 2);
		tree.addEdge(1, 3);
		tree.addEdge(1, 4);
		tree.addEdge(2, 5);
		tree.addEdge(2, 6);
		
		LCABinaryLifting lcabl = new LCABinaryLifting(tree);
		
		for (int i = 0;i < 7;i++) {
			for (int j = 0;j < 7;j++) {
				printLCA(i, j, lcabl);
			}
		}
	}
	
	static void printLCA(int u, int v, LCABinaryLifting lcabl) {
		System.out.println(u + ", " + v + ": " + lcabl.lcaNumber(u, v));
	}

}
