package icaro.aplicaciones.recursos.recTestPersonalidad;


import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

import java.rmi.RemoteException;




public interface ItfUsoRecTestPersonalidad extends ItfUsoRecursoSimple {
	/**
	 * Carga las preguntas del test en el sistema y devuelve las respuestas
	 * que pudiese haber anteriores
	 * @param preguntas
	 * @throws ExcepcionPreguntas
	 * @throws RemoteException
	 */
	public int[] cargaPreguntas(String[] preguntas) throws ExcepcionPreguntas;
	/**
	 * Almacena las respuestas de un grupo de preguntas
	 * @param preguntas
	 * @param respuestas
	 * @throws ExcepcionPreguntas
	 * @throws ExcepcionPreguntas 
	 * @throws RemoteException
	 */
	public void anotaRespuestas(String[] preguntas, int[] respuestas) throws ExcepcionPreguntas;
	/**
	 * Almacena las respuestas en un fichero
	 * @throws ExcepcionPreguntas
	 * @throws RemoteException
	 */
	public void guardarProgresoFichero() throws ExcepcionPreguntas;
	/**
	 * Devuelve el texto resultado del test
	 * @throws RemoteException
	 */
	public String textoResultado(String[] textos) throws ExcepcionPreguntas;
	/**
	 * Devuelve los resultados numéricos del test
	 * @throws RemoteException
	 */
	public int[] getResultados() throws ExcepcionPreguntas;
	/**
	 * Indica si se han respondido todas las respuestas del formulario
	 * @throws RemoteException
	 */
	public boolean todasRespondidas() throws ExcepcionPreguntas;
	/**
	 * Reinicia la estructura de las preguntas
	 * @throws RemoteException
	 */
	public void reinicia() throws ExcepcionPreguntas;
}