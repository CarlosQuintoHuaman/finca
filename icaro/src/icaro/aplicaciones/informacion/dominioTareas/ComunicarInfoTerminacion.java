/*
 * ComunicarInfoTerminacion.java
 *
 * Created on 22 de mayo de 2007, 11:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package icaro.aplicaciones.informacion.dominioTareas;

import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;

/**
 *
 * @author Javi
 */
public class ComunicarInfoTerminacion extends Tarea {

    /** Creates a new instance of ComunicarInfoTerminacion */
    public ComunicarInfoTerminacion() {
    }

    @Override
    public void ejecutar(Object... params) {
        String mensaje = (String) params[0];
        System.out.println("Mensaje de Terminacion:" + mensaje);
    }
}
