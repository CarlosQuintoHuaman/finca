package icaro.aplicaciones.recursos.visualizacionAdmin;


import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoVisualizadorAdmin extends ItfUsoRecursoSimple{

	/**
	 * Muestra la ventana de administrador
	 * @param nombreAgente
	 * @param tipo
	 * @throws Exception
	 */
	public void mostrarVisualizadorAdmin(String nombreAgente,String tipo) throws Exception;

	/**
	 * Cierra la ventana de administrador
	 * @throws Exception
	 */
	public void cerrarVisualizadorAdmin() throws Exception;
	
	public void optimizarBD() throws Exception;
	public void crearBD() throws Exception;
	public void resetearBD() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}