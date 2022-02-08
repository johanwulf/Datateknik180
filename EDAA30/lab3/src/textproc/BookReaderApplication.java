package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BookReaderApplication {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(new File("C:\\Users\\Wulf\\Skrivbord\\EDAA30\\lab1\\nilsholg.txt"));
		Scanner s2 = new Scanner(new File("C:\\Users\\Wulf\\Skrivbord\\EDAA30\\lab1\\undantagsord.txt"));

		Set<String> banned = new HashSet<String>();

		while (s2.hasNext()) {
			String word = s2.next().toLowerCase();
			banned.add(word);
		}

		GeneralWordCounter wordCount = new GeneralWordCounter(banned);

		s.findWithinHorizon("\uFEFF", 1);
		s.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+");

		while (s.hasNext()) {
			String word = s.next().toLowerCase();

			wordCount.process(word);
		}

		s.close();
		s2.close();

		BookReaderController bookRead = new BookReaderController(wordCount);
	}
}
