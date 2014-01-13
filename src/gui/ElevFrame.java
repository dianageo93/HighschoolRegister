package gui;

import gui.utils.BasicLabel;
import gui.utils.ElementsPanel;
import gui.utils.IUserFrameActions;
import gui.utils.UserFrameEquiped;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import liceu.Catalog;
import liceu.Centralizator;
import liceu.Clasa;
import liceu.Elev;
import liceu.Materie;
import liceu.Profesor;
import liceu.SituatieMaterieBaza;
import liceu.SituatieMaterieBaza.Absenta;
import liceu.SituatieMaterieBaza.Semestru;
import liceu.SituatieMaterieCuTeza;
import liceu.utils.Nota;

public class ElevFrame extends UserFrameEquiped implements IUserFrameActions {
	
	public ElevFrame() {
		super();
		String[] labels = {"Home", "Materii", "Catalog", "Colegi", "De-ale mele"};
		setLabels(labels);

		setOnClickLabelListener();
		setOnMouseOverLabelListener();
	}
	
	private BasicLabel materieDetailsLabel = null;
	// final in order to be used in Events() -> it requires constant values
	private final String NOTE_SEMESTRUL_1 = "show note semestrul 1";
	private final String NOTE_SEMESTRUL_2 = "show note semestrul 2";
	private final String CALCULEAZA_MEDIE_SEMESTRU = "calculeaza medie semestru";
	private final String CALCULEAZA_MEDIE_GENERALA = "calculeaza medie generala";
	private final String ABSENTE_SEMESTRU = "afiseaza absente semestrul";
	private final String TOTAL_ABSENTE = "calculeaza total absente";
	
	public void setMaterieDetailsLabel(BasicLabel materieDetailsLabel) {
		this.materieDetailsLabel = materieDetailsLabel;
	}
	
	public BasicLabel getMaterieDetailsLabel() {
		return materieDetailsLabel;
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
						setupMateriiPanel();
					}
					else if(labels.indexOf(i) == 2) {
						setupCatalogPanel();
					}
					else if(labels.indexOf(i) == 3) {
						setupColegiPanel();
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
	
	private JPanel clearCenterPanel() {
		JPanel centerPanel = super.getCenterPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPanel.removeAll();
		centerPanel.repaint();
		return centerPanel;
	}
	
	private void setupColegiPanel() {
		JPanel centerPanel = clearCenterPanel();
		JTable table = new JTable(new ColegiTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() - 10));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 35);
        }
        table.setAutoCreateRowSorter(true);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 35);
        }
        
        if(scrollPanel != null) {
        	centerPanel.remove(scrollPanel);
        }
        scrollPanel = new JScrollPane(table);
        scrollPanel.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() - 10));
        scrollPanel.getViewport().setBackground(Color.DARK_GRAY);
        setScrollPane(scrollPanel);
        centerPanel.add(scrollPanel);
        
        centerPanel.revalidate();
        centerPanel.repaint();
	}
	
	private void setupCatalogPanel() {
		JPanel centerPanel = clearCenterPanel();
		
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 20));

		//Build the first menu.
		menu = new JMenu("Situatie generala");
		menuBar.add(menu);
		
		//a submenu
		submenu = new JMenu("Note");

		menuItem = new JMenuItem("Semestrul 1");
		menuItem.addActionListener(new CustomActionListener());
		menuItem.setActionCommand(NOTE_SEMESTRUL_1);
		submenu.add(menuItem);

		menuItem = new JMenuItem("Semestrul 2");
		menuItem.addActionListener(new CustomActionListener());
		menuItem.setActionCommand(NOTE_SEMESTRUL_2);
		submenu.add(menuItem);
		menu.add(submenu);
		
		//another submenu
		menu.addSeparator();
		menuItem = new JMenuItem("Absente");
		menuItem.addActionListener(new CustomActionListener());
		menuItem.setActionCommand(ABSENTE_SEMESTRU);
		menu.add(menuItem);
		
		//Build second menu in the menu bar.
		menu = new JMenu("Materii");
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		String clasa = Centralizator.getInstance().getElevi().get(super.getUsername()).getClasa();
		Clasa found = null;
		for(Clasa c : clase) {
			if(c.getID().equals(clasa)) {
				found = c;
				break;
			}
		}
		if(found == null) {
			return;
		}
		Catalog ctg = found.getCatalog();
		TreeMap<Materie, SituatieMaterieBaza> sit = found.getCatalog().getMyMap().
				get(Centralizator.getInstance().getElevi().get(getUsername()));
		for(Map.Entry<Materie, SituatieMaterieBaza> entry : sit.entrySet()) {
			menuItem = new JMenuItem(entry.getKey().getNume());
			menu.add(menuItem);
		}
		
		menuBar.add(menu);
		
		centerPanel.add(menuBar);

	}
	
	private JScrollPane scrollPanel = null;
	
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPanel = scrollPane;
	}
	
	public JScrollPane getScrollPane() {
		return scrollPanel;
	}
	
	private JButton medieSemBttn = null;
	private BasicLabel medieSemLbl = null;
	private JButton medieGenBttn = null;
	private BasicLabel medieGenLbl = null;
	private double medieSem = 0.0;
	private double medieGen = 0.0;
	
	private void setupCatalogPanel(Semestru sem) {
		JPanel centerPanel = super.getCenterPanel();
		String[] columnNames = { "Semestru", 
								 "Materie", 
								 "Note", 
								 "Nota teza", 
								 "Medie" };
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		String clasa = Centralizator.getInstance().getElevi().get(super.getUsername()).getClasa();
		Clasa found = null;
		for(Clasa c : clase) {
			if(c.getID().equals(clasa)) {
				found = c;
				break;
			}
		}
		if(found == null) {
			return;
		}
		Catalog ctg = found.getCatalog();
		
		medieSem = ctg.getMedieElevSemestru(Centralizator.getInstance().getElevi().get(super.getUsername()), sem);
		medieGen = ctg.getMedieGeneralaElev(Centralizator.getInstance().getElevi().get(super.getUsername()));
		
		TreeMap<Materie, SituatieMaterieBaza> sit = found.getCatalog().getMyMap().
				get(Centralizator.getInstance().getElevi().get(getUsername()));
		String[][] data = new String[sit.size()][5];
		int contor = 0;
		for(Map.Entry<Materie, SituatieMaterieBaza> entry : sit.entrySet()) {
			if(contor == 0) {
				data[contor][0] = "<html>" + 
						"<p><i><b>" + 
						sem.toString() +
						"</b></i></p>" +
						"</html>";
			}
			else {
				data[contor][0] = "";
			}
			data[contor][1] = "<html>" +
					"<p><font size = \"5\">" + 
					entry.getKey().getNume() + 
					"</font></p></html>";
			LinkedList<Nota> noteSem = sem == Semestru.SEMESTRUL_1 ? 
					entry.getValue().getNoteSem1() : entry.getValue().getNoteSem2();
			data[contor][2] = "<html>";
			int blankLine = 0;
			for(Nota n : noteSem) {
				blankLine ++;
				data[contor][2] += n.getNota() + ", ";
				if(blankLine == 6) {
					blankLine = 0;
					data[contor][2] += "<br>";
				}
			}
			data[contor][2] += "</html>";
			data[contor][3] = "<html><p><font color = \"blue\" size = \"5\">";
			if(entry.getValue().getClass() == SituatieMaterieCuTeza.class) {
				SituatieMaterieCuTeza s = (SituatieMaterieCuTeza) entry.getValue();
				data[contor][3] += sem == Semestru.SEMESTRUL_1 ?
						s.getTezaSem1().getNota() : s.getTezaSem2().getNota();
			}
			data[contor][3] += "</font></p></html>";
			data[contor][4] = "<html><p><font color = \"red\" size = \"5\">";
			DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
			data[contor][4] += myDecimalFormat.format( sem == Semestru.SEMESTRUL_1 ?
					entry.getValue().getMedieSem1().getNota() : entry.getValue().getMedieSem2().getNota());
			data[contor][4] += "</font></p></html>";
			contor++;
		}
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() / 2));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 35);
        }
        if(scrollPanel != null) {
        	centerPanel.remove(scrollPanel);
        }
        scrollPanel = new JScrollPane(table);
        scrollPanel.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() * 6 / 7));
        scrollPanel.getViewport().setBackground(Color.DARK_GRAY);
        setScrollPane(scrollPanel);
        centerPanel.add(scrollPanel);
        
        if(medieSemBttn != null) {
        	centerPanel.remove(medieSemBttn);
        }
        if(medieSemLbl != null) {
        	centerPanel.remove(medieSemLbl);
        }
        if(medieGenBttn != null) {
        	centerPanel.remove(medieGenBttn);
        }
        if(medieGenLbl != null) {
        	centerPanel.remove(medieGenLbl);
        }
        if(nrAbsBttn != null) {
        	centerPanel.remove(nrAbsBttn);
        }
        if(nrAbsLbl != null) {
        	centerPanel.remove(nrAbsLbl);
        }
        
        medieSemBttn = new JButton("Calculeaza-ti media pe acest semestru :");
        medieSemBttn.setContentAreaFilled(false);
        medieSemBttn.setForeground(Color.WHITE);
        medieSemBttn.setActionCommand(CALCULEAZA_MEDIE_SEMESTRU);
        medieSemBttn.addActionListener(new CustomActionListener());
        centerPanel.add(medieSemBttn);
        medieSemLbl = new BasicLabel("Media ta aici");
        medieSemLbl.setForeground(Color.GRAY);
        medieSemLbl.setPreferredSize(new Dimension(100, 30));
        centerPanel.add(medieSemLbl);
        
        medieGenBttn = new JButton("Calculeaza-ti media generala :");
        medieGenBttn.setContentAreaFilled(false);
        medieGenBttn.setForeground(Color.WHITE);
        medieGenBttn.setActionCommand(CALCULEAZA_MEDIE_GENERALA);
        medieGenBttn.addActionListener(new CustomActionListener());
        centerPanel.add(medieGenBttn);
        medieGenLbl = new BasicLabel("Media ta aici");
        medieGenLbl.setForeground(Color.GRAY);
        centerPanel.add(medieGenLbl);
        
        centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	private JButton nrAbsBttn = null;
	private BasicLabel nrAbsLbl = null;
	private int nrAbs = 0;
	
	private void setupAbsentePanel() {
		JPanel centerPanel = super.getCenterPanel();
		String[] columnNames = { "Materie", 
								 "Absente", 
								 "Total absente" };
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		String clasa = Centralizator.getInstance().getElevi().get(super.getUsername()).getClasa();
		Clasa found = null;
		for(Clasa c : clase) {
			if(c.getID().equals(clasa)) {
				found = c;
				break;
			}
		}
		if(found == null) {
			return;
		}
		Catalog ctg = found.getCatalog();
		TreeMap<Materie, SituatieMaterieBaza> sit = found.getCatalog().getMyMap().
				get(Centralizator.getInstance().getElevi().get(getUsername()));
		String[][] data = new String[sit.size()][3];
		int contor = 0;
		nrAbs = 0;
		for(Map.Entry<Materie, SituatieMaterieBaza> entry : sit.entrySet()) {
			data[contor][0] = "<html>" +
					"<p><font size = \"5\">" + 
					entry.getKey().getNume() + 
					"</font></p></html>";
			LinkedList<Absenta> absente = entry.getValue().getAbsente();
			data[contor][1] = "<html>";
			int blankLine = 0;
			for(Absenta a : absente) {
				DateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
				blankLine ++;
				data[contor][1] += df.format(a.getDate().getTime()) + ", ";
				if(blankLine == 10) {
					blankLine = 0;
					data[contor][1] += "<br>";
				}
			}
			data[contor][1] += "</html>";
			data[contor][2] = "<html><p><font color = \"blue\" size = \"5\">";
			data[contor][2] += absente.size();
			nrAbs += absente.size();
			data[contor][2] += "</font></p></html>";
			contor++;
		}
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() / 2));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 45);
        }
        if(scrollPanel != null) {
        	centerPanel.remove(scrollPanel);
        }
        scrollPanel = new JScrollPane(table);
        scrollPanel.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() * 6 / 7));
        scrollPanel.getViewport().setBackground(Color.DARK_GRAY);
        setScrollPane(scrollPanel);
        centerPanel.add(scrollPanel);
        
        if(medieSemBttn != null) {
        	centerPanel.remove(medieSemBttn);
        }
        if(medieSemLbl != null) {
        	centerPanel.remove(medieSemLbl);
        }
        if(medieGenBttn != null) {
        	centerPanel.remove(medieGenBttn);
        }
        if(medieGenLbl != null) {
        	centerPanel.remove(medieGenLbl);
        }
        if(nrAbsBttn != null) {
        	centerPanel.remove(nrAbsBttn);
        }
        if(nrAbsLbl != null) {
        	centerPanel.remove(nrAbsLbl);
        }
        
        nrAbsBttn = new JButton("Calculeaza-ti numarul total de absente :");
        nrAbsBttn.setContentAreaFilled(false);
        nrAbsBttn.setForeground(Color.WHITE);
        nrAbsBttn.setActionCommand(TOTAL_ABSENTE);
        nrAbsBttn.addActionListener(new CustomActionListener());
        centerPanel.add(nrAbsBttn);
        nrAbsLbl = new BasicLabel("Numarul de absente aici");
        nrAbsLbl.setForeground(Color.GRAY);
        centerPanel.add(nrAbsLbl);
        
        centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	private void setupMateriiPanel() {
		final JPanel centerPanel = super.getCenterPanel();
		String clasa = Centralizator.getInstance().getElevi().get(super.getUsername()).getClasa();
		final TreeMap<Materie, Profesor> myTreeMap = new TreeMap<>();
		TreeMap<Materie, TreeMap<Clasa, Profesor>> repartizareProf = Centralizator.getInstance().getRepartizareProf();
		for(Map.Entry<Materie, TreeMap<Clasa, Profesor>> entry : repartizareProf.entrySet()) {
			TreeMap<Clasa, Profesor> value = entry.getValue();
			Profesor p;
			if(value.containsKey(new Clasa(clasa))) {
				p = value.get(new Clasa(clasa));
				myTreeMap.put(entry.getKey(), p);
			}
		}
		DefaultListModel<String> myListModel = new DefaultListModel<>();
		DefaultListModel<String> myListArrowModel = new DefaultListModel<>();
		for(Map.Entry<Materie, Profesor> entry : myTreeMap.entrySet()) {
			myListModel.addElement(entry.getKey().getNume());
			myListArrowModel.addElement(">");
		}
		final JList<String> list = new JList<>(myListModel);
		final JList<String> arrowList = new JList<>(myListArrowModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        arrowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        arrowList.setSelectedIndex(0);
        list.setCellRenderer(new WhiteYellowCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        arrowList.setSelectedIndex(0);
        arrowList.setCellRenderer(new WhiteYellowCellRenderer());
        arrowList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int index = arrowList.getSelectedIndex();
				String m = (String) list.getModel().getElementAt(index);
				Profesor p = myTreeMap.get(new Materie(m));
				if(materieDetailsLabel != null) {
					centerPanel.remove(materieDetailsLabel);
				}
				String content = "<html>" + 
						"<p align = \"center\">" + 
						"<font size=\"12\">" + 
						p.getNumeUtilizator().getNume() + " " + p.getNumeUtilizator().getPrenume() + 
						"</font>" + "</p>" +
						"<p align = \"center\">" + p.getLoginID() + "</p>";
				materieDetailsLabel = new BasicLabel(content, SwingConstants.CENTER);
				materieDetailsLabel.setPreferredSize(new Dimension(centerPanel.getWidth() / 2 - 50,
						centerPanel.getHeight() - 15));
				materieDetailsLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				centerPanel.add(materieDetailsLabel);
				centerPanel.revalidate();
				centerPanel.repaint();
				
			}
		});
        list.setVisibleRowCount(myTreeMap.size());
        arrowList.setVisibleRowCount(myTreeMap.size());
        JScrollPane listScrollPane = new JScrollPane(list);
        JScrollPane arrowListScrollPane = new JScrollPane(arrowList);
        listScrollPane.setPreferredSize(new Dimension(centerPanel.getWidth() / 2 - 10, centerPanel.getHeight() - 15));
        arrowListScrollPane.setPreferredSize(new Dimension(23, centerPanel.getHeight() - 15));
        centerPanel.removeAll();
        centerPanel.repaint();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerPanel.add(listScrollPane);
        centerPanel.add(arrowListScrollPane);
		
	}

	@Override
	public void setupHomePanel() {
		JPanel centerPanel = super.getCenterPanel();
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.removeAll();
		centerPanel.repaint();
		
		Elev e = Centralizator.getInstance().getElevi().get(super.getUsername());
		String content = "<html>" + 
				"<p align = \"center\">" + 
				"<font size=\"12\">" + 
				e.getNumeUtilizator().getNume() + " " + e.getNumeUtilizator().getPrenume() + 
				"</font>" + "</p>" +
				"<p align = \"center\">" + e.getLoginID() + "</p>" + "<br>" +
				"<p align = \"center\">" + e.getClasa() + "</html>";
		BasicLabel label = new BasicLabel(content);
		centerPanel.add(label);
	}
	
	private static class WhiteYellowCellRenderer extends DefaultListCellRenderer {  
        public Component getListCellRendererComponent(JList list, Object value, int index, 
        		boolean isSelected, boolean cellHasFocus) {  
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);  
            if (index % 2 == 0) {  
                c.setBackground(Color.YELLOW);  
            }  
            else {  
                c.setBackground(Color.WHITE);
            }
            c.setFont(new Font("Georgia", Font.PLAIN, 20));
            return c;  
        }  
    }
	
	private class CustomActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();
			DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
			switch(cmd) {
			case NOTE_SEMESTRUL_1 :
				setupCatalogPanel(Semestru.SEMESTRUL_1);
				break;
			case NOTE_SEMESTRUL_2 :
				setupCatalogPanel(Semestru.SEMESTRUL_2);
				break;
			case CALCULEAZA_MEDIE_SEMESTRU :
				medieSemLbl.setText(myDecimalFormat.format(medieSem));
				break;
			case CALCULEAZA_MEDIE_GENERALA :
				medieGenLbl.setText(myDecimalFormat.format(medieGen));
				break;
			case ABSENTE_SEMESTRU :
				setupAbsentePanel();
				break;
			case TOTAL_ABSENTE :
				nrAbsLbl.setText(myDecimalFormat.format(nrAbs));
				break;
			}
			
		}
		
	}
	
	private class ColegiTableModel extends AbstractTableModel {

		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		String[] columnNames = { "ID Logare",
				 "Nume si prenume", 
				 "Medie semestrul 1", 
				 "Medie semestrul 2",
				 "Medie generala" };
		String[][] data;
		
		public ColegiTableModel() {
			Clasa found = null;
			String clasa = Centralizator.getInstance().getElevi().get(getUsername()).getClasa();
			for(Clasa c : clase) {
				if(c.getID().equals(clasa)) {
					found = c;
					break;
				}
			}
			if(found == null) {
				return;
			}
			TreeSet<Elev> elevi = found.getElevi();
			Catalog ctg = found.getCatalog();
			
			data = new String[elevi.size()][5];
			int contor = 0;
			DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
			for(Elev e : elevi) {
				data[contor][1] = e.getNumeUtilizator().getNume() + " " + e.getNumeUtilizator().getPrenume();
				data[contor][2] = myDecimalFormat.format(ctg.getMedieElevSemestru(e, Semestru.SEMESTRUL_1));
				data[contor][3] = myDecimalFormat.format(ctg.getMedieElevSemestru(e, Semestru.SEMESTRUL_2));
				data[contor][4] = myDecimalFormat.format(ctg.getMedieGeneralaElev(e));
				data[contor][0] = e.getLoginID();
				contor++;
			}
			
		}
		
		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public String getColumnName(int col) {
            return columnNames[col];
        }

		@Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }
		
		@Override
		public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
		
	}
}
