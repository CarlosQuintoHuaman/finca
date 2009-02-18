package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL;

import icaro.aplicaciones.recursos.visualizacion.alta.ItfUsoVisualizadorAlta;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL._ItfUsoVisualizadorAltaIDLImplBase;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:13
 */
public class ItfUsoVisualizadorAltaIDLServant extends _ItfUsoVisualizadorAltaIDLImplBase {

	private ItfUsoVisualizadorAlta delegate;
	

	public ItfUsoVisualizadorAltaIDLServant(ItfUsoVisualizadorAlta delegate){
		this.delegate = delegate;
	}

	

	
	public void cerrarVisualizadorAlta() {
		try {
			delegate.cerrarVisualizadorAlta();
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

	
	public void mostrarVisualizadorAlta() {
		try {
			delegate.mostrarVisualizadorAlta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}