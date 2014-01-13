package gui;

import gui.utils.BasicLabel;
import gui.utils.ElementsPanel;
import gui.utils.IUserFrameActions;
import gui.utils.UserFrameEquiped;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import server.LogoutHelper;

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

public class ProfesorFrame extends UserFrameEquiped implements IUserFrameActions {
	
	public ProfesorFrame() {
		super();
		String[] labels = {"Home", "Clasele mele", "Editare elev", "De-ale mele"};
		setLabels(labels);

		setOnClickLabelListener();
		setOnMouseOverLabelListener();
	}
	
	private final String SHOW_CLASA = "afiseaza clasa selectata";
	private final String CALCULEAZA_MEDIE_GENERALA = "calculeaza medie generala";
	private final String CALCULEAZA_MEDIE_MAXIMA = "calculeaza medie maxima";
	private final String CALCULEAZA_MEDIE_MINIMA = "calculeaza medie minima";
	private final String CALCULEAZA_NR_ELEVI = "calculeaza numar elevi";
	private final String EDITEAZA_SITUATIE_ELEV = "editeaza situatie elev";
	private final String ADD_NOTA_SEM_1 = "adauga nota sem 1";
	private final String ADD_TEZA_SEM_1 = "adauga teza sem 1";
	private final String ADD_NOTA_SEM_2 = "adauga nota sem 2";
	private final String ADD_TEZA_SEM_2 = "adauga teza sem 2";
	private final String ADD_ABSENTA_SEM = "adauga absenta";

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
						setupClaseleMelePanel();
					}
					else if(labels.indexOf(i) == 2) {
						setupEditareElevPanel();
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
	
	private void setupClaseleMelePanel() {
		JPanel centerPanel = clearCenterPanel();
		
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 20));

		//Build the first menu.
		menu = new JMenu("Clasele mele");
		menuBar.add(menu);
		
		Profesor p = Centralizator.getInstance().getProfesori().get(getUsername());
		TreeSet<String> clase = p.getClase();
		for(String c : clase) {
			menuItem = new JMenuItem(c);
			menuItem.addActionListener(new CustomActionListener(c));
			menuItem.setActionCommand(SHOW_CLASA);
			menu.add(menuItem);
		}
		
		menuBar.add(menu);
		
		centerPanel.add(menuBar);
	}
	
	private void setupEditareElevPanel() {
		JPanel centerPanel = clearCenterPanel();
		
		JMenuBar menuBar;
		JMenu submenu, menu;
		JMenuItem menuItem;
		
		//Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 20));

		//Build the first menu.
		menu = new JMenu("Clasele mele - alege si editeaza situatie elev");
		menuBar.add(menu);
		
		Profesor p = Centralizator.getInstance().getProfesori().get(getUsername());
		TreeSet<String> clase = p.getClase();
		for(String c : clase) {
			submenu = new JMenu(c);
			TreeSet<Elev> elevi = p.listEleviFromClasa(c);
			for(Elev e : elevi) {
				menuItem = new JMenuItem(e.getNumeUtilizator().getNume() + " " + e.getNumeUtilizator().getPrenume());
				menuItem.addActionListener(new CustomActionListener(e));
				menuItem.setActionCommand(EDITEAZA_SITUATIE_ELEV);
				submenu.add(menuItem);
			}
			menu.add(submenu);
			
		}
		
		menuBar.add(menu);
		
		centerPanel.add(menuBar);
	}
	
	private final JTextField addNota1Lbl = new JTextField();
	private final JTextField addTeza1Lbl = new JTextField();
	private final JTextField addNota2Lbl = new JTextField();
	private final JTextField addTeza2Lbl = new JTextField();
	private final JTextField addAbs1Lbl = new JTextField();
	private final JTextField ziLbl = new JTextField();
	private final JTextField anLbl = new JTextField();
	private final JTextField lunaLbl = new JTextField();
	private final JTextField addAbs2Lbl = new JTextField();
	private final JTextField zi2Lbl = new JTextField();
	private final JTextField luna2Lbl = new JTextField();
	private final JTextField an2Lbl = new JTextField();
	private JTextField status = new JTextField();
	
	private void setupAdaugaNota(final Elev e) {
		setupAdaugaNotaSemestru(e, Semestru.SEMESTRUL_1);
		setupAdaugaNotaSemestru(e, Semestru.SEMESTRUL_2);
		JPanel centerPanel = super.getCenterPanel();
		if(absScrollPane != null ) {
			centerPanel.remove(absScrollPane);
		}
		String[] columnNames = { "Absente" };
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa found = null;
		for(Clasa c : clase) {
		if(c.getID().equals(e.getClasa())) {
			found = c;
			break;
			}
		}
		if(found == null) {
			return;
		}
		TreeMap<Materie, SituatieMaterieBaza> myMap = found.getCatalog().getMyMap().get(e);
		String materie = Centralizator.getInstance().getProfesori().get(getUsername()).getMaterie();
		SituatieMaterieBaza sit = myMap.get(new Materie(materie));
		String[][] data = new String[1][1];
		LinkedList<Absenta> absente = sit.getAbsente();
		data[0][0] = "<html>";
		int blankLine = 0;
		for(Absenta a : absente) {
			DateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
			blankLine ++;
			data[0][0] += df.format(a.getDate().getTime()) + ", ";
			if(blankLine == 10) {
				blankLine = 0;
				data[0][0] += "<br>";
			}
		}
		data[0][0] += "</html>";
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, 70));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        if(absScrollPane != null) {
        	centerPanel.remove(absScrollPane);
        }
        absScrollPane = new JScrollPane(table);
        absScrollPane.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 70));
        absScrollPane.getViewport().setBackground(Color.DARK_GRAY);
        setScrollPane(absScrollPane);
        centerPanel.add(absScrollPane);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 45);
        }
        for(int col = 0; col < table.getColumnCount(); col++) {
        	table.getColumnModel().getColumn(col).setPreferredWidth(200);
        }
        
        JButton addNota1Bttn = new JButton("Adauga nota sem I  :");
        addNota1Bttn.setContentAreaFilled(false);
        addNota1Bttn.setForeground(Color.WHITE);
        addNota1Bttn.setActionCommand(ADD_NOTA_SEM_1);
        addNota1Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addNota1Bttn);
        JTextField aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(710, 30));
        aux.setEditable(false);
        addNota1Lbl.setBackground(Color.DARK_GRAY);
        addNota1Lbl.setForeground(Color.WHITE);
        addNota1Lbl.setPreferredSize(new Dimension(60, 30));
        addNota1Lbl.setEditable(true);
        centerPanel.add(addNota1Lbl);
        centerPanel.add(aux);
        
        JButton addTeza1Bttn = new JButton("Adauga teza sem I  :");
        addTeza1Bttn.setContentAreaFilled(false);
        addTeza1Bttn.setForeground(Color.WHITE);
        addTeza1Bttn.setActionCommand(ADD_TEZA_SEM_1);
        addTeza1Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addTeza1Bttn);
        aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(710, 30));
        aux.setEditable(false);
        centerPanel.add(aux);addTeza1Lbl.setBackground(Color.DARK_GRAY);
        addTeza1Lbl.setForeground(Color.WHITE);
        addTeza1Lbl.setPreferredSize(new Dimension(60, 30));
        addTeza1Lbl.setEditable(true);
        centerPanel.add(addTeza1Lbl);
        centerPanel.add(aux);

        JButton addNota2Bttn = new JButton("Adauga nota sem II :");
        addNota2Bttn.setContentAreaFilled(false);
        addNota2Bttn.setForeground(Color.WHITE);
        addNota2Bttn.setActionCommand(ADD_NOTA_SEM_2);
        addNota2Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addNota2Bttn);
        addNota2Lbl.setBackground(Color.DARK_GRAY);
        addNota2Lbl.setForeground(Color.WHITE);
        addNota2Lbl.setPreferredSize(new Dimension(60, 30));
        addNota2Lbl.setEditable(true);
        centerPanel.add(addNota2Lbl);
        aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(710, 30));
        aux.setEditable(false);
        centerPanel.add(aux);
        
        JButton addTeza2Bttn = new JButton("Adauga teza sem II :");
        addTeza2Bttn.setContentAreaFilled(false);
        addTeza2Bttn.setForeground(Color.WHITE);
        addTeza2Bttn.setActionCommand(ADD_TEZA_SEM_2);
        addTeza2Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addTeza2Bttn);
        aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(710, 30));
        aux.setEditable(false);
        addTeza2Lbl.setBackground(Color.DARK_GRAY);
        addTeza2Lbl.setForeground(Color.WHITE);
        addTeza2Lbl.setPreferredSize(new Dimension(60, 30));
        addTeza2Lbl.setEditable(true);
        centerPanel.add(addTeza2Lbl);
        centerPanel.add(aux);
        
        JButton addAbs1Bttn = new JButton("Adauga absenta sem I  :");
        addAbs1Bttn.setContentAreaFilled(false);
        addAbs1Bttn.setForeground(Color.WHITE);
        addAbs1Bttn.setActionCommand(ADD_ABSENTA_SEM);
        addAbs1Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addAbs1Bttn);
        ziLbl.setBackground(Color.DARK_GRAY);
        ziLbl.setForeground(Color.WHITE);
        ziLbl.setPreferredSize(new Dimension(60, 30));
        ziLbl.setEditable(true);
        centerPanel.add(ziLbl);
        lunaLbl.setBackground(Color.DARK_GRAY);
        lunaLbl.setForeground(Color.WHITE);
        lunaLbl.setPreferredSize(new Dimension(60, 30));
        lunaLbl.setEditable(true);
        centerPanel.add(lunaLbl);
        anLbl.setBackground(Color.DARK_GRAY);
        anLbl.setPreferredSize(new Dimension(60, 30));
        anLbl.setEditable(true);
        centerPanel.add(anLbl);
        status.setBackground(Color.DARK_GRAY);
        status.setPreferredSize(new Dimension(60, 30));
        status.setEditable(true);
        centerPanel.add(status);
        aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(500, 30));
        aux.setEditable(false);
        centerPanel.add(aux);
        
        JButton addAbs2Bttn = new JButton("Adauga absenta sem II :");
        addAbs2Bttn.setContentAreaFilled(false);
        addAbs2Bttn.setForeground(Color.WHITE);
        addAbs2Bttn.setActionCommand(ADD_ABSENTA_SEM);
        addAbs2Bttn.addActionListener(new CustomActionListener(e));
        centerPanel.add(addAbs2Bttn);
        zi2Lbl.setBackground(Color.DARK_GRAY);
        zi2Lbl.setForeground(Color.WHITE);
        zi2Lbl.setPreferredSize(new Dimension(60, 30));
        zi2Lbl.setEditable(true);
        centerPanel.add(zi2Lbl);
        luna2Lbl.setBackground(Color.DARK_GRAY);
        luna2Lbl.setForeground(Color.WHITE);
        luna2Lbl.setPreferredSize(new Dimension(60, 30));
        luna2Lbl.setEditable(true);
        centerPanel.add(luna2Lbl);
        an2Lbl.setBackground(Color.DARK_GRAY);
        an2Lbl.setPreferredSize(new Dimension(60, 30));
        an2Lbl.setEditable(true);
        status = new JTextField();
        status.setBackground(Color.DARK_GRAY);
        status.setPreferredSize(new Dimension(60, 30));
        status.setEditable(true);
        centerPanel.add(status);
        centerPanel.add(an2Lbl);
        aux = new JTextField();
        aux.setBackground(Color.DARK_GRAY);
        aux.setBorder(null);
        aux.setPreferredSize(new Dimension(500, 30));
        aux.setEditable(false);
        centerPanel.add(aux);
        
        JButton incheieMedieBttn = new JButton("Incheie medie :");
        incheieMedieBttn.setContentAreaFilled(false);
        incheieMedieBttn.setForeground(Color.WHITE);
        incheieMedieBttn.setEnabled(false);
        centerPanel.add(incheieMedieBttn);
        final JCheckBox incheieMediaChb = new JCheckBox("Incheie media");
        incheieMediaChb.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				if(incheieMediaChb.isSelected()) {
					if(medieIncheiata == false) {
						JOptionPane.showMessageDialog(new JDialog(), 
								"Medie incheiata cu succes pentru elevul " + e.getNumeUtilizator().getNume() + " " +
										e.getNumeUtilizator().getPrenume(), 
								"Incheiere medie", JOptionPane.INFORMATION_MESSAGE);
						medieIncheiata = true;
					}
					else {
						JOptionPane.showMessageDialog(new JDialog(), 
								"Media a fost deja incheiata", "Incheiere medie", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
        centerPanel.add(incheieMediaChb);
        
		centerPanel.revalidate();
		centerPanel.repaint();
	}
	
	private BasicLabel semestruLbl = null;
	private boolean medieIncheiata = false;
	
	private void setupAdaugaNotaSemestru(Elev e, Semestru s) {
		JPanel centerPanel = super.getCenterPanel();
		semestruLbl = new BasicLabel(s == Semestru.SEMESTRUL_1 ? 
				"Situatie semestrul 1" : "Situatie semestrul 2");
		semestruLbl.setFontSize(20);
		semestruLbl.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 40));
		semestruLbl.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.add(semestruLbl);
		String[] columnNames = { "Nume",
								 "Note",
								 "Teza",
								 "Medie" };
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa found = null;
		for(Clasa c : clase) {
		if(c.getID().equals(e.getClasa())) {
			found = c;
			break;
			}
		}
		if(found == null) {
			return;
		}
		TreeMap<Materie, SituatieMaterieBaza> myMap = found.getCatalog().getMyMap().get(e);
		String materie = Centralizator.getInstance().getProfesori().get(getUsername()).getMaterie();
		SituatieMaterieBaza sit = myMap.get(new Materie(materie));
		String[][] data = new String[1][4];
		DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
		data[0][0] = "<html>" + e.getNumeUtilizator().getNume() + "<br>" + e.getNumeUtilizator().getPrenume() + "</html>";
		LinkedList<Nota> noteSem = s == Semestru.SEMESTRUL_1 ? sit.getNoteSem1() : sit.getNoteSem2();
		data[0][1] = "";
		for(Nota n : noteSem) {
			data[0][1] += myDecimalFormat.format(n.getNota());
			data[0][1] += ", ";
		}
		data[0][2] = "";
		if(sit.getClass() == SituatieMaterieCuTeza.class) {
			SituatieMaterieCuTeza s1 = (SituatieMaterieCuTeza) sit;
			data[0][2] += s == Semestru.SEMESTRUL_1 ? s1.getTezaSem1().getNota() : s1.getTezaSem2().getNota();
		}
		data[0][3] = myDecimalFormat.format(s == Semestru.SEMESTRUL_1 ? 
				sit.getMedieSem1().getNota() : sit.getMedieSem2().getNota());
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, 70));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        if(s == Semestru.SEMESTRUL_1) {
	        if(sitElevSem1 != null) {
	        	centerPanel.remove(sitElevSem1);
	        }
	        sitElevSem1 = new JScrollPane(table);
	        sitElevSem1.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 70));
	        sitElevSem1.getViewport().setBackground(Color.DARK_GRAY);
	        setScrollPane(sitElevSem1);
	        centerPanel.add(sitElevSem1);
        }
        else {
        	if(sitElevSem2 != null) {
	        	centerPanel.remove(sitElevSem2);
	        }
	        sitElevSem2 = new JScrollPane(table);
	        sitElevSem2.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, 70));
	        sitElevSem2.getViewport().setBackground(Color.DARK_GRAY);
	        setScrollPane(sitElevSem2);
	        centerPanel.add(sitElevSem2);
        }
        
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 45);
        }
        for(int col = 0; col < table.getColumnCount(); col++) {
        	table.getColumnModel().getColumn(col).setPreferredWidth(200);
        }
        
	}
	
	private JScrollPane scrollPanel = null;
	private JScrollPane sitElevSem1 = null;
	private JScrollPane sitElevSem2 = null;
	private JScrollPane absScrollPane = null;
	
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPanel = scrollPane;
	}
	
	public JScrollPane getScrollPane() {
		return scrollPanel;
	}
	
	private JButton nrTotalEleviBttn = null;
	private BasicLabel nrTotalEleviLbl = null;
	private JButton notaMaximaBttn = null;
	private BasicLabel notaMaximaLbl = null;
	private JButton notaMinimaBttn = null;
	private BasicLabel notaMinimaLbl = null;
	private JButton medieClsBttn = null;
	private BasicLabel medieClsLbl = null;
	private double notaMaxima = 0.0;
	private double notaMinima = 11.0;
	private double medieCls = 0.0;
	private int nrElevi = 0;
	
	private void showClasa(String clasa) {
		JPanel centerPanel = super.getCenterPanel();
		String[] columnNames = { "Nume elev",
								 "Note semestrul I",
								 "Note semestrul II",
								 "Nota teza semestrul I",
								 "Nota teza semestrul II",
								 "Medie materie semestrul I",
								 "Medie materie semestrul II",
								 "Medie materie generala",
								 "Medie generala elev",
								 "Numar absente materie",
								 "Numar total de absente"};
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
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
		notaMaxima = 0.0;
		notaMinima = 11.0;
		medieCls = 0.0;
		TreeMap<Elev, TreeMap<Materie, SituatieMaterieBaza>> myMap = found.getCatalog().getMyMap();
		String materie = Centralizator.getInstance().getProfesori().get(getUsername()).getMaterie();
		String[][] data = new String[myMap.size()][11];
		nrElevi = myMap.size();
		DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
		int contor = 0;
		for(Map.Entry<Elev, TreeMap<Materie, SituatieMaterieBaza>> entry : myMap.entrySet()) {
			data[contor][0] = entry.getKey().getNumeUtilizator().getNume() + " " +
					entry.getKey().getNumeUtilizator().getPrenume();
			SituatieMaterieBaza sit = myMap.get(entry.getKey()).get(new Materie(materie));
			// get note for semestrul 1
			LinkedList<Nota> noteSem = sit.getNoteSem1();
			data[contor][1] = "<html>";
			int blankLine = 0;
			for(Nota n : noteSem) {
				blankLine ++;
				data[contor][1] += n.getNota() + ", ";
				if(blankLine == 6) {
					blankLine = 0;
					data[contor][1] += "<br>";
				}
			}
			data[contor][1] += "</html>";
			// get note for semestrul 2
			noteSem = sit.getNoteSem2();
			data[contor][2] = "<html>";
			blankLine = 0;
			for(Nota n : noteSem) {
				blankLine ++;
				data[contor][2] += n.getNota() + ", ";
				if(blankLine == 6) {
					blankLine = 0;
					data[contor][2] += "<br>";
				}
			}
			data[contor][2] += "</html>";
			// get teza semestrul 1
			data[contor][3] = "<html><p><font color = \"blue\">";
			if(sit.getClass() == SituatieMaterieCuTeza.class) {
				SituatieMaterieCuTeza s = (SituatieMaterieCuTeza) sit;
				data[contor][3] += s.getTezaSem1().getNota();
			}
			data[contor][3] += "</font></p></html>";
			// get teza semestrul 2
			data[contor][4] = "<html><p><font color = \"blue\">";
			if(sit.getClass() == SituatieMaterieCuTeza.class) {
				SituatieMaterieCuTeza s = (SituatieMaterieCuTeza) sit;
				data[contor][4] += s.getTezaSem2().getNota();
			}
			data[contor][4] += "</font></p></html>";
			// get medie sem 1
			data[contor][5] = "<html><p><font color = \"red\">";
			data[contor][5] += myDecimalFormat.format(sit.getMedieSem1().getNota());
			if(sit.getMedieSem1().getNota() < notaMinima) {
				notaMinima = sit.getMedieSem1().getNota();
			}
			else if(sit.getMedieSem1().getNota() > notaMaxima) {
				notaMaxima = sit.getMedieSem1().getNota();
			}
			data[contor][5] += "</font></p></html>";
			// get medie sem 2
			data[contor][6] = "<html><p><font color = \"red\">";
			data[contor][6] += myDecimalFormat.format(sit.getMedieSem2().getNota());
			if(sit.getMedieSem2().getNota() < notaMinima) {
				notaMinima = sit.getMedieSem2().getNota();
			}
			else if(sit.getMedieSem2().getNota() > notaMaxima) {
				notaMaxima = sit.getMedieSem2().getNota();
			}
			data[contor][6] += "</font></p></html>";
			// get medie generala materie
			data[contor][7] = "<html><p><font color = \"red\">";
			data[contor][7] += myDecimalFormat.format(sit.getMedieGenerala());
			medieCls += sit.getMedieGenerala();
			data[contor][7] += "</font></p></html>";
			// get medie generala elev
			data[contor][8] = "" + myDecimalFormat.format(found.getCatalog().getMedieGeneralaElev(entry.getKey()));
			// get numar absente materie
			data[contor][9] = "" + sit.getNrAbsente();
			// get numar total absente
			data[contor][9] = "" + found.getCatalog().getNrAbsenteMaterieElev(entry.getKey(), new Materie(materie));
			// get numar total absente
			data[contor][10] = "" + found.getCatalog().getNrAbsenteTotalElev(entry.getKey());
			contor++;
		}
		medieCls /= myMap.size();
		
		JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() * 4 / 6));
        table.setFillsViewportHeight(true);
        table.setOpaque(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.setAutoCreateRowSorter(true);
        for(int row = 0; row < table.getRowCount(); row++) {
        	table.setRowHeight(row, 45);
        }
        for(int col = 0; col < table.getColumnCount(); col++) {
        	table.getColumnModel().getColumn(col).setPreferredWidth(200);
        }
        if(scrollPanel != null) {
        	centerPanel.remove(scrollPanel);
        }
        scrollPanel = new JScrollPane(table);
        scrollPanel.setPreferredSize(new Dimension(centerPanel.getWidth() - 10, centerPanel.getHeight() * 4 /6));
        scrollPanel.getViewport().setBackground(Color.DARK_GRAY);
        setScrollPane(scrollPanel);
        centerPanel.add(scrollPanel);
        
        if(nrTotalEleviBttn != null) {
        	centerPanel.remove(nrTotalEleviBttn);
        }
        if(nrTotalEleviLbl != null) {
        	centerPanel.remove(nrTotalEleviLbl);
        }
        if(notaMaximaBttn != null) {
        	centerPanel.remove(notaMaximaBttn);
        }
        if(notaMaximaLbl != null) {
        	centerPanel.remove(notaMaximaLbl);
        }
        if(notaMinimaBttn != null) {
        	centerPanel.remove(notaMinimaBttn);
        }
        if(notaMinimaLbl != null) {
        	centerPanel.remove(notaMinimaLbl);
        }
        if(medieClsBttn != null) {
        	centerPanel.remove(medieClsBttn);
        }
        if(medieClsLbl != null) {
        	centerPanel.remove(medieClsLbl);
        }
        
        nrTotalEleviBttn = new JButton("Numarul total de elevi :");
        nrTotalEleviBttn.setContentAreaFilled(false);
        nrTotalEleviBttn.setForeground(Color.WHITE);
        nrTotalEleviBttn.setActionCommand(CALCULEAZA_NR_ELEVI);
        nrTotalEleviBttn.addActionListener(new CustomActionListener());
        centerPanel.add(nrTotalEleviBttn);
        nrTotalEleviLbl = new BasicLabel("Numarul de elevi");
        nrTotalEleviLbl.setForeground(Color.GRAY);
        nrTotalEleviLbl.setPreferredSize(new Dimension(750, 30));
        centerPanel.add(nrTotalEleviLbl);
        
        medieClsBttn = new JButton("Media clasei :");
        medieClsBttn.setContentAreaFilled(false);
        medieClsBttn.setForeground(Color.WHITE);
        medieClsBttn.setActionCommand(CALCULEAZA_MEDIE_GENERALA);
        medieClsBttn.addActionListener(new CustomActionListener());
        centerPanel.add(medieClsBttn);
        medieClsLbl = new BasicLabel("Media aici");
        medieClsLbl.setForeground(Color.GRAY);
        medieClsLbl.setPreferredSize(new Dimension(750, 30));
        centerPanel.add(medieClsLbl);
        
        notaMaximaBttn = new JButton("Media maxima :");
        notaMaximaBttn.setContentAreaFilled(false);
        notaMaximaBttn.setForeground(Color.WHITE);
        notaMaximaBttn.setActionCommand(CALCULEAZA_MEDIE_MAXIMA);
        notaMaximaBttn.addActionListener(new CustomActionListener());
        centerPanel.add(notaMaximaBttn);
        notaMaximaLbl = new BasicLabel("Media aici");
        notaMaximaLbl.setForeground(Color.GRAY);
        notaMaximaLbl.setPreferredSize(new Dimension(750, 30));
        centerPanel.add(notaMaximaLbl);
        
        notaMinimaBttn = new JButton("Media minima :");
        notaMinimaBttn.setContentAreaFilled(false);
        notaMinimaBttn.setForeground(Color.WHITE);
        notaMinimaBttn.setActionCommand(CALCULEAZA_MEDIE_MINIMA);
        notaMinimaBttn.addActionListener(new CustomActionListener());
        centerPanel.add(notaMinimaBttn);
        notaMinimaLbl = new BasicLabel("Media aici");
        notaMinimaLbl.setForeground(Color.GRAY);
        notaMinimaLbl.setPreferredSize(new Dimension(750, 30));
        centerPanel.add(notaMinimaLbl);
        
        centerPanel.revalidate();
		centerPanel.repaint();
	}
	

	@Override
	public void setupHomePanel() {
		JPanel centerPanel = super.getCenterPanel();
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.removeAll();
		centerPanel.repaint();
		
		Profesor p = Centralizator.getInstance().getProfesori().get(super.getUsername());
		String content = "<html>" + 
				"<p align = \"center\">" + 
				"<font size=\"12\">" + 
				p.getNumeUtilizator().getNume() + " " + p.getNumeUtilizator().getPrenume() + 
				"</font>" + "</p>" +
				"<p align = \"center\">" + p.getLoginID() + "</p>" + "<br>" +
				"<p align = \"center\">" + p.getMaterie() + "</html>";
		BasicLabel label = new BasicLabel(content);
		centerPanel.add(label);
	}
	
	private class CustomActionListener implements ActionListener {
		
		private String clasa_selectata = "";
		private Elev elev = new Elev();
		
		public CustomActionListener(String clasa) {
			this.clasa_selectata = clasa;
		}
		
		public CustomActionListener() {
			// TODO Auto-generated constructor stub
		}

		public CustomActionListener(Elev e) {
			this.elev = e;
		}
		
		Profesor p = Centralizator.getInstance().getProfesori().get(getUsername());

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String cmd = e.getActionCommand();
			DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
			switch(cmd) {
			case SHOW_CLASA :
				showClasa(clasa_selectata);
				break;
			case CALCULEAZA_MEDIE_GENERALA :
				medieClsLbl.setText(myDecimalFormat.format(medieCls));
				break;
			case CALCULEAZA_MEDIE_MAXIMA :
				notaMaximaLbl.setText(myDecimalFormat.format(notaMaxima));
				break;
			case CALCULEAZA_MEDIE_MINIMA :
				notaMinimaLbl.setText(myDecimalFormat.format(notaMinima));
				break;
			case CALCULEAZA_NR_ELEVI :
				nrTotalEleviLbl.setText("" + nrElevi);
				break;
			case EDITEAZA_SITUATIE_ELEV :
				setupAdaugaNota(elev);
				break;
			case ADD_NOTA_SEM_1:
				if(medieIncheiata == false) {
					p.addNota(Semestru.SEMESTRUL_1, elev, new Nota(Double.parseDouble(addNota1Lbl.getText())));
					JOptionPane.showMessageDialog(new JDialog(), 
							"Nota adaugata !", "Nota adaugata", JOptionPane.INFORMATION_MESSAGE);
					LogoutHelper.getInstance().updateCatalog();
				}
				else {
					JOptionPane.showMessageDialog(new JDialog(), 
							"Media a fost deja incheiata, nu se mai pot adauga note", "Adaugare nota/teza", 
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			case ADD_NOTA_SEM_2 :
				if(medieIncheiata == false) {
					p.addNota(Semestru.SEMESTRUL_2, elev, new Nota(Double.parseDouble(addNota2Lbl.getText())));
					JOptionPane.showMessageDialog(new JDialog(), 
							"Nota adaugata !", "Nota adaugata", JOptionPane.INFORMATION_MESSAGE);
					LogoutHelper.getInstance().updateCatalog();
				}
				else {
					JOptionPane.showMessageDialog(new JDialog(), 
							"Media a fost deja incheiata, nu se mai pot adauga note", "Adaugare nota/teza", 
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			case ADD_TEZA_SEM_1 :
				if(medieIncheiata == false)	{
					p.addTeza(Semestru.SEMESTRUL_1, elev, new Nota(Double.parseDouble(addTeza1Lbl.getText())));
					JOptionPane.showMessageDialog(new JDialog(), 
							"Nota adaugata !", "Nota adaugata", JOptionPane.INFORMATION_MESSAGE);
					LogoutHelper.getInstance().updateCatalog();
				}
				else {
					JOptionPane.showMessageDialog(new JDialog(), 
							"Media a fost deja incheiata, nu se mai pot adauga note", "Adaugare nota/teza", 
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			case ADD_TEZA_SEM_2 :
				if(medieIncheiata == false) {
					p.addTeza(Semestru.SEMESTRUL_2, elev, new Nota(Double.parseDouble(addTeza2Lbl.getText())));
					JOptionPane.showMessageDialog(new JDialog(), 
							"Nota adaugata !", "Nota adaugata", JOptionPane.INFORMATION_MESSAGE);
					LogoutHelper.getInstance().updateCatalog();
				}
				else {
					JOptionPane.showMessageDialog(new JDialog(), 
							"Media a fost deja incheiata, nu se mai pot adauga note", "Adaugare nota/teza", 
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			case ADD_ABSENTA_SEM :
				if(medieIncheiata == false) {
				JOptionPane.showMessageDialog(new JDialog(), 
						"Am adaugat absenta", "Adaugare absenta", 
						JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(new JDialog(), 
							"Media a fost deja incheiata, nu se mai pot adauga absente", "Adaugare absenta", 
							JOptionPane.ERROR_MESSAGE);
				}
				/*private final String ADD_TEZA__SEM_1 = "adauga teza sem 1";
				private final String ADD_TEZA_SEM_2 = "adauga teza sem 2";
				private final String ADD_ABSENTA_SEM = "adauga absenta"; */
			}
		}
		
	}

}
