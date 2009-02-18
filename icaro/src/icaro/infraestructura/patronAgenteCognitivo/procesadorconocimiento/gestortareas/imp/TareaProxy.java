/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.imp;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfEnvioHechos;

/**
 *
 * @author carf
 */
public class TareaProxy extends Tarea{
    private Tarea tarea;
    public TareaProxy(Tarea tarea){
        this.tarea = tarea;
    }
    
    @Override
    public void setEnvioHechos(ItfEnvioHechos envioHechos){
        tarea.setEnvioHechos(envioHechos);
    }
    @Override
    public ItfEnvioHechos getEnvioHechos(){
        return tarea.getEnvioHechos();
    }
    
    @Override
    public void setAgente(AgenteCognitivo agente){
        tarea.setAgente(agente);
    }
    
    @Override
    public AgenteCognitivo getAgente(){
        return tarea.getAgente();
    }

    @Override
    public void ejecutar(Object... params) {	
		tarea.setParams(params);
		tarea.start();
        //tarea.ejecutar(params);
    }

}
