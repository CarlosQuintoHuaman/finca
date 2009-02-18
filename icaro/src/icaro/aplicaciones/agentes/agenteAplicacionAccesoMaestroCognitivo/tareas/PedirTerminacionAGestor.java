package icaro.aplicaciones.agentes.agenteAplicacionAccesoMaestroCognitivo.tareas;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

public class PedirTerminacionAGestor extends Tarea {

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoAgenteReactivo gestorAgentes = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			gestorAgentes.aceptaEvento(new EventoInput("peticion_terminar_todo", null, null));
			
			MensajeAgente mensaje = new MensajeAgente();
			mensaje.setEmisor("Task:PedirTerminacionAGestor");
			mensaje.setReceptor(this.getAgente().getNombre());
			mensaje.setContenido("peticionEnviada");
			this.getAgente().aceptaMensaje(mensaje);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
