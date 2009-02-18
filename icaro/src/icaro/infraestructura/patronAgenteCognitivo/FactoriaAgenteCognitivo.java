/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteCognitivo;


/**
 *
 * @author carf
 */
public abstract class FactoriaAgenteCognitivo {

    private static FactoriaAgenteCognitivo instancia;

    public static FactoriaAgenteCognitivo instancia() {
        if(instancia==null){
            String c = System.getProperty("organizacion.infraestructura.agentecognitivo2.FactoriaAgenteCognitivo",
                    "organizacion.infraestructura.agentecognitivo2.imp.FactoriaAgenteCognitivoImp");
            try{
                instancia = (FactoriaAgenteCognitivo) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for:organizacion.infraestructura.agentecognitivo2.FactoriaAgenteCognitivo");
            }
        }
        return instancia;
    }

    public abstract void crearAgenteCognitivo(String nombreAgente) throws Exception;
}
