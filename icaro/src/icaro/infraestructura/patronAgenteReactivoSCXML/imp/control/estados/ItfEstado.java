/*
 * IEstado.java
 *
 * Creado en 22 de noviembre de 2007, 10:25
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp.control.estados;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.AgenteReactivoImp;





/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public interface ItfEstado {
    public void arranca(AgenteReactivoImp agente);
    public void para(AgenteReactivoImp agente);
    public void termina(AgenteReactivoImp agente);
    public void continua(AgenteReactivoImp agente);
    public int monitorizacion(AgenteReactivoImp agente);
    public String estadoFuncionamiento(AgenteReactivoImp agente);
    public void aceptaEvento(AgenteReactivoImp agente,EventoInput e);
    public void falloTemporal(AgenteReactivoImp agente,String razon);
    public void error(AgenteReactivoImp agente,String razon);
}
