package gui;

import gui.utils.UserFrame;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class Run {
	
	private final static String LOOKANDFEEL = "Metal";
	private final static String THEME = "Ocean";
	private static void initLookAndFeel() {
	        String lookAndFeel = null;
	        
	        if (LOOKANDFEEL != null) {
	            if (LOOKANDFEEL.equals("Metal")) {
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            }
	            else if (LOOKANDFEEL.equals("System")) {
	                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
	            } 
	            else if (LOOKANDFEEL.equals("Motif")) {
	                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	            } 
	            else if (LOOKANDFEEL.equals("GTK")) { 
	                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	            } 
	            else {
	                System.err.println("Unexpected value of LOOKANDFEEL specified: " + LOOKANDFEEL);
	                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	            }
	            try {
	                UIManager.setLookAndFeel(lookAndFeel);
	                if (LOOKANDFEEL.equals("Metal")) {
	                	if (THEME.equals("DefaultMetal")) {
	                		MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
	                	}
	                	else if (THEME.equals("Ocean")) {
	                		MetalLookAndFeel.setCurrentTheme(new OceanTheme());
	                	}
	                  UIManager.setLookAndFeel(new MetalLookAndFeel()); 
	                }	
	            } 
	            catch (ClassNotFoundException e) {
	                System.err.println("Couldn't find class for specified look and feel:" + lookAndFeel);
	                System.err.println("Did you include the L&F library in the class path?");
	                System.err.println("Using the default look and feel.");
	            } 
	            catch (UnsupportedLookAndFeelException e) {
	                System.err.println("Can't use the specified look and feel ("
	                                   + lookAndFeel
	                                   + ") on this platform.");
	                System.err.println("Using the default look and feel.");
	            } 
	            catch (Exception e) {
	                System.err.println("Couldn't get specified look and feel ("
	                                   + lookAndFeel
	                                   + "), for some reason.");
	                System.err.println("Using the default look and feel.");
	                e.printStackTrace();
	            }
	        }
	 }
	 
	 private static void createAndShowGUI() {
	        //Set the look and feel.
	        initLookAndFeel();
	        JFrame.setDefaultLookAndFeelDecorated(true);

	        //Create and set up the window.
//	        LoginFrame loginWindow = new LoginFrame();
	        ElevFrame elevWindow = new ElevFrame();
//	        Run app = new Run();
	    }

	    public static void main(String[] args) {
	    	
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
	    
	}

