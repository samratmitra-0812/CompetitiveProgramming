package com.cp.dsalgo;

import java.util.HashMap;

public class DisjointSets<T> {
	
	HashMap<T, Node<T>> cache = new HashMap<T, Node<T>>();
	
	public void makeSet(T data) {
		if(cache.containsKey(data))
			return;
		
		Node<T> node = new Node<T>(data);
		cache.put(data, node);
	}
	
	public T findSet(T data) {
		if(!cache.containsKey(data))
			return null;
		
		return findSet(cache.get(data)).data;
	}
	
	private Node<T> findSet(Node<T> node) {
		if(node.parent != node) {
			node.parent = findSet(node.parent);
		}
		return node.parent;
	}
	
	public void union(T data1, T data2) {
		if(!cache.containsKey(data1) || !cache.containsKey(data2))
			return;
		
		Node<T> set1 = findSet(cache.get(data1));
		Node<T> set2 = findSet(cache.get(data2));
		
		if(set1.rank < set2.rank)
			set1.parent = set2;
		else if(set2.rank < set1.rank)
			set2.parent = set1;
		else {
			set2.parent = set1;
			set1.rank++;
		}
	}
	
	class Node<S>{
		S data;
		Node<S> parent;
		int rank;
		
		Node(S data){
			this.data = data;
			this.parent = this;
			this.rank = 0;
		}
	}
	
	public static void main(String[] args) {
		DisjointSets<Integer> ds = new DisjointSets<Integer>();
		
		ds.makeSet(1);
		ds.makeSet(2);
		ds.makeSet(3);
		ds.makeSet(4);
		ds.makeSet(5);
		ds.makeSet(6);
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
		
		ds.union(1, 2);
		System.out.println();
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
		
		ds.union(2, 3);
		System.out.println();
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
		
		ds.union(4, 5);
		System.out.println();
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
		
		ds.union(4, 6);
		System.out.println();
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
		
		ds.union(3, 6);
		System.out.println();
		
		System.out.println(ds.findSet(1));
		System.out.println(ds.findSet(2));
		System.out.println(ds.findSet(3));
		System.out.println(ds.findSet(4));
		System.out.println(ds.findSet(5));
		System.out.println(ds.findSet(6));
	}

}
