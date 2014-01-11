package liceu;

public interface IAdministrator extends IProfesor, ISecretar {

	public boolean adaugaUtilizator(Utilizator u);
	
	public Utilizator stergeUtilizator(Utilizator u);
	
	public void listareUtilizatori();	
	
}
