package liceu;

import java.util.TreeSet;

public class Clasa implements Comparable<Clasa> {
	
	private String ID;
	private TreeSet<Elev> elevi;
	private TreeSet<Materie> materii;
	private Catalog catalog;
	
	public Clasa(String ID) {
		this.ID = ID;
		elevi = new TreeSet<>();
		materii = new TreeSet<>();
		catalog = new Catalog();
	}
	
	public Clasa() {
		ID = "null";
	}

	public void setID(String iD) {
		ID = iD;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setElevi(TreeSet<Elev> elevi) {
		this.elevi = elevi;
	}
	
	public TreeSet<Elev> getElevi() {
		return elevi;
	}
	
	public void setMaterii(TreeSet<Materie> materii) {
		this.materii = materii;
	}
	
	public TreeSet<Materie> getMaterii() {
		return materii;
	}
	
	public boolean adaugaElev(Elev e) {
		if(e == null) {
			return false;
		}
		elevi.add(e);
		return true;
	}
	
	public boolean stergeElev(Elev e) {
		if(e == null) {
			return false;
		}
		if(materii.contains(e) == false) {
			return false;
		}
		materii.remove(e);
		return true;
	}
	
	public boolean adaugaMaterie(Materie m) {
		if(m == null) {
			return false;
		}
		materii.add(m);
		return true;
	}
	
	public boolean stergeMaterie(Materie m) {
		if(m == null) {
			return false;
		}
		if(materii.contains(m) == false) {
			return false;
		}
		materii.remove(m);
		return true;
	}
	
	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	public void deleteElev(Elev e) {
		elevi.remove(e);
	}

	@Override
	public String toString() {
		return "\nClasa [ID=" + ID + ", elevi=" + elevi + ", materii=" + materii
				+ ", catalog=" + catalog + "]";
	}

	@Override
	public int compareTo(Clasa c) {
		return ID.compareTo(c.getID());
	}
	
	@Override
	public boolean equals(Object obj) {
		Clasa c = (Clasa)obj;
		return ID.compareTo(c.getID()) == 0;
	}
	
}
