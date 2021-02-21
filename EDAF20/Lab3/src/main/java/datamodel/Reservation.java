package datamodel;

// Container for the booking data
public class Reservation {
	private int bookingId;
	private String movieTitle;
	private String performanceDate;
	private String theatreName;
	
	public Reservation() {
		this(-1, "N/A", "N/A", "N/A");
	}
	
	// constructor
	public Reservation(int bookingId, String movieTitle, String performanceDate, String theatreName) {
		this.bookingId = bookingId;
		this.movieTitle = movieTitle;
		this.performanceDate = performanceDate;
		this.theatreName = theatreName;
	}

	// getters
	public int getBookingId() {
		return bookingId;
	}
	
	public String getMovieTitle() {
		return movieTitle;
	}
	
	public String getPerformanceDate() {
		return performanceDate;
	}
	
	public String getTheatreName() {
		return theatreName;
	}
}
