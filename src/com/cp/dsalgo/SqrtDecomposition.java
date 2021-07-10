package com.cp.dsalgo;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class SqrtDecomposition<T> {
	
	BinaryOperator<T> function;
	ArrayList<T> block_values = new ArrayList<T>();
	Object[] orig_arr;
	int block_size;
	
	SqrtDecomposition(BinaryOperator<T> function){
		this.function = function;
	}
	
	public void decompose(T[] arr) {
		
		block_size = (int)Math.sqrt(arr.length);
		T block_value = null;
		orig_arr = new Object[arr.length];
		
		for(int i = 0;i < arr.length;i++) {
			orig_arr[i] = arr[i];
			
			if(i % block_size == 0) {
				block_value = arr[i];
				if(block_size == 1)
					block_values.add(block_value);
			}
			else {
				block_value = apply(block_value, arr[i]);
				if((i + 1) % block_size == 0) {
					block_values.add(block_value);
				}
			}
		}
		
		if(arr.length % block_size != 0)
			block_values.add(block_value);
	}
	
	T get(int left, int right) {
		
		if(left == right)
			return (T)orig_arr[left];
		
		T result = null;
		
		
		int start = left, end = right;
		boolean is_first_block = true;
		
		while(start <= end) {
			
			int current_block = getBlockNum(start);
			int block_start = getBlockStart(current_block);
			int block_end = getBlockEnd(current_block);
			int range_start = Math.max(start, block_start);
			int range_end = Math.min(end, block_end);
			
			if(is_first_block) {
				result = getValueForRangeInBlock(range_start, range_end);
				is_first_block = false;
			}
			else {
				result = apply(result, getValueForRangeInBlock(range_start, range_end));
			}
			
			start = block_end + 1;
		}
		
		return result;
	}
	
	public void update(int index, T val) {
	}
	
	T apply(T a, T b) {
		return function.apply(a, b);
	}
	
	void print() {
		System.out.println(block_values);
	}
	
	T getValueForBlock(int block_num) {
		return block_values.get(block_num);
	}
	
	T getValueForRangeInBlock(int start, int end) {
		
		if(start == end)
			return (T)orig_arr[start];
		
		int block_num = getBlockNum(start);
		int block_start = getBlockStart(block_num);
		int block_end = getBlockEnd(block_num);
		
		if(start == block_start && end == block_end) {
			return block_values.get(block_num);
		}
		
		T result = (T)orig_arr[start];
		
		for(int i = start + 1;i <= end;i++)
			result = apply(result, (T)orig_arr[i]);
		
		return result;
	}
	
	int getBlockNum(int index) {
		return index/block_size;
	}
	
	int getBlockStart(int block_num) {
		return block_num * block_size;
	}
	
	int getBlockEnd(int block_num) {
		return Math.min(orig_arr.length, getBlockStart(block_num + 1) - 1); 
	}
	
	public static void main(String[] args) {
		
		Integer[] arr = {1, 2};
		
		SqrtDecomposition<Integer> sd = new SqrtDecomposition<Integer>((a, b) -> Math.max(a, b));
		sd.decompose(arr);
		//sd.print();
		
		System.out.println(sd.get(0, 0));
		System.out.println(sd.get(0, 1));
		System.out.println(sd.get(1, 1));
		//System.out.println(sd.get(3, 4));
		//System.out.println(sd.get(4, 5));
		//System.out.println(sd.get(5, 6));
		//System.out.println(sd.get(6, 7));
		//System.out.println(sd.get(7, 8));
		//System.out.println(sd.get(8, 9));
		//System.out.println(sd.get(9, 9));
	}
}
