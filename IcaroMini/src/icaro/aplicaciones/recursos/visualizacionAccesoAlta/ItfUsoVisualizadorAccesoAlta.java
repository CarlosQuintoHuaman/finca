package icaro.aplicaciones.recursos.visualizacionAccesoAlta;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfUsoVisualizadorAccesoAlta extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorAccesoAlta() throws Exception;
	public void cerrarVisualizadorAccesoAlta() throws Exception;
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}