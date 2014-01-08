package liceu;

import exceptions.UnsupportedUserTypeException;

public class UtilizatorFactory {
	
	public static Utilizator creeazaUtilizator(TipUtilizator tip) throws UnsupportedUserTypeException {
		
		Utilizator u = null;
		
		switch(tip) {
		
		case ELEV:
			u = new Elev();
		break;
		
		case PROFESOR:
			u = new Profesor();
		break;
		
		case SECRETAR:
			u = new Secretar();
		break;
		
		case ADMINISTRATOR:
			u = new Administrator();
		break;
		default :
			throw new UnsupportedUserTypeException("Unsupported user type");
		}
		
		return u;
	}

}
