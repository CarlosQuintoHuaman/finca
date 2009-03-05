package icaro.aplicaciones.recursos.persistenciaSecretaria;

import java.util.ArrayList;




import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaSecretaria extends ItfUsoRecursoSimple {
	//public ArrayList<InfoPaciente> getPacientes();
	
	
	
	public boolean compruebaUsuario(String usuario, String password) throws Exception;
	
	//public boolean compruebaNombreUsuario(String usuario) throws Exception;
	//public void insertaUsuario (String usuario,String password) throws Exception;
	public ArrayList<DatosCitaSinValidar> getCitas();
	
}