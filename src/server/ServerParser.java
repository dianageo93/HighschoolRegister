package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import liceu.Administrator;
import liceu.Centralizator;
import liceu.Clasa;
import liceu.Elev;
import liceu.Materie;
import liceu.Profesor;
import liceu.Secretar;
import liceu.utils.NumePrenume;

public class ServerParser {
	
	private int BEGIN_PASSWD = 8;
	private int END_PASSWD = BEGIN_PASSWD + 6;
	private int BEGIN_CLASA = END_PASSWD;
	
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
		    
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}
		return clase;
	}
	
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
		    
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return elevi;
	}
	
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
		    
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return profesori;
	}
	
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
		    
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOExc");
			e.printStackTrace();
		}

		return secretari;
	}
	
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
