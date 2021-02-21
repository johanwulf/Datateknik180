package lpt;

public class Job {
	private String name;
	private int time;

	/** Skapar ett jobb med namnet name som tar 
	    tiden time att utföra. */
	public Job(String name, int time) {
		this.name = name;
		this.time = time;
	}
	
	/** Returnerar jobbets tidsåtgång. */
	public int getTime() {
		return time;
	}
	
	/** Returnerar en sträng som representerar jobbet
	 	på formen namn (tidsåtgång). */
	public String toString() {
		return name  + " " + time;
	}	
}
