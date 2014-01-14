package liceu.utils;

import java.text.DecimalFormat;

/**
 * This class retains a grade.
 *
 */
public class Nota {
	
	private double nota;
	private DecimalFormat myDecimalFormat = new DecimalFormat("#.00");
	
	/**
	 * Constructor
	 * @param val - the value of the grade
	 */
	public Nota(double val) {
		this.nota = val;
	}
	
	/**
	 * The constructor.
	 */
	public Nota() {
		this.nota = 0.0;
	}
	
	/**
	 * The constructor.
	 * @param notaString - a string will be parsed as a grade.
	 */
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
