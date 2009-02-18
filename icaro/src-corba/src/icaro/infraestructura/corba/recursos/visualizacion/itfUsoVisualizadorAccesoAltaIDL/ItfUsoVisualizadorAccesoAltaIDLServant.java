package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL;

import icaro.aplicaciones.recursos.visualizacion.accesoAlta.ItfUsoVisualizadorAccesoAlta;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL._ItfUsoVisualizadorAccesoAltaIDLImplBase;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:13
 */
public class ItfUsoVisualizadorAccesoAltaIDLServant extends _ItfUsoVisualizadorAccesoAltaIDLImplBase {

	private ItfUsoVisualizadorAccesoAlta delegate;
	

	public ItfUsoVisualizadorAccesoAltaIDLServant(ItfUsoVisualizadorAccesoAlta delegate){
		this.delegate = delegate;
	}

	

	
	public void cerrarVisualizadorAccesoAlta() {
		try {
			delegate.cerrarVisualizadorAccesoAlta();
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

	
	public void mostrarVisualizadorAccesoAlta() {
		try {
			delegate.mostrarVisualizadorAccesoAlta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}