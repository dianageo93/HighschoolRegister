package server;

import liceu.Administrator;
import liceu.Centralizator;
import liceu.Elev;
import liceu.Profesor;
import liceu.Secretar;
import liceu.TipUtilizator;
import liceu.Utilizator;

/**
 * 
 * This class is used in order to check the login. Inside the constructor, the AuthHelper class will set all
 * the field values of the Centralizator instance so that they will be used, instead of further querying the server.
 *
 */
public class AuthHelper {

	private String pathToServer = "/home/user/workspace/TemaPOO/server";
	private Centralizator myCentralizator = Centralizator.getInstance();
	private ServerParser myParser = null;
	private TipUtilizator tip = TipUtilizator.ELEV;
	
	/**
	 * The constructor that sets all the fields of the Centralizator instance.
	 */
	public AuthHelper() {
		
		myParser = new ServerParser();
		myCentralizator.setClase(myParser.setupClase(pathToServer + "/CLASE"));
		myCentralizator.setElevi(myParser.setupElevi(pathToServer + "/ELEVI/LISTA_ELEVI"));
		myParser.setupCatalog(pathToServer + "/CLASE");
		myCentralizator.setProfesori(myParser.setupProfesori(pathToServer + "/PERSONAL/LISTA_PROFESORI"));
		myCentralizator.setRepartizareProf(myParser.setupRepartizareProf());
		myCentralizator.setSecretari(myParser.setupSecretari(pathToServer + "/PERSONAL/LISTA_SECRETARI"));
		myCentralizator.setAdministratori(myParser.setupAdministratori(pathToServer + "/PERSONAL/LISTA_ADMIN"));
		
	}
	
	/**
	 * @param username - the username that the user will enter on startup
	 * @param passwd - the passwd that the user will enter on startup
	 * @return Boolean - The method returns true if the login is accepted or false otherwise
	 * 
	 * In order to determin the password of a user, the method calls upon getParolaUtilizator() method
	 * that can be found in the Utilizator class.
	 */
	public boolean loginAccepted(String username, String passwd) {
		Elev e = myCentralizator.getElevi().get(username);
		if(e != null && e.getParolaUtilizator().equals(passwd)) {
			setTip(TipUtilizator.ELEV);
			return true;
		}
		Profesor p = myCentralizator.getProfesori().get(username);
		if(p!= null && p.getParolaUtilizator().equals(passwd)) {
			setTip(TipUtilizator.PROFESOR);
			return true;
		}
		Secretar s = myCentralizator.getSecretari().get(username);
		if(s != null && s.getParolaUtilizator().equals(passwd)) {
			setTip(TipUtilizator.SECRETAR);
			return true;
		}
		Administrator a = myCentralizator.getAdministratori().get(username);
		if(a != null && a.getParolaUtilizator().equals(passwd)) {
			setTip(TipUtilizator.ADMINISTRATOR);
			return true;
		}
		return false;
	}
	
	public TipUtilizator getTip() {
		return tip;
	}
	
	public void setTip(TipUtilizator tip) {
		this.tip = tip;
	}
	
	/**
	 * @param pathToServer - String - the path to the local server. In this version of the application, the server
	 * is merely a folder on the computer.
	 * 
	 * This method sets the path to the local server, retained in the AuthHelper object as a String.
	 */
	public void setPathToServer(String pathToServer) {
		this.pathToServer = pathToServer;
	}
	
	/**
	 * This method returns the value of the pathToServer field.
	 * @return String - the value of the pathToServer field
	 */
	public String getPathToServer() {
		return pathToServer;
	}
	
	// implementing with singleton pattern
	private static AuthHelper instance = null;
	
	/**
	 * This method guarantees the implementation w/ singleton pattern of the object AuthHelper. At any given
	 * time, there exists only one such object in the environment of the app.
	 * @return AuthHelper - the one instance if AuthHelper (if it does exist and, if not, it creates one)
	 */
	public static AuthHelper getInstance() {
		if(instance == null) {
			instance = new AuthHelper();
		}
		return instance;
	}
	
}
