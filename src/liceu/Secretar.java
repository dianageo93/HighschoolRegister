package liceu;

public class Secretar extends Utilizator implements ISecretar {

	public Secretar() {
		super(TipUtilizator.SECRETAR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Secretar [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}
	
}
