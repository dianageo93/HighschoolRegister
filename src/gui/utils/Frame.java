package gui.utils;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Frame extends JFrame {
	
	public Frame(String label, Dimension d) {
		super(label);
		setSize(d);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setResizable(false);
	}
	
	public Frame(String label) {
		super(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        setResizable(false);
	}


}
