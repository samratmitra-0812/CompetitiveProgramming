package com.cp.dsalgo;

import java.util.function.BinaryOperator;

public class SegmentTree<T> {
	
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
	
	public void updateRange(int range_start, int range_end, int val) {
		updateRange(0, backing_array_size - 1, 0, range_start, range_end, val);
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
	
	
	private void updateRange(int start, int end, int seg_tree_index, int range_start, int range_end, int val) {
		if(end == start) {
			segment_tree[seg_tree_index] = val;
			return;
		}
		
		int mid = getMid(start, end);
		
		if(range_end <= mid)
			updateRange(start, mid, 2 * seg_tree_index + 1, range_start, range_end, val);
		else if(range_start > mid)
			updateRange(mid + 1, end, 2 * seg_tree_index + 2, range_start, range_end, val);
		else {
			updateRange(start, mid, 2 * seg_tree_index + 1, range_start, mid, val);
			updateRange(mid + 1, end, 2 * seg_tree_index + 2, mid + 1, range_end, val);
		}
		
		
		segment_tree[seg_tree_index] = getVal(segment_tree[2 * seg_tree_index + 1], segment_tree[2 * seg_tree_index + 2]);
	}
	
	private int getVal(int x, int y) {
        return function.apply(x, y); 
    }
	
	private int getMid(int start, int end) { 
        return start + (end - start) / 2; 
    }
	
	public static void main(String[] args) {
		int[] arr = {7,5,1,4,2,3,6};
		
		SegmentTree<Integer> segment_tree = new SegmentTree<Integer>(arr, (a, b) -> a <= b ? a : b);
		segment_tree.update(7, 8);
		
		System.out.println("0 - 6: " + segment_tree.calculate(0, 6));
		System.out.println("- 0 - 3: " + segment_tree.calculate(0, 3));
		System.out.println("-- 0 - 1: " + segment_tree.calculate(0, 1));
		System.out.println("-- 2 - 3: " + segment_tree.calculate(2, 3));
		System.out.println("- 4 - 6: " + segment_tree.calculate(4, 6));
		System.out.println("-- 4 - 5: " + segment_tree.calculate(4, 5));
		System.out.println("-- 6 - 6: " + segment_tree.calculate(6, 6));
		
		segment_tree.updateRange(0, 6, 8);
		
		System.out.println();
		
		System.out.println("0 - 6: " + segment_tree.calculate(0, 6));
		System.out.println("- 0 - 3: " + segment_tree.calculate(0, 3));
		System.out.println("-- 0 - 1: " + segment_tree.calculate(0, 1));
		System.out.println("-- 2 - 3: " + segment_tree.calculate(2, 3));
		System.out.println("- 4 - 6: " + segment_tree.calculate(4, 6));
		System.out.println("-- 4 - 5: " + segment_tree.calculate(4, 5));
		System.out.println("-- 6 - 6: " + segment_tree.calculate(6, 6));
	}

}
