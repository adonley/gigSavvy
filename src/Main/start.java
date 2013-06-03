package Main;

import javax.swing.UIManager;

import GUI.GUI;
import GUI.StartScreen;

public class start {

	public static void main(String [] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {	}
		
		StartScreen start = new StartScreen();
		
		GUI gigSavvy = new GUI();
		
		gigSavvy.getContentPane().add(start);
		
		gigSavvy.setVisible(true);
		
	}
	
}
