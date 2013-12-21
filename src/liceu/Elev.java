package liceu;

import java.util.Calendar;

public class Elev extends Utilizator implements IElev {
	
	private String CNP;
	private Calendar birthday;
	
	// TODO : CONSTRUCTORUL 
	public Elev(Calendar d, int year, int month, int date) {
		birthday = Calendar.getInstance();
		birthday.set(year, month, date);
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

}
