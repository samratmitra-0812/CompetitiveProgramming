import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test {

	static int EVEN_SUM_GROUP = 0;
	static int ODD_SUM_GROUP = 1;
	
	static HashMap<String, Integer> map = new HashMap<String, Integer>();
	static int range = 1;
	static int extraCount;
	
	public static void main(String[] args) {
		
		/*int n1 = 7;
		int n2 = 9;
		int k = 1;
		
		double digits = Math.pow(10, k - 1);
		
		for (int i = n1;i <= n2;i++) {
			double log2base10 = Math.log10(2.0);
			double log = (double)i * log2base10;
			double log1 = log - Math.floor(log);
			double decPart = Math.pow(10, log1);
			System.out.println(decPart);
			int result = (int)Math.floor(decPart + 0.5) * (int)digits;
			System.out.println(result);
			// System.out.println(Math.pow(10, log1));
			
		}*/
		
		int start = 2;
		int end = 16;
		
		for (int n = start;n <= end;n++) {
			map.clear();
			extraCount = 0;
			solve(n);
			print(n);
		}
	}
	
	static void print(int n) {
		
		StringBuffer sb = new StringBuffer("" + n);
		
		for (int i = 1;i <= range;i++) {
			String key = "even(" + i + ")";
			int val1 = getFromMap(key);
			sb.append("  " + key + ": " + val1);
			key = "odd(" + i + ")";
			int val2 = getFromMap(key);
			sb.append("  " + key + ": " + val2);
		}
		
		sb.append("  extra: " + extraCount);
		
		System.out.println(sb);
	}
	
	static void solve(int n) {
		evenSum(n);
		oddSum(n);
	}
	
	static void addToMap(String key) {
		Integer count = map.get(key);
		if (count == null)
			map.put(key, 1);
		else 
			map.put(key, count + 1);
	}
	
	static int getFromMap(String key) {
		return map.containsKey(key) ? map.get(key) : 0;
	}
	
	static void evenSum(int n) {
		
		if (n <= range) {
			String key = "even(" + n + ")";
			addToMap(key);
			return;
		}
		
		int groupType = getGroupType(n);
		
		for (int i = n - 1;i >= 1;i--) {
			int type = getGroupType(i);
			
			if (type == groupType)
				oddSum(i);
			
		}
		
		if (groupType == EVEN_SUM_GROUP)
			extraCount++;
	}
	
	static void oddSum(int n) {
		if (n <= range) {
			String key = "odd(" + n + ")";
			addToMap(key);
			return;
		}
		
		int groupType = getGroupType(n);
		
		for (int i = n - 1;i >= 1;i--) {
			int type = getGroupType(i);
			
			if (type != groupType)
				evenSum(i);
			
		}
		
		if (groupType == ODD_SUM_GROUP)
			extraCount++;
	}
	
	static int getGroupType(int n) {
		return (n % 4 == 1) || (n % 4 == 2) ? ODD_SUM_GROUP : EVEN_SUM_GROUP;
	}

	
}

/*
 n = 1, k = ? => f(1) = 1
 
 n = 2, k = 1 => f(1) = 1, f(2) = 1
 n = 2, k = 2 => f(1) = 2, f(2) = 1
 n = 2, k = 3 => f(1) = 3, f(2) = 1
 n = 2, k = 4 => f(1) = 4, f(2) = 1
 
 n = 3, k = 1 => f(1) = 1, f(2) = 1, f(3) = 1
 n = 3, k = 2 => f(1) = 3, f(2) = 2, f(3) = 1
 n = 3, k = 3 => f(1) = 6, f(2) = 3, f(3) = 1
 n = 3, k = 4 => f(1) = 10, f(2) = 4, f(3) = 1
 
 n = 4, k = 1 => f(1) = 1, f(2) = 1, f(3) = 1, f(4) = 1
 n = 4, k = 2 => f(1) = 4, f(2) = 3, f(3) = 2, f(4) = 1
 n = 4, k = 3 => f(1) = 10, f(2) = 6, f(3) = 3, f(4) = 1
 n = 4, k = 4 => f(1) = 20, f(2) = 10, f(3) = 4, f(4) = 1
 
 n = 5, k = 1 => f(1) = 1, f(2) = 1, f(3) = 1, f(4) = 1, f(5) = 1
 n = 5, k = 2 => f(1) = 5, f(2) = 4, f(3) = 3, f(4) = 2, f(5) = 1
 n = 5, k = 3 => f(1) = 15, f(2) = 10, f(3) = 6, f(4) = 3, f(5) = 1
 n = 5, k = 4 => f(1) = 35, f(2) = 20, f(3) = 10, f(4) = 4, f(5) = 1
 
 */

/*
 *       even(1)    odd(1)    even(2)    odd(2)     even(3)    odd(3)    even(4)     odd(4)
 */
