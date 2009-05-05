package icaro.aplicaciones.recursos.persistenciaHistorial;

import java.sql.Timestamp;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */

public interface ItfUsoPersistenciaHistorial extends ItfUsoRecursoSimple {
	/**
	 * Obtiene el historial completo de un paciente
	 * @param paciente Nombre de usuario del paciente
	 * @return ArrayList con las visitas que componen el historial
	 */
	public ArrayList<InfoVisita> getHistorial(String paciente);
	
	
	/**
	 * Obtiene una visita en concreto
	 * @param paciente Nombre de usuario del paciente
	 * @param fecha Fecha de la visita
	 * @return Un objeto InfoVisita con los datos
	 * 		   Si no esta la visita en la BD el objeto visita tendra todos los campos vacios menos
	 * 		   el nombre de usuario y la fecha
	 */
	public InfoVisita getHistorial(String paciente, Timestamp fecha);
	
	/**
	 * Obtiene todas las pruebas de un paciente para una visita concreta
	 * @param paciente Nombre de usuario
	 * @param fecha Fecha de la visita
	 * @return ArrayList con todas las pruebas
	 */
	public ArrayList<InfoPrueba> getPruebas(String paciente, Timestamp fecha);
	
	/**
	 * Guarda una visita en la BD. Si la visita ya existe en la BD la actualiza con los nuevos datos.
	 * Si no existe la crea nueva.
	 * @param v Objeto InfoVisita con los datos
	 */
	public void setVisita(InfoVisita v);
	
	/**
	 * Guarda una prueba en la BD
	 * @param p Objeto InfoPrueba con los datos
	 */
	public void setPrueba(InfoPrueba p);
	
	/**
	 * Borra una prueba de la BD
	 * @param p Objeto InfoPrueba de la prueba a borrar
	 */
	public void borrarPrueba(InfoPrueba p);
	
	/**
	 * Borra un medicamento de la BD
	 * @param m Objeto InfoMedicamento del medicamento a borrar
	 */
	public void borrarMedicamento(InfoMedicamento p) throws Exception;
}