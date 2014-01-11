package liceu;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;

public class Profesor extends Utilizator implements IProfesor {
	
	public Profesor() {
		super(TipUtilizator.PROFESOR);
		
		// TODO : CONSTRUCTOR
		// TODO Auto-generated constructor stub
	}

	private String materie;
	private TreeSet<String> clase = new TreeSet<>();

	public TreeSet<String> getClase() {
		return clase;
	}

	public void setClase(TreeSet<String> clase) {
		this.clase = clase;
	}
	
	public void adaugaClasa(String c) {
		clase.add(c);
	}

	@Override
	public TreeSet<Elev> listEleviFromClasa(String c) {
		TreeSet<Elev> listOfElevi = new TreeSet<>();
		TreeMap<String, Elev> elevi = Centralizator.getInstance().getElevi();
		for(Map.Entry<String, Elev> entry : elevi.entrySet()) {
			if(entry.getValue().getClasa().equals(c)) {
				listOfElevi.add(entry.getValue());
			}
		}
		return listOfElevi;
	}

	@Override
	public String toString() {
		return "Profesor [materie=" + materie + ", getUUID()=" + getUUID()
				+ ", getNumeUtilizator()=" + getNumeUtilizator()
				+ ", getLoginID()=" + getLoginID() + "getClase()=" + getClase() + "]\n";
	}

	public void setMaterie(String tokens) {
		this.materie = tokens;
	}
	
	public String getMaterie() {
		return materie;
	}

	@Override
	public boolean addNota(String c, Semestru s, Elev e, Nota n) {
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		String clasa = e.getClasa();
		Clasa found = new Clasa();
		for(Clasa k : clase) {
			if(k.getID().equals(clasa)) {
				found = k;
				break;
			}
		}
		Catalog ctg = found.getCatalog();
		ctg.getMyMap().get(e).get(new Materie(materie)).addNota(n, s);
		return true;
	}
	
	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu) {
		TreeSet<Elev> oldSet = listEleviFromClasa(c);
		TreeSet<Elev> newSet = null;
		if(criteriu == OrderOptions.NUME) {
			newSet = new TreeSet<>(COMP_NUME);
		}
		else if(criteriu == OrderOptions.MEDIE_GENERALA) {
			newSet = new TreeSet<>(COMP_MEDIE_GENERALA);
		}
		else if(criteriu == OrderOptions.NR_ABSENTE_TOTAL) {
			newSet = new TreeSet<>(COMP_NR_ABSENTE_TOTAL);
		}
		else if(criteriu == OrderOptions.NR_ABSENTE_MATERIE) {
			newSet = new TreeSet<>(COMP_NR_ABSENTE_MATERIE);
		}
		else {
			return null;
		}
		newSet.addAll(oldSet);
		return newSet;
	}

	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu, Semestru s) {
		TreeSet<Elev> oldSet = listEleviFromClasa(c);
		TreeSet<Elev> newSet = null;
		if(criteriu == OrderOptions.MEDIE_GENERALA_SEM) {
			if(s == Semestru.SEMESTRUL_1) {
				newSet = new TreeSet<>(COMP_MEDIE_GENERALA_SEM1);
			}
			else if(s == Semestru.SEMESTRUL_2) {
				newSet = new TreeSet<>(COMP_MEDIE_GENERALA_SEM2);
			}
			else {
				return null;
			}
		}
		else {
			if(s == Semestru.SEMESTRUL_1) {
				newSet = new TreeSet<>(COMP_MEDIE_SEM1_MATERIE);
			}
			else if(s == Semestru.SEMESTRUL_2) {
				newSet = new TreeSet<>(COMP_MEDIE_SEM2_MATERIE);
			}
			else {
				return null;
			}
		}
		newSet.addAll(oldSet);
		return newSet;
	}

	public Comparator<Elev> COMP_NUME = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			String nume1 = o1.getNumeUtilizator().getNume();
			String prenume1 = o1.getNumeUtilizator().getPrenume();
			String nume2 = o2.getNumeUtilizator().getNume();
			String prenume2 = o2.getNumeUtilizator().getPrenume();
			return nume1.compareTo(nume2) == 0 ?
					prenume1.compareTo(prenume2) : nume1.compareTo(nume2);
		}
	};
	
	public Comparator<Elev> COMP_MEDIE_GENERALA = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			double medie1 = ctg.getMedieGeneralaElev(o1);
			double medie2 = ctg.getMedieGeneralaElev(o2);
			return Double.compare(medie1, medie2);
		}
	};
	
	public Comparator<Elev> COMP_NR_ABSENTE_TOTAL = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			int nr1 = ctg.getNrAbsenteTotalElev(o1);
			int nr2 = ctg.getNrAbsenteTotalElev(o2);
			
			String nume1 = o1.getNumeUtilizator().getNume();
			String prenume1 = o1.getNumeUtilizator().getPrenume();
			String nume2 = o2.getNumeUtilizator().getNume();
			String prenume2 = o2.getNumeUtilizator().getPrenume();
			
			return Integer.compare(nr1, nr2) != 0 ? Integer.compare(nr1, nr2) :
				(nume1.compareTo(nume2) == 0 ?
					prenume1.compareTo(prenume2) : nume1.compareTo(nume2));
		}
	};
	
	public Comparator<Elev> COMP_NR_ABSENTE_MATERIE = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			int nr1 = ctg.getNrAbsenteMaterieElev(o1, new Materie(materie));
			int nr2 = ctg.getNrAbsenteMaterieElev(o2, new Materie(materie));
			return Integer.compare(nr1, nr2);
		}
	};
	
	public Comparator<Elev> COMP_MEDIE_SEM1_MATERIE = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			double medie1 = ctg.getMedieMaterieElev(o1, new Materie(materie), Semestru.SEMESTRUL_1);
			double medie2 = ctg.getMedieMaterieElev(o2, new Materie(materie), Semestru.SEMESTRUL_1);
			return Double.compare(medie1, medie2);
		}
	};
	
	public Comparator<Elev> COMP_MEDIE_SEM2_MATERIE = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			double medie1 = ctg.getMedieMaterieElev(o1, new Materie(materie), Semestru.SEMESTRUL_2);
			double medie2 = ctg.getMedieMaterieElev(o2, new Materie(materie), Semestru.SEMESTRUL_2);
			return Double.compare(medie1, medie2);
		}
	};
	
	public Comparator<Elev> COMP_MEDIE_GENERALA_SEM1 = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			double medie1 = ctg.getMedieElevSemestru(o1, Semestru.SEMESTRUL_1);
			double medie2 = ctg.getMedieElevSemestru(o2, Semestru.SEMESTRUL_1);
			return Double.compare(medie1, medie2);
		}
	};
	
	public Comparator<Elev> COMP_MEDIE_GENERALA_SEM2 = new Comparator<Elev>() {

		@Override
		public int compare(Elev o1, Elev o2) {
			TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
			String clasa = o1.getClasa();
			Clasa found = new Clasa();
			for(Clasa k : clase) {
				if(k.getID().equals(clasa)) {
					found = k;
					break;
				}
			}
			Catalog ctg = found.getCatalog();
			double medie1 = ctg.getMedieElevSemestru(o1, Semestru.SEMESTRUL_2);
			double medie2 = ctg.getMedieElevSemestru(o2, Semestru.SEMESTRUL_2);
			return Double.compare(medie1, medie2);
		}
	};


}
