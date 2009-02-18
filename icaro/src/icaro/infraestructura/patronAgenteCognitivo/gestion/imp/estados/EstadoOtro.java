package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;



public class EstadoOtro extends EstadoAbstracto {

	
	public int obtenerEstado(AgenteCognitivoImp agente) {
		return InterfazGestion.ESTADO_OTRO;
	}

}
