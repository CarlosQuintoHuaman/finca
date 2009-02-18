package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp2;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.FactoriaPercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;



public class FactoriaPercepcionAgenteCognitivoImp extends FactoriaPercepcionAgenteCognitivo {


	@Override
	public PercepcionAgenteCognitivo crearPercepcionAgenteCognitivo(
			AgenteCognitivo agenteCognitivo) {
		return new PercepcionAgenteCognitivoImp(agenteCognitivo);
	}

}
