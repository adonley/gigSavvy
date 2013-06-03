package GUI;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DomainLogic.DomainLogic;

public class GenerateInformation extends Container {

	DomainLogic logic = DomainLogic.getInstance();
	
	GridBagConstraints c;
	
	JButton generateCustomers;
	JButton generateVendors;
	JButton generateBoth;
	
	JSlider numCustomers;
	JSlider numVendors;
	
	JTextField numCustomersCounter;
	JTextField numVendorsCounter;
	
	public static int vendorSliderStart = 100, customerSliderStart = 1000, customerSliderVal = customerSliderStart,
			vendorSliderVal = vendorSliderStart, maxCustomers = 30000, maxVendors = 5000;
	
	public GenerateInformation(){
		
		c = new GridBagConstraints();
		
		generateCustomers = new JButton("Generate Customers");
		generateVendors = new JButton("Generate Vendors");
		generateBoth = new JButton("Generate Both");
		
		numCustomers = new JSlider(1,maxCustomers,customerSliderStart);
		numVendors = new JSlider(1,maxVendors,vendorSliderStart);
		
		// Create text areas with slider starting value
		numCustomersCounter = new JTextField((new Integer(customerSliderStart).toString()));
		numVendorsCounter = new JTextField(new Integer(vendorSliderStart).toString());
		
		numCustomersCounter.setHorizontalAlignment(JTextField.CENTER);
		numVendorsCounter.setHorizontalAlignment(JTextField.CENTER);
		
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = .3;
		c.weighty = .3;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10,10,0,10);
		add(numCustomersCounter,c);
		
		c.gridx = 1;
		add(numVendorsCounter,c);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight=1;
		c.gridwidth =1;
		add(numVendors,c);

		c.gridx = 0;
		c.gridy = 1;
		add(numCustomers,c);
		
		c.insets = new Insets(0,10,10,10);
		c.gridx = 0;
		c.gridy = 2;
		add(generateCustomers,c);
		
		c.gridx = 1;
		c.gridy = 2;
		add(generateVendors,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		add(generateBoth,c);
		
		numVendorsCounter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Integer i = new Integer(numVendorsCounter.getText());
				
					// If the user enters nonsense, This isn't dead code.
					if(i > maxVendors || i < 1) {
						i = new Integer(vendorSliderVal);
						numVendorsCounter.setText(i.toString());
					} else {
						numVendors.setValue(i);
					}
				} catch (Exception E) {
					Integer i = new Integer(vendorSliderVal);
					numVendorsCounter.setText(i.toString());
				}
			}
		});
		
		numCustomersCounter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Integer i = new Integer(numCustomersCounter.getText());
				
					// If the user enters nonsense, This isn't dead code.
					if(i > maxCustomers || i < 1) {
						i = new Integer(customerSliderVal);
						numCustomersCounter.setText(i.toString());
					} else {
						numCustomers.setValue(i);
					}
				} catch (Exception E) {
					Integer i = new Integer(customerSliderVal);
					numCustomersCounter.setText(i.toString());
				}
			}
		});
		
		// Add wrapper class change listener to update the values in the text areas.
		numCustomers.addChangeListener(new SliderListener(numCustomersCounter,numCustomers,0));
		numVendors.addChangeListener(new SliderListener(numVendorsCounter,numVendors,1));
		
		// Get the value of the slider for number of customers to be generated when button is clicked.
		generateCustomers.addActionListener(new ThreadedActionListener(1));
		generateVendors.addActionListener(new ThreadedActionListener(2));
		generateBoth.addActionListener(new ThreadedActionListener(3));
		
	}
	
	public void setButtonsNotEnabled() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		generateCustomers.setEnabled(false);
		generateVendors.setEnabled(false);
		generateBoth.setEnabled(false);
		
		numCustomers.setEnabled(false);
		numVendors.setEnabled(false);
		
		numCustomersCounter.setEnabled(false);
		numVendorsCounter.setEnabled(false);
		
	}
	
	public void setButtonsEnabled() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		generateCustomers.setEnabled(true);
		generateVendors.setEnabled(true);
		generateBoth.setEnabled(true);
		
		numCustomers.setEnabled(true);
		numVendors.setEnabled(true);
		
		numCustomersCounter.setEnabled(true);
		numVendorsCounter.setEnabled(true);

	}
	
	public class ThreadedActionListener implements ActionListener {

		int typeToGenerate;
		
		public ThreadedActionListener(int type) {typeToGenerate = type;}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			logic.deleteTransactions();
			
			switch(typeToGenerate){
				case 1:
					new Thread(new CreateCustomers()).start();
					break;
				case 2:
					new Thread(new CreateVendors()).start();
					break;
				case 3:
					new Thread(new CreateBoth()).start();
					break;
				default:
					new Thread(new CreateBoth()).start();
					break;
			}

		}
		
		class CreateBoth implements Runnable {
			@Override
			public void run() {
				setButtonsNotEnabled();
				logic.generateVendors(vendorSliderVal);
				logic.generateCustomers(customerSliderVal);
				setButtonsEnabled();

			}
		}
		
		class CreateCustomers implements Runnable {
			@Override
			public void run() {
				setButtonsNotEnabled();
				logic.generateCustomers(customerSliderVal);
				setButtonsEnabled();
			}
		}
		
		class CreateVendors implements Runnable {
			@Override
			public void run() {
				setButtonsNotEnabled();
				logic.generateVendors(vendorSliderVal);
				setButtonsEnabled();
			}
		}

	}
	
	class SliderListener implements ChangeListener {
		 
		JSlider slide;
		JTextField altDisplay;
		int displayType;
		
		public SliderListener(JTextField temp, JSlider tempSlide, int type) {
			super();
			altDisplay = temp;
			slide = tempSlide;
			displayType = type;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			switch(displayType){
				case 0:
					GenerateInformation.customerSliderVal = slide.getValue();
					altDisplay.setText(new Integer(slide.getValue()).toString());
					break;
				case 1:
					GenerateInformation.vendorSliderVal = slide.getValue();
					altDisplay.setText(new Integer(slide.getValue()).toString());
					break;
			}

		}

	}
		
}