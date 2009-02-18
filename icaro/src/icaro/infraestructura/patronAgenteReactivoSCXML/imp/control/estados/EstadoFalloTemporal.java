/*
 * EstadoFalloTemporal.java
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
public class EstadoFalloTemporal extends EstadoAbstracto {

    private static EstadoFalloTemporal instancia;
    private Log log = LogFactory.getLog(EstadoFalloTemporal.class);

    public static EstadoFalloTemporal instancia() {
        if (instancia == null) {
            instancia = new EstadoFalloTemporal();
        }
        return instancia;
    }

    public void continua(AgenteReactivoImp agente) {
        agente.setEstado(EstadoActivo.instancia());
    }

    public int monitorizacion(AgenteReactivoImp agente) {
        return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
    }

    public String estadoFuncionamiento(AgenteReactivoImp agente) {
        return agente.getEjecutor().getCurrentStatus().toString();
    }
}
