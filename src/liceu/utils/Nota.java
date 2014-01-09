package liceu.utils;

import java.text.DecimalFormat;

public class Nota {
	
	private double nota;
	private DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
	
	public Nota(double val) {
		this.nota = val;
	}
	
	public Nota() {
		this.nota = 0.0;
	}
	
	public Nota(String notaString) {
		nota = Double.parseDouble(notaString);
	}

	public double getNota() {
		return nota;
	}
	
	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "Nota [nota=" + myDecimalFormat.format(nota) + "]";
	}
	

}
