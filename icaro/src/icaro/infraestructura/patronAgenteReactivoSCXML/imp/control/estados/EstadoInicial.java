/*
 * EstadoInicial.java
 *
 * Creado en 22 de noviembre de 2007, 10:23
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
public class EstadoInicial extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoInicial.class);
    private static EstadoInicial instancia;
    public static EstadoInicial instancia(){
        if(instancia == null)
            instancia = new EstadoInicial();
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
        return InterfazGestion.ESTADO_CREADO;
    }

    
}
