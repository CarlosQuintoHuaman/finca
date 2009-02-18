/*
 * GenerarInfoTerminacion.java
 *
 * Created on 22 de mayo de 2007, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoCognitivo.tareas;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Creencia;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;

/**
 *
 * @author Javi
 */
public class GenerarInfoTerminacion extends Tarea {

    /** Creates a new instance of GenerarInfoTerminacion */
    public GenerarInfoTerminacion() {
    }

    @Override
    public void ejecutar(Object... params) {
        Creencia cre = new Creencia();
        cre.setEmisor("Task:GenerarInfoTerminacion");
        cre.setReceptor("AgenteAcceso");
        cre.setContenido("Se va a terminar el servicio");
        this.getEnvioHechos().agregarHecho(cre);
    }
}
