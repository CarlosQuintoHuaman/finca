package icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL;

import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.corba.entidadesbasicas.eventoStruct.EventoStruct;
import icaro.infraestructura.corba.entidadesbasicas.mensajeStruct.MensajeStruct;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.EventoInput;
import icaro.infraestructura.patronAgenteCognitivoSimple.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivoSimple.procesadorconocimiento.entidadesbasicas.MensajeAgente;



public class IPercepcionAgenteCognitivoIDLServant extends _IPercepcionAgenteCognitivoIDLImplBase {
	private ItfUsoAgenteCognitivo delegate;

	public IPercepcionAgenteCognitivoIDLServant(ItfUsoAgenteCognitivo delegate) {
		this.delegate = delegate;
	}

	

	
	public void aceptaEvento(EventoStruct ev) {
		try {
			Class claseEvento = Class.forName(ev.clase);
			if (claseEvento.equals(EventoInput.class)) {
				EventoInput eventoInput = (EventoInput) Serializer.deserialize(ev.contenido);

				delegate.aceptaEvento(eventoInput);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void aceptaMensaje(MensajeStruct msg) {
		try {
			Class claseMensaje = Class.forName(msg.clase);
			if (claseMensaje.equals(MensajeAgente.class)) {
				MensajeAgente mensaje = (MensajeAgente) Serializer.deserialize(msg.contenido);

				delegate.aceptaMensaje(mensaje);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
