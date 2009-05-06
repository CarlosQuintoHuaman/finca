package icaro.aplicaciones.recursos.persistenciaMensajeria;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoPersistenciaMensajeria extends ItfUsoRecursoSimple {
	
	/**
	 * Obtiene los medicamentos para una visita en concreto
	 * @param p Nombre de usuario
	 * @param f Fecha de la visita
	 * @return ArrayList con los medicamentos
	 * @throws Exception
	 */
	public ArrayList<InfoMensaje> getMensajes(String usuario) throws Exception;
	
	/**
	 * Inserta un medicamento en la BD
	 * @param m InfoMedicamento con los datos del medicamento
	 * @throws Exception
	 */
	public void insertaMensaje(InfoMensaje m) throws Exception;
	
	/**
	 * Elimina un medicamento completamente de la BD
	 * @param m
	 * @throws Exception
	 */
	public void eliminarMensaje(InfoMensaje m) throws Exception;
}