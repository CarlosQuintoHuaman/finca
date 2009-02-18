/*
 * EstadoAbstracto.java
 *
 * Creado en 22 de noviembre de 2007, 11:05
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */
package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class EstadoAbstracto implements ItfEstado {

    private Log log = LogFactory.getLog(EstadoAbstracto.class);

    public void arranca(AgenteCognitivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void para(AgenteCognitivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void termina(AgenteCognitivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void continua(AgenteCognitivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void aceptaMensaje(AgenteCognitivoImp agente, MensajeAgente e) {
        log.warn("Agente no preparado para aceptar mensajes");
    }
/*
    public void falloTemporal(AgenteCognitivoImp agente, String razon) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void error(AgenteCognitivoImp agente, String razon) {
        log.fatal("Pasando al estado de error por la siguiente razon:" + razon);
        agente.setEstado(EstadoError.instancia());
    }*/
    
    public void aceptaEvento(AgenteCognitivoImp agente, EventoInput evento) {
    	log.warn("Agente no preparado para aceptar eventos");
    }
    /*
    public String descripcion(AgenteCognitivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
        return "";
    }
    */
}
