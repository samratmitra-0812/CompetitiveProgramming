package com.cp.dsalgo;

import java.util.ArrayList;
import java.util.List;

public class NonOverlappingRangeTree {
	
	static int DEFAULT_VALUE = Integer.MIN_VALUE;
	
	Node root;
	
	void addRange(int start, int end) {
		addRange(start, end, DEFAULT_VALUE);
	}
	
	public void addRange(int start, int end, int value) {
		
		if(root == null) {
			root = new Node(start, end, value);
			return;
		}
		
		insert(new Range(start, end, value));
	}
	
	void insert(Range r) {
		insert(root, r);
	}
	
	void insert(Node root, Range new_range) {
		
		if(new_range.isEmpty())
			return;
		
		Node current_node = root;
		while(true) {
			if(new_range.compareTo(current_node.range) == -1) {
				if(current_node.left == null) {
					current_node.left = new Node(new_range);
					System.out.println("Inserted " + new_range + " to left of " + current_node.range);
					break;
				}
				current_node = current_node.left;
			}
			else if(new_range.compareTo(current_node.range) == 1) {
				if(current_node.right == null) {
					current_node.right = new Node(new_range);
					System.out.println("Inserted " + new_range + " to right of " + current_node.range);
					break;
				}
				current_node = current_node.right;
			}
			else if(new_range.compareTo(current_node.range) == 0)
				break;
			else {
				List<Range> nonOverlappingRanges = splitIntoNonOverlappingRanges(new_range, current_node.range);
				
				int mid = nonOverlappingRanges.size()/2;
				Range middle_range = nonOverlappingRanges.get(mid);
				System.out.print("Updating " + current_node.range + " to ");
				current_node.range.start = middle_range.start;
				current_node.range.end = middle_range.end;
				System.out.println(current_node.range);
				for(int i = 0;i < nonOverlappingRanges.size();i++) {
					if(i != mid)
						insert(current_node, nonOverlappingRanges.get(i));
				}
				break;
			}
		}
	}
	
	List<Range> splitIntoNonOverlappingRanges(Range r1, Range r2){
		List<Range> nonOverlappingRanges = new ArrayList<Range>();
		
		if(r1.equals(r2)) {
			nonOverlappingRanges.add(r1);
			return nonOverlappingRanges;
		}
		
		// (1,10) and (10, 20)
		// (10, 20) and (1, 10)
		if(r1.start == r2.end || r1.end == r2.start) {
			
			Range range1 = new Range(min(r1.start, r2.start), max(r1.start, r2.start) - 1);
			Range range2 = new Range(max(r1.start, r2.start), min(r1.end, r2.end));
			Range range3 = new Range(min(r1.end, r2.end) + 1, max(r1.end, r2.end));
			
			nonOverlappingRanges.add(range1);
			nonOverlappingRanges.add(range2);
			nonOverlappingRanges.add(range3);
			
			return nonOverlappingRanges;
		}
		
		if(r1.start == r2.start) {
			
			Range range1 = Range.EMPTY_RANGE;
			Range range2 = new Range(r1.start, min(r1.end, r2.end));
			Range range3 = new Range(min(r1.end, r2.end) + 1, max(r1.end, r2.end));
			
			nonOverlappingRanges.add(range1);
			nonOverlappingRanges.add(range2);
			nonOverlappingRanges.add(range3);
			
			return nonOverlappingRanges;
		}
		
		if(r1.end == r2.end) {
			
			Range range1 = new Range(min(r1.start, r2.start), max(r1.start, r2.start) - 1);
			Range range2 = new Range(max(r1.start, r2.start), r1.end);
			Range range3 = Range.EMPTY_RANGE;
			
			nonOverlappingRanges.add(range1);
			nonOverlappingRanges.add(range2);
			nonOverlappingRanges.add(range3);
			
			return nonOverlappingRanges;
		}
		
		Range range1 = new Range(min(r1.start, r2.start), max(r1.start, r2.start) - 1);
		Range range2 = new Range(max(r1.start, r2.start), min(r1.end, r2.end));
		Range range3 = new Range(min(r1.end, r2.end) + 1, max(r1.end, r2.end));
		
		nonOverlappingRanges.add(range1);
		nonOverlappingRanges.add(range2);
		nonOverlappingRanges.add(range3);
		
		return nonOverlappingRanges;
	}
	
	public void print() {
		print(root);
	}
	
	public void print(Node node) {
		if(node == null)
			return;
		
		print(node.left);
		System.out.print(node.range + " ");
		print(node.right);
	}
	int min(int a, int b) {
		return a < b ? a : b;
	}
	
	int max(int a, int b) {
		return a > b ? a : b;
	}
	
	static class Range implements Comparable<Range> {
		
		static Range EMPTY_RANGE = new Range(0, -1);
		
		int start;
		int end;
		int value;
		
		Range(int start, int end){
			this(start, end, DEFAULT_VALUE);
		}
		
		Range(int start, int end, int value){
			this.start = start;
			this.end = end;
			this.value = value;
		}
		
		public int size() {
			return end - start + 1;
		}
		
		public boolean isEmpty() {
			return size() <= 0;
		}
		
		@Override
		public boolean equals(Object obj) {
			Range r = (Range)obj;
			return this.start == r.start && this.end == r.end;
		}
		
		@Override
		public int hashCode() {
			return start + end;
		}
		
		@Override
		public String toString() {
			return "(" + start + ", " + end + ")";
		}
		
		@Override
		public int compareTo(Range r) {
			if (this.equals(r))
				return 0;
			
			if(r.end < this.start)
				return 1;
			
			if(r.start > this.end)
				return -1;
			
			return 2;
		}
		
		public boolean isOverlapping(Range r) {
			int c = this.compareTo(r);
			return c != 1 && c != -1;
		}
	}
	
	class Node {
		Range range;
		Node left;
		Node right;
		
		Node(Range r){
			this.range = r;
		}
		
		Node(int start, int end){
			range = new Range(start, end);
		}
		
		Node(int start, int end, int value){
			range = new Range(start, end, value);
		}
	}
	
	public static void main(String[] args) {
		
		NonOverlappingRangeTree tree = new NonOverlappingRangeTree();
		
		tree.addRange(1, 200);
		tree.addRange(1, 50);
		tree.addRange(51, 100);
		/*tree.addRange(71, 100);
		tree.addRange(101, 200);
		tree.addRange(41, 160);
		tree.addRange(1, 40);
		tree.addRange(11, 200);
		tree.addRange(1, 10);
		tree.addRange(171, 180);
		tree.addRange(181, 200);*/
		
		tree.print();
	}

}
