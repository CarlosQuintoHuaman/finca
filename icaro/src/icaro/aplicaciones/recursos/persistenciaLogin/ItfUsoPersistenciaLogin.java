package icaro.aplicaciones.recursos.persistenciaLogin;

import java.util.ArrayList;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaLogin extends ItfUsoRecursoSimple {
	public String compruebaUsuario(String usuario, String password) throws Exception;
	public boolean compruebaNombreUsuario(String usuario) throws Exception;
	public void insertaUsuario (String usuario,String password) throws Exception;
	
}