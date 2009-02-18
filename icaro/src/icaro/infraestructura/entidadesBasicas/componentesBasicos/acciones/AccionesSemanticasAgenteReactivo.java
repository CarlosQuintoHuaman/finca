package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Hashtable;

import org.apache.log4j.Logger;


	
/**
 * Clase base de la que deben heredar todas las acciones semánticas de los agentes reactivos.
 *
 * @author Jorge M. González Martín
 * @version 2.0
 */

public abstract class AccionesSemanticasAgenteReactivo {

  /**
   * Itf de uso del agente al que pertenecen estas acciones para permitir la
   * realimentación de inputs
   */
  protected ItfUsoAgenteReactivo itfUsoAgente;
  protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
  protected String nombreAgente;

  public void setNombreAgente(String nombreAgente) {
	this.nombreAgente = nombreAgente;
}

/**
   * Pizarra para almacenar parámetros enviados por la clase que implementa el interfaz de uso
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
   * Es necesario un constructor sin parámetros
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
   * Fija un parámetro para que sea recogido por alguna acción semántica
   * @param nombre Nombre del parámetro
   * @param valor Valor del parámetro
   */
  protected synchronized void fijarParametro(String nombre, Object valor)
  {
    if (!"".equals(nombre))
      this.tablaParametros.put(nombre,valor);
  }

  /**
   * Obtiene el parámetro
   * @param nombre Nombre del parámetro
   * @return Object Valor del parámetro o null si el parámetro no existe en la tabla.
   */
  protected Object obtenerParametro(String nombre)
  {
    if (!"".equals(nombre) && this.tablaParametros.containsKey(nombre))
      return this.tablaParametros.get(nombre);
    //else
    System.err.println("Se intentó acceder al parámetro: "+nombre+", pero no estaba definido en la tabla de parámetros");
    return null;
  }


  /**
   * Esta acción no hace nada, sirve para transitar de estado sin ejecutar nada.
   */
  public void nula() {}
  /**
   * Esta acción no hace nada, sirve para transitar de estado sin ejecutar nada.
   */
  public void vacio() {}
  
  /**
   * Fija la configuración 
   * @param conf
   */
  /*TODO public void setConfiguracionGlobal (ConfiguracionGlobal conf)
  {
    this.config=conf;
  }*/
  
  public abstract void clasificaError();
  /**
   * Este método debe ser implementado por todos los agentes para 
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