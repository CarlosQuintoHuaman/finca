/*
 * EstadoError.java
 *
 * Creado en 22 de noviembre de 2007, 10:58
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
public class EstadoErrorIrrecuperable extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoActivo.class);
    private static EstadoErrorIrrecuperable instancia;
    public static EstadoErrorIrrecuperable instancia(){
        if(instancia == null)
            instancia = new EstadoErrorIrrecuperable();
        return instancia;
    }

    public int obtenerEstado(AgenteCognitivoImp agente) {
        return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
    }
    
    public String toString() {
    	return "Erroneo Irrecuperable";
    }
}
