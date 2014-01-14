package liceu;

import java.util.Map;
import java.util.TreeSet;

import liceu.utils.NumePrenume;

public class Secretar extends Utilizator implements ISecretar {

	public Secretar() {
		super(TipUtilizator.SECRETAR);
	}
	
	public static enum EditOptions {
		EDIT_CLASA_EDIT_NAME, EDIT_CLASA_ADD_ELEV, EDIT_CLASA_DELETE_ELEV, EDIT_CLASA_EDIT_MATERII,
			EDIT_CLASA_DELETE_PROFESOR,
		EDIT_ELEV_CHANGE_NAME, EDIT_ELEV_CHANGE_CLASS, EDIT_ELEV_CHANGE_LOGIN_ID, EDIT_ELEV_CHANGE_CNP,
		EDIT_PROFESOR_CHANGE_NAME, EDIT_PROFESOR_CHANGE_MATERIE, EDIT_PROFESOR_ADD_CLASA, EDIT_PROFESOR_DELETE_CLASA,
			EDIT_PROFESOR_CHANGE_LOGIN_ID
	}

	@Override
	public String toString() {
		return "Secretar [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}
	
	@Override
	public void addClasa(String c) {
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa newClasa = new Clasa(c);
		clase.add(newClasa);
	}
	
	@Override
	public void delClasa(String c) {
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa found = new Clasa();
		for(Clasa k : clase) {
			if(k.getID().equals(c)) {
				found = k;
				break;
			}
		}
		TreeSet<Elev> elevi = found.getElevi();
		for(Elev e : elevi) {
			Centralizator.getInstance().getElevi().remove(e.getLoginID());
		}
		for(Map.Entry<String, Profesor> entry : Centralizator.getInstance().getProfesori().entrySet()) {
			entry.getValue().removeClasa(c);
		}
		Centralizator.getInstance().getClase().remove(found);
	}
	
	@Override
	public void editClasa(EditOptions option, Elev e, String CNP, String loginID, String newClasa) {
		switch(option) {
		case EDIT_ELEV_CHANGE_CNP :
			Centralizator.getInstance().getElevi().get(e.getLoginID()).setCNP(CNP);
			break;
		case EDIT_ELEV_CHANGE_LOGIN_ID :
			Centralizator.getInstance().getElevi().get(e.getLoginID()).setLoginID(loginID);
			break;
		case EDIT_ELEV_CHANGE_CLASS :
			Centralizator.getInstance().getElevi().get(e.getLoginID()).setClasa(newClasa);
			break;
		}
	}
	
	@Override
	public void editClasa(EditOptions option, Elev e, NumePrenume nume) {
		Centralizator.getInstance().getElevi().get(e.getLoginID()).setNume(nume);
	}
	
	@Override
	public void editProfesor(EditOptions option, Profesor p, NumePrenume nume) {
		Centralizator.getInstance().getProfesori().get(p.getLoginID()).setNume(nume);
	}
	
	@Override
	public void editClasa(EditOptions option, Elev e) {
		String clasa = e.getClasa();
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa found = new Clasa();
		for(Clasa k : clase) {
			if(k.getID().equals(clasa)) {
				found = k;
				break;
			}
		}
		switch(option) {
		case EDIT_CLASA_ADD_ELEV :
			e.setCNP(getUUID().substring(1, 15));
			found.adaugaElev(e);
			break;
		case EDIT_CLASA_DELETE_ELEV :
			found.deleteElev(e);
		}
	}
	
	@Override
	public void editClasa(EditOptions option, Profesor p) {
		
	}
	
	@Override
	public void editClasa(EditOptions option, Profesor p, String newLoginID, String newClasa, 
			String oldClasa, String materie) {
		switch(option) {
		case EDIT_PROFESOR_CHANGE_LOGIN_ID :
			Centralizator.getInstance().getProfesori().get(p.getLoginID()).setLoginID(newLoginID);
			break;
		case EDIT_PROFESOR_ADD_CLASA :
			Centralizator.getInstance().getProfesori().get(p.getLoginID()).adaugaClasa(newClasa);
			break;
		case EDIT_PROFESOR_DELETE_CLASA :
			Centralizator.getInstance().getProfesori().get(p.getLoginID()).removeClasa(oldClasa);
			break;
		case EDIT_PROFESOR_CHANGE_MATERIE :
			Centralizator.getInstance().getProfesori().get(p.getLoginID()).setMaterie(materie);
			break;
		}
	}
}
