package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbDemoMain {

	public static Connection getDbConnection() throws SQLException, ClassNotFoundException {

		Connection connection = null;
		try {
			// We get the connection in the try and close it in the finally
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/roommates", "root", "ammananna");

		} catch (SQLException se) {
			// This reports any exceptions that occurred during querying,
			// including database went down.
			System.out.println("Got a SQL exception: " + se.getMessage());
			se.printStackTrace();
		} finally {
			// This finally will ALWAYS run guaranteeing that the connection
			// gets closed even when line 27 executeQuery() throws an exception.
			if (connection != null) {
				try {
					System.out.println("Connection works");
					connection.close();
				} catch (SQLException se) {
					// we don't need to care about this exception.
				}
			}
		}
		return connection;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		getDbConnection();

	}

}
