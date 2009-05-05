package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamiento;


import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoValidada;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionAcceso extends AccionesSemanticasAgenteReactivo {
	
	/**
	 * @uml.property  name="visualizacion"
	 * @uml.associationEnd  
	 */
	private ItfUsoVisualizadorAcceso visualizacion;
	/**
	 * @uml.property  name="persistencia1"
	 * @uml.associationEnd  
	 */
	private ItfUsoPersistencia Persistencia1;
	/**
	 * @uml.property  name="agenteAcceso"
	 * @uml.associationEnd  
	 */
	private ItfUsoAgenteReactivo agenteAcceso;


	public void arranque(){
		
		try {
			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");
			visualizacion.mostrarVisualizadorAcceso(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Acceso en accion semantica 'arranque()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void valida(InfoAccesoSinValidar infoUsuario) {
		boolean ok = false;
		
//		InfoAccesoSinValidar datos = new InfoAccesoSinValidar(username,password);
		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"Persistencia1");
			ok = Persistencia1.compruebaUsuario(infoUsuario.tomaUsuario(),infoUsuario.tomaPassword());
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Comprobando usuario...", 
															  InfoTraza.NivelTraza.debug));
			}catch(Exception e){e.printStackTrace();}
		}

		catch (Exception ex){
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema en la Persistencia1 al comprobar el usuario y el password", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e){e.printStackTrace();}
		}
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{infoUsuario.tomaUsuario(), infoUsuario.tomaPassword()};
			if(ok){
				agenteAcceso.aceptaEvento(new EventoRecAgte("usuarioValido",datosEnvio,this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso"));
			}
			else{
				agenteAcceso.aceptaEvento(new EventoRecAgte("usuarioNoValido", datosEnvio,this.nombreAgente,this.nombreAgente));
			}
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}
	}
	
	public void mostrarUsuarioAccede(String username, String password){
		InfoAccesoValidada dav = new InfoAccesoValidada(username ,password);
		
		try{
			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");
			visualizacion.mostrarMensajeInformacion("AccesoCorrecto", "El usuario "+dav.tomaUsuario()+" ha accedido al sistema. \n A partir de aqu� deber�a continuar la aplicaci�n.");
			visualizacion.cerrarVisualizadorAcceso();

		}
		catch (Exception ex) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema al abrir el visualizador de Acceso", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}
		pedirTerminacionGestorAgentes();
	}
	
	public void mostrarUsuarioNoAccede(String username, String password){
		InfoAccesoSinValidar dav = new InfoAccesoSinValidar(username ,password);
		
		try{
			visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAcceso1");
			visualizacion.mostrarMensajeError("Acceso Incorrecto", "El usuario "+dav.tomaUsuario()+" no ha accedido al sistema.");
		}

		catch (Exception ex) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema al abrir el visualizador de Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
		}
		pedirTerminacionGestorAgentes();
	}
	
	public void terminacion() {
		try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Terminando agente: "+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso1", 
														  InfoTraza.NivelTraza.debug));
		}catch(Exception e2){e2.printStackTrace();}
		logger.debug("Terminando agente: "+this.nombreAgente);
	}
	
	public void clasificaError(){
	/*
	 *A partir de esta funci�n se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la pol�tica es que todos los errores son cr�ticos.  
	 */
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteAcceso.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
		}
	}
	public void pedirTerminacionGestorAgentes(){
		try {
			/*ItfUsoAgenteReactivo itfUsoGestorOrgan = (ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz
			("Itf_Uso_Gestor_Organizacion");
			itfUsoGestorOrgan.aceptaEvento(new EventoRecAgte("terminar_gestores_y_gestor_organizacion","AgenteAccesoUso","AgenteAccesoUso"));*/
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
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteAcceso.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
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