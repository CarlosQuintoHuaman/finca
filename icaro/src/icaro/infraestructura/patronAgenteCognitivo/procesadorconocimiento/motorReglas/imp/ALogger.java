/*
 * AgendaEventListenerForLog.java
 *
 * Creado en 24 de agosto de 2007, 9:43
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.WorkingMemory;
import org.drools.event.ActivationCancelledEvent;
import org.drools.event.ActivationCreatedEvent;
import org.drools.event.AfterActivationFiredEvent;
import org.drools.event.AgendaEventListener;
import org.drools.event.BeforeActivationFiredEvent;



/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ALogger implements AgendaEventListener{
    
	private AgenteCognitivo agente;
	private ItfUsoRecursoTrazas trazas;
    private Log log = LogFactory.getLog(getClass());
    
    /** Creates a new instance of AgendaEventListenerForLog */
    public ALogger(AgenteCognitivo agente) {
    	this.agente = agente;
    	try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) { e.printStackTrace(); }
    }

    public void activationCreated(ActivationCreatedEvent activationCreatedEvent, WorkingMemory workingMemory) {
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: regla activada -"+activationCreatedEvent.getActivation().getRule().getName(),NivelTraza.info));
        log.debug("Activacion creada:"+activationCreatedEvent.toString());
    }

    public void activationCancelled(ActivationCancelledEvent activationCancelledEvent, WorkingMemory workingMemory) {
    	
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: cancelada la activación de la regla -"+activationCancelledEvent.getActivation().getRule().getName(),NivelTraza.info));
        log.debug("Activacion cancelada:"+activationCancelledEvent.toString());
    }

    public void beforeActivationFired(BeforeActivationFiredEvent beforeActivationFiredEvent, WorkingMemory workingMemory) {
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: Estado del motor antes del disparo de la regla: -"+beforeActivationFiredEvent.getActivation().getRule().getName()+","+beforeActivationFiredEvent.getActivation().getTuple(),NivelTraza.info));
        log.debug("Antes del disparo de la activacion:"+beforeActivationFiredEvent.toString());
    }

    public void afterActivationFired(AfterActivationFiredEvent afterActivationFiredEvent, WorkingMemory workingMemory) {
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: Estado del motor después del disparo de la regla: -"+afterActivationFiredEvent.getActivation().getRule().getName(),NivelTraza.info));
        log.debug("Despues del disparo de la activacion:"+afterActivationFiredEvent.toString());
    }

    
}
