package liceu;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

import liceu.IProfesor.OrderOptions;
import liceu.SituatieMaterieBaza.Semestru;
import liceu.utils.Nota;
import server.AuthHelper;
import server.LogoutHelper;
import server.ServerBuilder.BackupOptions;

public class fakeMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		AuthHelper helper = new AuthHelper();
		
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		Clasa c = clase.first();
		Catalog cat = c.getCatalog();
		
		TreeMap<String, Elev> elevi = Centralizator.getInstance().getElevi();
		Elev e = elevi.get("andreea_mariana.chivu");
		System.out.println(e);
		
		TreeMap<String, Profesor> profesori = Centralizator.getInstance().getProfesori();
		Profesor p = profesori.get("adrian.surpateanu");
		LogoutHelper.getInstance().customBackupDataOnServer(BackupOptions.CLASE);
		
		LogoutHelper.getInstance().updateCatalog();
		
		
		
//		TreeMap<Materie, SituatieMaterieBaza> elevEntry = cat.getMyMap().get(e);
//		System.out.println(elevEntry);
		
//		LogoutHelper myLogoutHelper = new LogoutHelper();
//		myLogoutHelper.updateCatalog();
//		myLogoutHelper.updateListaProfesori();
//		myLogoutHelper.updateListaMaterii();
//		myLogoutHelper.customBackupdDataOnServer(BackupOptions.ELEVI);
//		System.out.println("gata");
		
		
		
//		for(Clasa i : Centralizator.getInstance().getClase()) {
//			String pathname = "/home/user/workspace/TemaPOO/server/CLASE/" + i.getID() + "_abs";
//			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathname))); 
//			TreeSet<Elev> elevi = i.getElevi();
//			for(Elev j : elevi) {
//				myFile.write(j.getLoginID());
//				for(Materie k : i.getMaterii()) {
//					Random rand = new Random();
//					myFile.write("," + k.getNume());
//					for(int l = 0; l < 4; l++) {
//						int zi = rand.nextInt(30);
//						int luna = rand.nextInt(11);
//						int an = 2014;
//						int status = rand.nextInt(3);
//						String sts = status == 0 ? "M" : (status == 1 ? "N" : "NM");
//						myFile.write(";" + Integer.toString(zi) + "?" + Integer.toString(luna) + "?" + Integer.toString(an) + "-" + sts);
//					}
//				}
//				myFile.write("\n");
//			}
//			myFile.close();
//		}
		
		
//		for(Clasa i : Centralizator.getInstance().getClase()) {
//			String pathname = "/home/user/workspace/TemaPOO/server/CLASE/" + i.getID();
//			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathname))); 
//			TreeSet<Elev> elevi = i.getElevi();
//			for(Elev j : elevi) {
//				myFile.write(j.getLoginID());
//				for(Materie k : i.getMaterii()) {
//					Random rand = new Random();
//					myFile.write("," + k.getNume());
//					for(int l = 0; l < 3; l++) {
//						int parteInt = rand.nextInt(9) + 1;
//						int parteFr = rand.nextInt(100);
//						myFile.write(";" + Integer.toString(parteInt) + "." + Integer.toString(parteFr));
//					}
//					if(k.getTeza()) {
//						int parteInt = rand.nextInt(9) + 1;
//						int parteFr = rand.nextInt(100);
//						myFile.write("-" + Integer.toString(parteInt) + "." + Integer.toString(parteFr));
//					}
//					myFile.write("|");
//					for(int l = 0; l < 3; l++) {
//						int parteInt = rand.nextInt(9) + 1;
//						int parteFr = rand.nextInt(100);
//						myFile.write(";" + Integer.toString(parteInt) + "." + Integer.toString(parteFr));
//					}
//					if(k.getTeza()) {
//						int parteInt = rand.nextInt(9) + 1;
//						int parteFr = rand.nextInt(100);
//						myFile.write("-" + Integer.toString(parteInt) + "." + Integer.toString(parteFr));
//					}
//				}
//				myFile.write("\n");
//				
//			}
//			
//			myFile.close();
//		}
//		System.out.println("done");
	}

}
