package liceu;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import liceu.SituatieMaterieBaza.Absenta;
import liceu.SituatieMaterieBaza.Absenta.Status;
import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;

public class Administrator extends Utilizator implements IAdministrator {

	public Administrator() {
		super(TipUtilizator.ADMINISTRATOR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeSet<Elev> listEleviFromClasa(String c) {
		TreeSet<Elev> listOfElevi = new TreeSet<>();
		TreeMap<String, Elev> elevi = Centralizator.getInstance().getElevi();
		for(Map.Entry<String, Elev> entry : elevi.entrySet()) {
			if(entry.getValue().getClasa().equals(c)) {
				listOfElevi.add(entry.getValue());
			}
		}
		return listOfElevi;
	}

	@Override
	public String toString() {
		return "Administrator [getTipUtilizator()=" + getTipUtilizator()
				+ ", getUUID()=" + getUUID() + ", getNumeUtilizator()="
				+ getNumeUtilizator() + ", getLoginID()=" + getLoginID() + "]\n";
	}

	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu,
			Semestru s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addNota(Semestru s, Elev e, Nota n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean adaugaUtilizator(Utilizator u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilizator stergeUtilizator(Utilizator u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void listareUtilizatori() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addTeza(Semestru s, Elev e, Nota n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAbsenta(Status s, Elev e, Absenta a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyAbsenta(Status s, Elev e, Absenta a) {
		// TODO Auto-generated method stub
		return false;
	}


}
