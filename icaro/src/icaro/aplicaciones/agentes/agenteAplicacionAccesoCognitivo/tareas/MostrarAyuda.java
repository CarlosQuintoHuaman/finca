/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoCognitivo.tareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

/**
 * 
 * @author carf
 */
public class MostrarAyuda extends Tarea {

	@Override
	public void ejecutar(Object... arg0) {
		try {
			ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
			// va.mostrarAyuda();
			va.mostrarMensajeInformacion("Ayuda", "Ayuda....");
			MensajeAgente resultado = new MensajeAgente();
			resultado.setEmisor("Task:MostrarAyuda");
			resultado.setReceptor(NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"AccesoCognitivo");			resultado.setContenido(new String("AyudaMostrada"));
			this.getAgente().aceptaMensaje(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
