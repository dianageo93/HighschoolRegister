package liceu;

import liceu.utils.NumePrenume;

public abstract class Utilizator {
	
	private String loginID;
	private String parolaUtilizator;
	private NumePrenume numeUtilizator;
	private String UUID;
	private TipUtilizator tipUtilizator = null;
	
	public Utilizator(TipUtilizator tip) {
		this.tipUtilizator = tip;
	}
	
	public TipUtilizator getTipUtilizator() {
		return tipUtilizator;
	}
	
	public void setTipUtilizator(TipUtilizator tipUtilizator) {
		this.tipUtilizator = tipUtilizator;
	}
	
	public String toString() {
		return numeUtilizator.toString();
	}
	
	public void setUUID(String tokens) {
		UUID = tokens;
	}
	
	public String getUUID() {
		return UUID;
	}
	
	public void setNume(NumePrenume nume) {
		this.numeUtilizator = nume;
	}
	
	public NumePrenume getNumeUtilizator() {
		return numeUtilizator;
	}
	
	public void setParolaUtilizator(String parolaUtilizator) {
		this.parolaUtilizator = parolaUtilizator;
	}
	
	public String getParolaUtilizator() {
		return parolaUtilizator;
	}
	
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	
	public String getLoginID() {
		return loginID;
	}

}
