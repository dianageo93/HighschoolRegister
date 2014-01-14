package liceu;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import server.LogoutHelper;
import server.ServerBuilder.BackupOptions;

import exceptions.UnsupportedUserTypeException;

import liceu.Secretar.EditOptions;
import liceu.SituatieMaterieBaza.Absenta;
import liceu.SituatieMaterieBaza.Absenta.Status;
import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;
import liceu.utils.NumePrenume;

public class Administrator extends Utilizator implements IAdministrator {

	public Administrator() {
		super(TipUtilizator.ADMINISTRATOR);
	}

	@Override
	public TreeSet<Elev> listEleviFromClasa(String c) {
		return new Profesor().listEleviFromClasa(c);
	}

	@Override
	public String toString() {
		return "Administrator [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}

	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu) {
		return new Profesor().ordonareClasa(c, criteriu);
	}

	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu,
			Semestru s) {
		return new Profesor().ordonareClasa(c, criteriu, s);
	}

	@Override
	public boolean addNota(Semestru s, Elev e, Nota n) {
		return new Profesor().addNota(s, e, n);
	}

	@Override
	public boolean adaugaUtilizator(TipUtilizator tip, String loginID, NumePrenume numeUtilizator, String UUID) {
		Utilizator u = null;
		try {
			u = UtilizatorFactory.creeazaUtilizator(tip);
		} catch (UnsupportedUserTypeException e) {
			e.printStackTrace();
			return false;
		}
		u.setLoginID(loginID);
		u.setNume(numeUtilizator);
		u.setUUID(UUID);
		u.setTipUtilizator(tip);
		switch(tip) {
		case ELEV :
			new Secretar().editClasa(EditOptions.EDIT_CLASA_ADD_ELEV, (Elev) u);
			Centralizator.getInstance().getElevi().put(loginID, (Elev) u);
			return true;
		case SECRETAR :
			Centralizator.getInstance().getSecretari().put(loginID, (Secretar) u);
			return true;
		case ADMINISTRATOR :
			Centralizator.getInstance().getAdministratori().put(loginID, (Administrator) u);
			return true;
		default :
			return false;
		}
		
	}
	
	@Override
	public boolean adaugaUtilizator(TipUtilizator tip, String loginID, NumePrenume numeUtilizator, String UUID,
			String materie, TreeSet<String> clase) {
		Utilizator u = null;
		try {
			u = UtilizatorFactory.creeazaUtilizator(tip);
		} catch (UnsupportedUserTypeException e) {
			e.printStackTrace();
			return false;
		}
		u.setLoginID(loginID);
		u.setNume(numeUtilizator);
		u.setUUID(UUID);
		switch(tip) {
		case PROFESOR :
			Centralizator.getInstance().getProfesori().put(loginID, (Profesor) u);
			new Secretar().editClasa(EditOptions.EDIT_PROFESOR_CHANGE_MATERIE, (Profesor) u, 
					null, null, null, materie);
			for(String c : clase) {
				new Secretar().editClasa(EditOptions.EDIT_PROFESOR_ADD_CLASA, (Profesor) u, 
						null, c, null, null);
			}
			Centralizator.getInstance().getProfesori().put(loginID, (Profesor) u);
			return true;
		default :
			return false;
		}
	}

	@Override
	public void stergeUtilizator(Utilizator u) {
		TipUtilizator tip = u.getTipUtilizator();
		switch(tip) {
		case ELEV :
			new Secretar().editClasa(EditOptions.EDIT_CLASA_DELETE_ELEV, (Elev) u);
			Centralizator.getInstance().getElevi().remove(u.getLoginID());
			return;
		case PROFESOR :
			new Secretar().editClasa(EditOptions.EDIT_CLASA_DELETE_PROFESOR, (Profesor) u);
			Centralizator.getInstance().getProfesori().remove(u.getLoginID());
			return;
		case SECRETAR :
			Centralizator.getInstance().getSecretari().remove(u.getLoginID());
			return;
		case ADMINISTRATOR :
			Centralizator.getInstance().getAdministratori().remove(u.getLoginID());
			return;
		default :
			return;
		}
	}

	@Override
	public TreeMap<String, ? extends Utilizator> listareUtilizatori(TipUtilizator tip) {
		TreeMap<String, ? extends Utilizator> mySet = new TreeMap<>();
		switch(tip)	 {
		case ELEV :
			return Centralizator.getInstance().getElevi();
		case SECRETAR :
			return Centralizator.getInstance().getSecretari();
		case PROFESOR :
			return Centralizator.getInstance().getProfesori();
		case ADMINISTRATOR :
			return Centralizator.getInstance().getAdministratori();
		}
		return mySet;
	}

	@Override
	public boolean addTeza(Semestru s, Elev e, Nota n) {
		return new Profesor().addTeza(s, e, n);
	}

	@Override
	public boolean addAbsenta(Status s, Elev e, Absenta a) {
		return new Profesor().addAbsenta(s, e, a);
	}

	@Override
	public boolean modifyAbsenta(Status s, Elev e, Absenta a) {
		return new Profesor().modifyAbsenta(s, e, a);
	}

	@Override
	public void addClasa(String c) {
		new Secretar().addClasa(c);
		
	}

	@Override
	public void delClasa(String c) {
		new Secretar().delClasa(c);
		
	}

	@Override
	public void editClasa(EditOptions option, Elev e, String CNP,
			String loginID, String newClasa) {
		new Secretar().editClasa(option, e, CNP, loginID, newClasa);
		
	}

	@Override
	public void editClasa(EditOptions option, Elev e, NumePrenume nume) {
		new Secretar().editClasa(option, e, nume);
		
	}

	@Override
	public void editProfesor(EditOptions option, Profesor p, NumePrenume nume) {
		new Secretar().editProfesor(option, p, nume);
		
	}

	@Override
	public void editClasa(EditOptions option, Profesor p, String loginID,
			String newClasa, String oldClasa, String materie) {
		new Secretar().editClasa(option, p, loginID, newClasa, oldClasa, materie);
		
	}

	@Override
	public void editClasa(EditOptions option, Elev e) {
		new Secretar().editClasa(option, e);
		
	}

	@Override
	public void editClasa(EditOptions option, Profesor p) {
		new Secretar().editClasa(option, p);
		
	}

	@Override
	public void customBackupDataOnServer(BackupOptions option) {
		LogoutHelper.getInstance().customBackupDataOnServer(option);
		
	}

	@Override
	public void backupAllDataOnServer() {
		LogoutHelper.getInstance().backupAllDataOnServer();
		
	}


}
