package icaro.aplicaciones.recursos.visualizacionMensajeria;


import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorMensajeria extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorMensajeNuevo(String nombreAgente, String tipo, String usuario) throws Exception;

      public void cerrarVisualizadorMensajeNuevo() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}