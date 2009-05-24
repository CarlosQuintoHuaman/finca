package icaro.aplicaciones.recursos.persistenciaSecretaria;

import java.util.ArrayList;




import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.recursos.persistenciaSecretaria.imp.ErrorEnRecursoException;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaSecretaria extends ItfUsoRecursoSimple {
	//public ArrayList<InfoPaciente> getPacientes();
	
	
	/**
	 * Funcion EJEMPLO de como usar la BD
	 */
	public boolean compruebaUsuario(String usuario, String password) throws Exception;
	
	//public boolean compruebaNombreUsuario(String usuario) throws Exception;
	//public void insertaUsuario (String usuario,String password) throws Exception;
	
	/**
	 * funcion con la que obtenemos la lista de pacientes para cada medico de la lista que se le pasa por parametro en una fecha determinada
	 *  que se le pasa por parametro
	 * @param fecha		:: fecha concreta de las citas que queremos consultar
	 * @param lnombres	:: Lista de nombres de todos los medicos cuyas citas queremos obtener
	 * @return medicos	:: devuelve una arrayList de datosmedicos (nomMedico,Citas para ese medico especificando la fecha) 
	 */
	public ArrayList<DatosMedico> getCitas(String fecha, ArrayList<DatosMedico> l);
	
	/**
	 * Funcion con la que se obtiene la lista de medicos que tienen como secretaria la que se pasa por parametro
	 * @param s			:: Nombre de la secretaria de la que se quiere consultar sus medicos asociados
	 * @return medicos	:: ArrayList nombresMedicos
	 */
	public ArrayList<DatosMedico> getMedicos(String s);
	
	/**
	 * Funcion con la que se inserta en la bbdd una nueva cita
	 * @param datos			:: Datos de la cita que se quiere guardar en la bbdd. Si existe otra en la misma posicion se reemplazan
	 * @throws ErrorEnRecursoException 
	 */
	public void setCita(DatosCitaSinValidar datos) throws ErrorEnRecursoException;
	
	/**
	 * Funcion con la que se inserta en la bbdd un nuevo extra
	 * @param datos			:: Datos del extra que se quiere guardar en la bbdd. Si existe otra en la misma posicion se reemplazan
	 * @throws ErrorEnRecursoException 
	 */
	public void setExtra(DatosLlamada datosA,DatosLlamada datosP ) throws ErrorEnRecursoException;
	
	
	/**
	 * Funcion con la que se actualiza el estado de una cita
	 * @param cita		:: Datos de la cita que se debe actulizar
	 * @throws ErrorEnRecursoException 
	 */
	public void setEstado(DatosCitaSinValidar cita) throws ErrorEnRecursoException;
	
	
	/**
	 * Funcion con la que se borra el extra que se le pasa por parametro de la bbdd
	 * @param datos			:: Datos del extra que se quiere borrar la bbdd. 
	 * @throws ErrorEnRecursoException 
	 */
	public void borraExtra(DatosLlamada datos ) throws ErrorEnRecursoException;
	
	/**
	 * Funcion con la que se borra de la bbdd una cita
	 * @param datos			:: Datos de la cita que se quiere borrar de la bbdd.
	 * @throws ErrorEnRecursoException 
	 */
	public void borraCita(DatosCitaSinValidar datos) throws ErrorEnRecursoException;
	/**
	 * Su proposito es guardar los datos que se le pasan por parametro enviandoselos a la persistencia 
	 * @param s		:: Datos que incluyen todas las citas de todos los medicos para los que trabaja una secretaria concreta en un dia preestablecido
	 * @return 		:: devuelve cierto si todo ha ido bien y fallo en caso contrario
	 */
	public boolean meteAgenda(DatosSecretaria s);
	
	/**
	 * Esta funcion nos sirve para obtener las citas posteriores al dia de hoy de un determinado paciente que se le pasa por parametro
	 * @param nom			:: nombre del paciente
	 * @param telf			:: telefono => pasara a ser de la persistencia mas adelante
	 * @param fecha		:: fecha concreta sobre la q deseamos hacer la consulta que suele ser la fecha actual
	 * @return p 		:: ArrayList DatosCita (nombre Paciente, telefono, medico, fecha, hora)
	 */
	public ArrayList<DatosCitaSinValidar> getPaciente(String nom,String telf, String fecha);
	
	/**
	 * Esta funcion nos sirve para obtener todos los pacientes de la bbdd
	 */
	public ArrayList<InfoPaciente> getPacientes();
}