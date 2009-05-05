package icaro.aplicaciones.agentes.agenteAplicacionLoginReactivo.comportamiento;


import icaro.aplicaciones.recursos.visualizacionLogin.ItfUsoVisualizadorLogin;
import icaro.aplicaciones.recursos.persistenciaLogin.ItfUsoPersistenciaLogin; 
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class AccionesSemanticasAgenteAplicacionLogin extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorLogin visualizacion;
	private ItfUsoPersistenciaLogin persistencia;
	private ItfUsoAgenteReactivo agenteLogin;
	private ItfUsoAgenteReactivo agenteMedico;
	private ItfUsoAgenteReactivo agenteSecretaria;
	private ItfUsoAgenteReactivo agenteAdmin;

	
	/**
	 * Muestra por pantalla la ventana de Login
	 */
	public void pintaVentanaLogin(){
		
		try {
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorLogin) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionLogin1");
			
			// Ejemplo de algo que podemos hacer con el
			visualizacion.mostrarVisualizadorLogin(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			//Y la persistencia
			persistencia = (ItfUsoPersistenciaLogin) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaLogin1");
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Login en accion semantica 'pintaVentanaLogin()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Dado un usuario y un password comprueba si esta en la BD
	 * @param u Usuario
	 * @param p Password
	 */
	public void compruebaUsuario(String u, String p) {
		try {
			agenteMedico = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"AgenteAplicacionMedico1");
			
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"AgenteAplicacionSecretaria1");
			
			agenteAdmin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"AgenteAplicacionAdmin1");
				
			String tipo = persistencia.compruebaUsuario(u, p);
			
			if (tipo == "Secretaria") {
				agenteSecretaria.aceptaEvento(new EventoRecAgte("inicio", u,"AgenteAplicacionLogin1", "AgenteAplicacionSecretaria1"));
				visualizacion.cerrarVisualizadorLogin();
			}
			else if (tipo == "Medico") {
				agenteMedico.aceptaEvento(new EventoRecAgte("inicio", u, "AgenteAplicacionLogin1", "AgenteAplicacionMedico1"));
				visualizacion.cerrarVisualizadorLogin();
			}
			else if (tipo == "Admin") {
				agenteAdmin.aceptaEvento(new EventoRecAgte("inicio","AgenteAplicacionLogin1", "AgenteAplicacionAdmin1"));
				visualizacion.cerrarVisualizadorLogin();
			}
			else {
				visualizacion.mostrarMensajeError("Fallo de Login", "Nombre de usuario o contraseña incorrectos");
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			visualizacion.cerrarVisualizadorLogin();
			
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
			agenteLogin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteLogin.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			try {
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
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			try{
				agenteLogin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteLogin.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
			}
			catch(Exception exc){
				try {
							trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
																  "Fallo al enviar un evento error.", 
																  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
	}
}