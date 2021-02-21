package datamodel;

import java.sql.*;
/**
 * Database is a class that specifies the interface to the 
 * movie database. Uses JDBC and the MySQL Connector/J driver.
 */
public class Database {
    /** 
     * The database connection.
     */
    private Connection conn;
        
    /**
     * Create the database interface object. Connection to the database
     * is performed later.
     */
    public Database() {
        conn = null;
    }
       
    /* --- TODO: Change this method to fit your choice of DBMS --- */
    /** 
     * Open a connection to the database, using the specified user name
     * and password.
     *
     * @param userName The user name.
     * @param password The user's password.
     * @return true if the connection succeeded, false if the supplied
     * user name and password were not recognized. Returns false also
     * if the JDBC driver isn't found.
     */
    public boolean openConnection(String userName, String password) {
        try {
        	// Connection strings for included DBMS clients:
        	// [MySQL]       jdbc:mysql://[host]/[database]
        	// [PostgreSQL]  jdbc:postgresql://[host]/[database]
        	// [SQLite]      jdbc:sqlite://[filepath]
        	
        	// Use "jdbc:mysql://puccini.cs.lth.se/" + userName if you using our shared server
        	// If outside, this statement will hang until timeout.
            conn = DriverManager.getConnection 
                ("jdbc:mysql://localhost:3306/lab3", userName, password);
        }
        catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Try to log in with the username
     * @param userName The user name
     * @return true if there was such user in db, otherwise no
     */
    public boolean login(String userName) {
        String query = "SELECT username FROM users WHERE username = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, userName);
            resultSet = statement.executeQuery();

            return resultSet.next();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all movies from databas
     * @return ResultSet of movies
     */
    public ResultSet getMovies() {
        String query = "SELECT * FROM movie";
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * @return ResultSet of dates where the movie is shown
     */
    public ResultSet getDates(String movieName) {
        String query = "SELECT date FROM screening WHERE movie = ?";
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, movieName);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Close the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        conn = null;
        
        System.err.println("Database connection closed.");
    }
        
    /**
     * Check if the connection to the database has been established
     *
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }
	
  	public Show getShowData(String mTitle, String mDate) {
        /* --- TODO: add code for database query --- */
        String query = "SELECT * FROM screening WHERE movie = ? AND date = ?";
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, mTitle);
            statement.setString(2, mDate);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return new Show(mTitle, mDate, resultSet.getString("theatre"), Integer.parseInt(resultSet.getString("availableSeats")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return null;
    }
    
    public ResultSet getReservations(String username) {
        String query = "SELECT * FROM ticket WHERE customer = ?";
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int bookTicket(String movie, String date, String user) {
        String query = "SELECT * FROM screening WHERE date = ? AND movie = ?";
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, movie);
            resultSet = statement.executeQuery();
            int avSeats = 0;
            String theatre = null;

            if(resultSet.next()) {
                avSeats = resultSet.getInt("availableSeats");
                theatre = resultSet.getString("theatre");
            }

            if (avSeats > 0) {
                query = "INSERT into ticket (customer, movie, theatreName, date) values (?, ?, ?, ?)";
                statement = conn.prepareStatement(query);
                statement.setString(1, user);
                statement.setString(2, movie);
                statement.setString(3, theatre);
                statement.setString(4, date);
                statement.executeUpdate();

                query = "SELECT * FROM ticket ORDER BY reservationNumber DESC LIMIT 1";
                statement = conn.prepareStatement(query); 
                resultSet = statement.executeQuery();
                int resNumber = 0;

                if (resultSet.next()) {
                    resNumber = resultSet.getInt("reservationNumber");
                }

                query = "UPDATE screening SET availableSeats = availableSeats-1 WHERE date = ? AND movie = ?";
                statement = conn.prepareStatement(query);
                statement.setString(1, date);
                statement.setString(2, movie);
                statement.executeUpdate();

                return resNumber;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
