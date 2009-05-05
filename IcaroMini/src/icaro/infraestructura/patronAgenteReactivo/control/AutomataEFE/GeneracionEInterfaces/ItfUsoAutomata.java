package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.GeneracionEInterfaces;

import org.apache.log4j.Logger;

/**
 * Interfaz de uso de un aut�mata
 * @author      Felipe Polo
 * @created     3 de Diciembre de 2007
 */

public interface ItfUsoAutomata {
	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    est� en estado final o no
	 */
	public boolean esEstadoFinal();


	/**
	 *  Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 *  transici�n correspondiente
	 *
	 *@param  input  Input a procesar
	 */
	public void procesaInput(String input, Object[] parametros);


	/**
	 *  Imprime la tabla de estados y el estado actual del aut�mata
	 *
	 *@return    Cadena con la informaci�n
	 */
	public String toString();


	/**
	 *  Devuelve el aut�mata a su estado inicial
	 */
	public void volverAEstadoInicial();
	
	public void cambiaEstado(String estado);
	
	/**
	 * @param logger
	 * @uml.property  name="logger"
	 */
	public void setLogger(Logger logger);
		
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  
	 */
	public Logger getLogger();
	
}
