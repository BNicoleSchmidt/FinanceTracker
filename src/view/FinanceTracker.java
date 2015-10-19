package view;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.AppDb;
import model.Transaction;
/**
 *
 * @Date 8/9/15
 *
 * responsibilities: user interface layout; display of transactions table resultSet
 *                   notification of changes
 **/
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.GridLayout;

public class FinanceTracker extends JFrame {
	private JTextField jTransactionID;
	private JTextField jUserID;
	private JTextField jCategoryID;
	private JTextField jTransactionDate;
	private JTextField jUserName;
	private JTextField jTransactionAmount;
	private JTextField jTransactionType;
	private JTextField jCategoryName;
	private JTextField jDescription;
	// private JTextField jErrorMessage;

	// hidden fields
	private JTextField jHTransactionID;
	private JTextField jHUserID;
	private JTextField jHUserName;
	private JTextField jHCategoryID;
	private JTextField jHCategoryName;

	public FinanceTracker() {
		// Create and show GUI
		super("Finance Tracker");

		System.out.println("You are in FinanceTracker constructor   FinanaceTracker.java"); // mmgg

		// setLayout(new GridLayout(3, 1));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel jPanel1 = new JPanel();

		// this.getRootPane().add( jPanel1 );
		this.add(jPanel1);
		// setLayout(new FlowLayout());

		jTransactionID = new JTextField("ID", 8); // s/b hidden field TODO mg
		jPanel1.add(jTransactionID);
		jTransactionID.setVisible(true);

		jUserName = new JTextField("Name", 20);
		jPanel1.add(jUserName);
		jUserName.setVisible(true);

		jTransactionDate = new JTextField("Date", 10);
		jPanel1.add(jTransactionDate);
		jTransactionDate.setVisible(true);

		jTransactionAmount = new JTextField("Amount", 10);
		jPanel1.add(jTransactionAmount);
		jTransactionAmount.setVisible(true);

		jTransactionType = new JTextField("Type", 6); // There seems to be some
														// disagreement, create
														// table indicates 'c'
														// or 'd'
		jPanel1.add(jTransactionType); // Coded Insert indicates, '+' or '-'.
										// TODO mg
		jTransactionType.setVisible(true);

		jCategoryName = new JTextField("Category", 20);
		jPanel1.add(jCategoryName);
		jCategoryName.setVisible(true);

		jDescription = new JTextField("Description", 20);
		jPanel1.add(jDescription);
		jDescription.setVisible(true);

		JButton jSaveButton = new JButton("Save");
		jPanel1.add(jSaveButton);
		jSaveButton.setVisible(true);
		/*****/
		JPanel jPanel2 = new JPanel();
		setLayout(new GridLayout(1, 4));

		this.add(jPanel2);

		jHUserID = new JTextField("Hidden Uid", 10); // s/b hidden field TODO mg
		jPanel2.add(jHUserID);
		jHUserID.setVisible(true);

		jHUserName = new JTextField("Hidden Name", 10); // s/b hidden field TODO
														// mg
		jPanel2.add(jHUserName);
		jHUserName.setVisible(true);

		jCategoryID = new JTextField("Hidden catID", 10); // s/b hidden field
															// TODO mg
		jPanel2.add(jCategoryID);
		jCategoryID.setVisible(true);

		jHCategoryName = new JTextField("Hidden catNAME", 10); // s/b hidden
																// field TODO mg
		jPanel2.add(jHCategoryName);
		jHCategoryName.setVisible(true);

		/*** jPanel 2 ***/

		// JTextField jErrorMessage = new JTextField();
		// jPanel2.add( jErrorMessage );
		// jErrorMessage.setVisible( true );

		/*** jPanel3 ***/

		// DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		JPanel jPanel3 = new JPanel();
		JScrollPane scrollPane = new JScrollPane(table);

		AppDb appDb = new AppDb();
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList = appDb.loadAll();

		model.addColumn("User ID");
		model.addColumn("Name");
		model.addColumn("Transaction Date");
		model.addColumn("Category");
		// model.addColumn("Amount");
		// model.addColumn("Description");

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// why is this constant
														// not
		// being pulled from the swing.JTable import file?

		for (int i = 0; i < transactionList.size(); i++) {
			model.addRow(new Object[] { transactionList.get(i).getTransactionID(), transactionList.get(i).getName(),
					transactionList.get(i).getTransactionDate(), transactionList.get(i).getCategoryName() });
		}

		jPanel3.add(scrollPane);
		this.add(jPanel3);

	} // end of Display constructor

	public static void main(String[] args) {
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
	} // end main

} // end Display class
