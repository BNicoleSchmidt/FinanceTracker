package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTransaction {
	public DeleteTransaction(int tranID) {
		// throws ClassNotFoundException SQLException {
		System.out.println("You are in DeleteTransaction of model...java");

		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/roommates", "root", "root");

			int returnStatus;
			PreparedStatement st = connection.prepareStatement("DELETE FROM transactions" + " WHERE transactionID = ?");
			st.setInt(1, tranID);

			try {
				returnStatus = st.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Got the SQL Exception " + e.getMessage());
				e.printStackTrace();
				throw e;
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Got the SQL Exception " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} // catch
			} // if
		} // finally
	}

} // end of DeleteTransaction()
