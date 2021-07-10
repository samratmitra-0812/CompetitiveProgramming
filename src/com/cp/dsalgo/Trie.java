package com.cp.dsalgo;


public class Trie {
	
	Node root;
	
	public void addString(String s) {
		
		if(root == null)
			root = new Node();
		
		Node parent = root;
		for(char c : s.toCharArray()) {
			if(parent.children[c - 'a'] == null) {
				parent.children[c - 'a'] = new Node();
			}
			
			parent = parent.children[c - 'a'];
		}
		
		parent.is_end_node = true;
		
	}
	
	void print(){
		if(root == null) {
			return;
		}
		
		print(root, new StringBuffer(""));
	
	}
	
	void print(Node parent, StringBuffer s) {
		
		if(parent.is_end_node) {
			System.out.println(s);
		}
		
		for(int i = 0;i < 26;i++) {
			if(parent.children[i] != null) {
				StringBuffer s1 = new StringBuffer(s);
				print(parent.children[i], s1.append((char)('a' + i)));
			}
		}
	}
	
	class Node{
		boolean is_end_node;
		Node[] children = new Node[26];
	}
	
	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.addString("abc");
		trie.print();
		System.out.println();
		trie.addString("abcd");
		trie.print();
		System.out.println();
		trie.addString("a");
		trie.print();
		System.out.println();
		trie.addString("x");
		trie.print();
		System.out.println();
		trie.addString("xy");
		
		trie.print();
	}

}

