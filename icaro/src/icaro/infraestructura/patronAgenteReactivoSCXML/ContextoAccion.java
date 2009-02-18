/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteReactivoSCXML;

import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.interfaces.ItfEventos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

import java.util.TreeMap;

/**
 * Contexto de una acci&oacute;n
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ContextoAccion {

    private ItfEventos percepcion;
    private ItfAvisos avisos;
    private EventoInput evento;
    private TreeMap global;
    private ItfUsoAgenteReactivo itfGestorAReportar;

    public ItfEventos getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(ItfEventos percepcion) {
        this.percepcion = percepcion;
    }

    public ItfAvisos getAvisos() {
        return avisos;
    }

    public void setAvisos(ItfAvisos avisos) {
        this.avisos = avisos;
    }

    public EventoInput getEvento() {
        return evento;
    }

    public void setEvento(EventoInput evento) {
        this.evento = evento;
    }

    public TreeMap getGlobal() {
        return global;
    }

    public void setGlobal(TreeMap global) {
        this.global = global;
    }

    public ItfUsoAgenteReactivo getItfGestorAReportar() {
        return itfGestorAReportar;
    }

    public void setItfGestorAReportar(ItfUsoAgenteReactivo itfGestorAReportar) {
        this.itfGestorAReportar = itfGestorAReportar;
    }
}
