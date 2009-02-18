/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfEnvioHechos;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 *
 * @author carf
 */
public class GestorTareasImp implements ItfGestorTareas{
    private AgenteCognitivo agente;
    private ItfEnvioHechos envioHechos;
    private Log log = LogFactory.getLog(GestorTareasImp.class);
    
    public GestorTareasImp(AgenteCognitivo agente,ItfEnvioHechos envioHechos){
        this.agente = agente;
        this.envioHechos = envioHechos;
    }

    public Tarea crearTarea(Class clase) throws Exception {
        Tarea tarea = (Tarea)clase.newInstance();
        tarea.setEnvioHechos(envioHechos);
        tarea.setAgente(agente);
        log.debug("Tarea creada:"+clase.getName());
        ItfUsoRecursoTrazas trazas = null;
		try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
			trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Gestor de Tareas: Se ejecutará la tarea "+tarea.getClass().getName(),NivelTraza.info));
		} catch (Exception e) {	e.printStackTrace(); }
        return new TareaProxy(tarea);
    }

}
