package liceu;

import java.util.Calendar;

public class Absenta {
	
	// TODO : toString
	// TODO : de facut CLASA INTERNA !!!

	private int STATUS;
	private Calendar data;
	
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	
	public int getSTATUS() {
		return STATUS;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public Calendar getData() {
		return data;
	}
	
}
