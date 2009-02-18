package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Evidencia;



public abstract class ControlCognitivoAbstracto implements ItfControlCognitivo {

	
	public abstract boolean aceptaEvidencia(Evidencia ev);
}
