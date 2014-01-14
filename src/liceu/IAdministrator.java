package liceu;

import java.util.TreeMap;
import java.util.TreeSet;

import server.ServerBuilder.BackupOptions;

import liceu.utils.NumePrenume;

public interface IAdministrator extends IProfesor, ISecretar {

	public boolean adaugaUtilizator(TipUtilizator tip, String loginID, NumePrenume numeUtilizator, String UUID);
	
	public void stergeUtilizator(Utilizator u);
	
	public TreeMap<String, ? extends Utilizator> listareUtilizatori(TipUtilizator tip);

	public boolean adaugaUtilizator(TipUtilizator tip, String loginID,
			NumePrenume numeUtilizator, String UUID, String materie,
			TreeSet<String> clase);	
	
	public void customBackupDataOnServer(BackupOptions option);
	
	public void backupAllDataOnServer();
}
