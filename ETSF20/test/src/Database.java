import java.sql.*;

/*
 * Class for managing the database.
 */
public class Database {

	// If you have the mysql server on your own computer use "localhost" as server
	// address.
	private static String databaseServerAddress = "vm23.cs.lth.se";
	private static String databaseUser = "jo4571wu"; // database login user
	private static String databasePassword = "mdxntqqy"; // database login password
	private static String database = "jo4571wu"; // the database to use, i.e. default schema
	Connection conn = null;

	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Necessary on Windows computers
			conn = DriverManager.getConnection("jdbc:mysql://" + databaseServerAddress + "/" +
					database, databaseUser, databasePassword);

			// Display the contents of the database in the console.
			// This should be removed in the final version
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Respondents");
			while (rs.next()) {
				String name = rs.getString("name");
				System.out.println(name);
			}

			stmt.close();

		} catch (SQLException e) {
			printSqlError(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean addName(String name) {
		boolean resultOK = false;
		PreparedStatement ps = null;
		try {
			String sql = "insert into Respondents (name) values(?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.executeUpdate();
			resultOK = true;
			ps.close();
		} catch (SQLException e) {
			resultOK = false; // one reason may be that the name is already in the database
			if (e.getErrorCode() == 1062 && e.getSQLState().equals("23000")) {
				// duplicate key error
				System.out.println(name + " already exists in the database");
			} else {
				printSqlError(e);
			}
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					printSqlError(e);
				}
			}
		}
		return resultOK;
	}

	public void addResults(String name, int first, int second, int third, int fourth) {
		PreparedStatement ps = null;

		try {
			String sql = "insert into Answers (Name, FirstAnswer, SecondAnswer, ThirdAnswer, FourthAnswer) values (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, first);
			ps.setInt(3, second);
			ps.setInt(4, third);
			ps.setInt(5, fourth);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public void addInfo(String name, String s1, String s2) {
		PreparedStatement ps = null;
		try {
			String sql = "insert into Project (name, projectName, projectClient) values (?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, s1);
			ps.setString(3, s2);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	public String showDatabaseEntries() {
		PreparedStatement ps = null;
		StringBuilder sb = new StringBuilder();
		Statement stmt = null;
		String sql = "SELECT Respondents.NAME, Project.projectName, Project.projectClient, Answers.FirstAnswer, Answers.SecondAnswer, Answers.ThirdAnswer, Answers.FourthAnswer FROM ((Respondents JOIN Project ON Respondents.NAME = Project.name) JOIN Answers ON Respondents.NAME = Answers.name)";
		sb.append("Name - Project name - Project client - 1st answer - 2nd answer - 3rd answer - 4th answer <br>");
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sb.append(rs.getString("Respondents.NAME") + " " + rs.getString("Project.projectName") + " "
						+ rs.getString("Project.projectClient") + " " + rs.getString("Answers.FirstAnswer") + " "
						+ rs.getString("Answers.SecondAnswer") + " " + rs.getString("Answers.ThirdAnswer") + " "
						+ rs.getString("Answers.FourthAnswer") + "<br>");
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return sb.toString();
	}

	private void printSqlError(SQLException e) {
		System.out.println("SQLException: " + e.getMessage());
		System.out.println("SQLState: " + e.getSQLState());
		System.out.println("VendorError: " + e.getErrorCode());
		e.printStackTrace();
	}

}
