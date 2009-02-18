package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL;

import icaro.aplicaciones.recursos.visualizacion.alta.ItfUsoVisualizadorAlta;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDL;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoVisualizadorAltaIDL implements ItfUsoVisualizadorAlta {

	private ItfUsoVisualizadorAltaIDL stub;
	

	public AdaptadorItfUsoVisualizadorAltaIDL(ItfUsoVisualizadorAltaIDL stub){
		this.stub = stub;
	}


	/**
	 * 
	 * @exception Exception
	 */
	public void cerrarVisualizadorAlta()
	  throws Exception{
		stub.cerrarVisualizadorAlta();
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
	public void mostrarVisualizadorAlta()
	  throws Exception{
		stub.mostrarVisualizadorAlta();
	}

}