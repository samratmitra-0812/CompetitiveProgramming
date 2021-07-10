import java.io.BufferedReader;
import java.io.FileReader;

public class OutputComparator {
	
public static void main(String[] args) throws Exception {
		
		BufferedReader br1 = new BufferedReader(new FileReader("/Users/z003kxj/Hackerrank/src/out.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("/Users/z003kxj/Hackerrank/src/expectedout.txt"));
		
		String s1, s2;
		int linesRead = 0;
		
		while ((s1 = br1.readLine()) != null && (s2 = br2.readLine()) != null) {
			linesRead++;
			if (!s1.equals(s2))
				System.out.println(s1 + " : " + s2);
		}
		
		System.out.println("Lines Read: " + linesRead);
		
		br1.close();
		br2.close();
	}

}
