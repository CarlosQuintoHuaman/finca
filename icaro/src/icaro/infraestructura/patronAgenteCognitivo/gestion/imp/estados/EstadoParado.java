/*
 * EstadoParado.java
 *
 * Creado en 22 de noviembre de 2007, 10:24
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class EstadoParado extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoParado.class);
    private static EstadoParado instancia;
    public static EstadoParado instancia(){
        if(instancia == null)
            instancia = new EstadoParado();
        return instancia;
    }
    
    
    
    public void termina(AgenteCognitivoImp agente) {
        log.info("Terminando el agente");
        agente.getPercepcion().termina();
        
        
    }
    
    public void continua(AgenteCognitivoImp agente) {
        log.info("Pasando al estado Activo");
        agente.setEstado(EstadoActivo.instancia());
    }
    
    public int obtenerEstado(AgenteCognitivoImp agente) {
        return InterfazGestion.ESTADO_PARADO;
    }
    
    public String toString() {
    	return "Parado";
    }
}
