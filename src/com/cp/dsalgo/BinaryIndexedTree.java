package com.cp.dsalgo;

public class BinaryIndexedTree {
	
static int DEFAULT_SIZE = 16;
	
	int[] tree;
	
	BinaryIndexedTree(){
		init(DEFAULT_SIZE);
	}
	
	public BinaryIndexedTree(int size) {
		init(size + 1);
	}
	
	void init(int size) {
		tree = new int[size];
	}
	
	public void build(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
            update(i, arr[i]);
		}
	}
	
	public int getSum(int index) {
		
		int sum = 0;
		index = index + 1; 
		
		while(index > 0){
			sum += tree[index]; 
            index -= index & (-index); 
        }
		
		return sum;
		
	}
	
	public void update(int index, int val) { 
	 
		index = index + 1; 
		
		while(index < tree.length) { 
			tree[index] += val; 
			index += index & (-index); 
		} 
	}
	
	public static void main(String[] args) {
		
		BinaryIndexedTree bit1 = new BinaryIndexedTree(1);
		System.out.println(bit1.getSum(0));
		bit1.update(0, 1);
		System.out.println(bit1.getSum(0));
		
		BinaryIndexedTree bit = new BinaryIndexedTree(10);
		bit.update(1, 1);
		
		System.out.println(bit.getSum(9));
		bit.update(4, 1);
		System.out.println(bit.getSum(9));
		bit.update(7, 1);
		System.out.println(bit.getSum(9));
		bit.update(9, 1);
		System.out.println(bit.getSum(9));
		System.out.println(bit.getSum(7));
		System.out.println(bit.getSum(4));
		System.out.println(bit.getSum(1));
		
	}

}
