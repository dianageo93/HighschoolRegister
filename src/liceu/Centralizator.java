package liceu;

import java.util.TreeMap;
import java.util.TreeSet;

public class Centralizator {
	
	private static Centralizator instance = null;
	
	private TreeSet<Elev> elevi;
	private TreeSet<Profesor> profesori;
	// TODO : restul de liste
	private TreeSet<Clasa> clase;
	private TreeMap<Materie, TreeMap<Clasa, Profesor>> materii;
	
	// TODO : CONSTRUCTOR
	// TODO : toString
	
	public static Centralizator getInstance() {
	      if(instance == null) {
	         instance = new Centralizator();
	      }
	      return instance;
	}

}
