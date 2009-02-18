package icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL;


import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.corba.entidadesbasicas.eventoStruct.EventoStruct;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.EventoInput;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

import org.apache.commons.codec.binary.Base64;

import organizacion.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL.ItfUsoAgenteReactivoIDL;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoAgenteReactivoIDL implements ItfUsoAgenteReactivo {

	private ItfUsoAgenteReactivoIDL stub;
	

	public AdaptadorItfUsoAgenteReactivoIDL(ItfUsoAgenteReactivoIDL stub){
		this.stub = stub;

	}

	
	/**
	 * Inserta un nuevo evento en la percepción del agente
	 * 
	 * @param ev    Evento que se inserta en el agente
	 * @exception RemoteException
	 */
	public void aceptaEvento(Object ev) {
		EventoStruct evStruct = new EventoStruct();
		if (ev instanceof EventoInput) {
			evStruct.clase = EventoInput.class.getName();
			evStruct.contenido = Serializer.serialize(ev);
			stub.aceptaEvento(evStruct);
		}
	}

}