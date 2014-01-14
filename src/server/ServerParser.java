package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import liceu.utils.NumePrenume;

/**
 * This method is used to query the server for data. It creates a Centralizator instance that will be further used
 * in the app.
 *
 */
public class ServerParser {
	
	private int BEGIN_PASSWD = 8;
	private int END_PASSWD = BEGIN_PASSWD + 6;
	private int BEGIN_CLASA = END_PASSWD;
	
	/**
	 * This method sets up the Catalog object associated to each class.
	 * @param clasa
	 * @param pathToServer
	 * @return the Catalog object
	 */
	private Catalog setupCatalogForClasa(String clasa, String pathToServer) {
		Catalog myCatalog = new Catalog();
		String myPathToServer = pathToServer + "/" + clasa;
		String myPathToServer2 = myPathToServer + "_abs";
		try(BufferedReader br = new BufferedReader(new FileReader(myPathToServer))) {
			BufferedReader br2 = new BufferedReader(new FileReader(myPathToServer2));
			for(String line; (line = br.readLine()) != null; ) {
				String line2 = br2.readLine();
				// a map for absente
				TreeMap<Materie, LinkedList<Absenta>> absenteMap = new TreeMap<>();
				// parse the dates in #clasa_abs
				String[] tokens = line2.split(",");
				for(int j = 1; j < tokens.length; j++) {
					String[] tokens2 = tokens[j].split(";");
					
					//create the key
					Materie keyMaterie = new Materie(tokens2[0]);
					
					// create the value
					LinkedList<Absenta> absente = new LinkedList<>();
					for(int k = 1; k < tokens2.length; k++) {
						String[] tokens3 = tokens2[k].split("\\?");
						String day = tokens3[0];
						String month = tokens3[1];
						String year = tokens3[2].substring(0, 4);
						String status = tokens3[2].substring(5);
						Absenta abs = new Absenta();
						Calendar date = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month), 
								Integer.parseInt(day));
						abs.setDate(date);
						if(status.equals("M")) {
							abs.setStatus(Status.MOTIVATA);
						}
						else if(status.equals("NM")) {
							abs.setStatus(Status.NEMOTIVATA);
						}
						else {
							status.equals(Status.NEDETERMINAT);
						}
						absente.add(abs);
					}
					absenteMap.put(keyMaterie, absente);
				}
				
				tokens = line.split(",");
				
				// create the key
				Elev key = Centralizator.getInstance().getElevi().get(tokens[0]);
				
				// create the value :
				TreeMap<Materie, SituatieMaterieBaza> value = new TreeMap<>();
				for(int j = 1; j < tokens.length; j++) {
					// create key for myValue.entry
					Materie m;
					LinkedList<Nota> noteSem1 = new LinkedList<>();
					LinkedList<Nota> noteSem2 = new LinkedList<>();
					String tezaNotaSem1 = "0.0";
					String tezaNotaSem2 = "0.0";
					boolean teza = false;
					
					String[] tokens2 = tokens[j].split(";");
					m = new Materie(tokens2[0]);
					boolean sem2 = false; // will switch the semester
					
					// create value for myValue.entry
					// loop through all the grades
					for(int k = 1; k < tokens2.length; k++) {
						// switch semester if necessary
						if(tokens2[k].contains("|") && tokens2[k].contains("-")) {
							teza = true;
							String[] tokens3 = tokens2[k].split("-");
							sem2 = true;
							int length = tokens3[1].length();
							tezaNotaSem1 = tokens3[1].substring(0, length - 1);
							// retain last grade
							tokens2[k] = tokens3[0];
							noteSem1.add(new Nota(tokens2[k]));
						}
						else if(tokens2[k].contains("|")) {
							sem2 = true;
							int length = tokens2[k].length();
							tokens2[k] = tokens2[k].substring(0, length - 1);
							noteSem1.add(new Nota(tokens2[k]));
						}
						else if(tokens2[k].contains("-")) {
							teza = true;
							String[] tokens3 = tokens2[k].split("-");
							// retain tezaNota
							if(sem2 == false) {
								tezaNotaSem1 = tokens3[1];
							}
							else {
								tezaNotaSem2 = tokens3[1];
							}
							// retain last grade
							tokens2[k] = tokens3[0];
							noteSem2.add(new Nota(tokens2[k]));
						}
						else {
							if(sem2 == false) { // a grade for semester 1
								noteSem1.add(new Nota(tokens2[k]));
							}
							else {
								noteSem2.add(new Nota(tokens2[k]));
							}
						}
						m.setTeza(teza);
					}
					if(teza == true) {
						SituatieMaterieCuTeza sit = new SituatieMaterieCuTeza(m);
						sit.setTezaSem1(new Nota(tezaNotaSem1));
						sit.setTezaSem2(new Nota(tezaNotaSem2));
						sit.setNoteSem1(noteSem1);
						sit.setNoteSem2(noteSem2);
						sit.setAbsente(absenteMap.get(m));
						value.put(m, sit);
					}
					else {
						SituatieMaterieBaza sit = new SituatieMaterieBaza(m);
						sit.setNoteSem1(noteSem1);
						sit.setNoteSem2(noteSem2);
						sit.setAbsente(absenteMap.get(m));
						value.put(m, sit);
					}
				}
				// add entry to Catalog
				myCatalog.getMyMap().put(key, value);
			}
			br.close();
			br2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myCatalog;
	}
	
	/**
	 * This method sets up the Catalog objects for every class present in the server. It actually parses the
	 * files on server.
	 * @param pathToServer
	 */
	public void setupCatalog(String pathToServer) {
		for(Clasa c : Centralizator.getInstance().getClase()) {
	    	c.setCatalog(setupCatalogForClasa(c.getID(), pathToServer));
		}
	}
	
	/**
	 * This method returns a dictionary containg a Materie key and a value formed of another dictionary, that
	 * contains a Clasa-type object and a Profesor-type value. It actually maps each teacher on a class for
	 * a certain subject.
	 * @return
	 */
	public TreeMap<Materie, TreeMap<Clasa, Profesor>> setupRepartizareProf() {
		TreeMap<Materie, TreeMap<Clasa, Profesor>> myMap = new TreeMap<>();
		TreeMap<String, Profesor> profesori = Centralizator.getInstance().getProfesori();
		
		// for-each profesor in Centralizator
		for(Map.Entry<String, Profesor> j : profesori.entrySet()) {
			Materie m = new Materie(j.getValue().getMaterie()); // materia predata de profesorul j
			// if myMap already contains the key, just add the corresponding "clasa" to the value
			if(myMap.containsKey(m)) {
				Profesor p = j.getValue();
				for(String k : p.getClase()) {
					myMap.get(m).put(new Clasa(k), p);
				}
			}
			// else, create new MapEntry
			else {
				TreeMap<Clasa, Profesor> values = new TreeMap<>();
				Profesor p = j.getValue();
				for(String k : p.getClase()) {
					values.put(new Clasa(k), p);
				}
				myMap.put(m, values);
			}
		}
		
		return myMap;
		
	}
	
	/**
	 * This method will generate a TreeSet<Clasa>, that will retain the details of every class in school.
	 * @param pathToServer
	 * @return the TreeSet<Clasa>
	 */
	public TreeSet<Clasa> setupClase(String pathToServer) {
		TreeSet<Clasa> clase = new TreeSet<>();
		try(BufferedReader br = new BufferedReader(new FileReader(pathToServer + "/LISTA_CLASE"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	Clasa c = new Clasa(line);
		    	line = line.substring(0, 2);
		    	try(BufferedReader br1 = new BufferedReader(new FileReader(pathToServer + "/MATERII/" + line))) {
				    for(String line1; (line1 = br1.readLine()) != null; ) {
				    	String[] tokens = line1.split(",");
				    	for(String i : tokens) {
				    		String tokens1[] = i.split(";");
				    		Materie m = new Materie();
				    		m.setNume(tokens1[0]);
				    		m.setNrOrePeSapt(Integer.parseInt(tokens1[1]));
				    		m.setTeza(tokens1[2].equals("DA") ? true : false);
				    		c.adaugaMaterie(m);
				    	}
				    }
			    } catch (FileNotFoundException e) {
					System.out.println("File not found");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("IOExc");
					e.printStackTrace();
				}
		    	clase.add(c);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
		return clase;
	}
	
	/**
	 * This method will generate a TreeMap, that will retain the details of every student in school.
	 * @param pathToServer
	 * @return the TreeMap
	 */
	public TreeMap<String, Elev> setupElevi(String pathToServer) {
		TreeMap<String, Elev> elevi = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(pathToServer))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	// tokenize the line
		    	String tokens[] = line.split(",");
	    		Elev e = new Elev();
	    		
	    		e.setUUID(tokens[0]);
	    		e.setLoginID(tokens[1]);
	    		e.setParolaUtilizator("elev" + tokens[0].substring(BEGIN_PASSWD, END_PASSWD));
	    		
	    		// tokenize the name
	    		String nameTok[] = tokens[2].split(" ");
	    		String nume = nameTok[0];
	    		String prenume = "";
	    		for(int i = 1; i < nameTok.length; i++) {
	    			prenume += nameTok[i] + " ";
	    		}
	    		prenume = prenume.substring(0, prenume.length() - 1);
	    		e.setNume(new NumePrenume(nume, prenume));
	    		
	    		//get the CNP
	    		String CNP = tokens[0].substring(1, tokens[0].length() - 3);
	    		e.setCNP(CNP);
	    		
				elevi.put(tokens[1], e);
				
				//add elev to a class
				String whatClasa = e.getUUID().substring(BEGIN_CLASA);
				Clasa c = new Clasa(whatClasa);
				TreeSet<Clasa> toateClasele = Centralizator.getInstance().getClase();
				for(Clasa i : toateClasele) {
					if(i.equals(c)) {
						c = i;
						break;
					}
				}
		    	c.adaugaElev(e);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return elevi;
	}
	
	/**
	 * This method will generate a TreeMap, that will retain the details of every teacher in school.
	 * @param pathToServer
	 * @return the TreeMap
	 */
	public TreeMap<String, Profesor> setupProfesori(String pathToServer) {
		TreeMap<String, Profesor> profesori = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(pathToServer))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	// tokenize the line
		    	String tokens[] = line.split(",");
	    		Profesor p = new Profesor();
	    		p.setUUID(tokens[0]);
	    		p.setLoginID(tokens[1]);
	    		p.setParolaUtilizator("prof" + tokens[0].substring(BEGIN_PASSWD));
	    		
	    		// tokenize the name
	    		String nameTok[] = tokens[2].split(" ");
	    		String nume = nameTok[0];
	    		String prenume = "";
	    		for(int i = 1; i < nameTok.length; i++) {
	    			prenume += nameTok[i] + " ";
	    		}
	    		prenume = prenume.substring(0, prenume.length() - 1);
	    		p.setNume(new NumePrenume(nume, prenume));
	    		
	    		//set materie
	    		p.setMaterie(tokens[3]);
	    		
	    		//set clase
	    		String[] tokens2 = tokens[4].split(";");
	    		for(String i : tokens2) {
	    			p.adaugaClasa(i);
	    		}
	    		
				profesori.put(tokens[1], p);
		    	
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return profesori;
	}
	
	/**
	 * This method will generate a TreeMap, that will retain the details of every secretary in school.
	 * @param pathToServer
	 * @return the TreeMap
	 */
	public TreeMap<String, Secretar> setupSecretari(String pathToServer) {
		TreeMap<String, Secretar> secretari = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(pathToServer))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	// tokenize the line
		    	String tokens[] = line.split(",");
	    		Secretar s = new Secretar();
	    		s.setUUID(tokens[0]);
	    		s.setLoginID(tokens[1]);
	    		s.setParolaUtilizator("secr" + tokens[0].substring(BEGIN_PASSWD));
	    		
	    		// tokenize the name
	    		String nameTok[] = tokens[2].split(" ");
	    		String nume = nameTok[0];
	    		String prenume = "";
	    		for(int i = 1; i < nameTok.length; i++) {
	    			prenume += nameTok[i] + " ";
	    		}
	    		prenume = prenume.substring(0, prenume.length() - 1);
	    		s.setNume(new NumePrenume(nume, prenume));
	    		
				secretari.put(tokens[1], s);
		    	
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return secretari;
	}
	
	/**
	 * This method will generate a TreeMap, that will retain the details of every admin in school.
	 * @param pathToServer
	 * @return the TreeMap
	 */
	public TreeMap<String, Administrator> setupAdministratori(String pathToServer) {
		TreeMap<String, Administrator> administratori = new TreeMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader(pathToServer))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		    	// tokenize the line
		    	String tokens[] = line.split(",");
	    		Administrator a = new Administrator();
	    		a.setUUID(tokens[0]);
	    		a.setLoginID(tokens[1]);
	    		a.setParolaUtilizator("admin" + tokens[0].substring(BEGIN_PASSWD + 1));
	    		
	    		// tokenize the name
	    		String nameTok[] = tokens[2].split(" ");
	    		String nume = nameTok[0];
	    		String prenume = "";
	    		for(int i = 1; i < nameTok.length; i++) {
	    			prenume += nameTok[i] + " ";
	    		}
	    		prenume = prenume.substring(0, prenume.length() - 1);
	    		a.setNume(new NumePrenume(nume, prenume));
	    		
				administratori.put(tokens[1], a);
		    	
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return administratori;
	}

}
