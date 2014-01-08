package gui.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserFrameEquiped extends UserFrame {
	
	private DateLabel myDateLabel;
	private BasicLabel usernameLabel;
	private BasicLabel logoutLabel;
	private BasicLabel whatUserLabel;
	private Date dateAndTime;
	private String LOGOUT = "Log out";
	
	public UserFrameEquiped() {
		
		super();
		setDateAndTime();
		setLogoutLabel();
		
	}
	
	public void setLabels(String[] labels) {
		JPanel leftPanel = super.getLeftPanel();
		ElementsPanel myElPanel = super.getMyElPanel();
		leftPanel.remove(myElPanel);
		myElPanel = new ElementsPanel(labels);
		super.setMyElPanel(myElPanel);
		leftPanel.add(myElPanel);
	}
	
	public void setDateAndTime() {

		dateAndTime = Calendar.getInstance().getTime();
		myDateLabel = new DateLabel(dateAndTime.toString());
		JPanel bottomPanel = super.getBottomPanel();
		bottomPanel.add(myDateLabel);
		
	}
	
	public void setUsernameLabel() {
		
		usernameLabel = new BasicLabel(super.getUsername() + ", ");
		whatUserLabel = new BasicLabel(super.getWhatUser());
		whatUserLabel.setFontStyle(Font.ITALIC);
		JPanel userStuffPanel = super.getUserStuffPanel();
		userStuffPanel.add(usernameLabel);
		userStuffPanel.add(whatUserLabel);
		
	}
	
	public void setLogoutLabel() {
		
		logoutLabel = new BasicLabel(LOGOUT);
		logoutLabel.setFontSize(10);
		logoutLabel.setFontStyle(Font.BOLD);
		JPanel logoutPanel = super.getLogoutPanel();
		logoutPanel.add(logoutLabel);
		
	}

}
