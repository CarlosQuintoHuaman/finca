package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Hashtable;

import org.apache.log4j.Logger;


	
/**
 * Clase base de la que deben heredar todas las acciones sem�nticas de los agentes reactivos.
 *
 * @author Jorge M. Gonz�lez Mart�n
 * @version 2.0
 */

public abstract class AccionesSemanticasAgenteReactivo {

  /**
   * Itf de uso del agente al que pertenecen estas acciones para permitir la
   * realimentaci�n de inputs
   */
  protected ItfUsoAgenteReactivo itfUsoAgente;
  protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
  protected String nombreAgente;

  public void setNombreAgente(String nombreAgente) {
	this.nombreAgente = nombreAgente;
}

/**
   * Pizarra para almacenar par�metros enviados por la clase que implementa el interfaz de uso
   * del agente reactivo
   */
  protected Hashtable<String,Object> tablaParametros;
  
  protected Logger logger;
  protected ItfUsoRecursoTrazas trazas;
  
  /**
   * Interfaz de uso del Repositorio de Interfaces
   */
  public ItfUsoRepositorioInterfaces itfUsoRepositorio;

  /**
   * Es necesario un constructor sin par�metros
   */
  public AccionesSemanticasAgenteReactivo() {
	
    this.itfUsoRepositorio = RepositorioInterfaces.instance();
    try{
    	trazas = (ItfUsoRecursoTrazas)itfUsoRepositorio.obtenerInterfaz(
    			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
    }catch(Exception e){
    	System.out.println("No se pudo usar el recurso de trazas");
    }
  }

  /**
   * Fija el interfaz de uso del agente para que pueda realimentarse la tabla con inputs
   * procidos por las acciones
   * @param itfUsoA
   */
  public void setItfUsoAgenteReactivo(ItfUsoAgenteReactivo itfUsoA)
  {
    this.itfUsoAgente = itfUsoA;
  }
  
  public void setItfUsoGestorAReportar(ItfUsoAgenteReactivo itfUsoA)
  {
    this.itfUsoGestorAReportar = itfUsoA;
  }
  
  public void setItfUsoRepositorioInterfaces(ItfUsoRepositorioInterfaces itfUsoR)
  {
    this.itfUsoRepositorio = itfUsoR;
  }
  public void setLogger(Logger logger){
	  this.logger = logger;
  }


  /**
   * Fija un par�metro para que sea recogido por alguna acci�n sem�ntica
   * @param nombre Nombre del par�metro
   * @param valor Valor del par�metro
   */
  protected synchronized void fijarParametro(String nombre, Object valor)
  {
    if (!"".equals(nombre))
      this.tablaParametros.put(nombre,valor);
  }

  /**
   * Obtiene el par�metro
   * @param nombre Nombre del par�metro
   * @return Object Valor del par�metro o null si el par�metro no existe en la tabla.
   */
  protected Object obtenerParametro(String nombre)
  {
    if (!"".equals(nombre) && this.tablaParametros.containsKey(nombre))
      return this.tablaParametros.get(nombre);
    //else
    System.err.println("Se intent� acceder al par�metro: "+nombre+", pero no estaba definido en la tabla de par�metros");
    return null;
  }


  /**
   * Esta acci�n no hace nada, sirve para transitar de estado sin ejecutar nada.
   */
  public void nula() {}
  /**
   * Esta acci�n no hace nada, sirve para transitar de estado sin ejecutar nada.
   */
  public void vacio() {}
  
  /**
   * Fija la configuraci�n 
   * @param conf
   */
  /*TODO public void setConfiguracionGlobal (ConfiguracionGlobal conf)
  {
    this.config=conf;
  }*/
  
  public abstract void clasificaError();
  /**
   * Este m�todo debe ser implementado por todos los agentes para 
   * realizar el necesario tratamiento de los errores.
   */

public String getNombreAgente() {
	return nombreAgente;
}

 public void generarTimeOut(long milis, String nombre, String origen, String destino) {
		EventoTimeOut timeout = new EventoTimeOut(milis, nombre, origen,destino, this.itfUsoRepositorio);
		logger.debug("Generando evento de timeout de "+ milis + " milisegundos");
		timeout.start();
 }
}