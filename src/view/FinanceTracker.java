package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
/**
 *
 * @Date 8/9/15
 *
 * responsibilities: user interface layout; display of transactions table resultSet
 *                   notification of changes
 **/
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.AppDb;
import model.DeleteTransaction;
import model.Transaction;

public class FinanceTracker extends JFrame implements ActionListener {
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

	static JTable table = new JTable();
	ArrayList<Transaction> transactionList;

	// selected transaction id
	int tableRow = 0;
	int tableColumn = 0;

	static int selectedTransactionID = 0;

	public FinanceTracker() {
		// Create and show GUI
		super("Finance Tracker CGJAVA-21");

		System.out.println("You are in FinanceTracker constructor   FinanaceTracker.java"); // mmgg
		setLayout(new GridLayout(4, 1)); // 4 rows one column
		JPanel jPanel1, jPanel2, jPanel3, jPanel4; // layout
													// experiment
		// mg

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jPanel1 = new JPanel();

		// this.getRootPane().add( jPanel1 );
		jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS)); // CGJAVA-15
																		// UI

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

		jTransactionType = new JTextField("Type", 6);
		jPanel1.add(jTransactionType);
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
		jPanel2 = new JPanel();
		jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.LINE_AXIS)); // CGJAVA-15
																		// UI
		// setLayout(new GridLayout(1, 4));

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

		this.add(jPanel2);
		/*** jPanel 2 ***/

		/**
		 * jPanel2a = new JPanel();
		 *
		 * JTextField jErrorMessage = new JTextField();
		 * jPanel2a.add(jErrorMessage); jErrorMessage.setVisible(true);
		 * this.add(jPanel2a);
		 **/
		/*** jPanel3 ***/

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		jPanel3 = new JPanel();
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // CGJAVA-15 UI

		// build table model
		model.addColumn("User ID");
		model.addColumn("Name");
		model.addColumn("Transaction Date");
		model.addColumn("Category");
		model.addColumn("Amount");
		model.addColumn("Description");

		// select one row
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.getSelectionModel().addListSelectionListener(new RowListener());

		// create AppDB object
		AppDb appDb = new AppDb();

		// create transaction list to hold data
		transactionList = new ArrayList<Transaction>();

		// load DB data from DB
		transactionList = appDb.loadAll();

		for (int i = 0; i < transactionList.size(); i++) {
			model.addRow(new Object[] { transactionList.get(i).getTransactionID(), transactionList.get(i).getName(),
					transactionList.get(i).getTransactionDate(), transactionList.get(i).getCategoryName() });
		}

		jPanel3.add(scrollPane);
		this.add(jPanel3);

		jPanel4 = new JPanel();

		// CGJAVA-21 Delete a Transaction - set up buttons for deletes and
		// archiving
		JButton deleteButton = new JButton("Delete");
		JButton archiveButton = new JButton("Archive");

		jPanel4.add(deleteButton);
		jPanel4.add(archiveButton);// Future feature

		deleteButton.setMnemonic(KeyEvent.VK_D);
		archiveButton.setMnemonic(KeyEvent.VK_A);

		deleteButton.setToolTipText("Click this button to delete transaction.");
		archiveButton.setToolTipText("Future Feature! Click this button to save transaction to History");

		deleteButton.addActionListener(this);
		archiveButton.addActionListener(this);
		// CGJAVA-15 Add Total Deposits

		// jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.X_AXIS));//
		{
			JLabel totalDepositsLabel = new JLabel("Total Deposits: ", JLabel.LEFT);
			jPanel4.add(totalDepositsLabel);
			String d = Transaction.totalDeposits(transactionList);
			JLabel tDLabel = new JLabel(d);
			jPanel4.add(tDLabel);
		}

		JLabel spLabel = new JLabel("     ", JLabel.CENTER);
		jPanel4.add(spLabel);

		{
			JLabel totalWithdrawalsLabel = new JLabel("Total Withdrawals: ", JLabel.RIGHT);
			jPanel4.add(totalWithdrawalsLabel);
			String w = Transaction.totalWithdrawals(transactionList);
			JLabel tWLabel = new JLabel(w);
			jPanel4.add(tWLabel);
		}

		this.add(jPanel4);
	} // end of Display constructor

	public int getSelectedTransactionID() {
		return selectedTransactionID;
	}

	void setSelectedTransactionID(int sTI) {
		selectedTransactionID = sTI;
	}

	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting())
				return;
			System.out.println("RowListener");
			// outputSelection();
			tableRow = table.getSelectionModel().getLeadSelectionIndex();
			tableColumn = 0;
			selectedTransactionID = (int) (getValueAt(tableRow, tableColumn));
		}
	}

	private void outputSelection() {
		System.out.printf("Lead %d, %d\n", table.getSelectionModel().getLeadSelectionIndex(), 0);
		tableRow = table.getSelectionModel().getLeadSelectionIndex();
		tableColumn = 0;
		setSelectedTransactionID((int) (getValueAt(tableRow, tableColumn)));
	}

	private int getValueAt(int row, int column) {
		System.out.println(transactionList.get(row).getTransactionID());
		return transactionList.get(row).getTransactionID();

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command.equals("Delete")) {
			System.out.println("You are in actionPerformed");
			selectedTransactionID = getSelectedTransactionID();
			DeleteTransaction dlt = new DeleteTransaction(selectedTransactionID);

		} else if (command.equals("Archive")) {
			// ArchiveTransaction archv = new ArchiveTransaction();
			// TODO
		}

	}

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
