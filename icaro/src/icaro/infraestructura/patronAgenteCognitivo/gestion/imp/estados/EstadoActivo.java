/*
 * EstadoActivo.java
 *
 * Creado en 22 de noviembre de 2007, 10:24
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class EstadoActivo extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoActivo.class);
    private static EstadoActivo instancia;
    public static EstadoActivo instancia(){
        if(instancia == null)
            instancia = new EstadoActivo();
        return instancia;
    }

    public void para(AgenteCognitivoImp agente) {
        log.info("Parando el agente");
        agente.getPercepcion().termina();
        agente.setEstado(EstadoParado.instancia());
        log.info("Agente parado");
    }
    
    public void termina(AgenteCognitivoImp agente) {
        log.info("Terminando el agente");
        agente.getPercepcion().termina();
    }

    public int obtenerEstado(AgenteCognitivoImp agente) {
        return InterfazGestion.ESTADO_ACTIVO;
    }
    public void aceptaMensaje(AgenteCognitivoImp agente, MensajeAgente e) {
    	agente.getPercepcion().aceptaMensaje(e);
    }

	@Override
	public void aceptaEvento(AgenteCognitivoImp agente, EventoInput evento) {
		try {
			agente.getPercepcion().aceptaEvento(evento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public String toString() {
    	return "Activo";
    }
    
}
