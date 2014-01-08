package liceu;

import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;

/**
 * This class also has the option of adding the grades for semester tests.
 * @author user
 *
 */
public class SituatieMaterieCuTeza extends SituatieMaterieBaza {
	
	private Nota tezaSem1;
	private Nota tezaSem2;
	
	/**
	 * The constructor
	 * @param m - the subject for which the grades and averages are computed.
	 */
	public SituatieMaterieCuTeza(Materie m) {
		super(m);
		tezaSem1 = new Nota(0.0);
		tezaSem2 = new Nota(0.0);
	}

	public Nota getTezaSem1() {
		return tezaSem1;
	}

	/**
	 * Also computes the average.
	 * @param tezaSem1
	 */
	public void setTezaSem1(Nota tezaSem1) {
		this.tezaSem1 = tezaSem1;
		computeAverage(Semestru.SEMESTRUL_1);
	}

	public Nota getTezaSem2() {
		return tezaSem2;
	}

	/**
	 * Also computes the average.
	 * @param tezaSem2
	 */
	public void setTezaSem2(Nota tezaSem2) {
		this.tezaSem2 = tezaSem2;
		computeAverage(Semestru.SEMESTRUL_2);
	}

	@Override
	public String toString() {
		return "SituatieMaterieCuTeza [tezaSem1=" + tezaSem1 + ", tezaSem2="
				+ tezaSem2 + ", toString()=" + super.toString() + "]\n";
	}
	
	/**
	 * A method for computing the average of given semester.
	 * Average = SemesterAverage * .75 + SemesterTest * .25
	 * @param s - the semester for which the average is computed
	 */
	private void computeAverage(Semestru s) {
		double sum = 0.0;
		switch(s) {
		case SEMESTRUL_1:
			for(Nota i : super.getNoteSem1()) {
				sum += i.getNota();
			}
			double medieNote = sum / super.getNoteSem1().size();
			super.setMedieSem1(new Nota((medieNote * 3 + tezaSem1.getNota()) / 4));
			break;
		case SEMESTRUL_2:
			for(Nota i : super.getNoteSem2()) {
				sum += i.getNota();
			}
			medieNote = sum / super.getNoteSem2().size();
			super.setMedieSem2(new Nota(medieNote * 0.75 + tezaSem2.getNota() * 0.25));
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
			super.getNoteSem1().add(n);
			computeAverage(s);
			break;
		case SEMESTRUL_2:
			super.getNoteSem2().add(n);
			computeAverage(s);
			break;
		}
	}

	
}
