package com.cp.utils;

public class StringUtils {
	
	static int lcs_length(String X, String Y) {
		
		int m = X.length();
		int n = Y.length();
        int[][] L = new int[m+1][n+1];
        
        for (int i=0; i<=m; i++) { 
            for (int j=0; j<=n; j++){ 
                if (i == 0 || j == 0) 
                    L[i][j] = 0; 
                else if (X.charAt(i-1) == Y.charAt(j-1)) 
                    L[i][j] = L[i-1][j-1] + 1; 
                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]); 
            } 
        }
        
        return L[m][n];
		
	}
	
	 static String lcs(String X, String Y){
		 
		int m = X.length();
		int n = Y.length();
        int[][] L = new int[m+1][n+1];
  
        for (int i=0; i<=m; i++) { 
            for (int j=0; j<=n; j++){ 
                if (i == 0 || j == 0) 
                    L[i][j] = 0; 
                else if (X.charAt(i-1) == Y.charAt(j-1)) 
                    L[i][j] = L[i-1][j-1] + 1; 
                else
                    L[i][j] = Math.max(L[i-1][j], L[i][j-1]); 
            } 
        }
        
        if(L[m][n] <= 0)
        	return "";
   
        int index = L[m][n];
 
        char[] lcs = new char[index+1]; 
   
        int i = m, j = n; 
        while (i > 0 && j > 0){ 
            if (X.charAt(i-1) == Y.charAt(j-1)){  
                lcs[index-1] = X.charAt(i-1);  
                i--;  
                j--;  
                index--;      
            }
            else if (L[i-1][j] > L[i][j-1]) 
                i--; 
            else
                j--; 
        }
        
        return String.valueOf(lcs);
    }
	 
	public static void main(String[] args) {
		String s = lcs("ABCD", "ABCD");
		print(s);
		s = lcs("AA", "BB");
		print(s);
		
	}
	
	static void print(String s) {
		if(s.equals(""))
			System.out.println("No LCS");
		else
			System.out.println(s);
	}

}
