// Description: This class stores and manages data for a single transaction record.
// It provides the accessor and mutator methods.

// Change log:
// 9/18/2015 EB
// - Fixed logic error in the setName method: check the userName instead of mName.
// - Refactored the name from TransactionModel to Transaction.

package model;

//this s/b in a helper file
// source rosettacode.org/wiki/Determine_if_a_string_is_numeric
//
// public static boolean isNumeric(String inputData) {
// return inputData.matches("[-+]?\\d+(\\.\\d+)?"); }
//public static boolean isNumeric(String inputData)
//{
//	  return inputData.matches("?\\d+\\.\\d+?");
//}
//
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

// Responsible for the database; database modifications, and the sending
// and receiving of data.

public class Transaction {
	private int mTransactionID;

	private String mTransactionDate;
	private int mUserID;
	private String mName; // user table
	private char mTransactionType;
	private double mAmount;
	private int mCategoryID;
	private String mCategoryName; // category table
	private String mDescription;

	// hidden values
	private int mHTransactionID;
	private int mHUserID;
	private String mHName;
	private int mHCategoryID;
	private String mHCategoryName;

	private String query;

	private static final String tableQuery = ("SELECT transactionID, transactionDate, t.userID,"
			+ " u.name, transactionType, amount, " + "categoryID, c.categoryName, " + "description, "
			+ "WHERE users.userID = transactions.userID, " + "categories.categoryID = transaction.categoryID;"
			+ "FROM users as u, transactions as t, categories as c" + "WHERE u.userID = t.userID, "
			+ "      c.categoryID = t.categoryID;");

	// constructor
	Transaction() {
		mTransactionID = 0; // should this be null. what happens on an insert if
							// this field is populated.

		mTransactionDate = "1/1/2000";
		mUserID = 0;
		mName = " ";
		mTransactionType = ' '; // CGJAVA-15
		mAmount = 0.00;
		mCategoryID = 0;
		mCategoryName = " ";
		mHCategoryName = " ";
		mDescription = " ";

		mHTransactionID = 0;
		mHUserID = 0;
		mHName = " ";
		mHCategoryID = 0;
	};

	// end of constructor

	// get methods
	public int getTransactionID() {
		return mTransactionID;
	}

	public int getUserID() {
		return mUserID;
	}

	public String getName() {
		return mName;
	}

	public String getHiddenName() {
		return mHName;
	}

	public String getTransactionDate() {
		return mTransactionDate;
	}

	public char getTransactionType() {
		return mTransactionType;
	}

	public double getAmount() {
		return mAmount;
	}

	public int getCategoryID() {
		return mCategoryID;
	}

	public String getCategoryName() {
		return mCategoryName;
	}

	public String getHiddenCategoryName() {
		return mHCategoryName;
	}

	public String getDescription() {
		return mDescription;
	}

	// set methods

	// transactionID is a hidden field
	// an auto increment field on insert
	void setTransactionID(int tranID) {
		this.mTransactionID = tranID;
		// do nothing
	}

	// uID is a hidden field
	// auto increment field on insert
	// do NOT use set when inserting Users into the db
	// set when userName changes
	void setUserID(int uID) {
		this.mUserID = uID;
	}

	// this logic will not work --
	void setName(String userName) {
		if (userName != " ") {
			this.mName = userName;
		} else {
			// display error msg TODO mg
			// "Name is a required field."
		}
	}

	// change field to a data type date
	// TODO mg
	void setTransactionDate(String tranDate) {
		if (tranDate != null) {
			this.mTransactionDate = tranDate;
		} else {
			// display error msg TODO mg
			// "Transaction date is a required field."
		}
	}

	void setTransactionType(char tranType) {
		// CGJAVA-15
		this.mTransactionType = tranType;
	}

	// cheesy edit
	// test TODO mg

	void setAmount(double amt) {
		if (amt >= 0.00 && amt <= 9999999.99) {
			this.mAmount = amt;
		} else {
			// display error msg TODO mg

			// "Amount is a required field and must be a positive number."
		}
	}

	void setCategoryID(int cID) {
		if (cID > 0 && cID < 99999) {
			this.mCategoryID = cID;
		} else {
			// display error msg TODO mg
			// "A category is required."
		}
	}

	// this logic will not work
	// see setUserID

	// overloaded method
	// don't know how this is going to be used ... mg TODO
	void setCategoryID(int cID, String categoryName) {
		if (cID > 0 && cID < 99999) {
			this.mCategoryID = cID;
		} else {
			// display error msg TODO mg
			// "A category is required."
		}
	}

	void setCategoryName(String catName) {
		this.mCategoryName = catName;
	}

	void setDescription(String desc) {
		this.mDescription = desc;
	}

	// CGJAVA-15
	public static String totalDeposits(ArrayList<Transaction> transactionList) {
		double total = 0;

		int sz = transactionList.size();
		for (int i = 0; i < sz; i++) {
			System.out.println((transactionList.get(i)).getTransactionType());
			System.out.println((transactionList.get(i)).getAmount());
			if (transactionList.get(i).getTransactionType() == 'D') {
				total += ((transactionList.get(i)).getAmount());
				System.out.println((transactionList.get(i)).getAmount());
			} // end of if
		}
		String dTotal = new DecimalFormat("0.00").format(total);
		return dTotal;
	}

	// CGJAVA-12
	public static String totalWithdrawals(ArrayList<Transaction> transactionList) {
		double total = 0;

		int sz = transactionList.size();
		for (int i = 0; i < sz; i++) {
			System.out.println((transactionList.get(i)).getTransactionType());
			System.out.println((transactionList.get(i)).getAmount());
			if (transactionList.get(i).getTransactionType() == 'W') {
				total += ((transactionList.get(i)).getAmount());
				System.out.println((transactionList.get(i)).getAmount());
			} // end of if
		}
		String wTotal = new DecimalFormat("0.00").format(total);
		return wTotal;
	}

	// when a row is selected
	// load one row to jfields in
	// jpanel1/TransactionUI

	/**
	 * public static ArrayList<Transaction> loadAll() { // throws
	 * ClassNotFoundException SQLException {
	 *
	 * ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
	 *
	 * Connection connection = null; try {
	 * Class.forName("com.mysql.jbdc.Driver"); connection =
	 * DriverManager.getConnection("jdbc:mysql://localhost:3306/roommates",
	 * "root", "LaLaland2012");
	 *
	 * Statement stmt = connection.createStatement(); ResultSet rs;
	 *
	 * try {
	 *
	 * rs = stmt.executeQuery("Select * from records"); while (rs.next()) {
	 * Transaction tran = new Transaction();
	 * tran.setTransactionID(rs.getInt("transactionID"));
	 * tran.setName(rs.getString("name"));
	 * tran.setTransactionDate(rs.getString("transactionDate"));
	 * transactionList.add(tran); }
	 *
	 * int sz = transactionList.size();
	 *
	 * // print out the list - code test for (int i = 0; i < sz; i++) {
	 * System.out.println((transactionList.get(i)).getTransactionID());
	 * System.out.println((transactionList.get(i)).getName());
	 * System.out.println((transactionList.get(i)).getTransactionDate()); } //
	 * for
	 *
	 * } // try
	 *
	 * catch (SQLException e) { System.out.println("Got the SQL Exception " +
	 * e.getMessage()); e.printStackTrace(); throw e; } } catch (SQLException |
	 * ClassNotFoundException e) { System.out.println("Got the SQL Exception " +
	 * e.getMessage()); e.printStackTrace(); throw new RuntimeException(e); }
	 * finally { if (connection != null) { try { connection.close(); } catch
	 * (SQLException se) {
	 *
	 * } } } return transactionList; } // end of loadALL()
	 ***/
	// get hidden transactionID from row selected from resultSet in panel3
	// select transaction with both userName and categoryName
	// store userName as both a visible field and a hidden field
	// store categoryName as both a visible field and a hidden field
	// store resultSet in top panel of screen
	//
	// ??? this logic does not work ???
	// if ( visible userName != hidden userName
	// { select userID from users where user table userName = visible
	// userName }
	// if ( resultSet userID != hidden userID )
	// { You have a new user, insert new user into user table
	// select to get new userID (an auto increment field)
	// set hidden userID with new userID }
	// does not protect against duplicate names. How to prevent duplicate
	// users. Make userID and userName a concatenated key???
	// store User names as an upper case letter followed by lower case
	// letters
	//
	//
	// same process for category name...

	// TODO mg this INSERT statement is not in the correct format!!!
	//
	PreparedStatement onSave(int tID, String tDate, int tUserID, char tType, double tAmount, int tCategoryID,
			String Description) throws SQLException {
		if (mTransactionID == 0) {
			// declare preparedStatement
			PreparedStatement prepInsertStmt = null;

			// declare insertQuery variable
			// transactionID is an auto increment field
			String insertQuery = ("INSERT INTO transactions" + " TransactionDate," + " userID, TransactionType, Amount,"
					+ " categoryID, description " + " VALUES( ?, ?, ?, ?, ?, ? )");

			// TODO mg the prepared statement requires a connection...you knew
			// that ;)
			// prepInsertStmt = connection.prepareStatement( insertQuery );

			// populate ?s
			// prepInsertStmt = setInt(1, getTransactionID() ); // TODO should
			// this be a null value
			prepInsertStmt.setString(2, getTransactionDate());
			prepInsertStmt.setInt(3, getUserID());
			prepInsertStmt.setString(4, "" + getTransactionType());
			prepInsertStmt.setString(5, getCategoryName());
			prepInsertStmt.setString(6, getDescription());

			return (prepInsertStmt);
		} else {
			PreparedStatement prepUpdateStmt = null;
			String query = (" UPDATE transactions  SET ?,?,?,?,?,?  WHERE transactionID = mUserID");

			prepUpdateStmt.setInt(1, getTransactionID());
			prepUpdateStmt.setString(2, getTransactionDate());
			prepUpdateStmt.setInt(3, getUserID());
			prepUpdateStmt.setString(4, "" + getTransactionType());
			prepUpdateStmt.setDouble(5, getAmount());
			prepUpdateStmt.setInt(6, getCategoryID());
			// prepUpdateStmt.setString( 7, description = mDescription "
			// +
			return (prepUpdateStmt);
		}
	}// end of onSave method

	String delete(int tranID) {
		return "DELETE FROM transactions WHERE transactionID = tranID;";
	} // end of delete method

} // end of Transaction class
