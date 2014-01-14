package grafic;

import grafic.utils.BasicLabel;
import grafic.utils.ElementsPanel;
import grafic.utils.IUserFrameActions;
import grafic.utils.UserFrameEquiped;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import server.LogoutHelper;

import liceu.Centralizator;
import liceu.Materie;
import liceu.Profesor;
import liceu.Secretar;
import liceu.Secretar.EditOptions;
import liceu.utils.NumePrenume;

public class SecretarFrame extends UserFrameEquiped implements
		IUserFrameActions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SAVE_CHANGED_DATA = "salveaza datele";
	private final String EDITEAZA_DATE_PROFESOR = "editeaza date profesor";

	/**
	 * The constructor. It also sets the left panel on the gui.
	 */
	public SecretarFrame() {
		super();
		String[] labels = {"Home", "Editare profesor", "Editare clasa", "De-ale mele"};
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
					else if(labels.indexOf(i) == 1) {
						setupEditareProfesorPanel();
					}
					else if(labels.indexOf(i) == 2) {
					}
					else {
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
	
	private void setupEditareProfesorPanel() {
		JPanel centerPanel = clearCenterPanel();
		
		JMenuBar menuBar;
		JMenu submenu, menu;
		JMenuItem menuItem;
		
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 20));

		//Build the first menu.
		menu = new JMenu("Profesori");
		menuBar.add(menu);
		
		Set<Materie> materii = Centralizator.getInstance().getRepartizareProf().keySet();
		
		for(Materie m : materii) {
			submenu = new JMenu(m.getNume());
			for(Profesor p : Centralizator.getInstance().getProfesori().values()) {
				if(p.getMaterie().equals(m.getNume())) {
					menuItem = new JMenuItem(p.getNumeUtilizator().getNume() + " " + p.getNumeUtilizator().getPrenume());
					menuItem.addActionListener(new CustomActionListener(p));
					menuItem.setActionCommand(EDITEAZA_DATE_PROFESOR);
					submenu.add(menuItem);
				}
			}
			menu.add(submenu);
			
		}
		
		menuBar.add(menu);
		
		centerPanel.add(menuBar);
	}
	
	private JPanel clearCenterPanel() {
		JPanel centerPanel = super.getCenterPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.removeAll();
		centerPanel.repaint();
		return centerPanel;
	}
	
	private BasicLabel dateOldProf = null;
	private BasicLabel dateNewProf = null;
	private JScrollPane dateOldScrollPane = null;
	private JScrollPane dateNewScrollPane = null;
	private JButton saveAllBttn = null;
	
	private void setupDateProfesor(final Profesor p) {
		JPanel centerPanel = super.getCenterPanel();
		if(dateNewProf != null) {
			centerPanel.remove(dateNewProf);
		}
		if(dateOldProf != null) {
			centerPanel.remove(dateOldProf);
		}
		dateOldProf = new BasicLabel("Date curente profesor " + p.getNumeUtilizator().getNume() +
				" " + p.getNumeUtilizator().getPrenume() + " - " + p.getMaterie());
		dateOldProf.setFontSize(20);
		dateOldProf.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 40));
		dateOldProf.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.add(dateOldProf);
		
		String[] columnNames = { "",
								 ""};
		String[][] data = new String[4][2];
		data[0][0] = "<html><font color = \"blue\"><b>Nume si prenume :</b></font></html>";
		data[0][1] = p.getNumeUtilizator().getNume() + " " + p.getNumeUtilizator().getPrenume();
		data[1][0] = "<html><font color = \"blue\"><b>Login ID :</b></font></html>";
		data[1][1] = p.getLoginID();
		data[2][0] = "<html><font color = \"blue\"><b>Materie :</b></font></html>";
		data[2][1] = p.getMaterie();
		data[3][0] = "<html><font color = \"blue\"><b>Clase :</b></font></html>";
		data[3][1] = "<html>";
		int contor = 0;
		TreeSet<String> clase = p.getClase();
		for(String cls : clase) {
			contor++;
			if(contor == 9) {
				data[3][1] += "<br>";
			}
			data[3][1] += cls + ", ";
		}
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, 190));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        

        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 45);
        }
        
        for(int col = 0; col < table.getColumnCount(); col++) {
        	table.getColumnModel().getColumn(col).setPreferredWidth(200);
        	if(col == table.getColumnCount() - 1) {
        		table.getColumnModel().getColumn(col).setPreferredWidth(250);
        	}
        }
        
        if(dateOldScrollPane != null) {
        	centerPanel.remove(dateOldScrollPane);
        }
        dateOldScrollPane = new JScrollPane(table);
        dateOldScrollPane.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 190));
        dateOldScrollPane.getViewport().setBackground(Color.DARK_GRAY);
        
        centerPanel.add(dateOldScrollPane);
        // ============
        dateNewProf = new BasicLabel("Editarea datelor");
        dateNewProf.setFontSize(20);
        dateNewProf.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 40));
        dateNewProf.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.add(dateNewProf);
		
		data = new String[5][2];
		data[0][0] = "<html><font color = \"blue\"><b>Nume si prenume :</b></font></html>";
		data[0][1] = "";
		data[1][0] = "<html><font color = \"blue\"><b>Login ID :</b></font></html>";
		data[1][1] = "";
		data[2][0] = "<html><font color = \"blue\"><b>Materie :</b></font></html>";
		data[2][1] = "";
		data[3][0] = "<html><font color = \"blue\"><b>Adauga o clasa :</b></font></html>";
		data[3][1] = "";
		data[4][0] = "<html><font color = \"blue\"><b>Sterge o clasa :</b></font></html>";
		data[4][1] = "";
		final JTable table1 = new JTable(data, columnNames);
        table1.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, 234));
        table1.setFillsViewportHeight(true);
        table1.setOpaque(false);

        for(int row = 0; row < table1.getRowCount(); row++) {
        	table1.setRowHeight(row, 45);
        }
        
        for(int col = 0; col < table1.getColumnCount(); col++) {
        	table1.getColumnModel().getColumn(col).setPreferredWidth(200);
        	if(col == table1.getColumnCount() - 1) {
        		table1.getColumnModel().getColumn(col).setPreferredWidth(250);
        	}
        }
        
        table1.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent arg0) {
				int row = arg0.getFirstRow();
				int col = arg0.getColumn();
				switch(row) {
				case 0 :
					new Secretar().editProfesor(EditOptions.EDIT_PROFESOR_CHANGE_NAME, p, 
							new NumePrenume((String) table1.getModel().getValueAt(row, col)));
					JOptionPane.showMessageDialog(new JDialog(), 
							"Ar trebui sa schimbi si LoginID :) !", "Nume schimbat", JOptionPane.INFORMATION_MESSAGE);
					break;
				case 1 :
					new Secretar().editClasa(EditOptions.EDIT_PROFESOR_CHANGE_LOGIN_ID, p,
							(String) table1.getModel().getValueAt(row, col), null, null, null);
					break;
				case 2 :
					new Secretar().editClasa(EditOptions.EDIT_PROFESOR_CHANGE_MATERIE, p, null, null, null,
							(String) table1.getModel().getValueAt(row, col));
					break;
				case 3 :
					new Secretar().editClasa(EditOptions.EDIT_PROFESOR_ADD_CLASA, p, null, 
							(String) table1.getModel().getValueAt(row, col), null, null);
					break;
				case 4 :
					new Secretar().editClasa(EditOptions.EDIT_PROFESOR_DELETE_CLASA, p, null, null,
							(String) table1.getModel().getValueAt(row, col), null);
					break;
				}
				System.out.println(p);
			}
		});
        
        if(dateNewScrollPane != null) {
        	centerPanel.remove(dateNewScrollPane);
        }
        dateNewScrollPane = new JScrollPane(table1);
        dateNewScrollPane.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 234));
        dateNewScrollPane.getViewport().setBackground(Color.DARK_GRAY);
        
        centerPanel.add(dateNewScrollPane);
        
        if(saveAllBttn != null) {
        	centerPanel.remove(saveAllBttn);
        }
        saveAllBttn = new JButton("Salveaza datele schimbate");
        saveAllBttn.setContentAreaFilled(false);
        saveAllBttn.setForeground(Color.WHITE);
        saveAllBttn.setActionCommand(SAVE_CHANGED_DATA);
        saveAllBttn.addActionListener(new CustomActionListener());
        centerPanel.add(saveAllBttn);
		
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	@Override
	public void setupHomePanel() {
		JPanel centerPanel = super.getCenterPanel();
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.removeAll();
		centerPanel.repaint();
		
		Secretar s = Centralizator.getInstance().getSecretari().get(super.getUsername());
		String content = "<html>" + 
				"<p align = \"center\">" + 
				"<font size=\"12\">" + 
				s.getNumeUtilizator().getNume() + " " + s.getNumeUtilizator().getPrenume() + 
				"</font>" + "</p>" +
				"<p align = \"center\">" + s.getLoginID() + "</p>" + "<br>" +
				"<p align = \"center\">" + "Secretar" + "</html>";
		BasicLabel label = new BasicLabel(content);
		centerPanel.add(label);

	}

	private class CustomActionListener implements ActionListener {
		
		private Profesor p = new Profesor();
		
		public CustomActionListener(Profesor p) {
			this.p = p;
		}

		public CustomActionListener() {
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String cmd = arg0.getActionCommand();
			switch(cmd) {
			case EDITEAZA_DATE_PROFESOR :
				setupDateProfesor(p);
				break;
			case SAVE_CHANGED_DATA :
				LogoutHelper.getInstance().updateListaProfesori();
				JOptionPane.showMessageDialog(new JDialog(), 
						"Update reusit !", "Update date pe server", 
						JOptionPane.ERROR_MESSAGE);
				break;
			}
			
		}
		
	}
	
}
