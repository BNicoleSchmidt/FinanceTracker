package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppDb {

	// public static void main(String args[]) throws ClassNotFoundException,
	// SQLException {

	// update: changed the second catch to ClassNotFound, deleted throws ~ella
	// public ArrayList<Transaction> getData() {
	public ArrayList<Transaction> loadAll() {
		// throws ClassNotFoundException SQLException {
		System.out.println("You are in loadAll of AppDb.java"); // mmgg

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/roommates", "root", "LaLaland2012");

			Statement stmt = connection.createStatement();
			ResultSet rs;

			try {

				rs = stmt.executeQuery("Select * from records");
				while (rs.next()) {
					Transaction tran = new Transaction();
					tran.setTransactionID(rs.getInt("transactionID"));
					tran.setName(rs.getString("name"));
					// tran.setDate(rs.getDate("transactionDate"));
					tran.setTransactionDate(rs.getString("transactionDate"));
					tran.setCategoryName(rs.getString("categoryName"));
					transactionList.add(tran);
				}

				int sz = transactionList.size();

				// print out the list
				for (int i = 0; i < sz; i++) {
					System.out.println((transactionList.get(i)).getTransactionID());
					System.out.println((transactionList.get(i)).getName());
					System.out.println((transactionList.get(i)).getTransactionDate());
					System.out.println((transactionList.get(i)).getCategoryName());
				}

			}

			catch (SQLException e) {
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

				}
			}
		}
		return transactionList;
	}
}
