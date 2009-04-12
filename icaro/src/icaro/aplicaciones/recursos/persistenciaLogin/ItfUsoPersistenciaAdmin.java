package icaro.aplicaciones.recursos.persistenciaAdmin;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoPersistenciaAdmin extends ItfUsoRecursoSimple {
	/**
	 * Comprueba si un nombre de usuario y password esta en la BD
	 * @param usuario
	 * @param password
	 * @return Devuelve un string con el tipo de usuario. Puede ser
	 * 		   Medico, Secretaria o Admin. Si no esta devuelve el string "False"
	 * @throws Exception
	 */
	public String compruebaUsuario(String usuario, String password) throws Exception;
	
}