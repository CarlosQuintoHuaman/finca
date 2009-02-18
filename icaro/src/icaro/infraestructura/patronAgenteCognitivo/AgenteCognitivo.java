/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.ControlCognitivoAbstracto;



/**
 *
 * @author carf
 */
public abstract class AgenteCognitivo implements ItfUsoAgenteCognitivo,InterfazGestion{

	public abstract String getNombre();

	public abstract ControlCognitivoAbstracto getControl(); 
	public abstract PercepcionAgenteCognitivo getPercepcion();
}
