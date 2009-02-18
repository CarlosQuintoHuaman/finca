/*
 * EstadoActivo.java
 *
 * Creado en 22 de noviembre de 2007, 10:24
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.AgenteReactivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class EstadoActivo extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoActivo.class);
    private static EstadoActivo instancia;
    public static EstadoActivo instancia(){
        if(instancia == null)
            instancia = new EstadoActivo();
        return instancia;
    }

    public void para(AgenteReactivoImp agente) {
        log.info("Parando el agente");
        agente.getQueue().clear();
        agente.setEstado(EstadoParado.instancia());
        log.info("Agente parado");
    }
    
    public void termina(AgenteReactivoImp agente) {
        log.info("Terminando el agente");
        agente.getQueue().clear();
        agente.getRepartidorEventos().pararConsumo();
    }

    public int monitorizacion(AgenteReactivoImp agente) {
        return InterfazGestion.ESTADO_ACTIVO;
    }
    public String estadoFuncionamiento(AgenteReactivoImp agente){
        return agente.getEjecutor().getCurrentStatus().toString();
    }
    public void aceptaEvento(AgenteReactivoImp agente, EventoInput e) {
        agente.getQueue().offer(e);
    }

    public void falloTemporal(AgenteReactivoImp agente, String razon) {
        log.info("Pasando al estado de Fallo Temporal por la siguiente razon:"+razon);
        agente.setEstado(EstadoFalloTemporal.instancia());
        
    }
    
}
