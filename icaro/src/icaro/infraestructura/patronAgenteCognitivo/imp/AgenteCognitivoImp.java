/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.patronAgenteCognitivo.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados.EstadoCreado;
import icaro.infraestructura.patronAgenteCognitivo.gestion.imp.estados.ItfEstado;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.FactoriaPercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.ControlCognitivoAbstracto;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.FactoriaControlCognitivo;
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
public class AgenteCognitivoImp extends AgenteCognitivo {

    //private LinkedBlockingQueue<Object> queue = null;
    private ItfEstado estado = null;
    
    private String nombre;
    //private ProcesadorMensajes procesadorMensajes;
    //private ItfMotorReglas motorReglas;
    private Log log = LogFactory.getLog(AgenteCognitivoImp.class);
    
    private PercepcionAgenteCognitivo percepcion;
    private ControlCognitivoAbstracto control;
    
    private ItfUsoRecursoTrazas trazas;

    public AgenteCognitivoImp(String nombre) throws Exception {
        
    	this.nombre =nombre;

		control = FactoriaControlCognitivo
				.instancia().crearControlCognitivo(this);
		percepcion = FactoriaPercepcionAgenteCognitivo
				.instancia().crearPercepcionAgenteCognitivo(this);
    	estado = EstadoCreado.instancia();
    	
    	
		try {
			trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) {
			log.error("Error al enviar nueva traza al recurso de trazas", e);
		}
		trazas.aceptaNuevaTraza(new InfoTraza(this.getNombre(),"Estado: "+ estado.toString(),NivelTraza.info));

    }

    public void aceptaMensaje(MensajeAgente mensaje) {
        estado.aceptaMensaje(this, mensaje);
    }

    public void arranca() {
        estado.arranca(this);
    }

    public void para() {
        estado.para(this);
    }

    public void termina() {
        estado.termina(this);
    }

    public void continua() {
        estado.continua(this);
    }

    public int obtenerEstado() {
    	trazas.aceptaNuevaTraza(new InfoTraza(this.getNombre(),"Estado: "+ estado.toString(),NivelTraza.info));
        return estado.obtenerEstado(this);
        
    }
/*
    public String descripcion() {
        return estado.descripcion(this);
    }
*/

    public ItfEstado getEstado() {
        return estado;
    }

    public void setEstado(ItfEstado estado) {
        this.estado = estado;
        trazas.aceptaNuevaTraza(new InfoTraza(this.getNombre(),"Cambio de estado: "+ estado.toString(),NivelTraza.info));
    }

	public PercepcionAgenteCognitivo getPercepcion() {
		return percepcion;
	}

	public void setPercepcion(PercepcionAgenteCognitivo percepcion) {
		this.percepcion = percepcion;
	}

	public ControlCognitivoAbstracto getControl() {
		return control;
	}

	public void setControl(ControlCognitivoAbstracto control) {
		this.control = control;
	}


	public void aceptaEvento(EventoInput evento) {
		estado.aceptaEvento(this,evento);
	}

	public String getNombre() {
		return this.nombre;
	}
}
