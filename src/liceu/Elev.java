package liceu;

import java.util.Calendar;

public class Elev extends Utilizator implements IElev, Comparable<Elev> {
	
	private String CNP;
	private Calendar birthday;
	
	public Elev() {
		super(TipUtilizator.ELEV);
	}
	
	public void setCNP(String CNP) {
		this.CNP = CNP;
	}
	
	public String getCNP() {
		return CNP;
	}
	
	@Override
	public void afiseazaSituatieScolara() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "Elev [UUID=" + getUUID() + ", NumeUtilizator="
				+ getNumeUtilizator() + ", LoginID=" + getLoginID() + ", CNP=" + getCNP() + "]\n";
	}

	public int compareTo_nume(Elev o) {
		if(getNumeUtilizator().getNume().compareTo(o.getNumeUtilizator().getNume()) == 0) {
			return getNumeUtilizator().getPrenume().compareTo(o.getNumeUtilizator().getPrenume());
		}
		else {
			return getNumeUtilizator().getNume().compareTo(o.getNumeUtilizator().getNume());
		}
	}
	
	@Override
	public int compareTo(Elev o) {
		return super.getLoginID().compareTo(o.getLoginID());
	}
	
	@Override
	public boolean equals(Object obj) {
		Elev e = (Elev)obj;
		if(getLoginID().compareTo(e.getLoginID()) == 0) {
			return true;
		}
		return false;
	}
	
	public String getClasa() {
		String clasa = null;
		clasa = getUUID().substring(14);
		return clasa;
	}
	
	public void setClasa(String newClasa) {
		String oldClasa = super.getUUID().substring(14);
		super.getUUID().replace(oldClasa, newClasa);
	}

}
