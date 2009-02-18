package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL;

import icaro.aplicaciones.recursos.visualizacion.accesoAlta.ItfUsoVisualizadorAccesoAlta;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL.ItfUsoVisualizadorAccesoAltaIDL;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoVisualizadorAccesoAltaIDL implements ItfUsoVisualizadorAccesoAlta {

	private ItfUsoVisualizadorAccesoAltaIDL stub;
	

	public AdaptadorItfUsoVisualizadorAccesoAltaIDL(ItfUsoVisualizadorAccesoAltaIDL stub){
		this.stub = stub;
	}


	/**
	 * 
	 * @exception Exception
	 */
	public void cerrarVisualizadorAccesoAlta()
	  throws Exception{
		stub.cerrarVisualizadorAccesoAlta();
	}

	/**
	 * 
	 * @param titulo
	 * @param mensaje
	 * @exception Exception
	 */
	public void mostrarMensajeAviso(String titulo, String mensaje)
	  throws Exception{
		stub.mostrarMensajeAviso(titulo, mensaje);
	}

	/**
	 * 
	 * @param titulo
	 * @param mensaje
	 * @exception Exception
	 */
	public void mostrarMensajeError(String titulo, String mensaje)
	  throws Exception{
		stub.mostrarMensajeError(titulo, mensaje);
	}

	/**
	 * 
	 * @param titulo
	 * @param mensaje
	 * @exception Exception
	 */
	public void mostrarMensajeInformacion(String titulo, String mensaje)
	  throws Exception{
		stub.mostrarMensajeInformacion(titulo, mensaje);
	}

	/**
	 * 
	 * @exception Exception
	 */
	public void mostrarVisualizadorAccesoAlta()
	  throws Exception{
		stub.mostrarVisualizadorAccesoAlta();
	}

}