package liceu;

public class Profesor extends Utilizator implements IProfesor {
	
	public Profesor() {
		super(TipUtilizator.PROFESOR);
		
		// TODO : CONSTRUCTOR
		// TODO Auto-generated constructor stub
	}

	private String[] materii;
	private int nrMaterii;


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
		// TODO Auto-generated method stub
		return super.toString();
	}

}
