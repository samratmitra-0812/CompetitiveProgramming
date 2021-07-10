package com.cp.dsalgo;

public class BinarySearchTree<T extends Comparable<T>> {
	
	BSTNode root;
	
	public BinarySearchTree(){}
	
	public void insert(T data) {
		
		BSTNode bstNode = new BSTNode(data);
		
		if(root == null) {
			root = bstNode;
			return;
		}
		
		BSTNode current_node = root;
		
		while(true) {
			if(data.compareTo(current_node.data) <= 0) {
				if(current_node.left == null) {
					current_node.left = bstNode;
					return;
				}
				current_node = current_node.left;
			}
			else {
				if(current_node.right == null) {
					current_node.right = bstNode;
					return;
				}
				current_node = current_node.right;
			}
		}
	}
	
	void inorder() {
		inorder(root);
	}
	
	void inorder(BSTNode node) {
		if(node == null)
			return;
		
		inorder(node.left);
		System.out.print(node.data + " ");
		inorder(node.right);
	}
	
	class BSTNode {
		
		T data;
		BSTNode left;
		BSTNode right;
		
		BSTNode(T data){
			this.data = data;
			this.left = null;
			this.right = null;
		}
		
	}
	
	public static void main(String[] args) {
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		
		bst.insert(5);
		bst.inorder();
		
		System.out.println();
		bst.insert(6);
		bst.inorder();
		
		System.out.println();
		bst.insert(7);
		bst.inorder();
		
		System.out.println();
		bst.insert(5);
		bst.inorder();
		
	}

}
