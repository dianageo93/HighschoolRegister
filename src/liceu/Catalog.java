package liceu;

import java.util.TreeMap;

public class Catalog {
	
	private TreeMap<Elev, TreeMap<Materie, SituatieMaterieBaza>> myMap;
	
	public Catalog() {
		myMap = new TreeMap<>();
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
