package icaro.aplicaciones.recursos.visualizacionAlta;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfUsoVisualizadorAlta extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorAlta() throws Exception;
	public void cerrarVisualizadorAlta() throws Exception;
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}
