package com.cp.dsalgo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.function.BinaryOperator;

public class HeavyLightDecomposition {
	
	HLDTree tree;
	int hldIndex;
	int chainNumber;
	int[] stBackingArr;
	
	BinaryOperator<Integer> function;
	SegmentTree st;
	
	HashMap<Integer, HLDNode> chainHeads = new HashMap<Integer, HLDNode>();
	
	HeavyLightDecomposition(HLDTree tree, BinaryOperator<Integer> function) {
		this.tree = tree;
		this.function = function;
		stBackingArr = new int[tree.size()];
	}
	
	public void decompose() {
		HLDDfs hldfs = new HLDDfs(tree);
		hldfs.perform();
		decomposeIterative(tree.root);
		st = new SegmentTree(stBackingArr, function);
	}
	
	public void decompose(HLDNode currentNode) {
		
		if (chainHeads.get(chainNumber) == null) {
			chainHeads.put(chainNumber, currentNode);
		}
		
		currentNode.chainNumber = chainNumber;
		currentNode.hldIndex = hldIndex++;
		stBackingArr[currentNode.hldIndex] = currentNode.value;
		
		if (currentNode.heavyChild != null)
			decompose(currentNode.heavyChild);
		
		for (HLDNode adjNode : currentNode.adjSet) {
	        if (adjNode != currentNode.parent && adjNode != currentNode.heavyChild) {
	        	chainNumber++;
	        	decompose(adjNode);
	        }
		}
	}
	
	public void decomposeIterative(HLDNode node) {
    	
    	Stack<HLDNode> stack = new Stack<HLDNode>();
    	
    	stack.push(node);
    	
    	while(!stack.isEmpty()) {
    		HLDNode currentNode = stack.pop();
    		currentNode.hldIndex = hldIndex++;
            stBackingArr[currentNode.hldIndex] = currentNode.value;
            
            if (currentNode == tree.root || currentNode != currentNode.parent.heavyChild)
            	chainNumber++;
    		
    		if (chainHeads.get(chainNumber) == null) {
                chainHeads.put(chainNumber, currentNode);
            }
    		
    		currentNode.chainNumber = chainNumber;
    		
    		for (HLDNode adjNode : currentNode.adjSet) {
    			if (adjNode != currentNode.parent && adjNode != currentNode.heavyChild) {
    				stack.push(adjNode);
    			}
    		}
    		
    		if (currentNode.heavyChild != null)
    			stack.push(currentNode.heavyChild);
    	}
    }
	
	public void update(int nodeNumber, int newVal) {
		
		HLDNode node = tree.getNode(nodeNumber);
		node.value = newVal;
		int hldIndex = node.hldIndex;
		st.update(hldIndex, newVal);
	}
	
	public int query(int from, int to) {
		HLDNode u = tree.getNode(from);
		HLDNode v = tree.getNode(to);
		
		if (from == to)
			return u.value;
		
		int result = 0;
		
		HLDNode lca = LCAFinder.findLCA(tree, u, v);
		
		System.out.println(u.number + " " + v.number + " " + lca.number);
		
		result = function.apply(findVal(u, lca), findVal(v, lca));
		
		return result;
	}
	
	int findVal(HLDNode u, HLDNode v) {
		
		if (u == v)
			return u.value;
		
		int result = 0;
		
		while (true) {
			if (u.chainNumber == v.chainNumber) {
				result = function.apply(result, st.calculate(v.hldIndex, u.hldIndex));
				break;
			}
			
			int currentChain = u.chainNumber;
			HLDNode chainHead = chainHeads.get(currentChain);
			result = function.apply(result, st.calculate(chainHead.hldIndex, u.hldIndex));
			u = chainHead.parent;
		}
		
		return result;
		
	}
	
	static class HLDNode {
		
		int number;
		int value;
		HashSet<HLDNode> adjSet = new HashSet<HLDNode>();
		HLDTree tree;
		
		HLDNode parent;
		HLDNode heavyChild;
		int subtreeSize;
		int depth;
		int chainNumber;
		int hldIndex;
		
		int visitTime;
		int exitTime;
		
		boolean visited = false;
		
		public HLDNode(int number, HLDTree tree) {
			this(number, Integer.MIN_VALUE, tree);
		}
		
		public HLDNode(int number, int value, HLDTree tree) {
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
		
		public void addAdjacentNode(HLDNode node) {
			adjSet.add(node);
		}
		
		public int countChildren() {
			if (isRoot()) return adjSet.size();
			return adjSet.size() - 1;
		}
		
		public void print() {
			System.out.println("Node " + number);
			System.out.println("-----------------");
			System.out.println("Value: " + value);
			System.out.println("Parent: " + parent.number);
			System.out.println("Size: " + subtreeSize);
			System.out.println("Depth: " + depth);
			System.out.println("Chain: " + chainNumber);
			System.out.println("HLDIndex: " + hldIndex);
			System.out.println();
		}
	}
	
	static class HLDTree {
		
		HLDNode root;;
		HashMap<Integer, HLDNode> nodes = new HashMap<Integer, HLDNode>();
		
		public HLDTree(HLDNode root) {
			this.root = root;
		}
		
		public HLDTree(int rootNodeNumber) {
			root = getOrCreateNode(rootNodeNumber);
		}
		
		private HLDNode getOrCreateNode(int nodeNumber) {
			HLDNode node = nodes.get(nodeNumber);
			if (node == null) {
				node = new HLDNode(nodeNumber, 0, this);
				nodes.put(nodeNumber, node);
			}
			
			return node;
		}
		
		public void addEdge(int u, int v) {
			HLDNode node1 = getOrCreateNode(u);
			HLDNode node2 = getOrCreateNode(v);
			
			node1.addAdjacentNode(node2);
			node2.addAdjacentNode(node1);
		}
		
		HLDNode getNode(int number) {
			return nodes.get(number);
		}
		
		public int size() {
			return nodes.size();
		}
		
		public boolean isEmpty() {
			return root == null;
		}
		
		public void printNodes() {
			for (HLDNode node: nodes.values())
				node.print();
		}
	}
	
	static class HLDDfs {
		
		HLDTree tree;
		int time;
		int entrytime;
		int exittime = Integer.MAX_VALUE;
		
		public HLDDfs(HLDTree tree) {
			this.tree =tree;
		}
		
		public void perform() {
			performIterative(tree.root, tree.root);
		}
		
		void perform(HLDNode node, HLDNode parent) {
			
			node.subtreeSize = 1;
			node.parent = parent;
			node.depth = parent.depth + 1;
			node.visitTime = ++time;
			
			int maxChildSize = 0;
			HLDNode heavyChild = null;
			
			for (HLDNode adjNode : node.adjSet) {
		        if (adjNode != parent) {
		        	perform(adjNode, node);
		        	node.subtreeSize += adjNode.subtreeSize;
		        	
		        	if (adjNode.subtreeSize > maxChildSize) {
		        		maxChildSize = adjNode.subtreeSize;
		        		heavyChild = adjNode;
		        	}
		        }
		    }
			
			node.heavyChild = heavyChild;
			node.exitTime = ++time;
		}
		
		void performIterative(HLDNode node, HLDNode parent) {
			
			Stack<HLDNode> stack1 = new Stack<HLDNode>();
			Stack<HLDNode> stack2 = new Stack<HLDNode>();
    		
    		stack1.push(node);
    		node.parent = parent;
    		
    		while (!stack1.isEmpty()) {
    			HLDNode currentNode = stack1.peek();
    			
    			if (currentNode.visited) {
    				currentNode.exitTime = ++time;
    				stack1.pop();
    				continue;
    			}
    			
    			stack2.push(currentNode);
    			
    			currentNode.subtreeSize = 1;
    			currentNode.depth = parent.depth + 1;
    			currentNode.visitTime = ++time;
    			currentNode.visited = true;
    			
    			for (HLDNode adjNode : currentNode.adjSet) {
    				
    		        if (adjNode != currentNode.parent) {
    		        	adjNode.parent = currentNode;
    		        	stack1.push(adjNode);
    		        }
    		    }
    		}
    		
    		while (!stack2.empty()) {
    			HLDNode currentNode = stack2.pop();
    			
    			if (currentNode == tree.root)
    				break;
    			
    			HLDNode currentNodeParent = currentNode.parent;
    			currentNodeParent.subtreeSize += currentNode.subtreeSize;
    			if (currentNodeParent.heavyChild == null || currentNode.subtreeSize > currentNodeParent.heavyChild.subtreeSize)
    				currentNodeParent.heavyChild = currentNode;
    			
    			// currentNode.print();
    		}
		}
	}
	
	public class SegmentTree {
		
		int[] segment_tree;
		BinaryOperator<Integer> function;
		int backing_array_size;
		
		public SegmentTree(int[] arr, BinaryOperator<Integer> function) {
			
			backing_array_size = arr.length;
			int x = (int) (Math.ceil(Math.log(backing_array_size) / Math.log(2))); 
			int max_size = 2 * (int) Math.pow(2, x) - 1; 
	        segment_tree = new int[max_size];
	        
	        this.function = function;
	  
	        build(arr, 0, backing_array_size - 1, 0);
		}
		
		private int build(int arr[], int start, int end, int current_index) {
			
			if (start == end) { 
				segment_tree[current_index] = arr[start];
	            return arr[start]; 
	        } 
	  
	        int mid = getMid(start, end); 
	        segment_tree[current_index] = getVal(build(arr, start, mid, current_index * 2 + 1), 
	        		                             build(arr, mid + 1, end, current_index * 2 + 2)); 
	        return segment_tree[current_index]; 
		}
		
		public int calculate(int qs, int qe) {
			return calculate(0, backing_array_size - 1, qs, qe, 0);
		}
		
		public void update(int index, int val) {
			update(0, backing_array_size - 1, 0, index, val);
		}
		
		private int calculate(int ss, int se, int qs, int qe, int index){ 
	        
	        if (qs <= ss && qe >= se) 
	            return segment_tree[index]; 
	  
	   
	        int mid = getMid(ss, se);
	        
	        if(qe <= mid)
	        	return calculate(ss, mid, qs, qe, 2 * index + 1);
	        if(qs > mid)
	        	return calculate(mid + 1, se, qs, qe, 2 * index + 2);
	        
	        return getVal(calculate(ss, mid, qs, qe, 2 * index + 1), 
	        		      calculate(mid + 1, se, qs, qe, 2 * index + 2)); 
	    }
		
		private void update(int start, int end, int seg_tree_index, int orig_index, int val) {
			if(end == start) {
				segment_tree[seg_tree_index] = val;
				return;
			}
			
			int mid = getMid(start, end);
			
			if(orig_index <= mid)
				update(start, mid, 2 * seg_tree_index + 1, orig_index, val);
			else
				update(mid + 1, end, 2 * seg_tree_index + 2, orig_index, val);
			
			segment_tree[seg_tree_index] = getVal(segment_tree[2 * seg_tree_index + 1], segment_tree[2 * seg_tree_index + 2]);
		}
		
		private int getVal(int x, int y) {
	        return function.apply(x, y); 
	    }
		
		private int getMid(int start, int end) { 
	        return start + (end - start) / 2; 
	    }
		
		public void print() {
			
			for(int i = 0;i < segment_tree.length;i++) {
				System.out.print(segment_tree[i] + " ");
			}
			
			System.out.println();
		}

	}
	
	static class LCAFinder {
		
		public static HLDNode findLCA(HLDTree tree, HLDNode u, HLDNode v) {
			
			if (u == v)
				return u;
			
			if (isAncestor(u, v))
				return u;
			
			if (isAncestor(v, u))
				return v;
			
			HLDNode node1 = u.depth <= v.depth ? u : v;
            HLDNode other = node1 == u ? v : u;
            
            while(node1 != tree.root && !isAncestor(node1, other)) {
                node1 = node1.parent;
            }
			
			return node1;
		}
		
		static boolean isAncestor(HLDNode u, HLDNode v) {
			return u.visitTime <= v.visitTime && u.exitTime >= v.exitTime;
		}
	}
	
	public static void main(String[] args) {
		
		HLDTree tree = new HLDTree(0);
		tree.addEdge(0, 1);
		tree.addEdge(0, 2);
		tree.addEdge(1, 3);
		tree.addEdge(1, 4);
		tree.addEdge(2, 5);
		tree.addEdge(2, 6);
		
		HeavyLightDecomposition hld = new HeavyLightDecomposition(tree, (a, b) -> a >= b ? a : b);
		hld.decompose();
		
		// tree.printNodes();
		// hld.st.print();
		
		System.out.println();
		
		hld.update(0, 7);
		hld.update(1, 6);
		hld.update(2, 5);
		hld.update(3, 4);
		hld.update(4, 3);
		hld.update(5, 2);
		hld.update(6, 1);
		
		// tree.printNodes();
		// hld.st.print();
		
		// 7 6 4 3 5 1 2
		
		for (int i = 0;i < 7;i++) {
			for (int j = 0;j < 7;j++) {
				// System.out.println("Querying " + i + " " + j);
				System.out.println(i + ", " + j + ": " + hld.query(i, j));
			}
		}
	}
}
