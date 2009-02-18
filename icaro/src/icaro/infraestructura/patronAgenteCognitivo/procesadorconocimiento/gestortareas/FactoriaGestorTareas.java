/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfEnvioHechos;



/**
 *
 * @author carf
 */
public abstract class FactoriaGestorTareas {
    private static FactoriaGestorTareas instancia;
    public static FactoriaGestorTareas instancia(){
        if(instancia==null){
            String c = System.getProperty("organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.gestortareas.FactoriaGestorTareas",
                    "organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.gestortareas.imp.FactoriaGestorTareasImp");
            try{
                instancia = (FactoriaGestorTareas) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for:organizacion.infraestructura.agentecognitivo2.gestortareas.FactoriaGestorTareas");
            }
        }
        return instancia;

    }
    
    public abstract ItfGestorTareas crearGestorTareas(AgenteCognitivo agente,
            ItfEnvioHechos envioHechos);
}
