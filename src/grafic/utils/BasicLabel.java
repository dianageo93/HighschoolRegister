package grafic.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class BasicLabel extends JLabel {
	
	private int SIZE = 14;
	private int STYLE = Font.PLAIN;
	private String FACE = "Georgia";
	private Color FOREGROUND = Color.WHITE;
	private Color BORDER_COLOR = Color.WHITE;
	private boolean CLICKED = false;
	private boolean MOUSEOVER = false;
	
	public BasicLabel(String content) {
			super(content);
			setFont(new Font(FACE, STYLE, SIZE));
			setForeground(FOREGROUND);
	}
	
	public BasicLabel() {
		
	}
	
	public BasicLabel(String content, int constraints) {
		super(content, constraints);
		setFont(new Font(FACE, STYLE, SIZE));
		setForeground(FOREGROUND);
	}
	
	public Font getFont() {
		return new Font(FACE, STYLE, SIZE);
	}
	
	public boolean isClicked() {
		return CLICKED == true;
	}
	
	public boolean isMouseOver() {
		return MOUSEOVER == true;
	}
	
	public void setFontSize(int size) {
		
		this.SIZE = size;
		setFont(new Font(FACE, STYLE, SIZE));
		
	}

	public void setFontStyle(int style) {
		
		this.STYLE = style;
		setFont(new Font(FACE, STYLE, SIZE));
		
	}
	
	public void setFontFace(String face) {
		
		this.FACE = face;
		setFont(new Font(FACE, STYLE, SIZE));
		
	}
	
	public void setBorderColor(Color borderColor) {
		this.BORDER_COLOR = borderColor;
	}
	
	public Color getBorderColor() {
		return BORDER_COLOR;
	}
	
	public void setClicked(boolean clicked) {
		if(clicked == true) {
			CLICKED = true;
			setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.GREEN));
			setBorderColor(Color.GREEN);
		}
		else {
			CLICKED = false;
			setBorderColor(Color.WHITE);
			setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.WHITE));
		}
	}
	
	public void setMouseOver(boolean mouseOver) {
		if(mouseOver == true) {
			setFontSize(45);
			MOUSEOVER = true;
		}
		else {
			setFontSize(40);
			MOUSEOVER = false;
		}
	}

}
