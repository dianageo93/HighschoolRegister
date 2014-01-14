package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import liceu.Administrator;
import liceu.Catalog;
import liceu.Centralizator;
import liceu.Clasa;
import liceu.Elev;
import liceu.Materie;
import liceu.Profesor;
import liceu.Secretar;
import liceu.SituatieMaterieBaza;
import liceu.SituatieMaterieBaza.Absenta;
import liceu.SituatieMaterieBaza.Absenta.Status;
import liceu.SituatieMaterieCuTeza;
import liceu.utils.Nota;

/**
 * This is a helping class, used for querying the server in order to obtain access and write back data.
 * It also provides back-up solutions for the user.
 *
 */
public class ServerBuilder {
	
	/**
	 * This enumeration allows the user to choose how to customize the back-up.
	 *
	 */
	public enum BackupOptions {
		ELEVI, MATERII, CLASE, PERSONAL
	}
	
	/**
	 * This method writes on the server the files that contain data about classes - students, grades and
	 * absences.
	 * @param pathToServer
	 */
	public void buildFileCLASE(String pathToServer) {
		File dir = new File(pathToServer);
		for(File file: dir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		})) {
			file.delete();
		}
		for(Clasa i : Centralizator.getInstance().getClase()) {
			String pathname = pathToServer +  "/" + i.getID();
			try {
				BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathname))); 
				TreeSet<Elev> elevi = i.getElevi();
				for(Elev j : elevi) {
					myFile.write(j.getLoginID());
					for(Materie k : i.getMaterii()) {
						myFile.write("," + k.getNume());
						Catalog catalog = i.getCatalog();
						TreeMap<Materie, SituatieMaterieBaza> elevEntry = catalog.getMyMap().get(j);
						if(k.getTeza()) {
							SituatieMaterieCuTeza sit = (SituatieMaterieCuTeza) elevEntry.get(k);
							for(Nota n : sit.getNoteSem1()) {
								myFile.write(";" + Double.toString(n.getNota()));
							}
							myFile.write("-" + Double.toString(sit.getTezaSem1().getNota()));
							myFile.write("|");
							for(Nota n : sit.getNoteSem2()) {
								myFile.write(";" + Double.toString(n.getNota()));
							}
							myFile.write("-" + Double.toString(sit.getTezaSem2().getNota()));
						}
						else {
							SituatieMaterieBaza sit = elevEntry.get(k);
							for(Nota n : sit.getNoteSem1()) {
								myFile.write(";" + Double.toString(n.getNota()));
							}
							myFile.write("|");
							for(Nota n : sit.getNoteSem2()) {
								myFile.write(";" + Double.toString(n.getNota()));
							}
						}
					}
					myFile.write("\n");
					
				}
			myFile.close();
			} catch (IOException e) {
				System.out.println("IOException found");
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * This method writes on the server the files that contain data about absences - dates and statuses.
	 * @param pathToServer
	 */
	public void buildFileCLASE_ABSENTE(String pathToServer) {
		for(Clasa i : Centralizator.getInstance().getClase()) {
			String pathname = pathToServer + "/" + i.getID() + "_abs";
			BufferedWriter myFile;
			try {
				myFile = new BufferedWriter(new FileWriter(new File(pathname)));
				TreeSet<Elev> elevi = i.getElevi();
				for(Elev j : elevi) {
					myFile.write(j.getLoginID());
					for(Materie k : i.getMaterii()) {
						myFile.write("," + k.getNume());
						// write them dates
						Catalog catalog = i.getCatalog();
						TreeMap<Materie, SituatieMaterieBaza> elevEntry = catalog.getMyMap().get(j);
						SituatieMaterieBaza sit = elevEntry.get(k);
						LinkedList<Absenta> absente = sit.getAbsente();
						for(Absenta l : absente) {
							Date d = l.getDate().getTime();
							int day = d.getDate() == 31 ? 0 : d.getDate();
							int month;
							if(d.getMonth() == 2 && d.getDay() == 0) {
								day = 28;
								month = 1;
							}
							month = d.getDate() == 31 ? d.getMonth() + 1 : d.getMonth();
							int year = d.getYear() + 1900;
							String sts = l.getStatus() == Status.MOTIVATA ? "M" : 
								(l.getStatus() == Status.NEMOTIVATA ? "NM" : "N");
							myFile.write(";" + Integer.toString(day) + "?" + Integer.toString(month) + "?" 
								+ Integer.toString(year) + "-" + sts);
						}
					}
					myFile.write("\n");
				}
				myFile.close();
			} catch (IOException e) {
				System.out.println("IOException found");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method writes on the server the files that contain data about classes - id.
	 * @param pathToServer
	 */
	public void updateListaClase(String pathToServer) {
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		try {
			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathToServer + "/LISTA_CLASE")));
			for(Clasa c : clase) {
				myFile.write(c.getID() + "\n");
			}
		    myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method locally updates the list of students in the Centralizator instance present on runtime.
	 * @param pathToServer
	 */
	public void updateListaElevi(String pathToServer) {
		TreeMap<String, Elev> elevi = Centralizator.getInstance().getElevi();
		try {
			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathToServer + "/LISTA_ELEVI")));
			for(Map.Entry<String, Elev> entry : elevi.entrySet()) {
				Elev e = entry.getValue();
				myFile.write(e.getUUID() + "," + e.getLoginID() + "," + e.getNumeUtilizator() + "\n");
			}
			myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method locally updates the list of admins in the Centralizator instance present on runtime.
	 * @param pathToServer
	 */
	public void updateListaAdmin(String pathToServer) {
		TreeMap<String, Administrator> adm = Centralizator.getInstance().getAdministratori();
		try {
			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathToServer + "/LISTA_ADMIN")));
			for(Map.Entry<String, Administrator> entry : adm.entrySet()) {
				Administrator a = entry.getValue();
				myFile.write(a.getUUID() + "," + a.getLoginID() + "," + a.getNumeUtilizator() + "\n");
			}
			myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method locally updates the list of secretaries in the Centralizator instance present on runtime.
	 * @param pathToServer
	 */
	public void updateListaSecretari(String pathToServer) {
		TreeMap<String, Secretar> scr = Centralizator.getInstance().getSecretari();
		try {
			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathToServer + "/LISTA_SECRETARI")));
			for(Map.Entry<String, Secretar> entry : scr.entrySet()) {
				Secretar s = entry.getValue();
				myFile.write(s.getUUID() + "," + s.getLoginID() + "," + s.getNumeUtilizator() + "\n");
			}
			myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method locally updates the list of teachers in the Centralizator instance present on runtime.
	 * @param pathToServer
	 */
	public void updateListaProfesori(String pathToServer) {
		TreeMap<String, Profesor> prf = Centralizator.getInstance().getProfesori();
		try {
			BufferedWriter myFile = new BufferedWriter(new FileWriter(new File(pathToServer + "/LISTA_PROFESORI")));
			for(Map.Entry<String, Profesor> entry : prf.entrySet()) {
				Profesor p = entry.getValue();
				myFile.write(p.getUUID() + "," + p.getLoginID() + "," + p.getNumeUtilizator() 
						+ "," + p.getMaterie() + ",");
				TreeSet<String> clase = p.getClase();
				for(String c : clase) {
					if(c.equals(clase.first())) {
						myFile.write(c);
					}
					else {
						myFile.write(";" + c);
					}
				}
				myFile.write("\n");
			}
			myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method locally updates the list of subjects in the Centralizator instance present on runtime.
	 * @param pathToServer
	 */
	public void updateListaMaterii(String pathToServer) {
		String pathname = pathToServer + "/MATERII";
		TreeSet<Clasa> clase = Centralizator.getInstance().getClase();
		TreeSet<String> materiiString = new TreeSet<>();
		try {
			for(Clasa c : clase) {
				String additionalString = c.getID().substring(0, 2);
				BufferedWriter myFile = new BufferedWriter(new FileWriter
						(new File(pathname + "/" + additionalString)));
				TreeSet<Materie> materii = c.getMaterii();
				for(Materie m : materii) {
					if(m.equals(materii.first())) {
						myFile.write(m.getNume() + ";" + m.getNrOrePeSapt() + ";" +
								(m.getTeza() == true ? "DA" : "NU"));
					}
					else {
						myFile.write("," + m.getNume() + ";" + m.getNrOrePeSapt() + ";" +
								(m.getTeza() == true ? "DA" : "NU"));
					}
					materiiString.add(m.getNume());
				}
				myFile.close();
			}
			BufferedWriter myFile = new BufferedWriter(new FileWriter
					(new File(pathname + "/" + "LISTA_MATERII")));
			for(String i : materiiString) {
				if(i.equals(materiiString.first())) {
					myFile.write(i);
				}
				else {
					myFile.write("," + i);
				}
			}
			myFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This method customly back-ups one folder at a time.
	 * @param pathToServer
	 */
	public void backupFolder(String pathToServer) {
		try {
			String source = pathToServer; // ..../server/ELEVI
										  // ... /server/backup/ELEVI
			String[] tokens = pathToServer.split("/");
			String lastToken = tokens[tokens.length - 1];
			String destination = "";
			if(lastToken.equals("MATERII")) {
				String beforeLastToken = tokens[tokens.length - 2];
				destination = pathToServer.replace(beforeLastToken, "backup/" + beforeLastToken);
			}
			else {
				destination = pathToServer.replace(lastToken, "backup/" + lastToken);
			}
			File[] files = new File(source).listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File arg0) {
					return arg0.isFile();
				}
			});
			for(File i : files) {
				Files.copy(new File(source + "/" + i.getName()).toPath(), 
						new File(destination + "/" + i.getName()).toPath(),
						StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method back-ups all data on server.
	 * @param pathToServer
	 */
	public void backupAllData(String pathToServer) {
		backupFolder(pathToServer + "/CLASE");
		backupFolder(pathToServer + "/CLASE/MATERII");
		backupFolder(pathToServer + "/ELEVI");
		backupFolder(pathToServer + "/PERSONAL");
	}
	
	/**
	 * This method allows the user to do custom back-up.
	 * @param pathToServer
	 */
	public void customBackup(String pathToServer, BackupOptions option) {
		switch(option) {
		case CLASE:
			backupFolder(pathToServer + "/CLASE");
			break;
		case MATERII:
			backupFolder(pathToServer + "/CLASE/MATERII");
			break;
		case ELEVI:
			backupFolder(pathToServer + "/ELEVI");
			break;
		case PERSONAL:
			backupFolder(pathToServer + "/PERSONAL");
			break;
		}
	}
	
}

