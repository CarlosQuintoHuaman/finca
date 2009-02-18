package icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL;

import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import organizacion.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL._ItfGestionAgenteReactivoIDLImplBase;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:09
 */
public class ItfGestionAgenteReactivoIDLServant extends
		_ItfGestionAgenteReactivoIDLImplBase {

	private ItfGestionAgenteReactivo delegate;

	public ItfGestionAgenteReactivoIDLServant(ItfGestionAgenteReactivo delegate) {
		this.delegate = delegate;
	}
	
	public void setGestorAReportar(String nombreGestor, String conjuntoEventos) {
		Set<Object> ctoEventos = (Set<Object>)Serializer.deserialize(conjuntoEventos);
		delegate.setGestorAReportar(nombreGestor, ctoEventos);
	}

	
	public void arranca() {
		delegate.arranca();
	}

	
	public void continua() {
		delegate.continua();

	}

	
	public int obtenerEstado() {
		return delegate.obtenerEstado();
	}

	
	public void para() {
		delegate.para();
	}

	
	public void termina() {
		delegate.termina();
	}

}