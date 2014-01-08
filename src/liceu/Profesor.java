package liceu;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
	public void listareEleviClasa(Clasa c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ordonareClasa(Clasa c) {
		// TODO Auto-generated method stub
		return false;
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
	

}
