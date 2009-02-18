package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL;

import icaro.aplicaciones.recursos.visualizacion.acceso.ItfUsoVisualizadorAcceso;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL._ItfUsoVisualizadorAccesoIDLImplBase;


/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:13
 */
public class ItfUsoVisualizadorAccesoIDLServant extends _ItfUsoVisualizadorAccesoIDLImplBase {

	private ItfUsoVisualizadorAcceso delegate;
	

	public ItfUsoVisualizadorAccesoIDLServant(ItfUsoVisualizadorAcceso delegate){
		this.delegate = delegate;
	}

	

	
	public void cerrarVisualizadorAcceso() {
		try {
			delegate.cerrarVisualizadorAcceso();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void mostrarMensajeAviso(String titulo, String mensaje) {
		try {
			delegate.mostrarMensajeAviso(titulo, mensaje);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void mostrarMensajeError(String titulo, String mensaje) {
		try {
			delegate.mostrarMensajeError(titulo, mensaje);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void mostrarMensajeInformacion(String titulo, String mensaje) {
		try {
			delegate.mostrarMensajeInformacion(titulo, mensaje);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void mostrarVisualizadorAcceso(String agente, String tipo) {
		try {
			delegate.mostrarVisualizadorAcceso(agente, tipo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}