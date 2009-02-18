/*
 * ValidarDatos.java
 *
 * Creado 23 de abril de 2007, 12:49
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.informacion.dominioTareas;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.MensajeAgente;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas.Tarea;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

/**
 * 
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ValidarDatos extends Tarea {

	/** Crea una nueva instancia de ValidarDatos */
	public ValidarDatos() {
	}

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoVisualizadorAcceso va = (ItfUsoVisualizadorAcceso) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
			// va.mostrarVentana(false);
			va.cerrarVisualizadorAcceso();
			DatosAccesoSinValidar datos = (DatosAccesoSinValidar) params[0];
			MensajeAgente resultado = new MensajeAgente();
			resultado.setEmisor("Task:ValidarDatos");
			resultado.setReceptor(this.getAgente().getNombre());
			ItfUsoPersistencia itfUsoPersistencia = (ItfUsoPersistencia) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO + "Persistencia");
			boolean usuarioValido = itfUsoPersistencia.compruebaUsuario(datos
					.tomaUsuario(), datos.tomaPassword());
			resultado.setContenido(new Boolean(usuarioValido));
			this.getAgente().aceptaMensaje(resultado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
