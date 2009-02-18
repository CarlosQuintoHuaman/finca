package icaro.aplicaciones.informacion.dominioTareas;

import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

public class EnviarInformacionAAgente extends Tarea {

	/**
	 * @param params[0]: receptor del mensaje
	 *        params[1]: contenido mensaje
	 * 
	 */
	@Override
	public void ejecutar(Object... params) {
		ItfUsoRecursoTrazas trazas = null;
		try {
			trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		InfoTraza traza = new InfoTraza(this.getAgente().getNombre(),"Tarea EnviarInformacionAAgente: Realizando tarea \"EnviarInformacionAAgente\"...",NivelTraza.info);
		try {
			trazas.aceptaNuevaTraza(traza);
			String receptor = (String) params[0];
			Object contenido = params[1];
			String nombreAgente = this.getAgente().getNombre();
			MensajeAgente mensaje = new MensajeAgente();
			mensaje.setEmisor(nombreAgente);
			mensaje.setReceptor(receptor);
			mensaje.setContenido(contenido);
		    
			ItfUsoAgenteCognitivo agente = (ItfUsoAgenteCognitivo) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+receptor);
			InterfazGestion gestionAgente = (InterfazGestion) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_GESTION+receptor);
			
			boolean estadoActivo = gestionAgente.obtenerEstado() == InterfazGestion.ESTADO_ACTIVO;
			int segs = 3;
			int numIntentos = 10;
			while (!estadoActivo && numIntentos !=0) {
				traza = new InfoTraza(nombreAgente,"Tarea EnviarInformacionAAgente: El destinatario "+receptor+" no está listo para recibir mensajes, se volverá a intentar dentro de "+segs+" segundos...",NivelTraza.debug);
				trazas.aceptaNuevaTraza(traza);
				Thread.sleep(segs * 1000);
				traza = new InfoTraza(nombreAgente,"Tarea EnviarInformacionAAgente: Obteniendo estado del destinatario "+receptor,NivelTraza.debug);
				trazas.aceptaNuevaTraza(traza);
				estadoActivo = gestionAgente.obtenerEstado() == InterfazGestion.ESTADO_ACTIVO;
				numIntentos--;
			}
			if (estadoActivo) {
				traza = new InfoTraza(nombreAgente,"Tarea EnviarInformacionAAgente: Enviando informacion al agente "+receptor+" ...",NivelTraza.info);
			    trazas.aceptaNuevaTraza(traza);
			    agente.aceptaMensaje(mensaje);
			}
					
			else {
				traza = new InfoTraza(nombreAgente,"Tarea EnviarInformacionAAgente: Intentos agotados. Abortando el envío del mensaje al agente "+receptor+" ...",NivelTraza.info);
			    trazas.aceptaNuevaTraza(traza);
			}			
		} catch (Exception e) {
			traza = new InfoTraza(this.getAgente().getNombre(),"Error al realizar la tarea: EnviarInformacionAAgente\n"+e.getMessage(),NivelTraza.error);
			trazas.aceptaNuevaTraza(traza);
		}

	}

}
