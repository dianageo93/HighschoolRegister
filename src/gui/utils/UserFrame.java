package gui.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Basic GUI for user's profile

public class UserFrame extends Frame {
	
	private JPanel mainPanel;
	private JPanel rightPanel;
	private JPanel userStuffPanel;
	private JPanel logoutPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JPanel upPanel;
	private JPanel leftPanel;
	private ElementsPanel myElPanel;
	private JLabel imageLabel;
	private static String FRAME_LABEL = "MyProfile";
	private int xDisplay;
	private int yDisplay;
	private Dimension screenDim;
	private Dimension rightDim;
	private Dimension leftDim;
	private Dimension userStuffDim;
	private Dimension logoutDim;
	private Dimension dateDim;
	private Dimension centerDim;
	private Dimension upDim;
	private String[] labels = {"mama mare", "tata mare", "caca mare", "pipi mare", "matza pute"};
	private String username;
	private String whatUser;
	
	public UserFrame() {
		
		super(FRAME_LABEL);
		
		setupDimensions();
		
		setupMainPanel();
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
		
	private void setupDimensions() {
		
		xDisplay = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 30;
		yDisplay = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60;
		
		screenDim = new Dimension(xDisplay, yDisplay);
		rightDim = new Dimension(3 * xDisplay / 4 - 20, yDisplay - 10);
		leftDim = new Dimension(xDisplay / 4, yDisplay - 10);
		upDim = new Dimension(3 * xDisplay / 4 - 23, 30);
		userStuffDim = new Dimension((3 * xDisplay / 4 - 25) / 2, 20);
		dateDim = new Dimension(3 * xDisplay / 4 - 23, 20);
		logoutDim = new Dimension((3 * xDisplay / 4 - 50) / 2, 20);
		centerDim = new Dimension(3 * xDisplay / 4 - 23, yDisplay - 10 - 20 - 15 - 15 - 21);
				
	}
	
	public void setMyElPanel(ElementsPanel myElPanel) {
		this.myElPanel = myElPanel;
	}
	
	private void setupLeftPanel() {

		BufferedImage img = null;
		leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		leftPanel.setPreferredSize(leftDim);
		leftPanel.setBackground(Color.DARK_GRAY);
		
		try {
			img = ImageIO.read(new File("/home/user/Desktop/example.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageLabel = new JLabel(new ImageIcon(img));
		leftPanel.add(imageLabel);
		
		myElPanel = new ElementsPanel(labels);
		leftPanel.add(myElPanel);
		
	}
	
	public ElementsPanel getMyElPanel() {
		return myElPanel;
	}
	
	protected void setupRightPanel() {
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(rightDim);
		rightPanel.setBackground(Color.DARK_GRAY);
		
		upPanel = new JPanel();
		upPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		upPanel.setPreferredSize(upDim);
		upPanel.setBackground(Color.DARK_GRAY);
		
		userStuffPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		userStuffPanel.setPreferredSize(userStuffDim);
		userStuffPanel.setBackground(Color.DARK_GRAY);
		upPanel.add(userStuffPanel);
		
		logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		logoutPanel.setPreferredSize(logoutDim);
		logoutPanel.setBackground(Color.DARK_GRAY);
		upPanel.add(logoutPanel);
		
		centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.setBackground(Color.DARK_GRAY);
		centerPanel.setPreferredSize(centerDim);
		
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setPreferredSize(dateDim);
		
		rightPanel.add(upPanel);
		rightPanel.add(centerPanel);
		rightPanel.add(bottomPanel);
		
	}
	
	private void setupMainPanel() {
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(screenDim);
		mainPanel.setBackground(Color.DARK_GRAY);
		setupLeftPanel();
		setupRightPanel();
		
        mainPanel.add(leftPanel);     
        mainPanel.add(rightPanel);
        add(mainPanel);
		
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}
	
	public JPanel getRightPanel() {
		return rightPanel;
	}
	
	public JPanel getUserStuffPanel() {
		return userStuffPanel;
	}
	
	public JPanel getCenterPanel() {
		return centerPanel;
	}
	
	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	
	public void setBottomPanel(JPanel bottomPanel) {
		this.bottomPanel = bottomPanel;
	}
	
	public JPanel getBottomPanel() {
		return bottomPanel;
	}
	
	public Dimension getDateDim() {
		return dateDim;
	}
	
	public static String getFRAME_LABEL() {
		return FRAME_LABEL;
	}
	
	public JPanel getLogoutPanel() {
		return logoutPanel;
	}
	
	public Dimension getCenterDim() {
		return centerDim;
	}
	
	public String getUsername() {
		String username = "";
		String[] tokens = this.username.split(".");
		for(String i : tokens) {
			System.out.println(i);
		}
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getWhatUser() {
		return this.whatUser;
	}
	
	public void setWhatUser(String whatUser) {
		this.whatUser = whatUser;
	}
	
	public void setUserStuffPanel(JPanel userStuffPanel) {
		this.userStuffPanel = userStuffPanel;
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
}
