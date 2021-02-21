package lpt;

import java.util.ArrayList;

public class TestScheduler {

	public static void main(String[] args) {
		Machine[] m = new Machine[3];
		for (int i = 0; i < m.length; i++) {
			m[i] = new Machine(i + 1);
		}
		
		ArrayList<Job> jobList = new ArrayList<Job>();
		String [] names = {"j1", "j2", "j3", "j4", "j5", "j6", "j7"};
		int[] times = {2, 14, 4, 16, 6, 5, 3};
		for (int i = 0; i < names.length; i++) {
			jobList.add(new Job(names[i], times[i]));
		}
		
		Scheduler s = new Scheduler(m);
		s.makeSchedule(jobList);
		s.printSchedule();
	}

}
