package grafic;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 * The main class of the project, will open with a LoginFrame and will create a Centralizato instance.
 * @author user
 *
 */
public class Run {
	
	private final static String LOOKANDFEEL = "Metal";
	private final static String THEME = "Ocean";
	/**
	 * This method sets the look and feel of the app.
	 */
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
	
//	private UserFrame elevWindow = new ElevFrame();
	private LoginFrame myLoginFrame = new LoginFrame();
//	private ElevFrame myFrame = new ElevFrame();
	
//	private Thread timerThread = new Thread(new Runnable() {
//		
//		@Override
//		public void run() {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			JPanel bottomPanel = elevWindow.getBottomPanel();
//			JPanel rightPanel = elevWindow.getRightPanel();
//			rightPanel.remove(bottomPanel);
//			
//			bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//			bottomPanel.setBackground(Color.DARK_GRAY);
//			bottomPanel.setPreferredSize(elevWindow.getDateDim());
//			elevWindow.setBottomPanel(bottomPanel);
//			((UserFrameEquiped) elevWindow).setDateAndTime();
//			rightPanel.add(bottomPanel);
//		}
//	});
	 
	/**
	 * This method will create and show the LoginFrame.
	 */
	 private static void createAndShowGUI() {
	        //Set the look and feel.
	        initLookAndFeel();
	        JFrame.setDefaultLookAndFeelDecorated(true);

	        //Create and set up the window.
	        
	        Run app = new Run();
//	        timerThread.start();
	    }

	    public static void main(String[] args) {
	    	
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	        
	    }
	    
	}

