package textproc;

import java.util.*;

public class MultiWordCounter implements TextProcessor {
	Map<String, Integer> map = new TreeMap<String, Integer>();;
	
	/**
	 * Adds the entries of inputVector to the TreeMap map
	 * @param inputVector vector of the words to be counted
	 */
	public MultiWordCounter(String[] inputVector) {
		for(String p : inputVector) {
			map.put(p, 0);
		}
	}
	
	/**
	 * Checks the TreeMap for any entries containing the word w, increase the value with 1 if found
	 * @param w the word we are looking for
	 */
	public void process(String w) {
		map.replace(w, map.getOrDefault(w, 0)+1);
	}

	/*
	 * Prints a line with the key and value for each entry in the TreeMap
	 */
	public void report() {
		map.forEach((k, v) -> System.out.println("Nyckel: " + k + " Antal förekomster: " + v));
	}
}
 