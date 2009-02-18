package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EstadoArrancando extends EstadoAbstracto {

	
	private Log log = LogFactory.getLog(EstadoArrancando.class);
    private static EstadoArrancando instancia;
    public static EstadoArrancando instancia(){
        if(instancia == null)
            instancia = new EstadoArrancando();
        return instancia;
    }

	

	public int obtenerEstado(AgenteCognitivoImp agente) {
		return InterfazGestion.ESTADO_ARRANCANDO;
	}
	
	
    public String toString() {
    	return "Arrancando";
    }

}
