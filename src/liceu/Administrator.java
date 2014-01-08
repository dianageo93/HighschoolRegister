package liceu;

public class Administrator extends Utilizator implements IProfesor, ISecretar {

	public Administrator() {
		super(TipUtilizator.ADMINISTRATOR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void listareEleviClasa(Clasa c) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ordonareClasa(Clasa c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "Administrator [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}
	

}
