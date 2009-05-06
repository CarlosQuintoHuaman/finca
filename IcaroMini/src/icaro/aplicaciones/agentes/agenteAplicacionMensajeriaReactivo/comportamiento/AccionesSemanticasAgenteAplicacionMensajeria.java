package icaro.aplicaciones.agentes.agenteAplicacionMensajeriaReactivo.comportamiento;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.visualizacionMensajeria.ItfUsoVisualizadorMensajeria;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
import icaro.aplicaciones.recursos.persistenciaMensajeria.ItfUsoPersistenciaMensajeria;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionMensajeria extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorMensajeria visualizacion;
	private ItfUsoPersistenciaMensajeria persistencia;
	private ItfUsoAgenteReactivo agenteMensajeria;


	public void cargarMensajes(String usuario, String origen){
		
		try {
			//Se obtiene la persistencia
			persistencia = (ItfUsoPersistenciaMensajeria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMensajeria1");
			
			ArrayList<InfoMensaje> r = persistencia.getMensajes(usuario);
			
			ItfUsoAgenteReactivo agenteOrigen = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+origen);
			
			agenteOrigen.aceptaEvento(new EventoRecAgte("mostrarMensajes", r, "AgenteAplicacionMensajeria1", origen));
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de cargar los mensajes",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Mensajeria en accion semantica 'pintaVentanaMensajeria()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void enviarMensaje(String usuario){
		
		try {
			//Se obtiene el visualizador
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorMensajeria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionMensajeria1");
			
			// Ejemplo de algo que podemos hacer con el
			visualizacion.mostrarVisualizadorMensajeNuevo(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, usuario);
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de cargar los mensajes",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Mensajeria en accion semantica 'pintaVentanaMensajeria()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			visualizacion.cerrarVisualizadorMensajeNuevo();
			
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Terminando agente: "+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha1", 
														  InfoTraza.NivelTraza.debug));
		} catch
			(Exception e2){e2.printStackTrace();
		}
		logger.debug("Terminando agente: "+this.nombreAgente);
	}
	
	public void clasificaError(){
	/*
	 *A partir de esta funcion se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la politica es que todos los errores son criticos.  
	 */
		try {
			agenteMensajeria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteMensajeria.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Ficha", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
		}
	}
	public void pedirTerminacionGestorAgentes(){
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte("peticion_terminar_todo",this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo",e);
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			try{
				agenteMensajeria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteMensajeria.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
			}
			catch(Exception exc){
				try {
					ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
							trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
																  "Fallo al enviar un evento error.", 
																  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
	}
}