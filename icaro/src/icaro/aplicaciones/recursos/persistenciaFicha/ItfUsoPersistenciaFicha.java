package icaro.aplicaciones.recursos.persistenciaFicha;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaFicha extends ItfUsoRecursoSimple {
	/**
	 * Su proposito es guardar los datos que se le pasan por parametro enviandoselos a la persistencia 
	 * @param s		:: Datos que incluye la ficha de un paciente
	 * @return 		:: devuelve cierto si todo ha ido bien y fallo en caso contrario
	 */
	public boolean meteFicha(DatosFicha original, DatosFicha fichaN);
	
	

}