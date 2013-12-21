package gui.utils;

import java.awt.Dimension;

import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField {

	public PasswordField(Dimension d, boolean editable) {
		super();
		setEditable(editable);
		setPreferredSize(d);
		setEchoChar('*');
	}

}
