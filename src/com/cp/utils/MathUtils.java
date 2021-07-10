package com.cp.utils;

import java.math.BigInteger;
import java.util.HashMap;

public class MathUtils {
	
	static int MOD = 1000000007;
	
	long[] fact;
	
	static HashMap<Character, Integer> char_value_map = new HashMap<Character, Integer>();
	
	static {
		char_value_map.put('0', 0);
		char_value_map.put('1', 1);
		char_value_map.put('2', 2);
		char_value_map.put('3', 3);
		char_value_map.put('4', 4);
		char_value_map.put('5', 5);
		char_value_map.put('6', 6);
		char_value_map.put('7', 7);
		char_value_map.put('8', 8);
		char_value_map.put('9', 9);
		char_value_map.put('A', 10);
		char_value_map.put('B', 11);
		char_value_map.put('C', 12);
		char_value_map.put('D', 13);
		char_value_map.put('E', 14);
		char_value_map.put('F', 15);
		char_value_map.put('G', 16);
		char_value_map.put('H', 17);
		char_value_map.put('I', 18);
		char_value_map.put('J', 19);
		char_value_map.put('K', 20);
		char_value_map.put('L', 21);
		char_value_map.put('M', 22);
		char_value_map.put('N', 23);
		char_value_map.put('O', 24);
		char_value_map.put('P', 25);
		char_value_map.put('Q', 26);
		char_value_map.put('R', 27);
		char_value_map.put('S', 28);
		char_value_map.put('T', 29);
		char_value_map.put('U', 30);
		char_value_map.put('V', 31);
		char_value_map.put('W', 32);
		char_value_map.put('X', 33);
		char_value_map.put('Y', 34);
		char_value_map.put('Z', 35);
	}
	
	int gcd(int a, int b){ 
		
		int larger = a > b ? a : b;
		int smaller = a > b ? b : a;
		
	    if (smaller == 0) 
	        return larger; 
	    
	    return gcd(smaller, larger % smaller);  
	      
	}
	
	int gcd(int arr[]) {
		
		int n = arr.length;
		
        int result = arr[0]; 
        for (int i = 1; i < n; i++) 
            result = gcd(arr[i], result); 
  
        return result; 
    }
	
	int lcm(int a, int b) {
		
		return (a * b)/gcd(a, b);
	}
	
	int lcm(int arr[]) {
		
		int n = arr.length;
		
        int result = arr[0]; 
        for (int i = 1; i < n; i++) 
            result = lcm(arr[i], result); 
  
        return result; 
    }
	
	int nCr(int n, int r) {
		
		int numerator = 1;
		int denominator = 1;
		
		for(int i = 1;i <= r;i++) {
			numerator *= (n -i + 1);
			denominator *= i;
		}
		
		return numerator/denominator;
	}
	
	BigInteger nCr_large(int n, int r) {
		
		if (n == 0) return BigInteger.ZERO;
		if (r == 0) return BigInteger.ONE;
		if (r == 1) return new BigInteger(String.valueOf(n));
		
		if (r > n/2) r = n - r;
		
		BigInteger numerator = BigInteger.ONE;
		BigInteger denominator = BigInteger.ONE;
		
		for(int i = 1;i <= r;i++) {
			numerator = numerator.multiply(new BigInteger(String.valueOf(n - i + 1)));
			denominator = denominator.multiply(new BigInteger(String.valueOf(i)));
		}
		
		return numerator.divide(denominator);
	}
	
	int reverse(int n) {
		int reverse = 0;
		
		while(n > 0) {
			reverse = (reverse * 10) + (n % 10);
			n /= 10;
		}
		
		return reverse;
	}
	
	long largeExpnOf2(int exp) {
		if(exp == 0)
			return 1;
		if(exp == 1)
			return 2;
		
		long temp = largeExpnOf2(exp/2);
		temp = (temp * temp) % MOD;
		if(exp % 2 == 1)
			temp = (temp * 2) % MOD;
		
		
		return temp;
		
	}
	
	long convertToBase10(String s, int base) {
		long ans = 0;
		if(base == 10)
			return Long.valueOf(s);
		
		int power = 0;
		
		for(int i = s.length() - 1;i >= 0;i--) {
			char c = s.charAt(i);
			ans += Math.pow(base, power) * char_value_map.get(c);
		}
		
		return ans;
	}
	
	static long[] getFirstNCatalanNumbers(int n) {
		
		long[] catalans = new long[n + 1];
		catalans[0] = 1;
		catalans[1] = 1;
		
		for (int i = 2;i <= n;i++) {
			long sum = 0;
			for (int j = 0;j < i/2;j++) {
				sum += (2 * catalans[j] * catalans[i - 1- j]) % 1000000009;
			}
			
			if (i % 2 == 1)
				sum += (catalans[i/2] * catalans[i/2])  % 1000000009;
			
			catalans[i] = sum % 1000000009;
		}
		
		return catalans;
	}
	
	long power(long x, long y, long p) {
		 
        long res = 1;

        x = x % p;
 
        while (y > 0) {
 
            if (y % 2 == 1)
                res = (res * x) % p;
 
            y = y >> 1;
            x = (x * x) % p;
        }
 
        return res;
    }
    
    long modInverse(long n, long p) {
        return power(n, p - 2, p);
    }
    
    long nCrModP(long n, long r, long p) {

		if (n < r) return 0;
		if (n == 0) return 0;
    	if (r == 0) return 1;
    	if (r == 1) return n;
    	
    	if (r > n/2) return nCrModP(n, n - r, p);
    	
    	long result = (fact[(int)n] * modInverse(fact[(int)r], p) % p * modInverse(fact[(int)n - (int)r], p) % p) % p;
		
		return result;
	}
    
    void calculateFactorials(long n, long p) {
    	fact = new long[(int)n + 1];
    	fact[0] = 1;
    	
    	for (int i = 1; i <= n; i++)
			fact[i] = fact[i - 1] * i % p;
    }
    
    void primeFactors(int n) {
    	
        while (n%2==0) {
            System.out.print(2 + " ");
            n /= 2;
        }

        for (int i = 3; i <= Math.sqrt(n); i+= 2) {
            while (n%i == 0) {
                System.out.print(i + " ");
                n /= i;
            }
        }

        if (n > 2)
            System.out.print(n);
    }
    
    long addMod(long a, long b, long m) {
    	return (a % m + b % m) % m;
    }
    
    long mulMod(long a, long b, long m) {
    	return (a % m * b % m) % m;
    }
    
    long getTrailingZeroesInFactN(long n) {
    	
    	long pow5 = 5;
    	long zeroes = 0;
    	
    	while(pow5 <= n) {
    		zeroes += n/pow5;
    		pow5 *= 5;
    	}
    	
    	return zeroes;
    }

}
