package gui.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ElementsPanel extends JPanel {
	
	private GridLayout myGridLayout;
	private int numberOfElements;
	private String[] myLabels = null;
	private ArrayList<BasicLabel> labels = null;
	
	public ElementsPanel(String[] labels) {
		
		super();
		setBackground(Color.DARK_GRAY);
		numberOfElements = labels.length;
		myGridLayout = new GridLayout(numberOfElements, 0, 40, 40);
		this.setLayout(myGridLayout);
		myLabels = labels;
		setupMyLabels();
		
	}

	private void setupMyLabels() {
		labels = new ArrayList<>();
		for(String i : myLabels) {
			BasicLabel label = new BasicLabel(i, SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(330, 65));
			label.setFontSize(40);
			label.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.WHITE));
			labels.add(label);
			add(label);
		}
		
	}
	
	public ArrayList<BasicLabel> getLabels() {
		return labels;
	}
	
	public void setupMyLabels(String[] myLabels) {
		
		for(String i : myLabels) {
			BasicLabel label = new BasicLabel(i, SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(330, 65));
			label.setFontSize(40);
			label.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY, Color.WHITE));
			add(label);
		}
		
	}

}
