package server;

import server.ServerBuilder.BackupOptions;

/**
 * This class is used when logging out from the gui. When changes are made to the internal Centralizator instance,
 * the LogoutHelper will provide the framework for saving data back to server. The methods in this class allow the
 * user to save particular or entire data.
 * The class uses a ServerBuilder instance in order to querry the server.
 * The class also provides methods for back-up. One can back-up particular or entire data.
 *
 */
public class LogoutHelper {

	private String pathToServer = "/home/user/workspace/TemaPOO/server";
	private ServerBuilder myBuilder = new ServerBuilder();
	
	/**
	 * This method will update the entire data on server, meaning all the files in folders and subfolders.
	 */
	public void updateAllDataOnServer() {
		updateCatalog();
		updateListaAdmin();
		updateListaClase();
		updateListaElevi();
		updateListaMaterii();
		updateListaProfesori();
		updateListaSecretari();
	}
	
	/**
	 * This method updates the data of the register, meaning grades and absences.
	 */
	public void updateCatalog() {
		myBuilder.buildFileCLASE(pathToServer + "/CLASE");
		myBuilder.buildFileCLASE_ABSENTE(pathToServer + "/CLASE");
		myBuilder.updateListaClase(pathToServer + "/CLASE");
	}
	
	/**
	 * This method updates the list of classes that are present in the school.
	 */
	public void updateListaClase() {
		myBuilder.updateListaClase(pathToServer + "/CLASE");
	}
	
	/**
	 * This method updates the list of students in the school.
	 */
	public void updateListaElevi() {
		myBuilder.updateListaElevi(pathToServer + "/ELEVI");
	}
	
	/**
	 * This method updates the list of admins on the server.
	 */
	public void updateListaAdmin() {
		myBuilder.updateListaAdmin(pathToServer + "/PERSONAL");
	}
	
	/** 
	 * This method updates the list of teachers on the server.
	 */
	public void updateListaProfesori() {
		myBuilder.updateListaProfesori(pathToServer + "/PERSONAL");
	}
	
	/**
	 * This method updates the list of secretaries on the server.
	 */
	public void updateListaSecretari() {
		myBuilder.updateListaSecretari(pathToServer + "/PERSONAL");
	}
	
	/**
	 * This method updates the list of subjects on the server.
	 */
	public void updateListaMaterii() {
		myBuilder.updateListaMaterii(pathToServer + "/CLASE");
		
	}
	
	/**
	 * This method allow the user to back-up data on server. The additional data is saved in a particular
	 * folder, called "backup".
	 */
	public void backupAllDataOnServer() {
		myBuilder.backupAllData(pathToServer);
	}
	
	/**
	 * This method allows the user to customize the backup on server
	 * @param option - Can be BackupOptions.ELEVI, MATERII, CLASE, PERSONAL
	 */
	public void customBackupDataOnServer(BackupOptions option) {
		myBuilder.customBackup(pathToServer, option);
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
	private static LogoutHelper instance = null;
	
	/**
	 * This method guarantees the implementation w/ singleton pattern of the object AuthHelper. At any given
	 * time, there exists only one such object in the environment of the app.
	 * @return AuthHelper - the one instance if AuthHelper (if it does exist and, if not, it creates one)
	 */
	public static LogoutHelper getInstance() {
		if(instance == null) {
			instance = new LogoutHelper();
		}
		return instance;
	}
	
}
