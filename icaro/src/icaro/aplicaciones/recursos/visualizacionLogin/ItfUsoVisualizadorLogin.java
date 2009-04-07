package icaro.aplicaciones.recursos.visualizacionLogin;


import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoVisualizadorLogin extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorLogin(String nombreAgente,String tipo) throws Exception;

    public void cerrarVisualizadorLogin() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}