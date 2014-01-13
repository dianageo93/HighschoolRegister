package liceu;

import java.util.TreeSet;

import liceu.SituatieMaterieBaza.Absenta;
import liceu.SituatieMaterieBaza.Absenta.Status;
import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;

public interface IProfesor {
	
	public enum OrderOptions {
		NUME, MEDIE_MATERIE, MEDIE_SEM_MATERIE, MEDIE_GENERALA,
		MEDIE_GENERALA_SEM,	NR_ABSENTE_MATERIE, NR_ABSENTE_TOTAL
	}
	
	public TreeSet<Elev> listEleviFromClasa(String c);
	
	// NUME, MEDIE_GENERALA, NR_ABSENTE_TOTAL, NR_ABSENTE_MATERIE
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu);
	
	// MEDIE_SEM_MATERIE, MEDIE_GENERALA_SEM
	public TreeSet<Elev> ordonareClasa(String c, OrderOptions criteriu, Semestru s);
	
	public boolean addNota(Semestru s, Elev e, Nota n);
	
	public String toString();

	public boolean addTeza(Semestru s, Elev e, Nota n);

	public boolean addAbsenta(Status s, Elev e, Absenta a);

	public boolean modifyAbsenta(Status s, Elev e, Absenta a);
	
}
