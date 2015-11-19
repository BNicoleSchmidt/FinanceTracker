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
	public ArrayList<Transaction> loadAll() throws ClassNotFoundException, SQLException {
		System.out.println("You are in loadAll of AppDb.java");

		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

		Connection connection = null;
		Class.forName("com.mysql.jdbc.Driver");

		PropertyResourceBundleSQL prbSQL = new PropertyResourceBundleSQL();
		connection = DriverManager.getConnection(prbSQL.getURL(), prbSQL.getUserName(), prbSQL.getPassword());

		// connection = DriverManager.getConnection(prbSQL.getURL(),
		// prbSQL.getUserName(), prbSQL.getPassword());
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			ResultSet rs;

			try {
				rs = stmt.executeQuery("Select * from records");
				while (rs.next()) {
					Transaction tran = new Transaction();
					tran.setTransactionID(rs.getInt("transactionID"));
					tran.setName(rs.getString("name"));
					tran.setTransactionDate(rs.getString("transactionDate"));
					tran.setTransactionType(rs.getString("transactionType").charAt(0)); // CGJAVA-15
					tran.setAmount(rs.getDouble("Amount")); // CGJAVA-15
					tran.setCategoryName(rs.getString("categoryName"));
					tran.setDescription(rs.getString("description"));
					transactionList.add(tran);
				} // end of while

				int sz = transactionList.size();

				// print out the list
				for (int i = 0; i < sz; i++) {
					System.out.println((transactionList.get(i)).getTransactionID());
					System.out.println((transactionList.get(i)).getName());
					System.out.println((transactionList.get(i)).getTransactionDate());
					System.out.println((transactionList.get(i)).getTransactionType());
					System.out.println((transactionList.get(i)).getAmount());
					System.out.println((transactionList.get(i)).getCategoryName());
					System.out.println((transactionList.get(i)).getDescription());
				}

			} // end of try
			catch (SQLException e) {
				System.out.println("Got the SQLException " + e.getMessage());
				e.printStackTrace();
				throw e;
			}
		} // end of try
		finally

		{
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException se) {
					System.out.println("Got the SQL Exception " + se.getMessage());
					se.printStackTrace();
				}
			}
		}
		return (transactionList);
	}
}
