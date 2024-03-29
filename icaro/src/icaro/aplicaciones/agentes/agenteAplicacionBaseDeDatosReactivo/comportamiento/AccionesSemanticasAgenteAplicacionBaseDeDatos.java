package icaro.aplicaciones.agentes.agenteAplicacionBaseDeDatosReactivo.comportamiento;


import icaro.aplicaciones.informacion.dominioClases.aplicacionBaseDeDatos.infoBaseDeDatos;
import icaro.aplicaciones.recursos.visualizacionBaseDeDatos.ItfUsoVisualizadorBaseDeDatos;
import icaro.aplicaciones.recursos.persistenciaBaseDeDatos.ItfUsoPersistenciaBaseDeDatos;
//import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionBaseDeDatos extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorBaseDeDatos visualizacion;
	private ItfUsoPersistenciaBaseDeDatos Persistencia1;
	private ItfUsoAgenteReactivo agenteBaseDeDatos;

	
	// Ejemplo de accion semantica sencilla
	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoInput. El nombre de este metodo debe corresponder con el nombre
	// de alguna accion definida en el automata
	public void pintaVentanaBaseDeDatos(){
		
		try {
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorBaseDeDatos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionBaseDeDatos1");
			
			// Ejemplo de algo que podemos hacer con el
			visualizacion.mostrarVisualizadorBaseDeDatos(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de BaseDeDatos en accion semantica 'pintaVentanaBaseDeDatos()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}	

	// Ejemplo de otra accion semantica mas compleja
	// OJO: Cada vez que se quiera enviar una traza hay que meterla en un bloque try catch
	// tal como se ve aqui	
	public void inserta(String nombre, String apell1, String telf) {
		boolean ok = false;
		
		// Hay que crear un objeto con los datos para enviar con el evento
		infoBaseDeDatos datos = new infoBaseDeDatos(nombre,apell1,telf);
		
		try {
			Persistencia1 = (ItfUsoPersistenciaBaseDeDatos) itfUsoRepositorio.obtenerInterfaz
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
			agenteBaseDeDatos = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
						
			if(ok){
				// Se envia el evento sin datos ya que no lo requiere
				agenteBaseDeDatos.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"BaseDeDatos"));
				// Si hubiera que enviar datos con el evento se haria asi:
				// agenteBaseDeDatos.aceptaEvento(new EventoInput("correcto",infoBaseDeDatos, this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"BaseDeDatos"));
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
			visualizacion.cerrarVisualizadorBaseDeDatos();
			
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
			agenteBaseDeDatos = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteBaseDeDatos.aceptaEvento(new EventoInput("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteBaseDeDatos = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteBaseDeDatos.aceptaEvento(new EventoInput("error",this.nombreAgente,this.nombreAgente));
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