package GUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DomainLogic.DomainLogic;

public class Vendors extends Container {

	DomainLogic logic = DomainLogic.getInstance();
	
	String [] columnNames = {"ID","Name","Location","Cashier","Number of Employees"};
	
	// Constraints for the whole tab pane and the table container 
	GridBagConstraints wholeTabConstraints, tableConstraints;

	// Inner Container that will show the customer results
	Container Results;
	JButton SearchVendors;
	JButton ViewTable;

	JTable table;
	JScrollPane scrollPane;
	JTextField searchBox;
		
		public Vendors(){
			
			wholeTabConstraints = new GridBagConstraints();
			tableConstraints = new GridBagConstraints();
			
			SearchVendors = new JButton("Search for Vendor");
			ViewTable = new JButton("View Table");
			ViewTable.setEnabled(false);
			
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
			add(SearchVendors,wholeTabConstraints);
			
			wholeTabConstraints.gridx = 1;
			add(ViewTable,wholeTabConstraints);
			
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
			
			ViewTable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					disableButtonsViewVendor();
					showVendors();
				}
			});
			
			SearchVendors.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					disableButtonsSearchVendor();
				}
			});
			
		}
		
		void disableButtonsViewVendor() {
			ViewTable.setEnabled(false);
			SearchVendors.setEnabled(true);
			
		}
		
		void disableButtonsSearchVendor() {
			ViewTable.setEnabled(true);
			SearchVendors.setEnabled(false);
		}
		
		void showVendors() {
			
			new Thread(new CreateTable()).start();
			
		}
		
		void updateVendors() {
			
			new Thread(new CreateTable()).start();
			
		}
		
		class CreateTable implements Runnable {
			
			@Override
			public void run() {
				
				table = new JTable(logic.getVendors(),columnNames);
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
				table = new JTable(logic.getVendors(),columnNames);
				table.updateUI();
			}
		}
		
		class SearchVendor implements Runnable {
			@Override
			public void run() {
				Results.removeAll();
				tableConstraints.weightx = 0;
				tableConstraints.weighty = 0;
				searchBox = new JTextField();
				//searchBox.se
			}
			
		}

		
}