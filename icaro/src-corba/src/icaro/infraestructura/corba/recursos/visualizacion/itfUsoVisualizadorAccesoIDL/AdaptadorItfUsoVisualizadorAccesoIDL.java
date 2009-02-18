package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL;

import icaro.aplicaciones.recursos.visualizacion.acceso.ItfUsoVisualizadorAcceso;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDL;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoVisualizadorAccesoIDL implements ItfUsoVisualizadorAcceso {

	private ItfUsoVisualizadorAccesoIDL stub;
	

	public AdaptadorItfUsoVisualizadorAccesoIDL(ItfUsoVisualizadorAccesoIDL stub){
		this.stub = stub;
	}


	/**
	 * 
	 * @exception Exception
	 */
	public void cerrarVisualizadorAcceso()
	  throws Exception{
		stub.cerrarVisualizadorAcceso();
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
	public void mostrarVisualizadorAcceso(String nombreAgente, String tipo)
	  throws Exception{
		stub.mostrarVisualizadorAcceso(nombreAgente, tipo);
	}

}