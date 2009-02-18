/*
 * EstadoFalloArranque.java
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
public class EstadoFalloArranque extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoFalloArranque.class);
    private static EstadoFalloArranque instancia;
    public static EstadoFalloArranque instancia(){
        if(instancia == null)
            instancia = new EstadoFalloArranque();
        return instancia;
    }

    public void arranca(AgenteReactivoImp agente) {
        try{
            log.info("Arrancando el agente");
            agente.getEjecutor().go();
            agente.getRepartidorEventos().start();
            agente.setEstado(EstadoActivo.instancia());
            log.info("Agente arrancado");
        }catch(Exception ex){
            log.error("A ocurrido un error arrancando el agente:",ex);
            agente.setEstado(EstadoFalloArranque.instancia());
        }
    }

    public int monitorizacion(AgenteReactivoImp agente) {
        return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
    }
}
