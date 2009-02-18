package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.recursos;
/**
 * Interfaz de uso de un aut�mata de ciclo de vida de un Recurso
 *@author     �lvaro Rodr�guez
 *@created    1 de Febrero de 2007
 */
public interface ItfUsoAutomataCicloVidaRecurso {
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
	public void transita(String input);

	/**
	 * 	Dice si el recurso est� en estado activo, es decir, que puede
	 *  ejecutar m�todos
	 * 
	 * @return est� en estado activo o no
	 */
	public boolean estadoActivo();
	
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
}
