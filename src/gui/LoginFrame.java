package gui;

import gui.utils.Frame;
import gui.utils.PasswordField;
import gui.utils.TextField;
import gui.utils.UserFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import server.AuthHelper;

/**
 * 
 * A LoginFrame object is the foremost frame a user will see when opening the app. It requires the username and
 * password in order to login. The login is done by finding a matching username/password pair  in the server.
 * The information on the server is parsed w/ the help of a Centralizator object.
 *
 */
public class LoginFrame extends Frame {

	private JTextField usernameTextField;
	private JPasswordField passwdTextField;
	private JLabel usernameLabel;
	private JLabel passwdLabel;
	private JPanel loginPanel;
	private JPanel buttonPanel;
	private JButton loginBttn;
	private JButton cancelBttn;
	private GridBagConstraints panelConstraints;
	private Dimension TEXTFIELD_DIM = new Dimension(200, 30);
	private static String FRAME_LABEL = "Login";
	private String USERNAME_LABEL = "Enter username : ";
	private String USERNAME_TEXTFIELD = "Your username here";
	private String PASSWD_LABEL = "Enter password : ";
	private String LOGIN_BTTN = "Login";
	private String CANCEL_BTTN = "Cancel";
	private String ERRDIALOG_TITLE = "Login error";
	private String ERRDIALOG_TEXT = "You should enter a username in order to login !";
	private String LOGINFAILED_TEXT = "Your password and/or username are incorrect.";
	private boolean TEXTFIELD_SET_EDITABLE = true;
	private AuthHelper myAuthHelper = AuthHelper.getInstance();

	public LoginFrame() {
		super(FRAME_LABEL);

		// add username and passwd fields to the frame
		setupLoginPanel();
		
		// add them buttons
		setupButtonPanel();
		
		// add them panels
        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	/**
	 * A method that displays the login panel, which holds the login and the password labels. The panel is added
	 * to the main frame.
	 */
	public void setupLoginPanel() {
		
		// create the username label and textfield
		setupUsernameLabel();
		
		// create the passwd label and textfield
		setupPasswdLabel();
		
		// add them elementes to the frame
		loginPanel = new JPanel(new GridBagLayout());
		panelConstraints = new GridBagConstraints();
		 
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelConstraints.gridx = 0;
        panelConstraints.gridy = 0;
        panelConstraints.gridwidth = 1;
        loginPanel.add(usernameLabel, panelConstraints);
        
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 0;
        panelConstraints.gridwidth = 2;
        loginPanel.add(usernameTextField, panelConstraints);
        
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.gridwidth = 1;
        loginPanel.add(passwdLabel, panelConstraints);
 
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 1;
        panelConstraints.gridwidth = 2;
        loginPanel.add(passwdTextField, panelConstraints);
        loginPanel.setBorder(new LineBorder(Color.GRAY));
        
	}
	
	/**
	 * A method that displays the button panel, which holds the login and the cancel buttons. The panel is added
	 * to the main frame.
	 */
	public void setupButtonPanel() {
		
		setupLoginBttn();
		setupCancelBttn();
		
		buttonPanel = new JPanel();
        buttonPanel.add(loginBttn);
        buttonPanel.add(cancelBttn);
		
	}
	
	/**
	 * A method that enables the cancel button. When pressed, the frame will be disposed.
	 */
	private void setupCancelBttn() {
		
		cancelBttn = new JButton(CANCEL_BTTN);
		
		cancelBttn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
	}

	/**
	 * A method that enables the login button. When pressed, the pair (username, password) is searched in the Centralizator
	 * object. If matched, the login frame is disposed and a new userframe is created, according to the type of the
	 * user that has entered credentials. If not, the user is given another try. 
	 */
	private void setupLoginBttn() {
		
		loginBttn = new JButton(LOGIN_BTTN);
		 
        loginBttn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getUsername().equals(USERNAME_TEXTFIELD)) {
					JOptionPane.showMessageDialog(new JDialog(), 
							ERRDIALOG_TEXT, ERRDIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(myAuthHelper.loginAccepted(getUsername(), getPassword())) {
						ElevFrame myElevFrame = new ElevFrame();
						myElevFrame.setUsername(getUsername());
						myElevFrame.setUsernameLabel();
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(new JDialog(), 
								LOGINFAILED_TEXT, ERRDIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
						usernameTextField.setForeground(Color.GRAY);
						usernameTextField.setText(USERNAME_TEXTFIELD);
						passwdTextField.setText("");
					}
                }
			}
		});
        
	}
    
	/**
	 * The method returns the entered username.
	 */
    private String getUsername() {
    	
		return usernameTextField.getText();
		
	}
    
    /**
	 * The method returns the entered passwords.
	 */
    private String getPassword() {
    	
    	return new String(passwdTextField.getPassword());
    	
    }

    /**
	 * A method that adds the username label to the frame.
	 */
	public void setupUsernameLabel() {
		
		usernameLabel = new JLabel(USERNAME_LABEL);
		usernameTextField = new TextField(USERNAME_TEXTFIELD, TEXTFIELD_DIM, TEXTFIELD_SET_EDITABLE);
		usernameTextField.setForeground(Color.GRAY);
		usernameTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if(getUsername().isEmpty()) {
					usernameTextField.setForeground(Color.GRAY);
					usernameTextField.setText(USERNAME_TEXTFIELD);
				}
				
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				if(getUsername().equals(USERNAME_TEXTFIELD)) {
					usernameTextField.setForeground(Color.BLACK);
					usernameTextField.setText("");
				}
				else {
					usernameTextField.selectAll();
				}
			}
		});
		
	}
	
	/**
	 * A method that adds the password label to the frame.
	 */
	public void setupPasswdLabel() {
		
		passwdLabel = new JLabel(PASSWD_LABEL);
		passwdTextField = new PasswordField(TEXTFIELD_DIM, TEXTFIELD_SET_EDITABLE);
		addWindowListener(new WindowAdapter(){ 
			public void windowOpened(WindowEvent e){ 
			    passwdTextField.requestFocus();
			 } 
		}); 
		
	}
	
	@Override
	public String toString() {
		return "LoginFrame [usernameTextField=" + usernameTextField
				+ ", passwdTextField=" + passwdTextField + ", usernameLabel="
				+ usernameLabel + ", passwdLabel=" + passwdLabel
				+ ", loginPanel=" + loginPanel + ", buttonPanel=" + buttonPanel
				+ ", loginBttn=" + loginBttn + ", cancelBttn=" + cancelBttn
				+ ", panelConstraints=" + panelConstraints + ", TEXTFIELD_DIM="
				+ TEXTFIELD_DIM + ", USERNAME_LABEL=" + USERNAME_LABEL
				+ ", USERNAME_TEXTFIELD=" + USERNAME_TEXTFIELD
				+ ", PASSWD_LABEL=" + PASSWD_LABEL + ", LOGIN_BTTN="
				+ LOGIN_BTTN + ", CANCEL_BTTN=" + CANCEL_BTTN
				+ ", ERRDIALOG_TITLE=" + ERRDIALOG_TITLE + ", ERRDIALOG_TEXT="
				+ ERRDIALOG_TEXT + ", LOGINFAILED_TEXT=" + LOGINFAILED_TEXT
				+ ", TEXTFIELD_SET_EDITABLE=" + TEXTFIELD_SET_EDITABLE + "]";
	}
	
}
