package icaro.aplicaciones.recursos.recInformacionUsuario;


import icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad.recInfoUsuario.MensajeUsuario;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

import java.rmi.RemoteException;
import java.util.Vector;



public interface ItfUsoRecInformacionUsuario  extends ItfUsoRecursoSimple {
	/**
	 * Guarda el mensaje de manera persistente
	 * @param mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void guardarMensaje(MensajeUsuario mensaje) throws ExcepcionMensajes;
	/**
	 * Dado un identificador y la descripcion de una alternativa, añade dicha alternativa al mensaje 
	 * @param id identificador de mensaje
	 * @param alternativa
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void anyadirAlternativaAlMensaje(String id,String alternativa) throws ExcepcionMensajes;
	/**
	 * Elimina el mensaje
	 * @param id identificador de mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void eliminarMensaje(String id) throws ExcepcionMensajes;
	/**
	 * Elimina la alternativa del mensaje cuyo identificador especificado
	 * @param id identificador de mensaje
	 * @param alternativa entero que indica la posicion de la alternativa a eliminar
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void eliminarAlternativaMensaje(String id, int alternativa) throws ExcepcionMensajes;
	/**
	 * Devuelve la descripción cuyo identificador se pasa por parametro, según el tipo de mensaje, si tiene varias alternativas
	 * las devolverá en un orden u otro
	 * primero: siempre la primer
	 * aleatorio: orden aleatorio
	 * secuencia: orden secuencial
	 * @param idMensaje identificador de mensaje
	 * @return Cuerpo o descripción del mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public String obtenerMensaje(String idMensaje) throws ExcepcionMensajes;
	/**
	 * Devuelve la alternativa especificada del mensaje especificado
	 * @param idMensaje  identificador de mensaje
	 * @param numAlternativa  entero que indica la posicion de la alternativa
	 * @return Cuerpo o descripción del mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public String obtenerMensajeAlternativo(String idMensaje, int numAlternativa) throws ExcepcionMensajes;
	/**
	 * Devuelve la descripción cuyo identificador se pasa por parametro, según el tipo de mensaje, si tiene varias alternativas
	 * las devolverá en un orden u otro
	 * primero: siempre la primer
	 * aleatorio: orden aleatorio
	 * secuencia: orden secuencial
	 * 
	 * Se pasa un vector de cadenas que sustituiran los parametros del mensaje, en el mismo orden. Tienen que ser compatibles en número
	 * @param idMensaje  identificador de mensaje
	 * @param parametros parametros para el mensaje
	 * @return Cuerpo o descripción del mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public String obtenerMensajeParametrizado(String idMensaje, Vector parametros) throws ExcepcionMensajes;
	/**
	 * Devuelve la alternativa especificada del mensaje especificado
	 * Se pasa un vector de cadenas que sustituiran los parametros del mensaje, en el mismo orden. Tienen que ser compatibles en número
	 * @param idMensaje identificador de mensaje
	 * @param parametros parametros para el mensaje
	 * @param numAlternativa  entero que indica la posicion de la alternativa a eliminar
	 * @return Cuerpo o descripción del mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public String obtenerMensajeAlternativoParametrizado(String idMensaje, Vector parametros, int numAlternativa) throws ExcepcionMensajes;
	/**
	 * Devuelve todos los mensajes guardados
	 * @return
	 * @throws RemoteException
	 */
	public MensajeUsuario[] obtenerTodosLosMensajes();
	/**
	 * Devuelve una matriz con 4 columnas: identificador, tipo de mensaje, número de alternativa y descripción
	 * @return
	 * @throws RemoteException
	 */
	public Object [][] dameTablaDatosMostrar();
	
}
