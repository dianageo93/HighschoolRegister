package liceu;

import java.io.IOException;
import java.util.GregorianCalendar;

import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;
import server.AuthHelper;

public class fakeMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		AuthHelper helper = new AuthHelper();
		SituatieMaterieCuTeza sit = new SituatieMaterieCuTeza(new Materie("Romana"));
		sit.addAbsenta(new GregorianCalendar(2013, 9, 12), SituatieMaterieBaza.Absenta.Status.MOTIVATA);
		
		sit.addNota(new Nota(10), Semestru.SEMESTRUL_1);
		sit.addNota(new Nota(9), Semestru.SEMESTRUL_1);
		sit.setTezaSem1(new Nota(9));
		sit.addNota(new Nota(10), Semestru.SEMESTRUL_2);
		sit.setTezaSem2(new Nota(9));
		System.out.println(sit);
		
		
	}

}
