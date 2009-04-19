package icaro.aplicaciones.recursos.persistenciaMedico;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoPersistenciaMedico extends ItfUsoRecursoSimple {
	/**
	 * Obtiene la lista de todos los pacientes
	 * @return ArrayList con objetos InfoPaciente
	 */
	public ArrayList<InfoPaciente> getPacientes(String usuario);
	
	/**
	 * Obtiene las citas de un medico
	 * @return ArrayList con objetos InfoCita
	 */
	public ArrayList<InfoCita> getCitas(String usuario);
	
}