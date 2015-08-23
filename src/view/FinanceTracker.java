package view;
/**
 *
 * @Date 8/9/15
 *
 * responsibilities: user interface layout; display of transactions table resultSet
 *                   notification of changes
 **/


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FinanceTracker extends JFrame
{
	private JTextField jTransactionID;
	private JTextField jUserID;
	private JTextField jCategoryID;
	private JTextField jTransactionDate;
	private JTextField jUserName;
	private JTextField jTransactionAmount;
	private JTextField jTransactionType;
	private JTextField jCategoryName;
	private JTextField jDescription;
	private JTextField jErrorMessage;

	// hidden fields
	private JTextField jHTransactionID;
	private JTextField jHUserID;
	private JTextField jHUserName;
	private JTextField jHCategoryID;
	private JTextField jHCategoryName;

	public FinanceTracker()
	{
		// Create and show GUI
		super( "Finance Tracker" );
		setLayout(new GridLayout( 3, 1 ) );

		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		JPanel jPanel1 = new JPanel();
		
		// this.getRootPane().add( jPanel1 );
		this.add( jPanel1 );
		setLayout(new GridLayout( 2, 8 ) );

		jTransactionID = new JTextField( "ID", 8 ); // s/b hidden field TODO mg
		jPanel1.add( jTransactionID );
		jTransactionID.setVisible( true );

		jUserName = new JTextField( "Name", 20 );
		jPanel1.add( jUserName );
		jUserName.setVisible( true );;

		jTransactionDate = new JTextField( "Date");
		jPanel1.add(jTransactionDate);
		jTransactionDate.setVisible( true );

		jTransactionAmount = new JTextField( "Amount", 10 );
		jPanel1.add( jTransactionAmount );
		jTransactionAmount.setVisible( true );

		jTransactionType = new JTextField( "Type", 6 ); // There seems to be some disagreement, create table indicates 'c' or 'd'
		jPanel1.add( jTransactionType );	// Coded Insert indicates, '+' or '-'.  TODO mg
		jTransactionType.setVisible( true );

		jCategoryName = new JTextField( "Category", 20 );
		jPanel1.add( jCategoryName );
		jCategoryName.setVisible( true );

		jDescription = new JTextField( "Description", 20 );
		jPanel1.add( jDescription );
		jDescription.setVisible( true );

		JButton jSaveButton = new JButton( "Save" );
		jPanel1.add( jSaveButton );
		jSaveButton.setVisible( true );
/*****/
		JPanel jPanel2 = new JPanel();
		
		this.add( jPanel2 );
		setLayout(new GridLayout( 2, 8 ) );
		
		jHUserID = new JTextField( "Hidden Uid", 10); // s/b hidden field TODO mg
		jPanel2.add( jHUserID );
		jHUserID.setVisible( true );

		jHUserName = new JTextField( "Hidden Name", 10); // s/b hidden field TODO mg
		jPanel2.add( jHUserName );
		jHUserName.setVisible( true );
		
		jCategoryID = new JTextField( "Hidden catID", 10 );  // s/b hidden field TODO mg
		jPanel2.add( jCategoryID );
		jCategoryID.setVisible( true );

		jHCategoryName = new JTextField( "Hidden catNAME", 10 );  // s/b hidden field TODO mg
		jPanel2.add( jHCategoryName );
		jHCategoryName.setVisible( true );
		
		

		/*** jPanel 2 ***/

	//	JTextField jErrorMessage = new JTextField();
	//	jPanel2.add( jErrorMessage );
	//	jErrorMessage.setVisible( true );

		/*** jPanel3 ***/

	}  // end of Display constructor

public static void main( String[] args )
{
	JFrame frame = new FinanceTracker();
	// Add a window listener for close button
	frame.addWindowListener(new WindowAdapter() {

		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});
	// This is an empty content area in the frame
	frame.pack();
	frame.setVisible(true);
}  // end main

} // end Display class

