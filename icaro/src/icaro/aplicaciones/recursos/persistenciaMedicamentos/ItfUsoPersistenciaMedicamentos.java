package icaro.aplicaciones.recursos.persistenciaMedicamentos;

import java.sql.Timestamp;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoPersistenciaMedicamentos extends ItfUsoRecursoSimple {
	/**
	 * Obtiene todos los medicamentos de la BD
	 * @return ArrayList con todos los medicamentos
	 * @throws Exception
	 */
	public ArrayList<InfoMedicamento> getMedicamentos() throws Exception;
	
	/**
	 * Obtiene los medicamentos para una visita en concreto
	 * @param p Nombre de usuario
	 * @param f Fecha de la visita
	 * @return ArrayList con los medicamentos
	 * @throws Exception
	 */
	public ArrayList<InfoMedicamento> getMedicamentos(String p, Timestamp f) throws Exception;
	
	/**
	 * Inserta un medicamento en la BD
	 * @param m InfoMedicamento con los datos del medicamento
	 * @throws Exception
	 */
	public void insertaMedicamento(InfoMedicamento m) throws Exception;
	
	/**
	 * Asigna un medicamento a un paciente en una visita. Es decir, añade un medicamento
	 * a la receta de una visita
	 * @param p Nombre de ususario
	 * @param m Medicamento
	 * @param f Fecha de la visita
	 * @throws Exception
	 */
	public void asignaMedicamento(String p, InfoMedicamento m, Timestamp f) throws Exception;
	
	/**
	 * Borra un medicamento de la BD
	 * @param p Nombre de usaurio
	 * @param t Fecha de la visita
	 * @param m Datos Medicamento
	 * @throws Exception
	 */
	public void borrarMedicamento(String p, Timestamp t, InfoMedicamento m) throws Exception;
}