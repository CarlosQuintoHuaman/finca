/*
 * SolicitarDatos.java
 *
 * Creado 23 de abril de 2007, 12:52
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.informacion.dominioTareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

/**
 * 
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class SolicitarDatos extends Tarea {

	/** Crea una nueva instancia de SolicitarDatos */
	public SolicitarDatos() {
	}

	@Override
	public void ejecutar(Object... params) {
		try {
		ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) RepositorioInterfaces
				.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
		va.mostrarVisualizadorAcceso(this.getAgente().getNombre(),NombresPredefinidos.TIPO_COGNITIVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
