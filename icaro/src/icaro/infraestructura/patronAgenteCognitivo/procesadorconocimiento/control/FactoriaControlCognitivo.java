package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;




public abstract class FactoriaControlCognitivo {
	private static FactoriaControlCognitivo instancia;

    public static FactoriaControlCognitivo instancia() {
        if(instancia==null){
            String c = System.getProperty("organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.control.FactoriaControlCognitivo",
            		"organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.control.imp.FactoriaControlCognitivoImp");
            try{
                instancia = (FactoriaControlCognitivo) Class.forName(c).newInstance();
                
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.control.FactoriaControlCognitivo");
            }
        }
        return instancia;
    }

    public abstract ControlCognitivoAbstracto crearControlCognitivo(AgenteCognitivo agenteCognitivo) throws Exception;

}
