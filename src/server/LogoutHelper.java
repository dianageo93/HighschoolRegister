package server;

import server.ServerBuilder.BackupOptions;


public class LogoutHelper {

	private String pathToServer = "/home/user/workspace/TemaPOO/server";
	private ServerBuilder myBuilder = new ServerBuilder();
	
	public void updateCatalog() {
		myBuilder.buildFileCLASE(pathToServer + "/CLASE");
		myBuilder.buildFileCLASE_ABSENTE(pathToServer + "/CLASE");
	}
	
	public void updateListaClase() {
		myBuilder.updateListaClase(pathToServer + "/CLASE");
	}
	
	public void updateListaElevi() {
		myBuilder.updateListaElevi(pathToServer + "/ELEVI");
	}
	
	public void updateListaAdmin() {
		myBuilder.updateListaAdmin(pathToServer + "/PERSONAL");
	}
	
	public void updateListaProfesori() {
		myBuilder.updateListaProfesori(pathToServer + "/PERSONAL");
	}
	
	public void updateListaSecretari() {
		myBuilder.updateListaSecretari(pathToServer + "/PERSONAL");
	}
	
	public void updateListaMaterii() {
		myBuilder.updateListaMaterii(pathToServer + "/CLASE");
		
	}
	
	public void backupAllDataOnServer() {
		myBuilder.backupAllData(pathToServer);
	}
	
	public void customBackupdDataOnServer(BackupOptions option) {
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
