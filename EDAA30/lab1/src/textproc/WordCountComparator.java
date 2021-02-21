package textproc;
import java.util.*;
import java.util.Map.Entry;

public class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {
	
	/**
	 * Compares the values o1 and o2
	 * @param o1 the first value to be compared
	 * @param o2 the second value to be compared
	 * @return o1 compared to o2
	 */
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		int res = -Integer.compare(o1.getValue(), o2.getValue());
		if(res == 0) {
			return o1.getKey().compareTo(o2.getKey());
		} else {
			return res;
		}
	}
}
