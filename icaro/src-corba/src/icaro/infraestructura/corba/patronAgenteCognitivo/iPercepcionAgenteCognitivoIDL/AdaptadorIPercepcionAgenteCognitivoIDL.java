package icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL;

import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.corba.entidadesbasicas.eventoStruct.EventoStruct;
import icaro.infraestructura.corba.entidadesbasicas.mensajeStruct.MensajeStruct;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.EventoInput;
import icaro.infraestructura.patronAgenteCognitivoSimple.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivoSimple.procesadorconocimiento.entidadesbasicas.MensajeAgente;


public class AdaptadorIPercepcionAgenteCognitivoIDL implements ItfUsoAgenteCognitivo{
	
	private IPercepcionAgenteCognitivoIDL stub;

public AdaptadorIPercepcionAgenteCognitivoIDL(IPercepcionAgenteCognitivoIDL stub) {
	this.stub = stub;
}

/**
 * Inserta un nuevo evento en la percepción del agente
 * 
 * @param ev    Evento que se inserta en el agente
 * @exception RemoteException
 */
	public void aceptaEvento(EventoInput ev) {
	EventoStruct evStruct = new EventoStruct();
	
		evStruct.clase = EventoInput.class.getName();
		evStruct.contenido = Serializer.serialize(ev);
		stub.aceptaEvento(evStruct);
	
	}

	
	public void aceptaMensaje(MensajeAgente mensaje) {
		MensajeStruct msgStruct = new MensajeStruct();
		msgStruct.clase = MensajeAgente.class.getName();
		msgStruct.contenido = Serializer.serialize(mensaje);
		stub.aceptaMensaje(msgStruct);
	}

}
