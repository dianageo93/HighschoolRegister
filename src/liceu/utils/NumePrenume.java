package liceu.utils;

/**
 * This class retains the name and forname a user. 
 *
 */
public class NumePrenume {
	
	private String nume;
	private String prenume;
	
	/**
	 * The constructor
	 * @param nume - name
	 * @param prenume - forname
	 */
	public NumePrenume(String nume, String prenume) {
		this.nume = nume;
		this.prenume = prenume;
	}
	
	/**
	 * The constructor.
	 * @param str - name and forname
	 */
	public NumePrenume(String str) {
		String[] tokens = str.split(" ");
		nume = tokens[0];
		prenume = tokens[1];
	}
	
	public String getNume() {
		return this.nume;
	}
	
	public String getPrenume() {
		return this.prenume;
	}
	
	@Override
	public String toString() {
		return this.nume + " " + this.prenume;
	}

}
