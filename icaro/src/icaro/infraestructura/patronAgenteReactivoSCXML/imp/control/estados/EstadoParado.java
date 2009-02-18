/*
 * EstadoParado.java
 *
 * Creado en 22 de noviembre de 2007, 10:24
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.AgenteReactivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class EstadoParado extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoParado.class);
    private static EstadoParado instancia;
    public static EstadoParado instancia(){
        if(instancia == null)
            instancia = new EstadoParado();
        return instancia;
    }
    
    
    
    public void termina(AgenteReactivoImp agente) {
        log.info("Terminando el agente");
        agente.getQueue().clear();
        agente.getRepartidorEventos().pararConsumo();
        
    }
    
    public void continua(AgenteReactivoImp agente) {
        log.info("Pasando al estado Activo");
        agente.setEstado(EstadoActivo.instancia());
    }
    
    public int monitorizacion(AgenteReactivoImp agente) {
        return InterfazGestion.ESTADO_PARADO;
    }
    
}
