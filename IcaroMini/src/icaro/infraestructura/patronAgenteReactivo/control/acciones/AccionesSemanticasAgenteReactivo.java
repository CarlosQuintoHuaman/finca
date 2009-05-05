package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
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
 * Itf de uso del agente al que pertenecen estas acciones para permitir la realimentaci�n de inputs
 * @uml.property  name="itfUsoAgente"
 * @uml.associationEnd  
 */
  public ItfUsoAgenteReactivo itfUsoPropiadeEsteAgente;
  /**
 * @uml.property  name="itfUsoGestorAReportar"
 * @uml.associationEnd  
 */
public ItfUsoAgenteReactivo itfUsoGestorAReportar;
  /**
 * @uml.property  name="nombreAgente"
 */
protected String nombreAgente;


  /**
 * @param nombreAgente
 * @uml.property  name="nombreAgente"
 */
public void setNombreAgente(String nombreAgente) {
	this.nombreAgente = nombreAgente;
}

/**
 * Pizarra para almacenar par�metros enviados por la clase que implementa el interfaz de uso del agente reactivo
 * @uml.property  name="tablaParametros"
 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.lang.Object" qualifier="nombre:java.lang.String java.lang.Object"
 */
  protected Hashtable<String,Object> tablaParametros;
  
  /**
 * @uml.property  name="logger"
 * @uml.associationEnd  
 */
protected Logger logger;
  /**
 * @uml.property  name="trazas"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
public ItfUsoRecursoTrazas trazas;
  
  /**
 * Interfaz de uso del Repositorio de Interfaces
 * @uml.property  name="itfUsoRepositorio"
 * @uml.associationEnd  multiplicity="(1 1)"
 */
  public ItfUsoRepositorioInterfaces itfUsoRepositorio;

  /**
   * Es necesario un constructor sin par�metros
   */
  public AccionesSemanticasAgenteReactivo() {
	
    this.itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();
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
    this.itfUsoPropiadeEsteAgente = itfUsoA;
  }
  
  /**
 * @param itfUsoA
 * @uml.property  name="itfUsoGestorAReportar"
 */
public void setItfUsoGestorAReportar(ItfUsoAgenteReactivo itfUsoA)
  {
    this.itfUsoGestorAReportar = itfUsoA;
  }
  
  public void setItfUsoRepositorioInterfaces(ItfUsoRepositorioInterfaces itfUsoR)
  {
    this.itfUsoRepositorio = itfUsoR;
  }
  /**
 * @param logger
 * @uml.property  name="logger"
 */
public void setLogger(Logger logger){
	  this.logger = logger;
  }
 public void informaraMiAutomata(String input, Object[] infoComplementaria){
  // Este método crea un evento con la información de entrada y se le envía a si mismo por medio de
  // la  interfaz de uso
        EventoRecAgte eventoaEnviar;
      // En primer lugar se crea el evento con  la informacion de entrada
       if (infoComplementaria == null) {
             eventoaEnviar = new EventoRecAgte(input, nombreAgente, nombreAgente);
                            }
       else {
               eventoaEnviar = new EventoRecAgte(input,infoComplementaria, nombreAgente, nombreAgente);
                   }
        try {

			itfUsoPropiadeEsteAgente.aceptaEvento(eventoaEnviar);

		}
		catch (Exception ex) {
			logger.error("Ha habido un problema enviar un  input a este agente " +nombreAgente);
			trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                    "Ha habido un problema enviar el evento con informacion "+ input + "a  a si mismo", InfoTraza.NivelTraza.error));
			}

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
 * Este m�todo debe ser implementado por todos los agentes para  realizar el necesario tratamiento de los errores.
 * @uml.property  name="nombreAgente"
 */

public String getNombreAgente() {
	return nombreAgente;
}

 public void generarTimeOut(long milis, String nombre, String origen, String destino) {
		GenerarEventoTimeOut timeout = new GenerarEventoTimeOut(milis, nombre, origen,destino, this.itfUsoRepositorio);
		logger.debug("Generando evento de timeout de "+ milis + " milisegundos");
		timeout.start();
 }
}