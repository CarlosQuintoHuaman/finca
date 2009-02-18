/*
 * AgenteReactivoImp.java
 *
 * Creado en 21 de noviembre de 2007, 10:43
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */
package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.ProcesadorEventos;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.AgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.Configuracion;
import icaro.infraestructura.patronAgenteReactivoSCXML.ItfAvisos;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados.EstadoInicial;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados.ItfEstado;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.env.SimpleContext;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class AgenteReactivoImp extends AgenteReactivo implements ItfAvisos {

    private Percepcion queue = null;
    private SCXMLExecutor ejecutor;
    private ItfEstado estado = null;
    private ProcesadorEventos repartidorEventos;
    private SimpleContext contexto;

    /** Creates a new instance of AgenteReactivoImp */
    public AgenteReactivoImp(Configuracion configuracion) throws Exception {
        ListaAcciones listaAccionesUtils = new ListaAcciones();
        listaAccionesUtils.cargar(configuracion.getAcciones());
        queue = new Percepcion(10);
        contexto = new SimpleContext();
        contexto.set("ItfAvisos", this);
        contexto.set("ItfEventos", this);
        contexto.set("Global", new TreeMap());
        ConstructorSCXMLExecutor constructor = new ConstructorSCXMLExecutor(this);
        ejecutor = constructor.crearAutomata(configuracion.getMaquinaEstados(), listaAccionesUtils.getAcciones(), contexto);
        repartidorEventos = new ProcesadorEventos(queue, ejecutor, configuracion.isDaemon());
        estado = EstadoInicial.instancia();
    }

    /**
     *Comienza la ejecucion del automata
     */
    public void arranca() {
        estado.arranca(this);
    }

    /**
     *Detiene la aceptacion de eventos.
     */
    public void para() {
        estado.para(this);
    }

    /**
     *Detiene la aceptacion de eventos y sale.
     */
    public void termina() {
        estado.termina(this);
    }

    /**
     *Arranca la aceptacion de eventos si esta detenida
     */
    public void continua() {
        estado.continua(this);
    }

    /**
     *Devuelve el estado
     */
    public int monitorizacion() {
        return estado.monitorizacion(this);
    }

    public String descripcion() {
        return null;
    }

    public ItfEstado getEstado() {
        return estado;
    }

    public void setEstado(ItfEstado estado) {
        this.estado = estado;
    }

    public Percepcion getQueue() {
        return queue;
    }

    public void falloTemporal(String razon) {
        estado.falloTemporal(this, razon);
    }

    public void error(String razon) {
        estado.error(this, razon);
    }

    public SCXMLExecutor getEjecutor() {
        return ejecutor;
    }

    public ProcesadorEventos getRepartidorEventos() {
        return repartidorEventos;
    }

    public void terminarInterno() {
        estado.termina(this);
    }

    public int obtenerEstado() {
        return estado.monitorizacion(this);
    }

    @Override
    public void setGestorAReportar(String nombreGestor) {
        ItfUsoAgenteReactivo itf = null;
        try {
            itf = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreGestor);
        } catch (Exception ex) {
            Logger.getLogger(AgenteReactivoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        contexto.set("GestorAReportar", itf);
    }

    public void aceptaEvento(EventoInput ev) throws Exception {
        estado.aceptaEvento(this, ev);
    }
}
