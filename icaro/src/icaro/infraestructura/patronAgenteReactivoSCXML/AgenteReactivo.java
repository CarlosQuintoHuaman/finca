/*
 * AgenteReactivoAbstracto.java
 *
 * Creado en 20 de noviembre de 2007, 11:57
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.ItfEventos;


/**
 *Reuni&oacute;n de las interfaces de uso y gesti&oacute;n del agente.
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class AgenteReactivo implements InterfazGestion,ItfEventos{
    public abstract void setGestorAReportar(String nombreGestor);
}
