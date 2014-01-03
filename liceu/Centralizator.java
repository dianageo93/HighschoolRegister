package liceu;

import java.util.TreeMap;
import java.util.TreeSet;

public class Centralizator {
	
	private TreeSet<Utilizator> elevi;
	private TreeSet<Profesor> profesori;
	// TODO : restul de liste
	private TreeSet<Clasa> clase;
	private TreeMap<Materie, TreeMap<Clasa, Profesor>> materii;
	
	// TODO : toString
	
	public Centralizator() {
		
	}
	
	public void setElevi(TreeSet<Utilizator> treeSet) {
		this.elevi = treeSet;
	}

	public TreeSet<Utilizator> getElevi() {
		return elevi;
	}
	
	// implementing with singleton pattern
	private static Centralizator instance = null;
	public static Centralizator getInstance() {
	      if(instance == null) {
	         instance = new Centralizator();
	      }
	      return instance;
	}

}