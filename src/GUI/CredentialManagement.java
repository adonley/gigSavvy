package GUI;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JTextField;

public class CredentialManagement extends Container {

	public CredentialManagement(){
		JTextField text = new JTextField("Credential Management Data");
		setLayout(new GridLayout(1,1));
		add(text);
	}
	
}
