package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata;

import org.apache.log4j.Logger;

/**
 *  Interfaz de uso de un autómata
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */

public interface ItfUsoAutomata {
	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    está en estado final o no
	 */
	public boolean esEstadoFinal();


	/**
	 *  Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 *  transición correspondiente
	 *
	 *@param  input  Input a procesar
	 */
	public void procesaInput(String input, Object[] parametros);


	/**
	 *  Imprime la tabla de estados y el estado actual del autómata
	 *
	 *@return    Cadena con la información
	 */
	public String toString();


	/**
	 *  Devuelve el autómata a su estado inicial
	 */
	public void volverAEstadoInicial();
	
	public void cambiaEstado(String estado);
	
	public void setLogger(Logger logger);
		
	public Logger getLogger();
	
}
