package view;

import java.awt.BorderLayout;
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
		super("FinanceTracker");
		// Create and show GUI

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println("You are in FinanceTracker constructor   FinanaceTracker.java");

		Container container = new Container();
		BoxLayout b = new BoxLayout(container, BoxLayout.Y_AXIS);
		container.setLayout(b);
		add(container);

		GridBagConstraints c = new GridBagConstraints();

		JPanel jPanel1 = new JPanel(new GridBagLayout()); // cg 40
		container.add(jPanel1);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridy = 0;

		jTransactionID = new JTextField("ID", 8); // s/b hidden field TODO mg
		c.gridx = 0;
		jPanel1.add(jTransactionID, c);

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
		JPanel jPanel2 = new JPanel(new GridBagLayout());
		container.add(jPanel2);

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

		// Message Panel
		JPanel jPanelEM = new JPanel(new BorderLayout());
		container.add(jPanelEM);
		JTextField jErrorMessage = new JTextField("Message", 80);
		jPanelEM.add(jErrorMessage);

		/*** jPanel3 ***/
		// DefaultTableModel model = new DefaultTableModel();
		JPanel jPanel3 = new JPanel(new BorderLayout());

		JTable table = new JTable();

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // CGJAVA-15 UI
		table.setPreferredSize(new Dimension(800, 600)); // mg adds V scroll bar
															// ???
		container.add(jPanel3);

		// Create transaction list to hold data and load data from DB
		AppDb appDb = new AppDb();
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
		try {
			transactionList = appDb.loadAll();
		} catch (Exception se) { // TODO
			se.printStackTrace();
		}

		// build table model
		model.addColumn("T-ID");
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

		for (int i = 0; i < transactionList.size(); i++) {
			model.addRow(new Object[] { transactionList.get(i).getTransactionID(), transactionList.get(i).getName(),
					transactionList.get(i).getTransactionDate(), transactionList.get(i).getTransactionType(),
					transactionList.get(i).getAmount(), transactionList.get(i).getCategoryName(),
					transactionList.get(i).getDescription() });
		}

		jPanel3.add(scrollPane);

		// CGJAVA-15 Add Total Deposits
		JPanel jPanel4 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
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

	void setSelectedTransactionID(int sTI) {
		selectedTransactionID = sTI;
	}

	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			ListSelectionModel selectionModel = table.getSelectionModel();
			selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			if (event.getValueIsAdjusting())
				return;
			System.out.println("RowListener");
			// outputSelection();
			tableRow = table.getSelectionModel().getLeadSelectionIndex();
			System.out.printf("Table Row %d \n", tableRow);
			tableColumn = 0;
			selectedTransactionID = (int) (getValueAt(tableRow, tableColumn));
			System.out.printf("select transaction id %d \n", selectedTransactionID);
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
