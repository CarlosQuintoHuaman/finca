/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import icaro.infraestructura.patronAgenteReactivoSCXML.*;

/**
 *
 * @author Carlos Rodríguez Fernández
 */
public class PatronHelper {

    public AgenteReactivo crearAgenteReactivo(Configuracion configuracion) throws Exception {
        return new AgenteReactivoImp(configuracion);
    }
}
