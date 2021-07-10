import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
	
	long beauty;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.execute();
	}
	
	private void execute() {
		BufferedReader br = getReader();
		
		try {
			
			int t = Integer.valueOf(br.readLine().trim());
			
			while(t > 0) {
				
				beauty = 0;
				
				int n = Integer.valueOf(br.readLine().trim());
				int[] parent_arr = convertToIntArray(br.readLine().trim(), n - 1);
				
				Tree tree  = new Tree(1, n);
				DFS dfs = new DFS(tree);
				
				StringBuffer result = new StringBuffer("0");
				
				for (int i = 2;i <= n;i++) {
					
					tree.addEdge(parent_arr[i - 2], i);
					
					Node newNode = tree.getNode(i);
					
					if (!newNode.isFirstChild) {
						result.append(" " + beauty);
						continue;
					}
					
					Node parent = tree.getNode(newNode.parent);
					
					if (parent == tree.root) {
						beauty = 1;
						tree.root.height = 1;
						result.append(" " + beauty);
						continue;
					}
					
					Node grandParent = tree.getNode(parent.parent);
					
					if (grandParent == tree.root) {
						if (tree.root.height == 1) {
							tree.root.height = 2;
							beauty += 3;
							result.append(" " + beauty);
							continue;
						}
						else if (tree.root.height == 2) {
							beauty += 1;
							result.append(" " + beauty);
							continue;
						}
						
						
					}
					
					beauty = 0;
					dfs.perform();
					
					result.append(" " + beauty);
				}
				
				System.out.println(result);
				
				t--;
			}
			
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void printArray(long[] arr){
		for(int i = 0;i < arr.length;i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	
	int[] convertToIntArray(String s, int n) {
		int current_index = 0, input_start = 0, index = 0;
		int[] arr = new int[n];
		while(current_index < s.length()) {
			if(s.charAt(current_index)  == ' ') {
				arr[index++] = Integer.valueOf(s.substring(input_start, current_index));
				input_start = current_index + 1;
			}
			current_index++;
		}
		arr[index] = Integer.valueOf(s.substring(input_start, current_index));
		return arr;
	}
	
	long[] convertToLongArray(String s, int n) {
		int current_index = 0, input_start = 0, index = 0;
		long[] arr = new long[n];
		while(current_index < s.length()) {
			if(s.charAt(current_index)  == ' ') {
				arr[index++] = Long.valueOf(s.substring(input_start, current_index));
				input_start = current_index + 1;
			}
			current_index++;
		}
		arr[index] = Long.valueOf(s.substring(input_start, current_index));
		return arr;
	}
	
	private static BufferedReader getReader() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		catch(Exception e) {
			
		}
		
		return br;
	}
	
	class Node {
		
		int number;
		int value;
		HashSet<Node> adjSet = new HashSet<Node>();
		Tree tree;
		
		int parent;
		int height;
		boolean isFirstChild;
		
		Node nextNodeInLadder;
		Ladder ladder;
	
		public Node(int number, Tree tree) {
			this(number, Integer.MIN_VALUE, tree);
		}
		
		public Node(int number, int value, Tree tree) {
			this.number = number;
			this.value = value;
			this.tree = tree;
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
	
	class Tree {
		
		Node root;;
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		
		public Tree(Node root, int size) {
			this.root = root;
		}
		
		public Tree(int rootNodeNumber, int size) {
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
			node2.parent = node1.number;
			
			if (node1 == root)
				node2.isFirstChild = node1.adjSet.size() == 0;
			else
				node2.isFirstChild = node1.adjSet.size() == 1;
			
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
	
	public class DFS {
		
		Tree tree;
		
		public DFS(Tree tree) {
			this.tree =tree;
		}
		
		public void perform() {
			perform(tree.root, tree.root);
		}
		
		Ladder perform(Node node, Node parent) {
			
			if (node.isLeaf()) {
				node.height = 0;
				
				Ladder ladder = new Ladder(node);
				node.ladder = ladder;
				return ladder;
			}
			
			int maxChildHeight = -1;
			Node childWithMaxHeight = null;
			
			Ladder ladder = null;
			
			for (Node adjNode : node.adjSet) {
		        if (adjNode != parent) {
		        	
		        	adjNode.parent = node.number;
		        	Ladder childLadder = perform(adjNode, node);
		        	
		        	childLadder.head = adjNode;
		        	adjNode.ladder = childLadder;
		        	
		        	if (adjNode.height > maxChildHeight) {
		        		maxChildHeight = adjNode.height;
		        		childWithMaxHeight = adjNode;
		        		ladder = childLadder;
		        	}
		        	else if (adjNode.height == maxChildHeight && childWithMaxHeight.number > adjNode.number) {
		        		maxChildHeight = adjNode.height;
		        		childWithMaxHeight = adjNode;
		        		ladder = childLadder;
		        	}
		        }
		    }
			
			ladder = childWithMaxHeight.ladder;
			ladder.head = node;
			node.ladder = ladder;
			childWithMaxHeight.ladder = null;
			
			for (Node adjNode : node.adjSet) {
		        if (adjNode != parent && adjNode != childWithMaxHeight) {
		        	adjNode.ladder.parentLadder = ladder;
		        	ladder.addChild(adjNode.ladder);
		        }
			}
			
			node.height = childWithMaxHeight.height + 1;
			node.nextNodeInLadder = childWithMaxHeight;
			beauty += (2 * node.height) - 1;
			
			return ladder;
		}

	}
	
	class Ladder {
		
		Node head;
		Node tail;
		
		Ladder parentLadder;
		HashSet<Ladder> childLadders;
		
		public Ladder(Node node) {
			this(node, node);
		}
		
		public Ladder(Node head, Node tail) {
			this.head = head;
			this.tail = tail;
		}
		
		int height() {
			return tail.height - head.height;
		}
		
		long beautyContribution() {
			long height = (long)height();
			return height * height;
		}
		
		void addChild(Ladder l) {
			
			if (childLadders == null) 
				childLadders = new HashSet<Ladder>();
			
			childLadders.add(l);
		}
	}
}