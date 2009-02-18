/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.imp;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.FactoriaGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfEnvioHechos;



/**
 *
 * @author carf
 */
public class FactoriaGestorTareasImp extends FactoriaGestorTareas{

    @Override
    public ItfGestorTareas crearGestorTareas(AgenteCognitivo agente, ItfEnvioHechos envioHechos) {
        return new GestorTareasImp(agente,envioHechos);
    }

}
