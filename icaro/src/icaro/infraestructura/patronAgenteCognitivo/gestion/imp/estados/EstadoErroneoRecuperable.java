/*
 * EstadoFalloTemporal.java
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
public class EstadoErroneoRecuperable extends EstadoAbstracto {

    private static EstadoErroneoRecuperable instancia;
    private Log log = LogFactory.getLog(EstadoErroneoRecuperable.class);

    public static EstadoErroneoRecuperable instancia() {
        if (instancia == null) {
            instancia = new EstadoErroneoRecuperable();
        }
        return instancia;
    }

    public void continua(AgenteCognitivoImp agente) {
        agente.setEstado(EstadoActivo.instancia());
    }

    public int obtenerEstado(AgenteCognitivoImp agente) {
        return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
    }
    
    public String toString() {
    	return "Erroneo Recuperable";
    }
}
