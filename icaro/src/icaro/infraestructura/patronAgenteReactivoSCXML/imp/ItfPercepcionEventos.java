/*
 * IPercepcionAgente.java
 *
 * Creado en 20 de noviembre de 2007, 11:56
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;

/**
 * Percepci&oacute;n del agente.
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public interface ItfPercepcionEventos {
    public void aceptaEvento(EventoInput e);
}
