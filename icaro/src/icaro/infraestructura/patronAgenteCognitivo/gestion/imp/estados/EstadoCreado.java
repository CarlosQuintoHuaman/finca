/*
 * EstadoInicial.java
 *
 * Creado en 22 de noviembre de 2007, 10:23
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
public class EstadoCreado extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoCreado.class);
    private static EstadoCreado instancia;
    public static EstadoCreado instancia(){
        if(instancia == null)
            instancia = new EstadoCreado();
        return instancia;
    }

    public void arranca(AgenteCognitivoImp agente) {
        try{
            log.info("Arrancando el agente");
            agente.setEstado(EstadoArrancando.instancia());
            agente.getControl().arranca();
            agente.getPercepcion().arranca();
            agente.setEstado(EstadoActivo.instancia());
            log.info("Agente arrancado");
        }catch(Exception ex){
            log.error("A ocurrido un error arrancando el agente:",ex);
            agente.setEstado(EstadoErrorIrrecuperable.instancia());
        }
    }

 
    public int obtenerEstado(AgenteCognitivoImp agente) {
        return InterfazGestion.ESTADO_CREADO;
    }

    public String toString() {
    	return "Creado";
    }
    
}
