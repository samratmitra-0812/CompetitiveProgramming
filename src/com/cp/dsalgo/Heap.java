package com.cp.dsalgo;

import java.util.function.BinaryOperator;

public class Heap<T extends Comparable<T>> {
	
	T[] heap;
	int num_elements;
	BinaryOperator<T> function;
	
	Heap(T[] elements){
		this(elements, (a, b) -> a.compareTo(b) <= 0 ? a : b);
	}
	
	Heap(T[] elements, BinaryOperator<T> function){
		heap = elements;
		num_elements = heap.length;
		this.function = function;
	}
	
	public void build() {
		
		for(int i = num_elements/2 - 1; i >= 0; i--) 
            heapify(i);
	}
	
	public T extract() {
		if(num_elements <= 0)
			return null;
		
		T value = heap[0];
		heap[0] = heap[num_elements - 1];
		num_elements--;
		heapify(0);
		
		return value;
	}
	
	public T get() {
		return num_elements > 0 ? heap[0] : null;
	}
	
	public boolean isEmpty() {
		return num_elements <= 0;
	}
	
	void heapify(int index) {
		
		int current_index = index;
        int left    = 2 * index + 1; 
        int right   = 2 * index + 2;
		
        if (left < num_elements && heap[current_index].compareTo(compare(heap[current_index], heap[left])) != 0) 
        	current_index = left; 
  
        if (right < num_elements && heap[current_index].compareTo(compare(heap[current_index], heap[right])) != 0) 
        	current_index = right; 
  
        
        if (current_index != index) { 
        	
            T swap = heap[index]; 
            heap[index] = heap[current_index]; 
            heap[current_index] = swap; 
  
            heapify(current_index); 
        }
	}

	T compare(T val1, T val2) {
		return function.apply(val1, val2);
	}
	
	public static void main(String[] args) {
		
		Integer[] arr = {7,6,8,2,4,3,1,5};
		Heap<Integer> heap = new Heap<Integer>(arr, (a, b) -> a >= b? a : b);
		heap.build();
		
		while(!heap.isEmpty()) {
			System.out.println(heap.extract());
		}
	}

}
