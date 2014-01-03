package liceu;

import java.util.Calendar;
import java.util.LinkedList;

import liceu.utils.Nota;



public class SituatieMaterieBaza {
	
	private Materie m;
	private Nota[] medii;
	private LinkedList<Nota>[] note;
	private LinkedList<Absenta> absente;
	
	// TODO : CONSTRUCTOR
	// TODO : toString
	
	private Nota calculeazaMedie(int sem) {
		if(sem != 0 || sem != 1) {
			return null;
		}
		Nota medie = new Nota();
		for(Nota i : this.note[sem]) {
			medie.setNota(medie.getNota() + i.getNota());
		}
		medie.setNota(medie.getNota() / this.note[sem].size());
		return medie;
	}
	
	public Nota getMedii(int sem) {
		if(sem != 0 || sem != 1) {
			return null;
		}
		return this.medii[sem];
	}
	
	public boolean adaugaNota(Nota e, int sem) {
		if(sem != 0 || sem != 1) {
			return false;
		}
		if(e == null) {
			return false;
		}
		this.note[sem].add(e);
		this.medii[sem] = calculeazaMedie(sem);
		return true;
	}
	
	public boolean adaugaAbsenta(Absenta a) {
		if(a == null) {
			return false;
		}
		this.absente.add(a);
		return true;
	}
	
	public boolean modificareAbsenta(int STATUS, Calendar data) {
		if(STATUS != 0 || STATUS != 1) {
			return false;
		}
		if(data == null) {
			return false;
		}
		// TODO : Cauta absenta cu data resp si modifica statusul
		return true;
	}

}
