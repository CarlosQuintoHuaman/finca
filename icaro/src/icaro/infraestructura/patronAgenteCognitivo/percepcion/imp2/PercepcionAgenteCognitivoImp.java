package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp2;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.percepcion.PercepcionAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PercepcionAgenteCognitivoImp extends PercepcionAgenteCognitivo {

	private static final int CAPACIDAD_BUZON = 15;
	private ItfProcesadorItems procesador;
	private LinkedBlockingQueue<Object> buzon;
	private EnvioItemsThread envioItems;
	
	private AgenteCognitivo agente;

	private Log log = LogFactory.getLog(PercepcionAgenteCognitivoImp.class);
	private ItfUsoRecursoTrazas trazas;

	public PercepcionAgenteCognitivoImp(AgenteCognitivo agente) {
		buzon = new LinkedBlockingQueue<Object>(CAPACIDAD_BUZON);
		this.agente = agente;
		this.procesador = new ProcesadorItems(agente);
		this.envioItems = new EnvioItemsThread();
		try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch(Exception e) {	e.printStackTrace(); }
	}

	public PercepcionAgenteCognitivoImp(ItfProcesadorItems procesador) {
		this.procesador = procesador;
		buzon = new LinkedBlockingQueue<Object>(CAPACIDAD_BUZON);
	}


	public void aceptaEvento(EventoInput evento) {
		trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getNombre(),"Percepción: Ha llegado un nuevo evento desde "+((EventoInput)evento).getOrigen(),NivelTraza.info));
		buzon.offer(evento);
	}

	
	public void aceptaMensaje(MensajeAgente mensaje) {
		trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getNombre(),"Percepción: Ha llegado un nuevo mensaje desde "+mensaje.getEmisor(),NivelTraza.info));	
		buzon.offer(mensaje);
	}

	private class EnvioItemsThread extends Thread {

		private static final long TIEMPO_ESPERA = 10;
		
		private boolean termina;

		public EnvioItemsThread() {
			this.setDaemon(true);
			termina = false;
		}

		public void run() {
			while (!termina) {
				Object item = null;
				try {
					log
							.debug("Recogiendo item desde el buzon de items de la percepcion...");
					item = buzon.take();
					if (item != null) {
						boolean seguirEnviando = procesador.procesarItem(item);
						if (!seguirEnviando)
							Thread.sleep(10);
					}
					else 
						log.debug("Item == NULL!!!!!");
				} catch (InterruptedException e) {
					log.debug("Interrumpida la espera de nuevo item en el buzon de items");
				}
			}
			log.debug("Terminando EnvioItems");
		}

		public void termina() {
			this.termina = true;
			envioItems.interrupt();
		}
	}

	
	public void termina() {
		this.envioItems.termina();
		this.buzon.clear();
		this.procesador.termina();
	}

	public void arranca() {
		this.envioItems.start();
		this.procesador.arranca();
	}

	
	
}
