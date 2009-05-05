package icaro.infraestructura.entidadesBasicas.interfaces;

import icaro.infraestructura.entidadesBasicas.EventoRecAgte;

public interface ItfEventos {
	
	/**
	 *  Inserta un nuevo evento en la percepci�n del agente
	 *
	 *@param  ev  Evento que se inserta en el agente
	 */
	public void aceptaEvento(EventoRecAgte ev) throws Exception;

}
