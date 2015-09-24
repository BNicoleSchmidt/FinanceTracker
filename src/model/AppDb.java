package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class AppDb {
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/roommates", "root", "ammananna");

			PreparedStatement preparedstatement = null;

			String query = "insert into roommates.Transactions(transactionID, transactionDate, UserID,transactionType, amount, categoryID, description)"
					+ "values(?, ?, ?, ?, ?, ?, ?)";
			// 1, 2, 3, 4
			try {
				preparedstatement = connection.prepareStatement(query);
				preparedstatement.setInt(1, 6); // (parameterindex, x)
				preparedstatement.setString(2, "2015-07-14");
				preparedstatement.setInt(3, 4);
				preparedstatement.setString(4, "C");
				preparedstatement.setFloat(5, 400);
				preparedstatement.setInt(6, 4);
				preparedstatement.setString(7, "Description 4");
				int i = preparedstatement.executeUpdate();
				if (i == 1) {
					System.out.println("record added successfully");
				}
			} finally {
				if (preparedstatement != null) {
					preparedstatement.close();
				}
			}
		} catch (SQLException e) {
			System.out.println("Got the SQL Exception " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
				}
			}
		}
	}
}
