/*
 * EstadoError.java
 *
 * Creado en 22 de noviembre de 2007, 10:58
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
public class EstadoError extends EstadoAbstracto{
    private Log log = LogFactory.getLog(EstadoActivo.class);
    private static EstadoError instancia;
    public static EstadoError instancia(){
        if(instancia == null)
            instancia = new EstadoError();
        return instancia;
    }

    public int monitorizacion(AgenteReactivoImp agente) {
        return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
    }
    
}
