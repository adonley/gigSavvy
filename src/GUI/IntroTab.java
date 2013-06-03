package GUI;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IntroTab extends JPanel {

	ImageIcon icon;
	JLabel logo;
	public static String companyLogoPath = "Images/gigsavvylogo.png";
	
	public IntroTab(){
		
		try {
			
			setLayout(new GridLayout(1,1));
			icon = createImageIcon(companyLogoPath);
			logo = new JLabel(icon);
			//logo.setVerticalAlignment(JLabel.CENTER);
			//logo.setHorizontalAlignment(JLabel.CENTER);
			add(logo);
			setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    protected static ImageIcon createImageIcon(String path) {
    	java.net.URL imgURL = IntroTab.class.getResource(path);
    	if (imgURL != null) {
    		return new ImageIcon(imgURL);
    	} else {
    		System.out.println("Couldn't find picture... " + path);
    		return null;
    	}
}
	
}