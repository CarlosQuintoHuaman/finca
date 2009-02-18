/*
 * Logger.java
 *
 * Creado en 26 de noviembre de 2007, 10:42
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.SCXMLListener;
import org.apache.commons.scxml.model.Transition;
import org.apache.commons.scxml.model.TransitionTarget;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class Logger implements SCXMLListener{
    private Log log = LogFactory.getLog(Logger.class);
    
    public void onEntry(TransitionTarget transitionTarget) {
        log.debug("Entrando en:"+transitionTarget.getId());
    }
    
    public void onExit(TransitionTarget transitionTarget) {
        log.debug("Saliendo de:"+transitionTarget.getId());
    }
    
    public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget0, Transition transition) {
        log.debug("Transitando desde:"+transitionTarget.getId()+", hacia:"+transitionTarget0.getId());
    }
    
}
