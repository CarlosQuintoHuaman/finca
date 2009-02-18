/*
 * EstadoAbstracto.java
 *
 * Creado en 22 de noviembre de 2007, 11:05
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */
package icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.AgenteReactivoImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class EstadoAbstracto implements ItfEstado {

    private Log log = LogFactory.getLog(EstadoAbstracto.class);

    public void arranca(AgenteReactivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void para(AgenteReactivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void termina(AgenteReactivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void continua(AgenteReactivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void aceptaEvento(AgenteReactivoImp agente, EventoInput e) {
        log.warn("Agente no preparado para aceptar eventos");
    }

    public void falloTemporal(AgenteReactivoImp agente, String razon) {
        log.warn("Mensaje de gestion no valido para este estado");
    }

    public void error(AgenteReactivoImp agente, String razon) {
        log.fatal("Pasando al estado de error por la siguiente razon:" + razon);
        agente.setEstado(EstadoError.instancia());
    }

    public String estadoFuncionamiento(AgenteReactivoImp agente) {
        log.warn("Mensaje de gestion no valido para este estado");
        return "";
    }
}
