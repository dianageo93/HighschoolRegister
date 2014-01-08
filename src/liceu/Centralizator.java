package liceu;

import java.util.TreeMap;
import java.util.TreeSet;

public class Centralizator {
	
	private TreeMap<String, Elev> elevi;
	private TreeMap<String, Profesor> profesori;
	private TreeMap<String, Secretar> secretari;
	private TreeMap<String, Administrator> administratori;
	private TreeSet<Clasa> clase;
	private TreeMap<Materie, TreeMap<Clasa, Profesor>> repartizareProf;
	
	// TODO : toString
	
	public Centralizator() {
		
	}
	
	public void setElevi(TreeMap<String, Elev> treeMap) {
		this.elevi = treeMap;
	}

	public TreeMap<String, Elev> getElevi() {
		return elevi;
	}
	
	public void setProfesori(TreeMap<String, Profesor> profesori) {
		this.profesori = profesori;
	}
	
	public TreeMap<String, Profesor> getProfesori() {
		return profesori;
	}
	
	public void setSecretari(TreeMap<String, Secretar> secretari) {
		this.secretari = secretari;
	}
	
	public TreeMap<String, Secretar> getSecretari() {
		return secretari;
	}
	
	public void setAdministratori(TreeMap<String, Administrator> administratori) {
		this.administratori = administratori;
	}
	
	public TreeMap<String, Administrator> getAdministratori() {
		return administratori;
	}
	
	public void setClase(TreeSet<Clasa> clase) {
		this.clase = clase;
	}
	
	public TreeSet<Clasa> getClase() {
		return clase;
	}
	
	public void setRepartizareProf(
			TreeMap<Materie, TreeMap<Clasa, Profesor>> repartizareProf) {
		this.repartizareProf = repartizareProf;
	}
	
	public TreeMap<Materie, TreeMap<Clasa, Profesor>> getRepartizareProf() {
		return repartizareProf;
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