package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp2;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.ControlCognitivoAbstracto;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Evidencia;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ProcesadorItems implements ItfProcesadorItems {

	private static final int CAPACIDAD_BUZON_EVIDENCIAS = 15;
	private LinkedBlockingQueue<Evidencia> buzonEvidencias;

	

	private EnvioEvidenciaThread envioEvidencias;

	private ControlCognitivoAbstracto control;
	private Object item;
	private AgenteCognitivo agente;

	private Log log = LogFactory.getLog(ProcesadorItems.class);
	private ItfUsoRecursoTrazas trazas;

	public ProcesadorItems(AgenteCognitivo agente) {
		this.agente = agente;

		this.buzonEvidencias = new LinkedBlockingQueue<Evidencia>(
				CAPACIDAD_BUZON_EVIDENCIAS);
		this.control = agente.getControl();
		this.envioEvidencias = new EnvioEvidenciaThread();
		try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) {	e.printStackTrace(); }
	}

	// De momento filtra los items que no tengan como destinatario este agente.
	private boolean filtrarItem() {
		String nombreAgente = agente.getNombre();
		if (item instanceof EventoInput) {
			EventoInput evento = (EventoInput) item;

			if (!evento.getDestino().equals(nombreAgente)) {
				log.warn("El evento" + evento.toString()
						+ " no tiene como destino el agente " + nombreAgente +"\n Destino del evento: "+ evento.getDestino());
				trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getNombre(),"Percepción: El evento" + evento.toString()
						+ " no tiene como destino el agente " + nombreAgente +"\n Destino del evento: "+ evento.getDestino(),NivelTraza.info ));
				return false;
			} else
				return true;
		} else if (item instanceof MensajeAgente) {
			MensajeAgente mensaje = (MensajeAgente) item;
			if (!mensaje.getReceptor().equals(nombreAgente)) {
				log.warn("El mensaje" + mensaje.toString()
						+ " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor());
				trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getNombre(),"Percepción: El mensaje" + mensaje.toString()
						+ " no tiene como receptor el agente " + nombreAgente+"\n Destinatario del mensaje: "+mensaje.getReceptor(),NivelTraza.info ));
				return false;
			} else
				return true;
		} else {
			log.error("Item " + item + " no reconocido");
			trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getNombre(),"Percepción: Item "+ item + " no reconocido",NivelTraza.error));
			return false;
		}
		

	}

	//De momento no hace nada
	private void decodificarItem() {
		/*
		if (item instanceof EventoInput) {
			EventoInput evento = (EventoInput) item;
		} else if (item instanceof MensajeAgente) {
			MensajeAgente mensaje = (MensajeAgente) item;
		} else
			log.error("Item " + item + " no reconocido");
			*/
	}
//Genera una evidencia a partir de un evento o un mensaje
	private Evidencia generarEvidencia() {
		Evidencia evidencia = null;
		if (item instanceof EventoInput) {
			EventoInput evento = (EventoInput) item;
			evidencia = new Evidencia();
			log.debug("Evento null?: "+ evento==null);
			log.debug("Destino: "+ evento.getDestino());
			evidencia.setOrigen(evento.getOrigen());
			//TODO Cambiar
			evidencia.setContenido(evento.getParametros()[0]);
		} else if (item instanceof MensajeAgente) {
			MensajeAgente mensaje = (MensajeAgente) item;
			evidencia = new Evidencia();
			evidencia.setOrigen(mensaje.getEmisor());
			evidencia.setContenido(mensaje.getContenido());
		} else {
			log.error("Item " + item + " no reconocido");
			trazas.aceptaNuevaTraza(new InfoTraza (this.agente.getNombre(),"Percepción: Item "+ item + " no reconocido",NivelTraza.error));
		}
		return evidencia;
	}

	
	public boolean procesarItem(Object item) {
		Evidencia ev = procesar(item);
		if (!encolarEvidencia(ev))
			return false;
		else
			return true;
	}

	private Evidencia procesar(Object item) {
		this.item = item;
		if (filtrarItem()) {
			decodificarItem();
			return generarEvidencia();
		} else
			return null;
	}

	private boolean encolarEvidencia(Evidencia ev) {
		if (ev != null)
			if (!buzonEvidencias.offer(ev))
				return false;
			else
				return true;
		else
			return true;
	}
	
	
//Envia evidencias por la interfaz del control.
	private class EnvioEvidenciaThread extends Thread {
		private static final long TIEMPO_ESPERA = 10;
		private boolean termina;

		public EnvioEvidenciaThread() {
			setDaemon(true);
			termina = false;
		}

		public void run() {
			while (!termina) {
				try {
					Evidencia ev = buzonEvidencias.take();
					if (!control.aceptaEvidencia(ev)) {
						sleep(TIEMPO_ESPERA*1000);
					}
				} catch (InterruptedException e) {
					log.debug("Interrumpida la espera de nueva evidencia en el buzon de evidencias");
				}
			}

		}

		public void termina() {
			this.termina = true;
			this.interrupt();
		}
	}

	
	public void termina() {
		envioEvidencias.termina();		
		this.buzonEvidencias.clear();
	}
	
	public void arranca() {
		envioEvidencias.start();
	}

}
