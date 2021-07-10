import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Solution {
	
	static BigInteger MOD = new BigInteger("1000000007");
	static long MODULO = 1000000007;
	
	HashMap<Long, Long> cache = new HashMap<Long, Long>();
    
    public static void main(String[] args)  throws IOException{
        
        Solution sol = new Solution();
        sol.execute();
    }
    
    void execute() throws IOException {
        BufferedReader br = getBufferedReader();
        
        // int t = Integer.valueOf(br.readLine().trim());
        
        int t = 1;
        
        while(t > 0) {
        	
        	String s = br.readLine().trim();
        	
        	String[] arr = s.split(" ");
        	BigInteger k = new BigInteger(String.valueOf(arr[0]));
        	BigInteger x = new BigInteger(String.valueOf(arr[1]));
        	
        	
        	BigInteger result = solve(k, x);
        	
        	System.out.println(result);
        	
        	t--;

        }
    }
    
    BigInteger solve(BigInteger k, BigInteger x) {
    	
    	if (x.compareTo(k) < 0) return BigInteger.ONE;
    	
    	BigInteger quotient = x.divide(k);
    	BigInteger numBlocksBefore = quotient.divide(k);
    	BigInteger lastNumberOfPrevBlock = k.multiply(sumTill(numBlocksBefore));
    	BigInteger blockNumOfX = numBlocksBefore.add(BigInteger.ONE);
    	
    	BigInteger blockDiff = solve(k, blockNumOfX.subtract(BigInteger.ONE).multiply(k));
    	
    	BigInteger offset = quotient.subtract(k.multiply(numBlocksBefore)).add(BigInteger.ONE);
    	BigInteger result = lastNumberOfPrevBlock.add(blockDiff.multiply(offset));
    	
    	return result;
    }
    
    BigInteger sumTill(BigInteger n) {
    	BigInteger TWO = getBigIntger(2);
    	BigInteger nPlusOne = n.add(BigInteger.ONE);
    	
    	return n.multiply(nPlusOne).divide(TWO);
    }
    
    BigInteger getBigIntger(long n) {
    	return new BigInteger(String.valueOf(n));
    }
    
    
    void printArray(int[] arr){
    	String sep = "";
        for(int i = 1;i < arr.length;i++) {
            System.out.print(sep + arr[i]);
            sep = " ";
        }
        System.out.println();
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
    
    int[] convertToIntArray1Based(String s, int n) {
        int current_index = 0, input_start = 0, index = 1;
        int[] arr = new int[n + 1];
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

    private static BufferedReader getBufferedReader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
