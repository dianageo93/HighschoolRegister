package grafic.utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	private Image img = null;
	
	public ImagePanel(Image img) {
		this.img = img;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(img != null) {
			g.drawImage(img, 0, 0, null);
		}
	}

}
