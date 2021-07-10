package com.cp.utils;

import java.util.HashMap;
import java.util.Stack;
import java.util.function.BiFunction;

public class ArrayUtils {
	
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
	
	static void printArray(int[] arr, int start, int end){
		for(int i = start;i <= end;i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	long[] constructCumulativeSumArr(long[] arr) {
    	
    	long[] csArr = new long[arr.length];
    	
    	csArr[0] = arr[0];
    	for (int i = 1;i < arr.length;i++)
    		csArr[i] = csArr[i - 1] + arr[i];
    	
    	return csArr;
    }
    
	long getCumulativeSum(long[] csArr, int start, int end) {
		   
		   if (start > end) return 0;
		   if (end < 0) return 0;
		   if (start == end) return csArr[start];
		   if (start == 0) return csArr[end];
		   
		   return csArr[end] - csArr[start - 1];
	   }
	
	int getNumElements(int start_index, int end_index) {
        if(end_index < start_index)
            return 0;
        return end_index - start_index + 1;
    }
	
	int[] reverseArr(int[] arr, int start, int end) {
		int n = end - start + 1;
		int[] rev_arr = new int[n];
		for(int i = 0;i < n;i++, end--) {
			rev_arr[i] = arr[end];
		}
		
		return rev_arr;
	}
	
	boolean arrEquals(int[] arr1, int from1, int to1, int[] arr2, int from2, int to2) {
		if(to1 - from1 != to2 - from2)
			return false;
		
		int start1 = from1;
		int start2 = from2;
		
		while(start1 <= to1) {
			if(arr1[start1] != arr2[start2])
				return false;
			
			start1++;
			start2++;
		}
		return true;
	}
	
	// 1 2 3 returns 1
	// 1 2 3 3 returns 2
	int findNumIncreasingSequence(int[] arr, int start, int end) {
		if(start >= end)
			return 0;
		
		int num_inc_sequences = 1;
		int index = start + 1;
		
		while(index <= end) {
			if(arr[index] > arr[index - 1]) {
				while(index <= end && arr[index] > arr[index - 1]) {
					index++;
				}
			}
			else {
				index++;
				num_inc_sequences++;
			}
			
		}
		return num_inc_sequences;
	}
	
	int[] getNextElementWithProperty(int[] arr, BiFunction<Integer, Integer, Boolean> function) {
    	
    	int[] result = new int[arr.length];
    	
    	Stack<Integer> stack = new Stack<Integer>();
    	stack.push(0);
    	
    	for(int i = 1;i < arr.length;i++) {
    		
    		if(!function.apply(arr[stack.peek()], arr[i]))
    			stack.push(i);
    		else {
    			while(!stack.isEmpty() && function.apply(arr[stack.peek()], arr[i])) {
    				int index = stack.pop();
    				result[index] = i;
    			}
    			stack.push(i);
    			
    		}
    	}
    	
    	while(!stack.isEmpty()) {
    		int index = stack.pop();
			result[index] = -1;
    	}
    	
    	return result;
    }
	
	int[] lcs(int[] arr1, int[] arr2) {
		
		int m = arr1.length;
		int n = arr2.length;
		
        int[][] L = new int[m+1][n+1]; 
   
        for (int i = 0; i <= m; i++){ 
            for (int j = 0; j <= n; j++) { 
                if (i == 0 || j == 0) 
                    L[i][j] = 0; 
                else if (arr1[i-1] == arr2[j-1]) 
                    L[i][j] = L[i-1][j-1] + 1; 
                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]); 
            } 
        } 
   
        int index = L[m][n];
        int[] lcs = new int[index + 1]; 
   
        int i = m, j = n; 
        while (i > 0 && j > 0){  
            if (arr1[i-1] == arr2[j-1]){  
                lcs[index-1] = arr1[i-1];  
                  
                i--;  
                j--;  
                index--;      
            }
            else if (L[i-1][j] > L[i][j-1]) 
                i--; 
            else
                j--; 
        } 
        return lcs;
    }
	
	void getSubsequencesWithSum(int[] arr, int end, int sum, int numElementsAdded, HashMap<Integer, Long> result_map, String s) {
		
		//System.out.println(sum + " " + numElementsAdded);
		
		int index = end;
		
		while(index>= 0 && arr[index] > sum)
			index--;
		
		if(index < 0)
			return;
		
		String s1;
		
		for(int i = index;i >= 0;i--) {
			int next_element = arr[i];
			s1 = s + " " + next_element;
			
			//System.out.println("Staring With: " + next_element);
			int remaining = sum - next_element;
			
    		if(remaining == 0) {
    			//System.out.println(s1);
    			Long count = result_map.get(numElementsAdded + 1);
    			//System.out.println("Adding For: " + remaining);
    			if(count == null)
    				result_map.put(numElementsAdded + 1, 1L);
    			else
    				result_map.put(numElementsAdded + 1, count + 1);
    			
    			
    		}
    		else {
    			//System.out.println("Calling With: " + remaining);
    			getSubsequencesWithSum(arr, i - 1, remaining, numElementsAdded + 1, result_map, s1);
    			
    		}
    		
    		//System.out.println();
		}
		
		//System.out.println();
    }
	
	// Divide array in subset with minimum difference
	// of their sums.
	public int findMinDiff(int[] arr, int n, int sum) {
		
		boolean dp[][] = new boolean[n + 1][sum + 1];
		
		for (int i = 0; i <= n; i++)
            dp[i][0] = true;
		
		for (int i = 1; i <= sum; i++)
            dp[0][i] = false;
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j];
				
				if (arr[i - 1] <= j)
                    dp[i][j] |= dp[i - 1][j - arr[i - 1]];
			}
		}
		
		int diff = Integer.MAX_VALUE;
		
		for (int j = sum / 2; j >= 0; j--) {
			if (dp[n][j] == true) {
				diff = sum - 2 * j;
                break;
			}
		}
		
		return diff;
	}
	
	static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
		
		int i = 0, j = 0, k = 0;
		int l1 = arr1.length, l2 = arr2.length;
		int[] merged = new int[l1 + l2];
		
		while (i < l1 && j < l2) {
			if (arr1[i] < arr2[j]) merged[k++] = arr1[i++];
            else merged[k++] = arr2[j++];
		}
		
		while (i < l1) merged[k++] = arr1[i++];
        while (j < l2) merged[k++] = arr2[j++];
        
        return merged;
		
	}
	
	public static void main(String[] args) {
		
		int[] arr1 = {1, 2, 3, 8, 9};
		int[] arr2 = {4, 5, 6, 7};
		
		printArray(mergeSortedArrays(arr1, arr2), 0, arr1.length + arr2.length - 1);
	}

}
