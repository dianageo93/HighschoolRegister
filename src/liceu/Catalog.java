package liceu;

import java.util.Map;
import java.util.TreeMap;

import liceu.SituatieMaterieBaza.Semestru;

public class Catalog {
	
	private TreeMap<Elev, TreeMap<Materie, SituatieMaterieBaza>> myMap;
	
	public Catalog() {
		myMap = new TreeMap<>();
	}
	
	public double getMedieGeneralaElev(Elev e) {
		TreeMap<Materie, SituatieMaterieBaza> sit = myMap.get(e);
		double suma = 0.0;
		int nr = 0;
		for(Map.Entry<Materie, SituatieMaterieBaza> i : sit.entrySet()) {
			suma += i.getValue().getMedieGenerala();
			nr++;
		}
		return suma / (double) nr;
	}
	
	public double getMedieMaterieElev(Elev e, Materie m, Semestru s) {
		TreeMap<Materie, SituatieMaterieBaza> sit = myMap.get(e);
		SituatieMaterieBaza smb = sit.get(m);
		if(s == Semestru.SEMESTRUL_1) {
			return smb.getMedieSem1().getNota();
		}
		else {
			return smb.getMedieSem2().getNota();
		}
	}
	
	public double getMedieElevSemestru(Elev e, Semestru s) {
		TreeMap<Materie, SituatieMaterieBaza> sit = myMap.get(e);
		double suma = 0.0;
		int nr = 0;
		for(Map.Entry<Materie, SituatieMaterieBaza> i : sit.entrySet()) {
			if(s == Semestru.SEMESTRUL_1) {
				suma += i.getValue().getMedieSem1().getNota();
			}
			else {
				suma += i.getValue().getMedieSem2().getNota();
			}
			nr++;
		}
		return suma / (double) nr;
	}
	
	public int getNrAbsenteTotalElev(Elev e) {
		TreeMap<Materie, SituatieMaterieBaza> sit = myMap.get(e);
		int nr = 0;
		for(Map.Entry<Materie, SituatieMaterieBaza> i : sit.entrySet()) {
			nr += i.getValue().getNrAbsente();
		}
		return nr;
	}
	
	public int getNrAbsenteMaterieElev(Elev e, Materie m) {
		TreeMap<Materie, SituatieMaterieBaza> sit = myMap.get(e);
		SituatieMaterieBaza smb = sit.get(m);
		return smb.getNrAbsente();
	}

	@Override
	public String toString() {
		return "Catalog [myMap=" + myMap + "]";
	}

	public TreeMap<Elev, TreeMap<Materie, SituatieMaterieBaza>> getMyMap() {
		return myMap;
	}

	public void setMyMap(
			TreeMap<Elev, TreeMap<Materie, SituatieMaterieBaza>> myMap) {
		this.myMap = myMap;
	}
	
}
