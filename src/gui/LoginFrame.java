package gui;

import gui.utils.Frame;
import gui.utils.PasswordField;
import gui.utils.TextField;

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
	private static String FRAME = "Login";
	private String USERNAME_LABEL = "Enter username : ";
	private String USERNAME_TEXTFIELD = "Your username here";
	private String PASSWD_LABEL = "Enter password : ";
	private String LOGIN_BTTN = "Login";
	private String CANCEL_BTTN = "Cancel";
	private String ERRDIALOG_TITLE = "Login error";
	private String ERRDIALOG_TEXT = "You should enter a username in order to login !";
	private boolean TEXTFIELD_SET_EDITABLE = true;

	public LoginFrame() {
		super(FRAME);

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
	
	public void setupLoginPanel() {
		
		// create the username label and textfield
		setupUsername();
		
		// create the passwd label and textfield
		setupPasswd();
		
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
	
	public void setupButtonPanel() {
		
		setupLoginBttn();
		setupCancelBttn();
		
		buttonPanel = new JPanel();
        buttonPanel.add(loginBttn);
        buttonPanel.add(cancelBttn);
		
	}
	
	private void setupCancelBttn() {
		
		cancelBttn = new JButton(CANCEL_BTTN);
		
		cancelBttn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
	}

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
					System.out.println(getUsername() + "\n" + getPassword());
	                dispose(); 
                }
			}
		});
        
	}
                
    private String getUsername() {
    	
		return usernameTextField.getText();
		
	}
    
    private String getPassword() {
    	
    	return new String(passwdTextField.getPassword());
    	
    }

	public void setupUsername() {
		
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
	
	public void setupPasswd() {
		
		passwdLabel = new JLabel(PASSWD_LABEL);
		passwdTextField = new PasswordField(TEXTFIELD_DIM, TEXTFIELD_SET_EDITABLE);
		addWindowListener(new WindowAdapter(){ 
			public void windowOpened(WindowEvent e){ 
			    passwdTextField.requestFocus();
			 } 
		}); 
		
	}
	
}
