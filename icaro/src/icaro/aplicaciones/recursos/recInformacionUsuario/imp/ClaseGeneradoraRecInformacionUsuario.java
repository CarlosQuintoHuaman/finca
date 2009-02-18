package icaro.aplicaciones.recursos.recInformacionUsuario.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad.recInfoUsuario.MensajeUsuario;
import icaro.aplicaciones.recursos.recInformacionUsuario.ExcepcionMensajes;
import icaro.aplicaciones.recursos.recInformacionUsuario.ItfUsoRecInformacionUsuario;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.rmi.RemoteException;
import java.util.Vector;



public class ClaseGeneradoraRecInformacionUsuario extends ImplRecursoSimple implements ItfUsoRecInformacionUsuario{
	
	public static String RUTA_MENSAJES = "./config/mensajesInfo.txt";
	
	private MensajesSistema mensajesSistema;
	
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
  		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 * @throws RemoteException
	 */
	public ClaseGeneradoraRecInformacionUsuario(String id)  {
		super(id);
		try{
	      	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
		mensajesSistema=new MensajesSistema(RUTA_MENSAJES);
		
	}

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
	public String obtenerMensaje(String idMensaje) throws ExcepcionMensajes {
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo mensaje "+idMensaje,
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.obtenerMensaje(idMensaje);
	}
	/**
	 * Devuelve la alternativa especificada del mensaje especificado
	 * @param idMensaje  identificador de mensaje
	 * @param numAlternativa  entero que indica la posicion de la alternativa
	 * @return Cuerpo o descripción del mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public String obtenerMensajeAlternativo(String idMensaje, int numAlternativa) throws ExcepcionMensajes {
		String alternativa ="";
		switch(numAlternativa){
			case 0: alternativa = "aleatoria";break;
			case 1: alternativa = "secuencial";break;
			case 2: alternativa = "siempre el primero";break;
		}
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo mensaje "+idMensaje+" tomándolo de manera: "+alternativa,
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.obtenerMensaje(idMensaje,numAlternativa);
	}
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
	public String obtenerMensajeParametrizado(String idMensaje, Vector parametros) throws ExcepcionMensajes {
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo mensaje "+idMensaje+" con parámetros ",
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.obtenerMensajeParametrizado(idMensaje,parametros);
	}
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
	public String obtenerMensajeAlternativoParametrizado(String idMensaje, Vector parametros, int numAlternativa) throws ExcepcionMensajes {
		String alternativa ="";
		switch(numAlternativa){
			case 0: alternativa = "aleatoria";break;
			case 1: alternativa = "secuencial";break;
			case 2: alternativa = "siempre el primero";break;
		}
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo mensaje "+idMensaje+" con parámetros. Tomándolo de manera: "+alternativa,
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.obtenerMensajeParametrizado(idMensaje,parametros,numAlternativa);
	}
	/**
	 * Guarda el mensaje de manera persistente
	 * @param mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void guardarMensaje(MensajeUsuario mensaje) throws ExcepcionMensajes{
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Guardando mensaje "+mensaje.id,
  				InfoTraza.NivelTraza.debug));
		mensajesSistema.guardarMensaje(mensaje);
	}
	/**
	 * Dado un identificador y la descripcion de una alternativa, añade dicha alternativa al mensaje 
	 * @param id identificador de mensaje
	 * @param alternativa
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void anyadirAlternativaAlMensaje(String id,String alternativa) throws ExcepcionMensajes{
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Añadiendo alternativa al mensaje "+id,
  				InfoTraza.NivelTraza.debug));
		mensajesSistema.anyadirAlternativaAlMensaje(id, alternativa);
	}
	/**
	 * Elimina el mensaje
	 * @param id identificador de mensaje
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void eliminarMensaje(String id) throws ExcepcionMensajes{
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Eliminando el mensaje "+id,
  				InfoTraza.NivelTraza.debug));
		mensajesSistema.eliminarMensaje(id);
	}
	/**
	 * Elimina la alternativa del mensaje cuyo identificador especificado
	 * @param id identificador de mensaje
	 * @param alternativa entero que indica la posicion de la alternativa a eliminar
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void eliminarAlternativaMensaje(String id, int numAlternativa) throws ExcepcionMensajes{
		String alternativa ="";
		switch(numAlternativa){
			case 0: alternativa = "aleatoria";break;
			case 1: alternativa = "secuencial";break;
			case 2: alternativa = "siempre el primero";break;
		}
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Eliminando alternativa '"+alternativa+"' del mensaje "+id,
  				InfoTraza.NivelTraza.debug));
		mensajesSistema.eliminarAlternativaDeMensaje(id, numAlternativa);
	}
	/**
	 * Devuelve todos los mensajes guardados
	 * @return
	 * @throws RemoteException
	 */
	public MensajeUsuario[] obtenerTodosLosMensajes(){
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo todos los mensajes",
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.obtenerTodosLosMensajes();
	}
	/**
	 * Devuelve una matriz con 4 columnas: identificador, tipo de mensaje, número de alternativa y descripción
	 * @return
	 * @throws RemoteException
	 */
	public Object [][] dameTablaDatosMostrar(){
		trazas.aceptaNuevaTraza(new InfoTraza("InfoUsuario",
  				"Obteniendo la tabla de datos",
  				InfoTraza.NivelTraza.debug));
		return mensajesSistema.dameTablaDatosMostrar();
	}
	

}
