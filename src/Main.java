import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

	long beauty;

	Ladders ladders;

	public static void main(String[] args) {
		Main main = new Main();
		main.execute();
	}

	private void execute() {
		BufferedReader br = getReader();

		try {

			int t = Integer.valueOf(br.readLine().trim());

			while(t > 0) {

				ladders = new Ladders();

				int n = Integer.valueOf(br.readLine().trim());
				int[] parent_arr = convertToIntArray(br.readLine().trim(), n - 1);

				Tree tree  = new Tree(1, n);
				new Ladder(tree.root);

				StringBuffer result = new StringBuffer("0");

				for (int i = 2;i <= n;i++) {

					tree.addEdge(parent_arr[i - 2], i);

					Node newNode = tree.getNode(i);
					processNode(tree, newNode);


					result.append(" " + ladders.beauty);
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

	void processNode(Tree tree, Node newNode) {

		if (!newNode.isFirstChild) {
			new Ladder(newNode);
		}

		Ladder l = tree.getNode(newNode.parent).ladder;
		l.growDown(newNode);

		Ladder nextLadder = l;
		while(true) {

			nextLadder = nextLadder.mergeWithParent();

			if (nextLadder == null) break;
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
		int depth;
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

		void perform(Node node, Node parent) {

			Ladder ladder = null;

			for (Node adjNode : node.adjSet) {
		        if (adjNode != parent) {

		        	adjNode.parent = node.number;
		        	adjNode.depth = node.depth + 1;
		        	perform(adjNode, node);
		        }
		    }
		}

	}

	class Ladder {

		Node head;
		Node tail;

		Tree tree;

		public Ladder(Node node) {
			this(node, node);
		}

		public Ladder(Node head, Node tail) {
			this.head = head;
			this.tail = tail;

			head.ladder = this;
			tail.ladder = this;

			ladders.addLadder(this);
		}

		int height() {
			return tail.height - head.height;
		}

		long beautyContribution() {
			long height = (long)height();
			return height * height;
		}

		Node mergeNode() {
			return tree.getNode(head.parent);
		}

		void growDown(Node newTail) {

			newTail.ladder = this;

			if (head != tail)
				tail.ladder = null;

			tail.nextNodeInLadder = newTail;

			tail = newTail;
		}

		Ladder getParentLadder() {

			Ladder parentLadder = null;

			return parentLadder;
		}

		boolean shouldMergeWithParent(Ladder parent) {

			int height = height();
			int currentParentHeight = parent.height();
			Node mergeNode = mergeNode();
			int mergeNodeDepth = mergeNode.depth;

			int newLadderHeight = height + mergeNodeDepth + 1;

			if (newLadderHeight < currentParentHeight) return false;

			if (newLadderHeight == currentParentHeight) {
				Node childOfMergeNode = mergeNode.nextNodeInLadder;
				if (childOfMergeNode.number <= head.number) return false;
			}

			return true;
		}

		Ladder mergeWithParent() {

			if (head == tree.root) return null;

			Ladder parentLadder = getParentLadder();

			if (!shouldMergeWithParent(parentLadder)) return null;

			delete();
			parentLadder.delete();

			Node mergeNode = mergeNode();

			Node headOfNewLadder = mergeNode.nextNodeInLadder;
			Node tailOfNewLadder = parentLadder.tail;
			new Ladder(headOfNewLadder, tailOfNewLadder);



			mergeNode.nextNodeInLadder = head;
			head.ladder = null;

			Ladder newLadder = new Ladder(parentLadder.head, tail);

			return newLadder;
		}

		void delete() {
			ladders.removeLadder(this);
		}
	}

	class Ladders {

		long beauty = 0;

		HashSet<Ladder> ladders = new HashSet<Ladder>();

		void addLadder(Ladder l) {
			ladders.add(l);
			beauty += l.beautyContribution();
		}

		void removeLadder(Ladder l) {
			ladders.remove(l);
			beauty -= l.beautyContribution();
		}

		void growDown(Ladder l) {

		}
	}
}
