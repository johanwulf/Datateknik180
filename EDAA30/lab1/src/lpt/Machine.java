package lpt;

import java.util.ArrayList;

public class Machine {
	private int nbr;
	private ArrayList<Job> jobs;
	private int scheduledTime;
	
	/** Skapar maskin nr nbr. */
	public Machine(int nbr) {
		this.nbr = nbr;
		jobs = new ArrayList<Job>();
		scheduledTime = 0;
	}

	/** Tilldelar maskinen jobbet j. */
	public void assignJob(Job j) {
		jobs.add(j);
		scheduledTime += j.getTime();
	}
	
	/** Tar bort alla jobb från maskinen. */
	public void clearJobs() {
		jobs.clear();
		scheduledTime = 0;
	}

	/** Tar bort och returnerar nästa jobb som maskinen ska utföra. 
	 	Returnerar null om maskinen inte har några jobb. */
	public Job getNextJob() {
		if (jobs.isEmpty()) {
			return null;
		}
		scheduledTime -= jobs.get(0).getTime();
		return jobs.remove(0);
	}
	
	/** Tar reda på den totala schemalagda tiden för 
	    maskinens jobb. */
	public int getScheduledTime() {
		return scheduledTime;
	}
	
	/** Returnerar en sträng som innehåller maskinens nr,  
	   total schemalagd tid samt maskinens
       schemalagda jobb inom [] med kommatecken mellan.
       Exempel: 2 17 [j2 (14), j7 (3)] */
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Maskin ");
		b.append(nbr);
		b.append(" har total schemalagd tid ");
		b.append(getScheduledTime());
		b.append(' ');
		b.append('[');
		for (Job j : jobs) {
			b.append(j);
			b.append(", ");
		}
		if (jobs.size() != 0) {
			b.deleteCharAt(b.length() - 1);
			b.deleteCharAt(b.length() - 1);
		}
		b.append(']');
		return b.toString();
	}	
}
