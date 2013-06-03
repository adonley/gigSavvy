package GUI;

import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {
	
	public GUI() {
		
		setTitle("Gig Savvy");
		
		// Open full screen
		Toolkit tool = Toolkit.getDefaultToolkit();
		int xSize = (int)tool.getScreenSize().getWidth();
		int ySize = (int)tool.getScreenSize().getHeight();
		setSize(xSize,ySize);
		
		// Exit When app is closed
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

}
