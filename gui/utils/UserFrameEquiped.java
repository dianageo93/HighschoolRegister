package gui.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserFrameEquiped extends UserFrame implements UserFrameActions {
	
	private DateLabel myDateLabel;
	private BasicLabel usernameLabel;
	private BasicLabel logoutLabel;
	private BasicLabel whatUserLabel;
	private Date dateAndTime;
	private String LOGOUT = "Log out";
	private String username;
	private String whatUser;
	
	public UserFrameEquiped() {
		
		super();
		setDateAndTime();
		/* username si what-user aici --> */ setUserName("Username" + ", ", "Elev");
		setLogoutLabel();
		setOnClickLabelListener();
		
	}
	
	public void setLabels(String[] labels) {
		JPanel leftPanel = super.getLeftPanel();
		ElementsPanel myElPanel = super.getMyElPanel();
		leftPanel.remove(myElPanel);
		myElPanel = new ElementsPanel(labels);
		super.setMyElPanel(myElPanel);
		leftPanel.add(myElPanel);
		setOnClickLabelListener();
	}
	
	public void setDateAndTime() {

		dateAndTime = Calendar.getInstance().getTime();
		myDateLabel = new DateLabel(dateAndTime.toString());
		JPanel bottomPanel = super.getBottomPanel();
		bottomPanel.add(myDateLabel);
		
	}
	
	public void setUserName(String username, String whatUser) {
		
		usernameLabel = new BasicLabel(username);
		whatUserLabel = new BasicLabel(whatUser);
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
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setWhatUser(String whatUser) {
		this.whatUser = whatUser;
	}

	@Override
	public void setOnClickLabelListener() {
		ElementsPanel myElPanel = super.getMyElPanel();
		final ArrayList<BasicLabel> labels = myElPanel.getLabels();
		for(final BasicLabel i : labels) {
			i.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(i.getBorderColor() == Color.WHITE) {
						for(BasicLabel j : labels) {
							if(j.getBorderColor() == Color.GREEN) {
								j.setClicked(false);
							}
						}
						i.setClicked(true);
					}
					else {
						i.setClicked(false);
					}
					
				}
			});
		}
		
	}

	@Override
	public void setOnMouseOverLabelListener() {
		// TODO Auto-generated method stub
		
	}

}
