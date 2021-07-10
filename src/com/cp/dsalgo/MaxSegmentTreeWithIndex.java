package com.cp.dsalgo;

public class MaxSegmentTreeWithIndex {
	
	SegmentTreeNode[] segment_tree;
	int backing_array_size;
	
	public MaxSegmentTreeWithIndex(int[] arr) {
		
		backing_array_size = arr.length;
		int x = (int) (Math.ceil(Math.log(backing_array_size) / Math.log(2))); 
		int max_size = 2 * (int) Math.pow(2, x) - 1; 
        segment_tree = new SegmentTreeNode[max_size];
  
        build(arr, 0, backing_array_size - 1, 0);
	}
	
	private SegmentTreeNode build(int arr[], int start, int end, int current_index) {
		
		if (start == end) { 
			segment_tree[current_index] = newNode(arr[start], start);
            return segment_tree[current_index]; 
        } 
  
        int mid = getMid(start, end); 
        segment_tree[current_index] = getVal(build(arr, start, mid, current_index * 2 + 1), 
        		                             build(arr, mid + 1, end, current_index * 2 + 2)); 
        return segment_tree[current_index]; 
	}
	
	public SegmentTreeNode calculate(int qs, int qe) {
		return calculate(0, backing_array_size - 1, qs, qe, 0);
	}
	
	public void update(int index, int val) {
		update(0, backing_array_size - 1, 0, index, val);
	}
	
	public void updateRange(int range_start, int range_end, int val) {
		updateRange(0, backing_array_size - 1, 0, range_start, range_end, val);
	}
	
	private SegmentTreeNode calculate(int ss, int se, int qs, int qe, int index){ 
        
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
			segment_tree[seg_tree_index].value = val;
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
			segment_tree[seg_tree_index].value = val;
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
	
	private SegmentTreeNode getVal(SegmentTreeNode x, SegmentTreeNode y) {
        if (x.value == y.value)
        	return x.index < y.index ? x : y;
        
        return x.value > y.value ? x : y;
    }
	
	private int getMid(int start, int end) { 
        return start + (end - start) / 2; 
    }
	
	void print() {
		for (int i = 0;i < segment_tree.length;i++) {
			if (segment_tree[i] == null)
				System.out.print("-1");
			else
				segment_tree[i].print();
			
			System.out.print(" ");
		}
		System.out.println();
	}
	
	SegmentTreeNode newNode(int value, int index) {
		return new SegmentTreeNode(value, index);
	}
	
	static class SegmentTreeNode {
		
		int value;
		int index;
		
		SegmentTreeNode(int value, int index) {
			this.value = value;
			this.index = index;
		}
		
		void print() {
			System.out.print("(" + index + ", " + value + ")");
		}
	}
	
	public static void main(String[] args) {
		
		int[] arr = {1, 2, 3};
		
		MaxSegmentTreeWithIndex st = new MaxSegmentTreeWithIndex(arr);
		
		st.print();
		
		st.update(0, 4);
		st.update(1, 5);
		st.update(2, 6);
		
		st.print();
		
		st.calculate(0, 1).print();
		st.calculate(1, 2).print();
		st.calculate(0, 2).print();
	}

}
