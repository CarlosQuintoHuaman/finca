package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfEnvioHechos;

public abstract class Tarea extends Thread {

    private ItfEnvioHechos envioHechos;
    private AgenteCognitivo agente;
    
    private Object[] params;
    
    


	public abstract void ejecutar(Object... params);
    

    public ItfEnvioHechos getEnvioHechos() {
        return envioHechos;
    }

    public void setEnvioHechos(ItfEnvioHechos envioHechos) {
        this.envioHechos = envioHechos;
    }

    public AgenteCognitivo getAgente() {
        return agente;
    }

    public void setAgente(AgenteCognitivo agente) {
        this.agente = agente;
    }
	
    public Object[] getParams() {
		return params;
	}


	public void setParams(Object... params) {
		this.params = params;
	}
    
    public void run() {
    	ejecutar(params);
    }
    
}