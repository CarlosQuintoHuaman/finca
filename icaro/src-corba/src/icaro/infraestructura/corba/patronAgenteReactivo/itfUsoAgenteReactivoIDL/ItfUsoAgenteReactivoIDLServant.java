package icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL;

import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.corba.entidadesbasicas.eventoStruct.EventoStruct;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.EventoInput;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.apache.commons.codec.binary.Base64;

import organizacion.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL._ItfUsoAgenteReactivoIDLImplBase;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:11
 */
public class ItfUsoAgenteReactivoIDLServant extends
		_ItfUsoAgenteReactivoIDLImplBase {

	private ItfUsoAgenteReactivo delegate;

	public ItfUsoAgenteReactivoIDLServant(ItfUsoAgenteReactivo delegate) {
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

}