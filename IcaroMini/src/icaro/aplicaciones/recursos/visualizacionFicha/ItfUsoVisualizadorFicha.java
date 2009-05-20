package icaro.aplicaciones.recursos.visualizacionFicha;



import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorFicha extends ItfUsoRecursoSimple{
	/**
	 * Su proposito es pintar la ventana
	 */
	public void mostrarVisualizadorFicha(String nombreAgente,String tipo) throws Exception;
    /**
     * Generamos un evento para el automataFicha con input: 'mostrarVentanaFicha', con 'datos' como parametro para la accion semantica 
     * que le corresponde 'pintaVentanaFicha'. Este es un evento con origen VisualizacionSecretaria y destino AgenteFicha.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la ficha (nombre, telefono, hora,fecha, crear)
     */
	public void mostrarVisualizadorFicha(String nombreAgente,String tipo, DatosCitaSinValidar datos) throws Exception;
	
	   /**
     * Generamos un evento para el automataFicha con input: 'mostrarVentanaFicha', con 'datos' como parametro para la accion semantica 
     * que le corresponde 'pintaVentanaFicha'. Este es un evento con origen VisualizacionSecretaria y destino AgenteFicha.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro buscando la informacion de la ficha en la BBDD 
     * @param datos		:: Datos con los que rellenar la ficha (Paciente y descripcion)
     */
	public void mostrarVisualizadorFichaBD(String nombreAgente,String tipo, DatosFicha datos) throws Exception;
	/**
	 * Su proposito es cerrar la ventana
	 */
    public void cerrarVisualizadorFicha() throws Exception;
    
    //Funciones que nos muestran ventanas con mensajes de error/informacion/aviso
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public boolean mostrarMensajeAvisoC(String titulo,String mensaje);
  	public boolean mostrarMensajeAvisoCA(String titulo,String mensaje);
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}