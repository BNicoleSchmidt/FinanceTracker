package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
/**
 *
 * @Date 8/9/15
 *
 * responsibilities: user interface layout; display of transactions table resultSet
 *                   notification of changes
 **/
/** CGJAVA-37 fix user interface
 *  container uses BoxLayout for panels
 *  panels use GridBagLayout
 */
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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println("You are in FinanceTracker constructor   FinanaceTracker.java");

		Container container = new Container();
		add(container);
		BoxLayout b = new BoxLayout(container, BoxLayout.Y_AXIS);
		container.setLayout(b);

		JPanel jPanel1, jPanel2, jPanel3, jPanel4;

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		jPanel1 = new JPanel();
		container.add(jPanel1);
		jPanel1.setLayout(gridBagLayout);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridy = 0;

		jTransactionID = new JTextField("ID", 8); // s/b hidden field TODO mg
		c.gridx = 0;
		jPanel1.add(jTransactionID, c);
		// jTransactionID.setVisible(true); // CGJAVA-37

		jUserName = new JTextField("Name", 20);
		c.gridx = 1;
		jPanel1.add(jUserName, c);

		jTransactionDate = new JTextField("Date", 10);
		c.gridx = 2;
		jPanel1.add(jTransactionDate, c);

		jTransactionAmount = new JTextField("Amount", 10);
		c.gridx = 3;
		jPanel1.add(jTransactionAmount, c);

		jTransactionType = new JTextField("Type", 6);
		c.gridx = 4;
		jPanel1.add(jTransactionType, c);

		jCategoryName = new JTextField("Category", 20);
		c.gridx = 5;
		jPanel1.add(jCategoryName, c);

		jDescription = new JTextField("Description", 20);
		c.gridx = 6;
		jPanel1.add(jDescription, c);

		JButton jSaveButton = new JButton("Save");
		c.gridx = 7;
		jPanel1.add(jSaveButton, c);
		jSaveButton.setVisible(true);

		/*****/
		// JPanel jPanel2;
		jPanel2 = new JPanel();
		container.add(jPanel2);
		jPanel2.setLayout(gridBagLayout);

		c.gridy = 0;

		jHUserID = new JTextField("Hidden Uid", 10); // s/b hidden field TODO
		c.gridx = 0;
		jPanel2.add(jHUserID, c);
		jHUserID.setVisible(true);

		jHUserName = new JTextField("Hidden Name", 10); // s/b hidden field
		c.gridx = 1;
		jPanel2.add(jHUserName, c);
		jHUserName.setVisible(true);

		jCategoryID = new JTextField("Hidden catID", 10); // s/b hidden field
		c.gridx = 2;
		jPanel2.add(jCategoryID, c);
		jCategoryID.setVisible(true);

		jHCategoryName = new JTextField("Hidden catNAME", 10); // s/b hidden
		c.gridx = 3;
		jPanel2.add(jHCategoryName, c);
		jHCategoryName.setVisible(true);

		// JTextField jErrorMessage = new JTextField();
		// jPanel2.add( jErrorMessage );
		// jErrorMessage.setVisible( true );

		/*** jPanel3 ***/
		// DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable();
		this.add(jPanel2);

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		jPanel3 = new JPanel();
		container.add(jPanel3);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // CGJAVA-15 UI
		table.setPreferredSize(new Dimension(600, 600)); // mg adds V scroll bar
															// ???

		AppDb appDb = new AppDb();
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		transactionList = appDb.loadAll();

		model.addColumn("T-ID");
		// build table model

		model.addColumn("Name");
		model.addColumn("T-Date");
		model.addColumn("T-Type");
		model.addColumn("Amount");
		model.addColumn("Category");
		model.addColumn("Description");

		// select one row
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.getSelectionModel().addListSelectionListener(new RowListener());

		// create transaction list to hold data
		transactionList = new ArrayList<Transaction>();

		// load DB data from DB
		transactionList = appDb.loadAll();

		for (int i = 0; i < transactionList.size(); i++) {
			model.addRow(new Object[] { transactionList.get(i).getTransactionID(), transactionList.get(i).getName(),
					transactionList.get(i).getTransactionDate(), transactionList.get(i).getTransactionType(),
					transactionList.get(i).getAmount(), transactionList.get(i).getCategoryName(),
					transactionList.get(i).getDescription() });
		}

		jPanel3.add(scrollPane);
		// container.add(jPanel3);

		// CGJAVA-15 Add Total Deposits
		FlowLayout f = new FlowLayout(FlowLayout.RIGHT, 10, 10);
		jPanel4 = new JPanel(f);

		container.add(jPanel4);

		JLabel totalDepositsLabel = new JLabel("Total Deposits: ", JLabel.LEFT);
		jPanel4.add(totalDepositsLabel);
		String d = Transaction.totalDeposits(transactionList);
		JLabel tDLabel = new JLabel(d);
		jPanel4.add(tDLabel);

		JLabel totalWithdrawalsLabel = new JLabel("Total Withdrawals: ", JLabel.RIGHT);
		jPanel4.add(totalWithdrawalsLabel);
		String w = Transaction.totalWithdrawals(transactionList);
		JLabel tWLabel = new JLabel(w);
		jPanel4.add(tWLabel);

	} // end of Display constructor

	public int getSelectedTransactionID() {
		return selectedTransactionID;
	}

	void setSelectedTransactionID() {

	}

	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting())
				return;
			System.out.println("RowListener");
			outputSelection();
		}
	}

	private void outputSelection() {
		System.out.printf("Lead %d, %d\n", table.getSelectionModel().getLeadSelectionIndex(), 0);
		tableRow = table.getSelectionModel().getLeadSelectionIndex();
		tableColumn = 0;
		selectedTransactionID = (int) (getValueAt(tableRow, tableColumn));
	}

	private int getValueAt(int row, int column) {
		System.out.println(transactionList.get(row).getTransactionID());
		return transactionList.get(row).getTransactionID();

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if (command == "Delete") {
			System.out.println("You are in actionPerformed");
			// DeleteTransaction dlt = new DeleteTransaction();// TODO
		} else if (command == "Archive") {
			// ArchiveTransaction archv = new ArchiveTransaction();
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
