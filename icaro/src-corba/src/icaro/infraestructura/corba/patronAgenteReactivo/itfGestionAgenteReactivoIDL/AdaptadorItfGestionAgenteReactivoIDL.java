package icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL;
import icaro.infraestructura.corba.Serializer;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import organizacion.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDL;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:04
 */
public class AdaptadorItfGestionAgenteReactivoIDL implements ItfGestionAgenteReactivo {

	private ItfGestionAgenteReactivoIDL stub;
	

	public AdaptadorItfGestionAgenteReactivoIDL(ItfGestionAgenteReactivoIDL stub){
		this.stub = stub;
	}


	/**
	 * Inicializa y prepara el elemento para recibir órdenes o información
	 * 
	 */
	public void arranca() {
		stub.arranca();
	}

	
	/**
	 * Reanuda la disponibilidad del elemento si estaba parado, si no lo estaba no
	 * hace nada. Esta operacion es OPCIONAL
	 * 
	 */
	public void continua() {
		stub.continua();
	}

	/**
	 * Comprueba el estado actual del elemento
	 * @return                      Estado en el que se encuentra el elemento
	 * 
	 */
	public int obtenerEstado() {
	  return stub.obtenerEstado();
	}

	/**
	 * Detiene el servicio del elemento momentáneamente Esta operacion es OPCIONAL
	 * 
	 */
	public void para() {
		stub.para();
	}

	/**
	 * Acaba con el uso del elemento y destruye los recursos que estuviesen ligados a
	 * él
	 * @exception RemoteException Se ha producido un error de RMI
	 */
	public void termina() {
		stub.termina();
	}

	
	public void setGestorAReportar(String nombreGestor, Set<Object> conjuntoEventos) {
		String ctoEventos = Serializer.serialize(conjuntoEventos);
		stub.setGestorAReportar(nombreGestor, ctoEventos);
	}
	
	





}