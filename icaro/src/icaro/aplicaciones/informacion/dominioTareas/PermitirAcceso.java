/*
 * PermitirAcceso.java
 *
 * Creado 23 de abril de 2007, 12:50
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.informacion.dominioTareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

/**
 * 
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class PermitirAcceso extends Tarea {

	/** Crea una nueva instancia de PermitirAcceso */
	public PermitirAcceso() {
	}

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
			// va.mostrarMensajeAceptacion();
			va.mostrarMensajeInformacion("Acceso permitido", "Acceso permitido");
			MensajeAgente resultado = new MensajeAgente();
			resultado.setEmisor("Task:PermitirAcceso");
			resultado.setReceptor(this.getAgente().getNombre());
			resultado.setContenido(new String("visualizacion ok"));
			
			//TODO: pedir terminación al agente...
			this.getAgente().aceptaMensaje(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
