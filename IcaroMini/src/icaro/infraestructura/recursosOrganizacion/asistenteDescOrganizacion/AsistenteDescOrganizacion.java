/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.recursosOrganizacion.asistenteDescOrganizacion;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 */
public abstract class AsistenteDescOrganizacion implements ItfUsoAsistenteDescOrganizacion,InterfazGestion {
    private static AsistenteDescOrganizacion instancia;
    public static AsistenteDescOrganizacion instancia(){
        if(instancia==null){
            try {
                instancia = (AsistenteDescOrganizacion) Class.forName("icaro.infraestructura.recursosOrganizacion.asistenteDescOrganizacion.imp.AsistenteDescOrganizacionImpl").newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Implementation not found",ex);
            }
        }
        return instancia;
    }
}
