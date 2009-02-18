/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.FactoriaAccionesSemanticas;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.FactoriaAutomataControl;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.AutomataControlImp;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ControlAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControl;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.EventoControl;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.FactoriaPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.rmi.RemoteException;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *  Clase que implementa la base del componente agente reactivo
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */
public class AgenteReactivoImp extends AgenteReactivoAbstracto {

    private static final long serialVersionUID = 1L;
    /**
     * Control del agente
     */
    protected ControlAbstracto control;
    protected InterfazGestion itfGesControl; //Control
    /**
     * Percepcion del agente
     */
    protected ItfConsumidorPercepcion itfConsumidorPercepcion;
    protected ItfProductorPercepcion itfProductorPercepcion;
    /**
     * Nombre del agente a efectos de traza
     */
    protected String nombre;
    /**
     * Estado del agente reactivo
     */
    protected int estado = InterfazGestion.ESTADO_OTRO;
    /**
     * Acciones sem�nticas del agente reactivo
     */
    protected AccionesSemanticasAbstracto accionesSemanticas;
    private boolean DEBUG = false;
    /**
     * Conocimiento del agente reactivo
     */
    protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    protected ItfUsoRecursoTrazas trazas;

    /**
     *  Crea un instancia del agente reactivo
     *
     *@param  objetoContenedorAcciones   Objeto que contiene la implementaci�n de todos los m�todos referidos en la tabla
     *@param  nombreFicheroTablaEstados  Nombre Fichero que contene la tabla de estados XML
     *@param  nombreDelAgente            Nombre del agente
     *@todo propagar el nivel de traza hasta el constructor para poder ser modificado sin tocar el componente
     */
    public AgenteReactivoImp(Object accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente) {
        super();
        new ConfiguracionTrazas(logger);
        try {
            trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception e) {
            logger.error("No se pudo usar el recurso de trazas", e);
        }
        this.setParametrosAgReactivo((AccionesSemanticasAgenteReactivo) accionesSemanticasEspecificas,
                nombreFicheroTablaEstados, nombreDelAgente);

    }

    /**
     * Constructor sin par�metros: HAY QUE UTILIZAR INMEDIATAMENTE DESPU�S EL M�TODO
     * setParametrosAgReactivo
     * @throws RemoteException
     */
    public AgenteReactivoImp() {
        super();
    }

    /**
     * Fija los par�metros necesarios del agente reactivo para permitir la creaci�n de las clases
     * herederas de esta en 2 pasos y facilitar la obtenci�n de la clase que implementa las acciones
     * @param objetoContenedorAcciones
     * @param nombreFicheroTablaEstados
     * @param nombreDelAgente
     * @throws RemoteException
     */
    public void setParametrosAgReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente) {
        nombre = nombreDelAgente;

        accionesSemanticasEspecificas.setLogger(logger);
        // crea las Acciones sem�nticas referidas al contenedor de acciones
        this.accionesSemanticas = FactoriaAccionesSemanticas.instancia().crearAccionesSemanticas(accionesSemanticasEspecificas);
        // crea el automata de control
        AutomataControlAbstracto ac = null;
        boolean error = false;
        try {
            try {
                ac = FactoriaAutomataControl.instancia().crearAutomataControl(nombreFicheroTablaEstados, accionesSemanticas, AutomataControlImp.NIVEL_TRAZA_DESACTIVADO, this.nombre);
            } catch (Exception e) {
                logger.error("Error al leer la tabla de estados del agente " + nombreDelAgente + " desde el fichero " + nombreFicheroTablaEstados);
                trazas.aceptaNuevaTraza(new InfoTraza(nombreDelAgente,
                        "Error al leer la tabla de estados del agente " + nombreDelAgente + " desde el fichero " + nombreFicheroTablaEstados,
                        InfoTraza.NivelTraza.error));
                error = true;
            }
            // Incluyo el logger al automata para marcar transiciones

            ac.setLogger(logger);
            PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion();
            itfConsumidorPercepcion = (ItfConsumidorPercepcion) percepcion;
            itfProductorPercepcion = (ItfProductorPercepcion) percepcion;

            // crea control
            control = FactoriaControl.instancia().crearControl(itfConsumidorPercepcion, ac,
                    itfProductorPercepcion, nombreDelAgente);

            itfGesControl = (InterfazGestion) control;

            logger.debug(nombreDelAgente + ":Creaci�n del Agente ...ok");
            trazas.aceptaNuevaTraza(new InfoTraza(nombreDelAgente,
                    nombreDelAgente + ":Creaci�n del Agente ...ok",
                    NivelTraza.debug));
        } catch (Exception e) {
            logger.error("Error al crear el agente: " + nombreDelAgente, e);
        }

    }

    /**
     *  Introduce un nuevo evento en la percepci�n
     *
     *@param  evento  Evento que llega nuevo
     */
    public void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog) {
        new ConfiguracionTrazas(logger, archivoLog, nivelLog);
    }

    public synchronized void aceptaEvento(EventoInput evento) {
        logger.debug(nombre + ": Ha llegado un evento por el interfaz de Uso:" + evento.toString());
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": Ha llegado un evento por el interfaz de Uso:" + evento.toString(),
                InfoTraza.NivelTraza.debug));
        clasificarEvento(evento);
    }

    /**
     *  Inicializa los recursos necesarios para la ejecuci�n del componente agente reactivo
     *
     */
    public synchronized void arranca() {
        //arranca el control

        logger.debug(nombre + ": arranca()");
        trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                ": arranca()",
                InfoTraza.NivelTraza.debug));
        itfGesControl.arranca();
        aceptaEvento(new EventoInput("comenzar", nombre, nombre));
    }

    /*
    public synchronized void arrancaConEvento() 
    {
    //arranca el control con un input predefinido
    
    logger.debug(nombre + ": arranca()");
    itfGesControl.arranca();
    
    }
    
    public synchronized void arrancaConInput (String nombreInput) 
    {
    //arranca el control con un input definido por el usuario
    
    logger.debug(nombre + ": arranca()");
    itfGesControl.arranca();
    aceptaEvento(new EventoInput(nombreInput,null,null));
    }
     */
    /**
     *  Continua la ejecuci�n si se ha parado
     *
     */
    public synchronized void continua() {
    }

    /**
     * Devuelve el estado interno del agente
     *@return     Description of the Returned Value
     */
    public int obtenerEstado() {

        logger.debug(nombre + ": monitoriza()");
        trazas.aceptaNuevaTraza(new InfoTraza(
                nombre, ": monitoriza()",
                InfoTraza.NivelTraza.debug));
        return itfGesControl.obtenerEstado();
    }

    /**
     *  Detiene la ejecuci�n actual, descarta nuevos eventos.
     *
     */
    public synchronized void para() {
    }

    /**
     *  Pruebas en desarrollo
     */
    public void printOutThreadGroups() {
        //create a thread
        //Thread t = new Thread();
        //get the current thread
        Thread tt = Thread.currentThread();
        //get the thread group of current thread
        ThreadGroup tg = tt.getThreadGroup();
        ThreadGroup topMost = null;
        //find the topmost thread group
        while (tg != null) {
            topMost = tg;
            tg = tg.getParent();
        }
        //get an estimate of active thread groups under topMost
        int groupCount = topMost.activeGroupCount();
        //get an enumeration of thread groups under topMost
        ThreadGroup[] tgArray = new ThreadGroup[groupCount];
        topMost.enumerate(tgArray, true);
        //for every thread group under topMost, print out the active threads
        System.out.println("Top Most ThreadGroup: \"" + topMost.getName() +
                "\" has the following Thread Groups:");
        System.out.println("**********************");
        for (int i = 0; i < tgArray.length; i++) {
            ThreadGroup aThreadGroup = tgArray[i];
            int count = aThreadGroup.activeCount();
            //get an enumeration of threads in aThreadGroup
            Thread[] tArray = new Thread[count];
            aThreadGroup.enumerate(tArray, true);
            System.out.println("Thread Group: \"" + aThreadGroup.getName() +
                    "\" has the following threads: ");
            String indent = " ";
            for (int j = 0; j < tArray.length; j++) {
                if (tArray[j] != null) {
                    Thread aThread = tArray[j];
                    System.out.println(indent + " \"" + aThread.getName() + "\"");
                    indent += " ";
                }
            }
            System.out.println("**********************");
            tArray = null;
        }
        tgArray = null;
    //t = null;
    }

    /**
     *  Libera los recursos que se ocuparon en la creaci�n
     *
     */
    public synchronized void termina() {

        logger.debug(nombre + ": termina()");
        trazas.aceptaNuevaTraza(new InfoTraza(
                nombre, ": termina()",
                InfoTraza.NivelTraza.debug));
//		itfGesControl.termina();
        this.aceptaEvento(new EventoInput("termina", nombre, nombre));
    }

    /**
     * Despacha el evento para tratarlo de forma diferente seg�n su tipo
     *@param  ev  Description of Parameter
     */
    protected void clasificarEvento(Object ev) {
        if (ev instanceof EventoInput) {
            itfProductorPercepcion.produce(ev);
        } else if (ev instanceof EventoControl) {
            itfProductorPercepcion.produceParaConsumirInmediatamente(ev);
        } else {
            logger.debug("ERROR:" + nombre + ": Clasificar evento: el evento no es de una clase conocida por el Agente Reactivo");
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "ERROR: Clasificar evento: el evento no es de una clase conocida por el Agente Reactivo",
                    InfoTraza.NivelTraza.error));
        }
    }

    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int e) {
        this.estado = e;
    }

    public ControlAbstracto getControl() {
        return this.control;
    }

    public AgenteReactivoAbstracto getPatron() {
        return this;
    }

    @Override
    public String toString() {
        return nombre;
    }

    //@Override
    /**
     *  Establece el gestor a reportar
     *  @param nombreGestor nombre del gestor a reportar
     *  @param listaEventos lista de posibles eventos que le puede enviar.
     *  
     *  El gestionador obtendr� las interfaces del gestor a partir del repositorio de interfaces y podr� validar la informaci�n.
     *
     */
    public void setGestorAReportar(String nombreGestor, Set<Object> conjuntoEventos) {
        try {
            this.logger.info("Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Estableciendo gestor a reportar. Agente: " + this.nombre + ". Gestor: " + nombreGestor,
                    InfoTraza.NivelTraza.info));
            // listaEventos: a�n no se va a utilizar.
            itfUsoGestorAReportar = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + nombreGestor);

            this.accionesSemanticas.getAccionesSemanticas().setItfUsoGestorAReportar(itfUsoGestorAReportar);
        } catch (Exception e) {
            logger.error("Error al asignar el gestor a reportar", e);
            trazas.aceptaNuevaTraza(new InfoTraza(nombre,
                    "Error al asignar el gestor a reportar",
                    InfoTraza.NivelTraza.error));
        }

    }
}
