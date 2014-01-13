package liceu;

import java.util.TreeSet;

public class Secretar extends Utilizator implements ISecretar {

	public Secretar() {
		super(TipUtilizator.SECRETAR);
	}

	@Override
	public String toString() {
		return "Secretar [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}
	
	@Override
	public void addClasa(String c) {
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa newClasa = new Clasa(c);
		
		
	}
	
}
