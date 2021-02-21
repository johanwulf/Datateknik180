

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Results.
 */
@WebServlet("/Results")
public class Results extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define states 
	private static final int NEED_NAME = 0;
	private static final int NEED_PROJECT_INFO = 1;
	private static final int NEED_PROJECT_DATA = 2;
	
	private FormGenerator formGenerator = new FormGenerator(); 
	
	static private Database db = new Database();
	
		
    /**
     * Default constructor. 
     */
    public Results() {
    }
    
    /*
     * Checks first if name includes characters (i.e. is longer than zero characters) and then 
     * if so if the name is possible to add to 
     * the database. It is not possible to add an already existing name to the database. 
     */
    boolean nameOk(String name){
    	boolean result = !name.equals("");
    	if (result)
    		result = db.addName(name);
    	return result;
    }
    
    /*
     * Checks if a value entered as answer is OK. Answers should be between 1 and 10.
     */
    boolean valueOk(int value){
    	return value > 0 && value <11;
    }
        
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get the session
		HttpSession session = request.getSession(true);
		
		int state = 0;
		String name;
		boolean sessionShouldBeEnded = false;
		
		// Decide which state the session is in
		if (session.isNew())
			state = NEED_NAME;
		else {
			state = (Integer) session.getAttribute("state");
		}
		
		// Get a writer, which will be used to write the next page for the user 
		PrintWriter out = response.getWriter();
		
		// Start the page, print the HTML header and start the body part of the page
		out.println("<html>");
        out.println("<head><title> FANTASTIC WEB APPLICATION </title></head>");
        out.println("<body>");
        out.println("<p>" + db.showDatabaseEntries() + "</p>");
        // Save the state in the session until next time doGet is requested
        session.setAttribute("state", state);
       
        // Print the end of the HTML-page
        out.println("</body></html>");
        
        if (sessionShouldBeEnded)
        	session.invalidate();        		    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
