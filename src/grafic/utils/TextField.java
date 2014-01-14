package grafic.utils;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	public TextField(String label, Dimension d, boolean editable) {
		super(label);
		setEditable(editable);
		setPreferredSize(d);
	}
	
	public TextField(Dimension d, boolean editable) {
		super();
		setEditable(editable);
		setPreferredSize(d);
	}
	
}
