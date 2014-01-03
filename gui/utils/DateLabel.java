package gui.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class DateLabel extends JLabel {
	
	public DateLabel(String content) {
		
		super(content);
		setFont(new Font("Serif", Font.ITALIC, 10));
		setForeground(Color.WHITE);
		
	}

}
