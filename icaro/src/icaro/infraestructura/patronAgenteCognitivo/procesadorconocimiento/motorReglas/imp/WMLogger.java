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
import org.drools.event.ObjectAssertedEvent;
import org.drools.event.ObjectModifiedEvent;
import org.drools.event.ObjectRetractedEvent;
import org.drools.event.WorkingMemoryEventListener;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class WMLogger implements WorkingMemoryEventListener{
    
    private Log log = LogFactory.getLog(getClass());
    private AgenteCognitivo agente;
    private ItfUsoRecursoTrazas trazas;

    public WMLogger(AgenteCognitivo agente) {
    	this.agente = agente;
    	try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void objectAsserted(ObjectAssertedEvent arg0) {
		trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas - Hecho agregado: "+arg0.getObject().toString(),NivelTraza.info));
        log.debug("Hecho agregado:"+arg0.getObject().toString());
    }

    public void objectModified(ObjectModifiedEvent arg0) {
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas - Hecho modificado: "+arg0.getObject().toString(),NivelTraza.info));
    	log.debug("Hecho modificado:"+arg0.getObject().toString());
    }

    public void objectRetracted(ObjectRetractedEvent arg0) {
    	trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas - Hecho eliminado: "+arg0.getOldObject().toString(),NivelTraza.info));
        log.debug("Hecho eliminado:"+arg0.getOldObject().toString());
    }
    
}
