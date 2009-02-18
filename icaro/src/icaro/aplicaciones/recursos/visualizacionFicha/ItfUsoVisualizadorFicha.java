package icaro.aplicaciones.recursos.visualizacionFicha;


import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosAgenda;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorFicha extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorFicha(String nombreAgente,String tipo) throws Exception;
	public void mostrarVisualizadorFicha(String nombreAgente,String tipo, DatosAgenda datos) throws Exception;

    public void cerrarVisualizadorFicha() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}