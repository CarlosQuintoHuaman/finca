/*
 * DenegarAcceso.java
 *
 * Creado 23 de abril de 2007, 12:49
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
public class DenegarAcceso extends Tarea {

	/** Crea una nueva instancia de DenegarAcceso */
	public DenegarAcceso() {
	}

	@Override
	public void ejecutar(Object... params) {
		MensajeAgente resultado = new MensajeAgente();
		resultado.setEmisor("Task:DenegarAcceso");
		resultado.setReceptor(this.getAgente().getNombre());
		try {
			ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
			va.mostrarMensajeError("Acceso denegado", "Acceso denegado");

			resultado.setContenido(new String("ejecucion ok"));
			this.getAgente().aceptaMensaje(resultado);
			
			//TODO: pedir terminación al gestor de agentes...
		} catch (Exception e) {
			resultado.setContenido("error");
			this.getAgente().aceptaMensaje(resultado);
			e.printStackTrace();

		}
	}
}
