package lpt;
import java.util.*;

public class labb {

	public static void main(String[] args) {
		int n;
		double x, m, s;
		double sumx = 0;
		double sumx2 = 0;
		
		Scanner scan = new Scanner(System.in);
		
		n = scan.nextInt();
		for(int i = 1; i <= n; i++) {
			x = scan.nextDouble();
			sumx = sumx+x;
			sumx2 = sumx2+x*x;
		}
		
		m = sumx/n;
		System.out.println("Medelvärde" + m);
		
		if(n>5) {
			s = Math.sqrt((sumx2-n*m*m)/(n-1));
			System.out.println(s);
		}
	}

}
