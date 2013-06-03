package GUI;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DomainLogic.DomainLogic;

public class StartScreen extends Container {

	JTabbedPane tabbedPane;
	Customers customerPanel;
	Vendors vendorsPanel;
	Transactions transactionsPanel;
	
	DomainLogic logic = DomainLogic.getInstance();
	
	public StartScreen() {

		// Create the Tabs
		tabbedPane = new JTabbedPane();
		
		JPanel intro = new IntroTab();
		tabbedPane.addTab("Gig Savvy", intro);

		Container generateInfo = new GenerateInformation();
		tabbedPane.addTab("Generate Information", generateInfo);
		
		customerPanel = new Customers();
		tabbedPane.addTab("Customer Data", customerPanel);
		
		vendorsPanel = new Vendors();
		tabbedPane.addTab("Vendor Data", vendorsPanel);
		
		transactionsPanel = new Transactions();
		tabbedPane.addTab("Transaction Data", transactionsPanel);
		
		/*JPanel credentialManagement = new CredentialManagement();
		tabbedPane.addTab("Credential Management Data", credentialManagement); */
		
		tabbedPane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(tabbedPane.getSelectedIndex() == 2) {
					customerPanel.updateCustomers();
				}
				else if (tabbedPane.getSelectedIndex() == 3) {
					vendorsPanel.updateVendors();
				}
				else if (tabbedPane.getSelectedIndex() == 4) {
					if(DomainLogic.numberOfTransactions == 0)
							transactionsPanel.clearTransactions();
				}
				
			}
			
		});
		
		//Basic Layout to Display Panes
		setLayout(new GridLayout(1,1));
		
		add(tabbedPane);
	
	}
		
}