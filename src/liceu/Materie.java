package liceu;

public class Materie implements Comparable<Materie> {
	
	private String nume;
	private int nrOrePeSapt;
	private boolean teza;
	
	public Materie(String nume, int nrOrePeSapt, boolean teza) {
		this.nume = nume;
		this.nrOrePeSapt = nrOrePeSapt;
		this.teza = teza;
	}
	
	public Materie() {
		super();
	}
	
	public Materie(String nume) {
		this.nume = nume;
		nrOrePeSapt = 0;
		teza = false;
	}

	public String getNume() {
		return nume;
	}
	
	public void setNume(String nume) {
		this.nume = nume;
	}
	
	public int getNrOrePeSapt() {
		return nrOrePeSapt;
	}
	
	public void setNrOrePeSapt(int nrOrePeSapt) {
		this.nrOrePeSapt = nrOrePeSapt;
	}
	
	public boolean isTeza() {
		return teza;
	}
	
	public void setTeza(boolean teza) {
		this.teza = teza;
	}

	@Override
	public String toString() {
		return "Materie [nume=" + nume + ", nrOrePeSapt=" + nrOrePeSapt
				+ ", teza=" + teza + "]\n";
	}

	@Override
	public int compareTo(Materie o) {
		return nume.compareTo(o.getNume());
	}
	
	@Override
	public boolean equals(Object obj) {
		Materie m = (Materie)obj;
		return nume.compareTo(m.getNume()) == 0;
	}
	
}
