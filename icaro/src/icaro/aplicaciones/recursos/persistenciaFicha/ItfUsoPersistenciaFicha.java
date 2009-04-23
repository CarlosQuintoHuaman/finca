package icaro.aplicaciones.recursos.persistenciaFicha;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaFicha extends ItfUsoRecursoSimple {
	/**
	 * Su proposito es guardar los datos que se le pasan por parametro enviandoselos a la persistencia 
	 * @param original, fichaN		:: Datos que incluye la ficha de un paciente (original y modificada respectivamente)
	 * @return 						:: devuelve cierto si todo ha ido bien y fallo en caso contrario
	 */
	public boolean meteFicha(DatosFicha original, DatosFicha fichaN);
	
	/**
	 * Su proposito es borrar los datos que se le pasan por parametro enviandoselos a la persistencia 
	 * @param ficha		:: Datos que incluye la ficha de un paciente
	 * @return 		:: devuelve cierto si todo ha ido bien y fallo en caso contrario
	 */
	public boolean borraFicha(DatosFicha ficha);
	
	/**
	 * Su proposito buscar una ficha con los datos que se le pasan por parametro enviandoselos a la persistencia 
	 * @param datos		:: Datos que incluye datos para buscar una ficha
	 * @return 		:: devuelve la ficha si esta y una ficha vacia si no
	 */
	public DatosFicha getFicha(DatosCita datos);
	
	

}