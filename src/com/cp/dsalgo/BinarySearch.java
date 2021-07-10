package com.cp.dsalgo;

public class BinarySearch {
	
	int[] arr;
	
	public BinarySearch(int[] arr) {
		this.arr = arr;
	}
	
	public int search(int element) {
		return search(element, 0, arr.length - 1);
	}
	
	int search(int element, int start, int end){
		
		if(start >= end){
			if(arr[start] == element)
				return start;
			return -1;
		}
		
		int mid = (start + end) / 2;
		if(arr[mid] == element)
			return mid;
		if(arr[mid] > element)
			return search(element, start, mid - 1);
		else
			return search(element, mid + 1, end);
	}
	
	// Index of first element >= lb
	int firstElementGreaterThanEqualtTo(int lb) {
		
		if (arr[arr.length - 1] < lb)
			return -1;
		
		if (arr[0] >= lb)
			return 0;
		
        int l = 0, h = arr.length - 1;
        while (l <= h) {
            int mid = (l + h) / 2;
            if (arr[mid] >= lb)
                h = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
	
	// Index of last element <= ub
	int lastElementLessThanEqualtTo(int ub) {
		
		if (arr[0] > ub)
			return -1;
		
		if (arr[arr.length - 1] <= ub)
			return arr.length - 1;
		
        int l = 0, h = arr.length - 1;
        while (l <= h) {
            int mid = (l + h) / 2;
            if (arr[mid] <= ub)
                l = mid + 1;
            else
                h = mid - 1;
        }
        return h;
    }
	
	int countInRange(int lb, int ub) {
		
		int ubIndex = lastElementLessThanEqualtTo(ub);
		if (ubIndex < 0)
			return 0;
		
		int lbIndex = firstElementGreaterThanEqualtTo(lb);
		if (lbIndex < 0)
			return 0;
		
        return ubIndex - lbIndex + 1;
    }
	
	public static void main(String[] args) {
		
		int[] arr = {1, 2, 3};
		BinarySearch bs = new BinarySearch(arr);
		
		/*System.out.println(bs.search(0));
		System.out.println(bs.search(1));
		System.out.println(bs.search(2));
		System.out.println(bs.search(3));
		System.out.println(bs.search(4));*/
		
		int[] arr1 = {1};
		BinarySearch bs1 = new BinarySearch(arr1);
		
		/*System.out.println(bs1.lastElementLessThanEqualtTo(0));
		System.out.println(bs1.lastElementLessThanEqualtTo(1));
		System.out.println(bs1.lastElementLessThanEqualtTo(2));
		System.out.println(bs1.lastElementLessThanEqualtTo(3));
		System.out.println(bs1.lastElementLessThanEqualtTo(4));
		System.out.println(bs1.lastElementLessThanEqualtTo(5));
		System.out.println(bs1.lastElementLessThanEqualtTo(6));
		System.out.println(bs1.lastElementLessThanEqualtTo(7));*/
		
		System.out.println(bs1.countInRange(-5, -1));
		System.out.println(bs1.countInRange(-5, 1));
		System.out.println(bs1.countInRange(1, 3));
		System.out.println(bs1.countInRange(1, 6));
		System.out.println(bs1.countInRange(1, 7));
		System.out.println(bs1.countInRange(4, 6));
		System.out.println(bs1.countInRange(5, 6));
		System.out.println(bs1.countInRange(6, 6));
		System.out.println(bs1.countInRange(4, 10));
		System.out.println(bs1.countInRange(5, 10));
		System.out.println(bs1.countInRange(-5, 10));
	}

}
