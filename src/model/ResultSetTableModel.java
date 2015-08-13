package model;

/***
 *
 * Date 8/8/15
 *
 * Responsibilities:  Connection to database, Retrieving db table metadata and data,
 *                    creation of result set for jtable
 *
 *  code based on Java: How to program, 6th edition, 2005
 *  Chapter 25 Accessing Databases with JDBC, Figure 25.28
 **/

import java.sql.Connection;
import java.sql.Statement;
//import java.sql.PreparedStatement;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableModel;


public class ResultSetTableModel extends AbstractTableModel
{
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;

	static final long serialVersionUID = 0;  // compile error Eclipse indicated variable was needed.

	// keep track of database connection
	private boolean connectedToDatabase = false;

	// constructor initializes resultSet, obtains its meta data objects,
	// determines number of rows
	public ResultSetTableModel( String driver, String url,
			String userName, String password,
			String query )
					throws SQLException, ClassNotFoundException
	{
		Class.forName( driver );

		// connect to database
		statement = connection.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		// update database connection status
		connectedToDatabase = true;

		// set query and execute it
		setQuery( query );
	} // end of ResultSetTableModel constructor

	// get class that represents column type

	public Class getColumnClass(  int column  ) throws IllegalStateException
	{
		// ensure database connection is available
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		try
		{
			String className = metaData.getColumnClassName( column + 1 );

			// return class object that represents className
			return Class.forName( className );
		} // end try
		catch ( Exception exception )
		{
			exception.printStackTrace();
		} // end catch

		return Object.class;  // if problem occurs above, assume type object
	} // end method get Column class

	// get number of columns in resultSet
	public int getColumnCount() throws IllegalStateException
	{
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		// determine number of columns
		try
		{
			return metaData.getColumnCount();
		} // end try
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
		} // end catch

		return 0; // problem, return 0 for number of columns
	} // end of method getColumnCount

	// get name of a particular column in resultSet
	public String getColumnName( int column ) throws IllegalStateException
	{
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		// determine column name
		try
		{
			return metaData.getColumnName( column + 1 );
		} // end try
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
		} // end catch

		return ""; // problem, return empty string for number of columns
	} // end of method getColumnName

	public int getRowCount() throws IllegalStateException
	{
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		return numberOfRows;
	} // end method getRowCount

	public Object getValueAt( int row, int column )
		throws IllegalStateException
	{
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		// obtain a value at specified row and column
		try
		{
			resultSet.absolute( row + 1 );
			return resultSet.getObject( column + 1 );  // compile problem resolved by explicity casting result set as an object
		} // end try
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
		} // end catch

		return "";
	} // end method getValueAt

	// set new database query string
	public void setQuery( String query )
		throws SQLException, IllegalStateException
	{
		if ( !connectedToDatabase )
			throw new IllegalStateException( "Not connected to database");

		// specify query and execute it
		resultSet = statement.executeQuery( query );

		metaData = resultSet.getMetaData();

		resultSet.last();					// move to last row
		numberOfRows = resultSet.getRow();  // get row number

		// notify JTable that model has changed
		fireTableStructureChanged();
	} // end method setQuery

	//close Statement and Connection
	public void disconnectFromDatabase()
	{
		if ( !connectedToDatabase )
			return;

		try
		{
			statement.close();
			connection.close();
		} // end try
		catch ( SQLException sqlException )
		{
			sqlException.printStackTrace();
		} // end catch
		finally // update database connection status
		{
			connectedToDatabase = false;
		} // end finally
	} // end class method disconnectFromDatabase
} // end ResultSetTableModel class
