package liceu.utils;

public class NumePrenume {
	
	private String nume;
	private String prenume;
	
	public NumePrenume(String nume, String prenume) {
		this.nume = nume;
		this.prenume = prenume;
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
