package icaro.infraestructura.patronAgenteReactivoSCXML;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.interfaces.ItfEventos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

import java.util.Collection;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.scxml.ErrorReporter;
import org.apache.commons.scxml.EventDispatcher;
import org.apache.commons.scxml.SCInstance;
import org.apache.commons.scxml.SCXMLExpressionException;
import org.apache.commons.scxml.model.Action;
import org.apache.commons.scxml.model.ModelException;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class AccionAbstracta extends Action {

    /**
     * Implementacion del m&eacute;todo de la clase "Action" del framework "commons-scxml"
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @param arg4
     * @throws org.apache.commons.scxml.model.ModelException
     * @throws org.apache.commons.scxml.SCXMLExpressionException
     */
    public void execute(EventDispatcher arg0, ErrorReporter arg1, SCInstance arg2, Log arg3, Collection arg4) throws ModelException, SCXMLExpressionException {

        ItfAvisos avisos = (ItfAvisos) arg2.getRootContext().get("ItfAvisos");
        ItfEventos percepcion = (ItfEventos) arg2.getRootContext().get("ItfEventos");
        EventoInput evento = (EventoInput) arg2.getRootContext().get("Evento");
        TreeMap global = (TreeMap) arg2.getRootContext().get("Global");
        ItfUsoAgenteReactivo itfGestorReportar = (ItfUsoAgenteReactivo) arg2.getRootContext().get("GestorAReportar");
        ContextoAccion contexto = new ContextoAccion();
        contexto.setAvisos(avisos);
        contexto.setEvento(evento);
        contexto.setGlobal(global);
        contexto.setPercepcion(percepcion);
        contexto.setItfGestorAReportar(itfGestorReportar);
        ejecutar(contexto);

    }

    /**
     * M&eacute;todo que debe implementar toda acci&oacute;n de la maquina de estados.
     * @param contexto
     * @throws org.apache.commons.scxml.model.ModelException
     * @throws org.apache.commons.scxml.SCXMLExpressionException
     */
    public abstract void ejecutar(ContextoAccion contexto) throws ModelException, SCXMLExpressionException;
}
