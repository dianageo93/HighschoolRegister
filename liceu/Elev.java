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
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void afiseazaSituatieScolara() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Elev o) {
		if(getNumeUtilizator().getNume().compareTo(o.getNumeUtilizator().getNume()) == 0) {
			return getNumeUtilizator().getPrenume().compareTo(o.getNumeUtilizator().getPrenume());
		}
		else {
			return getNumeUtilizator().getNume().compareTo(o.getNumeUtilizator().getNume());
		}
	}

}
