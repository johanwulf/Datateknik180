package datamodel;

// Container for the database data
/* --- TODO: Modify as needed --- */

public class Show {
		// attributes associated with database columns
		private String title;
		private String date;
		private String venue;
		private Integer freeSeats;
		
		// constructor for "no show"
		public Show() {
			init("","","",-1);
		}
		
		// constructor defining all content
		public Show(String t, String d, String v, Integer fs) {
			init(t,d,v,fs);
		}
		
		// constructor defining only the title
		public Show(String t) {
			init(t,"","",-1);
		}
		
		// all constructors use this
		private void init(String t, String d, String v, Integer fs) {
			title = t; date = d; venue = v; freeSeats = fs;
		}
		
		// getters
		public String getTitle() { return title; }
		public String getDate() { return date; }
		public String getVenue() { return venue; }
		public Integer getSeats() { return freeSeats; }

}
