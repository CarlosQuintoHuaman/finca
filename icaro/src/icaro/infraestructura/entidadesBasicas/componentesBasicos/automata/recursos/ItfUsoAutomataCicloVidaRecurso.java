package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.recursos;
/**
 * Interfaz de uso de un autómata de ciclo de vida de un Recurso
 *@author     Álvaro Rodríguez
 *@created    1 de Febrero de 2007
 */
public interface ItfUsoAutomataCicloVidaRecurso {
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
	public void transita(String input);

	/**
	 * 	Dice si el recurso está en estado activo, es decir, que puede
	 *  ejecutar métodos
	 * 
	 * @return está en estado activo o no
	 */
	public boolean estadoActivo();
	
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
}
