package lpt;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
	private Machine[] machines;
	/** Skapar en schemaläggare för maskinerna 
		i vektorn machines. */
	
	public Scheduler(Machine[] machineArray) {
		this.machines = machineArray;
	}
	
	/* Returnerar den maskin som har minst att göra. */
	private Machine machineWithLeastToDo() {
		int min = Integer.MAX_VALUE;
		int minPos = -1;
		for (int i = 0; i < machines.length; i++) {
			int totalTime = machines[i].getScheduledTime();
			if (totalTime < min) {
				min = totalTime;
				minPos = i;
			}
		}
		return machines[minPos];
	}
	
	/** Fördelar jobben i listan jobs på maskinerna. */
	public void makeSchedule(List<Job> jobs) {
		List<Job> tempJobList = new ArrayList<>(jobs);
		tempJobList.sort(new DescTimeComp());
		for (Job j : tempJobList) {
			Machine m = machineWithLeastToDo();	
			m.assignJob(j);
		}	
	}
	
	/** Tar bort alla jobb från maskinerna. */
	public void clearSchedule() {
		for(int i = 0; i < machines.length; i++) {
			machines[i].clearJobs();
		}	
	}

	/** Skriver ut maskinernas scheman. */
	public void printSchedule() {
		for (int i = 0; i < machines.length; i++) {
			System.out.println(machines[i]);
		}
	}
}
