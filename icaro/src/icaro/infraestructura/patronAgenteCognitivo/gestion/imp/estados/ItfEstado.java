/*
 * IEstado.java
 *
 * Creado en 22 de noviembre de 2007, 10:25
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.patronAgenteCognitivo.imp.AgenteCognitivoImp;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public interface ItfEstado {
    public void arranca(AgenteCognitivoImp agente);
    public void para(AgenteCognitivoImp agente);
    public void termina(AgenteCognitivoImp agente);
    public void continua(AgenteCognitivoImp agente);
    public int obtenerEstado(AgenteCognitivoImp agente);
   // public String descripcion(AgenteCognitivoImp agente);
    public void aceptaMensaje(AgenteCognitivoImp agente,MensajeAgente e);
 /*   public void falloTemporal(AgenteCognitivoImp agente,String razon);
    public void error(AgenteCognitivoImp agente,String razon);*/
	public void aceptaEvento(AgenteCognitivoImp agente, EventoInput evento);
}
