package icaro.aplicaciones.agentes.agenteAplicacionAltaReactivo.comportamiento;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoValidados;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.aplicaciones.recursos.visualizacionAlta.ItfUsoVisualizadorAlta;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
/**
 *  Acciones Semánticas para un agente de aplicación
 *
 *@author     Felipe Polo
 *@created    3 de diciembre de 2007
 */

public class AccionesSemanticasAgenteAplicacionAlta extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorAccesoAlta visualizacionAccesoAlta; //ventana para el acceso
	private ItfUsoVisualizadorAlta visualizacionAlta; //ventana para las altas
	private ItfUsoPersistencia persistencia;
	private ItfUsoAgenteReactivo agenteAcceso;
	
	public void arranque(){
		try {			
			visualizacionAccesoAlta = (ItfUsoVisualizadorAccesoAlta) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAccesoAlta");
			visualizacionAccesoAlta.mostrarVisualizadorAccesoAlta();
		}
		
		catch (Exception ex) {
			logger.error("Ha habido un problema al abrir el visualizador de Acceso");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema al abrir el visualizador de Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			ex.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
	}
	
	public void valida(String username, String password) {
		boolean ok = false;
		
		DatosAccesoSinValidar datos = new DatosAccesoSinValidar(username,password);
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			persistencia = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"Persistencia");
			ok = persistencia.compruebaUsuario(datos.tomaUsuario(),datos.tomaPassword());
		}
		
		catch (Exception ex){
			logger.error("Ha habido un problema en la persistencia al comprobar el usuario y el password",ex);
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema en la persistencia al comprobar el usuario y el password", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			Object[] datosEnvio = new Object[]{datos.tomaUsuario(), datos.tomaPassword()};
			if(ok){
				agenteAcceso.aceptaEvento(new EventoInput("usuarioValido",datosEnvio,
						NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta")); 
														  //requiere los parámetros para mostrarlos
			}
			else{
				
				visualizacionAccesoAlta.mostrarMensajeAviso("Usuario no valido", "El usuario no se encuentra en la base de datos.\n        Se procede a darle de alta");
				agenteAcceso.aceptaEvento(new EventoInput("usuarioNoValido",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta")); //sin parámetros (nueva petición)
			}
		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
	}
	
	public void mostrarUsuarioAccede(String username, String password){
		DatosAccesoValidados dav = new DatosAccesoValidados(username ,password);
		
		try{
			visualizacionAccesoAlta = (ItfUsoVisualizadorAccesoAlta) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAccesoAlta");
			visualizacionAccesoAlta.mostrarMensajeInformacion("AccesoCorrecto", "El usuario "+dav.tomaUsuario()+" ha accedido al sistema. \n A partir de aquí debería continuar la aplicación.");
		}
		
		catch (Exception ex) {
			logger.error("Ha habido un problema al abrir el visualizador de Acceso");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema al abrir el visualizador de Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			ex.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
		pedirTerminacionGestorAgentes();
	}
	
	
	/****************************************************************************************************************/
	
	public void mostrarAltaUsuario(){
	//Se muestra el formulario para rellenar con los datos del nuevo usuario

		try {
			visualizacionAccesoAlta.cerrarVisualizadorAccesoAlta();
			visualizacionAlta = (ItfUsoVisualizadorAlta) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionAlta");
			visualizacionAlta.mostrarVisualizadorAlta();
		}
		catch (Exception e) {
			logger.error("Ha habido un problema en mostrarAltaUsuario");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema en mostrarAltaUsuario", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
		
	}
	
	public void darAlta (String username, String password){
		
	//Se da de alta en el recurso de persistencia al nuevo usuario
		
		boolean ok = false;
		
		DatosAccesoSinValidar datos = new DatosAccesoSinValidar(username,password);
		try {
			persistencia = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"Persistencia");
			ok = persistencia.compruebaNombreUsuario(datos.tomaUsuario());
		}
		
		catch (Exception ex){
			logger.error("Ha habido un problema en la persistencia al comprobar el usuario");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema en la persistencia al comprobar el usuario", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			ex.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			
			if(ok){ //El usuario está en la base de datos
				visualizacionAlta.mostrarMensajeError("Error", "El nombre de usuario elegido ya está ocupado.");
				agenteAcceso.aceptaEvento(new EventoInput("incorrecto",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta")); //volverá a mostrar el formulario
			}
			else{ //se introduce en la base de datos y se manda el evento 'correcto'
				try{
					persistencia.insertaUsuario(username, password);
				}
				catch(Exception exc){
					logger.error("Fallo al insertar un nuevo valor.");
					try {
						ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
								NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
								trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
																	  "Fallo al insertar un nuevo valor.", 
																	  InfoTraza.NivelTraza.error));
					}catch(Exception e2){e2.printStackTrace();}
					exc.printStackTrace();
					try{
						agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
						(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
						agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
					}
					catch(Exception exce){
						logger.error("Fallo al enviar un evento error.");
						exce.printStackTrace();
					}
				}
				Object[] datosEnvio = new Object[]{datos.tomaUsuario(), datos.tomaPassword()};
				agenteAcceso.aceptaEvento(new EventoInput("correcto",datosEnvio,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta")); 
			}
		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
	}
	
	public void mostrarNuevoUsuario (String username, String password){
		try{
			visualizacionAlta.mostrarMensajeAviso("Nueva alta.", "Usuario creado: "+username+"\nPassword: "+password);
			visualizacionAlta.cerrarVisualizadorAlta();
		}
		catch(Exception e){
			logger.error("Excepción en recurso de visualización para altas.");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Excepción en recurso de visualización para altas.", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
		pedirTerminacionGestorAgentes();
	}
	
	public void terminacion() {
		logger.debug("Terminando agente de alta");
		try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
														  "Terminando agente de alta", 
														  InfoTraza.NivelTraza.debug));
		}catch(Exception e2){e2.printStackTrace();}
	}
	
	public void pedirTerminacionGestorAgentes(){
		try {
			/*ItfUsoAgenteReactivo itfUsoGestorOrgan = (ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz
			("Itf_Uso_Gestor_Organizacion");
			itfUsoGestorOrgan.aceptaEvento(new EventoInput("terminar_gestores_y_gestor_organizacion","AgenteAccesoUso","AgenteAccesoUso"));*/
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput("peticion_terminar_todo",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
			try{
				agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
		}
	}
	
	public void clasificaError(){
	/*
	 *A partir de esta función se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la política es que todos los errores son críticos.  
	 */
		try {
			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			agenteAcceso.aceptaEvento(new EventoInput("errorIrrecuperable",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));

		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso");
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			e.printStackTrace();
		}
	}
}