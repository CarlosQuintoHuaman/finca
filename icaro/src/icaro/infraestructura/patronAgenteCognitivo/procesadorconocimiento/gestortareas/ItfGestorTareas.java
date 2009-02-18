/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;

/**
 *
 * @author carf
 */
public interface ItfGestorTareas {
    public Tarea crearTarea(Class clase) throws Exception;
}
