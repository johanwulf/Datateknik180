package lpt;

import java.util.Comparator;

public class DescTimeComp implements Comparator<Job> {

	@Override
	public int compare(Job j1, Job j2) {
		return j2.getTime() - j1.getTime();
	}

}
