package icaro.aplicaciones.agentes.agenteAplicacionLoginReactivo.comportamiento;


import icaro.aplicaciones.informacion.dominioClases.aplicacionLogin.InfoLogin;
import icaro.aplicaciones.recursos.visualizacionLogin.ItfUsoVisualizadorLogin;
import icaro.aplicaciones.recursos.persistenciaHistorial.ItfUsoPersistenciaHistorial;
import icaro.aplicaciones.recursos.persistenciaLogin.ItfUsoPersistenciaLogin; 
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionLogin extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorLogin visualizacion;
	private ItfUsoPersistenciaLogin persistencia;
	private ItfUsoAgenteReactivo agenteLogin;
	private ItfUsoAgenteReactivo agenteMedico;
	private ItfUsoAgenteReactivo agenteSecretaria;

	
	// Ejemplo de accion semantica sencilla
	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoInput. El nombre de este metodo debe corresponder con el nombre
	// de alguna accion definida en el automata
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
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Login en accion semantica 'pintaVentanaLogin()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void compruebaUsuario(String u, String p) {
		try {
			agenteMedico = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"AgenteAplicacionMedico1");
			
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"AgenteAplicacionSecretaria1");
				
			String tipo = persistencia.compruebaUsuario(u, p);
			
			if (tipo == "Secretaria") {
				agenteSecretaria.aceptaEvento(new EventoInput("inicio", u,"AgenteAplicacionLogin1", "AgenteAplicacionSecretaria1"));
				visualizacion.cerrarVisualizadorLogin();
			}
			else if (tipo == "Medico") {
				agenteMedico.aceptaEvento(new EventoInput("inicio","AgenteAplicacionLogin1", "AgenteAplicacionMedico1"));
				visualizacion.cerrarVisualizadorLogin();
			}
			else {
				visualizacion.mostrarMensajeError("Fallo de Login", "Nombre de usuario o contrase�a incorrectos");
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Ejemplo de otra accion semantica mas compleja
	// OJO: Cada vez que se quiera enviar una traza hay que meterla en un bloque try catch
	// tal como se ve aqui	
	public void inserta(String nombre, String apell1, String telf) {
		boolean ok = false;
		
		// Hay que crear un objeto con los datos para enviar con el evento
		InfoLogin datos = new InfoLogin(nombre,apell1,telf);
		
		try {
			persistencia = (ItfUsoPersistenciaLogin) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"Persistencia1");
			//ok = Persistencia1.compruebaUsuario(datos.tomaUsuario(),datos.tomaPassword());
			ok=true;
			
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Comprobando usuario...", 
															  InfoTraza.NivelTraza.debug));
			}catch(Exception e){e.printStackTrace();}
		}

		catch (Exception ex){
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema en la Persistencia1 al comprobar cita", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e){e.printStackTrace();}
		}
		try {
			// Hay que coger la instancia del agente para poder enviarle eventos
			agenteLogin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
						
			if(ok){
				// Se envia el evento sin datos ya que no lo requiere
				agenteLogin.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Login"));
				// Si hubiera que enviar datos con el evento se haria asi:
				// agenteLogin.aceptaEvento(new EventoInput("correcto",infoLogin, this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Login"));
			}
			
			// Aqui se pueden seguir haciendo cosas con la visualizacion, trazas, etc
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}
	}
	
	
	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			visualizacion.cerrarVisualizadorLogin();
			
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
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
			agenteLogin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteLogin.aceptaEvento(new EventoInput("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Ficha", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
		}
	}
	public void pedirTerminacionGestorAgentes(){
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput("peticion_terminar_todo",this.nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo",e);
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			try{
				agenteLogin = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteLogin.aceptaEvento(new EventoInput("error",this.nombreAgente,this.nombreAgente));
			}
			catch(Exception exc){
				try {
					ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
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