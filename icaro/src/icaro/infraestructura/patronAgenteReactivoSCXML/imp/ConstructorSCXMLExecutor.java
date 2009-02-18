/*
 * ConstructorSCXMLExecutor.java
 *
 * Creado en 26 de noviembre de 2007, 10:32
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.Context;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.env.SimpleDispatcher;
import org.apache.commons.scxml.env.SimpleErrorReporter;
import org.apache.commons.scxml.env.jexl.JexlEvaluator;
import org.apache.commons.scxml.io.SCXMLDigester;
import org.apache.commons.scxml.model.SCXML;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ConstructorSCXMLExecutor implements ErrorHandler{
    private AgenteReactivoImp agente;
    private Log log = LogFactory.getLog(ConstructorSCXMLExecutor.class);
    /**
     * Creates a new instance of ConstructorSCXMLExecutor
     */
    public ConstructorSCXMLExecutor(AgenteReactivoImp agente) {
        this.agente = agente;
    }
    public SCXMLExecutor crearAutomata(InputStream is,List acciones, Context contexto) throws Exception{
        log.info("Comenzando la creacion del motor de maquinas de estados...");
        SCXMLExecutor exec = null;
        
        SCXML automata = SCXMLDigester.digest(new InputSource(is),this,acciones);
        log.info("La maquina de estado ha sido cargada en memoria");
        exec = new SCXMLExecutor(new JexlEvaluator(),
                new SimpleDispatcher(),new SimpleErrorReporter());
        
        exec.setStateMachine(automata);
        exec.addListener(automata, new Logger());
        exec.setRootContext(contexto);
        log.info("El motor de maquinas de estados ha sido creado y configurado");
        return exec;
    }

    public void warning(SAXParseException exception) throws SAXException {
        log.warn("Aviso desde SAX:",exception);
    }

    public void error(SAXParseException exception) throws SAXException {
        log.error("Error haciendo el parsing del fichero:",exception);
        agente.error("Fallo en la lectura del fichero");
    }

    public void fatalError(SAXParseException exception) throws SAXException {
        log.fatal("Error Fatal haciendo el parsing del fichero:",exception);
        agente.error("Error fatal en la lectura del fichero");
    }
}
