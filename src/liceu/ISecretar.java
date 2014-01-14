package liceu;

import liceu.Secretar.EditOptions;
import liceu.utils.NumePrenume;

public interface ISecretar {

	public void addClasa(String c);

	public void delClasa(String c);

	public void editClasa(EditOptions option, Elev e, String CNP, String loginID, String newClasa);

	public void editClasa(EditOptions option, Elev e, NumePrenume nume);

	public void editProfesor(EditOptions option, Profesor p, NumePrenume nume);

	public void editClasa(EditOptions option, Profesor p, String loginID,
			String newClasa, String oldClasa, String materie);

	public void editClasa(EditOptions option, Elev e);

	public void editClasa(EditOptions option, Profesor p);

}
