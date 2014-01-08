package gui;

import gui.utils.BasicLabel;
import gui.utils.ElementsPanel;
import gui.utils.IUserFrameActions;
import gui.utils.UserFrameEquiped;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ElevFrame extends UserFrameEquiped implements IUserFrameActions {
	
	public ElevFrame() {
		super();
		String[] labels = {"Home", "Materii", "Catalog", "Colegi", "De-ale mele"};
		setLabels(labels);

		setOnClickLabelListener();
		setOnMouseOverLabelListener();
	}
	

	@Override
	public void setOnClickLabelListener() {
		ElementsPanel myElPanel = super.getMyElPanel();
		final ArrayList<BasicLabel> labels = myElPanel.getLabels();
		for(final BasicLabel i : labels) {
			i.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(i.isClicked() == false) {
						for(BasicLabel j : labels) {
							if(j.isClicked() == true) {
								j.setClicked(false);
							}
						}
						i.setClicked(true);
					}
					else {
						i.setClicked(false);
					}
					
					if(labels.indexOf(i) == 0) {
						setupHomePanel();
					}
					
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
	}

	@Override
	public void setOnMouseOverLabelListener() {
		ElementsPanel myElPanel = super.getMyElPanel();
		final ArrayList<BasicLabel> labels = myElPanel.getLabels();
		for(final BasicLabel i : labels) {
			i.addMouseListener(new MouseAdapter() {
				@Override
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                i.setMouseOver(true);
	            }

	            @Override
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	            	i.setMouseOver(false);
	            }
			});
		}
		
	}

	@Override
	public void setupHomePanel() {
		JPanel rightPanel = super.getRightPanel();
		JPanel centerPanel = super.getCenterPanel();
		JPanel bottomPanel = super.getBottomPanel();
		rightPanel.remove(centerPanel);
		rightPanel.remove(bottomPanel);
		
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setPreferredSize(super.getDateDim());
		super.setBottomPanel(bottomPanel);
		super.setDateAndTime();
		
		centerPanel.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.setBackground(Color.DARK_GRAY);
		centerPanel.setPreferredSize(super.getCenterDim());
		
		super.setCenterPanel(centerPanel);
		rightPanel.add(centerPanel);
		rightPanel.add(bottomPanel);
	}

}
