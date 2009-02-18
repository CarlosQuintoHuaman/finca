package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Evidencia;

public interface ItfControlCognitivo {
	public boolean aceptaEvidencia(Evidencia ev);
	
	public void arranca();
	
}
