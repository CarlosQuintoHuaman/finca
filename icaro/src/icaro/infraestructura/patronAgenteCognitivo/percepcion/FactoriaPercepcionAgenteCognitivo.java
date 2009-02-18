package icaro.infraestructura.patronAgenteCognitivo.percepcion;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.imp2.FactoriaPercepcionAgenteCognitivoImp;



public abstract class FactoriaPercepcionAgenteCognitivo {
	private static FactoriaPercepcionAgenteCognitivo instancia;
	
	public static FactoriaPercepcionAgenteCognitivo instancia() {
		if (instancia == null)
			instancia = new FactoriaPercepcionAgenteCognitivoImp();
		
	return instancia;
	}
	
	
	public abstract PercepcionAgenteCognitivo crearPercepcionAgenteCognitivo(AgenteCognitivo agenteCognitivo);

}
