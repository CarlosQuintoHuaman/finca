package icaro.aplicaciones.recursos.persistenciaAdmin;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin.InfoUsuario;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoPersistenciaAdmin extends ItfUsoRecursoSimple {
	public ArrayList<InfoUsuario> getUsuarios() throws Exception;
	public void optimizar() throws Exception;
}