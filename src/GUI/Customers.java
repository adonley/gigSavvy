package GUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import DomainLogic.DomainLogic;

public class Customers extends Container {
	
DomainLogic logic = DomainLogic.getInstance();

// Constraints for the whole tab pane and the table container 
GridBagConstraints wholeTabConstraints, tableConstraints;

// Column names for the table we are going to create
String [] columnNames = {"ID","First Name","Last Name","Cell Number","PreAuthentication"};

// Inner Container that will show the customer results
Container Results;
JButton SearchCustomers;

JTable table;
JScrollPane scrollPane;
	
	public Customers(){
		
		wholeTabConstraints = new GridBagConstraints();
		tableConstraints = new GridBagConstraints();
		
		SearchCustomers = new JButton("Search for Customer");
		
		Results = new Container();
		
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
		add(SearchCustomers,wholeTabConstraints);
		
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

		//showCustomers();
		
	}
	
	void showCustomers() {
		
		new Thread(new CreateTable()).start();
		
	}
	
	void updateCustomers() {
		
		new Thread(new CreateTable()).start();
		
	}
	
	class CreateTable implements Runnable {
		
		@Override
		public void run() {
			
			table = new JTable(logic.getCustomers(),columnNames);
			table.setShowVerticalLines(false);
			table.setAutoCreateRowSorter(true);
			scrollPane = new JScrollPane(table);
			
			Results.removeAll();
			tableConstraints.fill = GridBagConstraints.BOTH;
			Results.add(scrollPane,tableConstraints);
			
		}
		
	}
	
	class UpdateTable implements Runnable {

		@Override
		public void run() {
			table = new JTable(logic.getCustomers(),columnNames);
			table.updateUI();
			
		}
		
	}

}