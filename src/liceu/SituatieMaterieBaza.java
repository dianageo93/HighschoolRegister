package liceu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import liceu.SituatieMaterieBaza.Absenta.Status;
import liceu.utils.Nota;

/**
 * An istance of this class will retain all the grades and absences of a student during a year od study. 
 *
 */
public class SituatieMaterieBaza {
	
	private Materie m;
	private Nota medieSem1;
	private Nota medieSem2;
	private LinkedList<Nota> noteSem1;
	private LinkedList<Nota> noteSem2;
	private LinkedList<Absenta> absente;
	private boolean medieIncheiata = false;
	
	/**
	 * An istance of this class represents an absence. Such an object would retain the date and status of
	 * the absence. The status is represented through an enumeration. 
	 */
	public static class Absenta {
		
		/**
		 *	This enumeration retains the status of an absence, which can be MOTIVATA, NEMOTIVATA or NEDETERMINATA
		 */
		public enum Status {
			MOTIVATA, NEMOTIVATA, NEDETERMINAT
		}
		private Calendar date;
		private Status status;

		public Absenta() {
			status = Status.NEDETERMINAT;
			date = new GregorianCalendar();
		}
		
		/**
		 * @return Status - The current status of the object.
		 */
		public Status getStatus() {
			return status;
		}

		/**
		 * The method sets the status of the object.
		 * @param status - of type Status
		 */
		public void setStatus(Status status) {
			this.status = status;
		}

		/**
		 * @return Calendar - Date of the absence.
		 */
		public Calendar getDate() {
			return date;
		}
		
		/**
		 * The method sets the date of the object.
		 * @param data - of type Calendar
		 */
		public void setDate(Calendar data) {
			this.date = data;
		}

		@Override
		public String toString() {
			DateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
			Date d = date.getTime();
			return "Absenta [data=" + df.format(d) + ", status=" + status + "]";
		}
		
		@Override
		public boolean equals(Object obj) {
			Absenta a = (Absenta) obj;
			return getDate().equals(a.getDate());
		}
		
	}
	
	/**
	 * This enumeration is used when computing the average.
	 */
	public enum Semestru {
		SEMESTRUL_1, SEMESTRUL_2
	}
	
	/**
	 * The constructor
	 * @param m - the subject for which the grades and averages are computed.
	 */
	public SituatieMaterieBaza(Materie m) {
		this.m = m;
		medieSem1 = new Nota(0.0);
		medieSem2 = new Nota(0.0);
		noteSem1 = new LinkedList<>();
		noteSem2 = new LinkedList<>();
		absente = new LinkedList<>();
	}
	
	/**
	 * A method for computing the average of given semester.
	 * @param s - the semester for which the average is computed
	 */
	private void computeAverage(Semestru s) {
		double sum = 0.0;
		switch(s) {
		case SEMESTRUL_1:
			for(Nota i : noteSem1) {
				sum += i.getNota();
			}
			medieSem1 = new Nota(sum / noteSem1.size());
			break;
		case SEMESTRUL_2:
			for(Nota i : noteSem2) {
				sum += i.getNota();
			}
			medieSem2 = new Nota(sum / noteSem2.size());
			break;
		}
	}
	
	/**
	 * This method adds a grade to the grade list of given semester. It also computes the average of the semester.
	 * @param n - the grade to be added
	 * @param s - the semester for which the grade is added
	 */
	public void addNota(Nota n, Semestru s) {
		switch(s) {
		case SEMESTRUL_1:
			noteSem1.add(n);
			computeAverage(s);
			break;
		case SEMESTRUL_2:
			noteSem2.add(n);
			computeAverage(s);
			break;
		}
	}
	
	/**
	 * This method adds an absence to the list.
	 * @param date - Date of the absence
	 * @param s - status
	 */
	public void addAbsenta(Calendar date, Absenta.Status s) {
		Absenta a = new Absenta();
		a.setDate(date);
		a.setStatus(s);
		absente.add(a);
	}
	
	
	/**
	 * A method for modifying the status of an absence. One can modify the status of an absence only when 
	 * the previous status is NEDETERMINAT.
	 * @param date
	 * @param s
	 */
	public void modifyAbsenta(Calendar date, Absenta.Status s) {
		int index = absente.indexOf(date);
		if(absente.get(index).getStatus().equals(Status.NEDETERMINAT)) {
			absente.get(index).setStatus(s);
		}
	}

	public Materie getM() {
		return m;
	}
	
	public void setM(Materie m) {
		this.m = m;
	}
	
	public Nota getMedieSem1() {
		return medieSem1;
	}
	
	public Nota getMedieSem2() {
		return medieSem2;
	}
	
	public LinkedList<Nota> getNoteSem1() {
		return noteSem1;
	}
	
	public void setNoteSem1(LinkedList<Nota> noteSem1) {
		this.noteSem1 = noteSem1;
		computeAverage(Semestru.SEMESTRUL_1);
	}
	
	public LinkedList<Nota> getNoteSem2() {
		return noteSem2;
	}
	
	public void setNoteSem2(LinkedList<Nota> noteSem2) {
		this.noteSem2 = noteSem2;
		computeAverage(Semestru.SEMESTRUL_2);
	}
	
	public LinkedList<Absenta> getAbsente() {
		return absente;
	}
	
	public void setAbsente(LinkedList<Absenta> absente) {
		this.absente = absente;
	}
	
	public void setMedieSem1(Nota medieSem1) {
		this.medieSem1 = medieSem1;
	}

	public void setMedieSem2(Nota medieSem2) {
		this.medieSem2 = medieSem2;
	}
	
	public double getMedieGenerala() {
		return medieSem1.getNota() / 2 + medieSem2.getNota() / 2;
	}
	
	public int getNrAbsente() {
		return absente.size();
	}
	
	public boolean isMedieIncheiata() {
		return medieIncheiata;
	}
	
	public void setMedieIncheiata(boolean medieIncheiata) {
		this.medieIncheiata = medieIncheiata;
	}
	
	@Override
	public String toString() {
		return "SituatieMaterieBaza [m=" + m + "\n, medieSem1=" + medieSem1
				+ "\n, medieSem2=" + medieSem2 + "\n, noteSem1=" + noteSem1
				+ "\n, noteSem2=" + noteSem2 + "\n, absente=" + absente + "]";
	}

	
	

}
