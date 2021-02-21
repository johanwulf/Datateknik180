package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Holgersson {

	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
		long t0 = System.nanoTime();
		Scanner s = new Scanner(new File("C:\\Users\\Wulf\\Skrivbord\\EDAA30\\lab1\\nilsholg.txt"));
		Scanner s2 = new Scanner(new File("C:\\Users\\Wulf\\Skrivbord\\EDAA30\\lab1\\undantagsord.txt"));
		List<TextProcessor> procList = new ArrayList<>();
		Set<String> banned = new HashSet<String>();
		
		procList.add(new SingleWordCounter("nils"));
		procList.add(new SingleWordCounter("norge"));
		procList.add(new MultiWordCounter(REGIONS));
		
		while(s2.hasNext()) {
			String word = s2.next().toLowerCase();		
			banned.add(word);
		}
		
		procList.add(new GeneralWordCounter(banned));
		
		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+");

		while (s.hasNext()) {
			String word = s.next().toLowerCase();
			
			for(TextProcessor p : procList) {
				p.process(word);
			}
		}

		s.close();
		s2.close();

		for(TextProcessor p : procList) {
			p.report();
		}
		long t1 = System.nanoTime();
		System.out.println("tid: " + (t1 - t0) / 1000000.0 + " ms");
	}
}