package GUI;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import DataBase.TransactionDB;
import DomainLogic.DomainLogic;

public class Transactions extends Container {

	DomainLogic logic = DomainLogic.getInstance();
	
	Thread simulation;
	
	// Constraints for the whole tab pane and the table container 
	GridBagConstraints wholeTabConstraints, tableConstraints;
	
	// Column names for the table we are going to create
	String [] columnNames = {"ID","Customer ID","Vendor ID","Amount Charged","Status","Resolved","TimeStamp"};
	
	// Inner Container that will show the customer results
	Container Results;
	JButton startSimulation;
	JButton stopSimulation;
	JButton viewTable;
	JButton clearTransactions;
	JButton deleteTransactions;
	
	JTable table;
	JScrollPane scrollPane;
	
	JTextArea liveUpdate;
	
	boolean inTable;
	
	public Transactions(){
		
		// start as true so that we have bottom scrolling
		inTable = true;
		
		wholeTabConstraints = new GridBagConstraints();
		tableConstraints = new GridBagConstraints();
		
		startSimulation = new JButton("Begin Simulation");
		stopSimulation = new JButton("Stop Simulation");
		viewTable = new JButton("View Table of Transactions");
		clearTransactions = new JButton("Clear Transactions");
		deleteTransactions = new JButton("Delete All Transactions");
		stopSimulation.setEnabled(false);
		
		Results = new Container();
		
		liveUpdate = new JTextArea();
		liveUpdate.setEditable(false);
		liveUpdate.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
		
		setLayout(new GridBagLayout());
		Results.setLayout(new GridBagLayout());
		
		//Constraints for better looks
		wholeTabConstraints.insets = new Insets(10,10,10,10);
		wholeTabConstraints.weightx = 0;
		wholeTabConstraints.weighty = 0;
		wholeTabConstraints.gridx = 0;
		wholeTabConstraints.gridwidth=1;
		wholeTabConstraints.gridheight =1;
		wholeTabConstraints.gridy = 0;
		add(startSimulation,wholeTabConstraints);
		
		wholeTabConstraints.gridx = 1;
		add(stopSimulation,wholeTabConstraints);
		
		wholeTabConstraints.gridx = 2;
		add(viewTable,wholeTabConstraints);
		
		wholeTabConstraints.gridx = 3;
		add(clearTransactions,wholeTabConstraints);
		
		wholeTabConstraints.gridx = 4;
		add(deleteTransactions,wholeTabConstraints);
		
		wholeTabConstraints.fill = GridBagConstraints.BOTH;
		wholeTabConstraints.weightx = 1;
		wholeTabConstraints.weighty = 1;
		wholeTabConstraints.gridx = 0;
		wholeTabConstraints.gridy = 1;
		wholeTabConstraints.gridwidth=10;
		wholeTabConstraints.gridheight=10;
		wholeTabConstraints.insets = new Insets(0,0,0,0);
		add(Results,wholeTabConstraints);
		
		tableConstraints.weightx = 1;
		tableConstraints.weighty = 1;
		tableConstraints.gridx = 0;
		tableConstraints.gridy = 0;
		tableConstraints.fill = GridBagConstraints.BOTH;
		
		scrollPane = new JScrollPane(liveUpdate);
		Results.add(scrollPane,tableConstraints);
		
		startSimulation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setButtonsDisabled();
				inTheTable();
				scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  	
					public void adjustmentValueChanged(AdjustmentEvent e) {  
						e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
					}
				}); 
				simulation = new Thread(new Simulation());
				simulation.start();
			
			}
		});
		
		stopSimulation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				simulation.interrupt();
				
				// Get rid of auto scroll
				AdjustmentListener [] temp = scrollPane.getVerticalScrollBar().getAdjustmentListeners();
				for(int i = 0; i < temp.length; i++) {
					scrollPane.getVerticalScrollBar().removeAdjustmentListener(temp[i]);
				}
				setButtonsEnabled();
				
			}
			
		});
		
		clearTransactions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				liveUpdate.setText("");
				inTheTable();
			}
			
		});
		
		deleteTransactions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logic.deleteTransactions();
				liveUpdate.setText("");
				inTheTable();
			}
			
		});
		
		viewTable.addActionListener(new ButtonCreateTable());
		
	}
	
	public void setButtonsEnabled() {
		startSimulation.setEnabled(true);
		viewTable.setEnabled(true);
		stopSimulation.setEnabled(false);
		clearTransactions.setEnabled(true);
		deleteTransactions.setEnabled(true);
	}
	
	public void setButtonsDisabled() {
		startSimulation.setEnabled(false);
		viewTable.setEnabled(false);
		stopSimulation.setEnabled(true);
		clearTransactions.setEnabled(false);
		deleteTransactions.setEnabled(false);
	}
	
	public void clearTransactions() {
		liveUpdate.setText("");
	}
	
	class Simulation implements Runnable  {

		@Override
		public void run() {
			
			TransactionDB temp = new TransactionDB();
			String format = "%1$-8s $%2$-6d %3$-31s %4$-11s %5$-7s %6$-7b %7$-19s %8$tc \n";
			String update = new String("");
			
			while(!Thread.interrupted()) {
				
				temp = logic.runTransactionSimulation();
				update = String.format(format, temp.getID(), temp.getAmountCharged(), temp.getCustName(),
						temp.getVendName(), temp.getLocation(), temp.getResolved(), temp.getReason(), temp.getDate());
				liveUpdate.append(update);
				
			}
			
		}
		
	}
	
	protected void inTheTable() {
		
		if(inTable) {
			
			scrollPane = new JScrollPane(liveUpdate);
			Results.removeAll();
			Results.add(scrollPane,tableConstraints);
			inTable = false;
	
		}
	}
	
	class CreateTable implements Runnable {

		@Override
		public void run() {
			setButtonsDisabled();
			if(logic.getTransactions()!=null) {
				
				table = new JTable(logic.getTransactions(),columnNames);
				table.setShowVerticalLines(false);
				scrollPane = new JScrollPane(table);
				
				// Remove auto scroll to bottom
				AdjustmentListener [] temp = scrollPane.getVerticalScrollBar().getAdjustmentListeners();
				
				for(int i = 0; i < temp.length; i++) {
					scrollPane.getVerticalScrollBar().removeAdjustmentListener(temp[i]);
				}
				
				inTable = true;
				Results.removeAll();
				Results.add(scrollPane,tableConstraints);
				
			} else {
				liveUpdate.setText("There are no transactions");
			}
			setButtonsEnabled();
		}
		
	}
	
	public class ButtonCreateTable implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new CreateTable()).start();
		}
	}
	
}
