package icaro.aplicaciones.agentes.agenteAplicacionFichaReactivo.comportamiento;



import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.recursos.visualizacionFicha.ItfUsoVisualizadorFicha;
import icaro.aplicaciones.recursos.visualizacionSecretaria.ItfUsoVisualizadorSecretaria;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
import icaro.aplicaciones.recursos.persistenciaFicha.ItfUsoPersistenciaFicha;
import icaro.aplicaciones.recursos.persistenciaSecretaria.ItfUsoPersistenciaSecretaria;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;


public class AccionesSemanticasAgenteAplicacionFicha extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorFicha visualizacion;
	private ItfUsoPersistenciaFicha Persistencia;
	private ItfUsoAgenteReactivo agenteFicha;

	

	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoRecAgte. El nombre de este metodo debe corresponder con el nombre
	// de alguna accion definida en el automata
	/**
	 * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la ficha (nombre, telefono, hora,fecha, crear)
     */
	public void pintaVentanaFicha(DatosCitaSinValidar datos){
		
		try {
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionFicha1");
			
			Persistencia = (ItfUsoPersistenciaFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaFicha1");
			
			//Manda los datos a la persistencia
			DatosFicha ficha=Persistencia.getFicha(datos);
			if (!ficha.isEsta())
			
			// Pinta la ficha con datos de la cita pq no esta en la bbdd
				visualizacion.mostrarVisualizadorFicha(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			else
				visualizacion.mostrarVisualizadorFichaBD(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, ficha);
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Ficha en accion semantica 'pintaVentanaFicha()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	
	
	
	/**
	 * Pinta la ventana de ficha vacia
	 */
	public void pintaVentanaFicha(){
		
		try {
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionFicha1");
			
			// Ejemplo de algo que podemos hacer con el
			visualizacion.mostrarVisualizadorFicha(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Ficha en accion semantica 'pintaVentanaFicha()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Guarda todos los datos de una ficha de un determinado paciente
	 * @param datos		:: contiene todos los datos de una ficha
	 */
	public void guardaFicha(DatosFicha original, DatosFicha fichaN){
		
		try {
			visualizacion = (ItfUsoVisualizadorFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionFicha1");
			
			Persistencia = (ItfUsoPersistenciaFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaFicha1");
			
			//Comprobar si existe la ficha y añadirle la variable!!!!!!!
			
			//Manda los datos a la persistencia
			boolean b=Persistencia.meteFicha(original,fichaN);
			if (!b){
				visualizacion.mostrarMensajeError("Error en base de datos", "Los datos modificados no se han guardado");
			}
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba guardar datos del visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al guardar datos el visualizador de Secretaria en accion semantica 'guardaAgenda'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * borra todos los datos de una ficha de un determinado paciente
	 * @param datos		:: contiene  los datos de una ficha
	 */
	public void borraFicha(DatosFicha ficha){
		
		try {
			visualizacion = (ItfUsoVisualizadorFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionFicha1");
			
			Persistencia = (ItfUsoPersistenciaFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaFicha1");
			
			//Manda los datos a la persistencia
			boolean b=Persistencia.borraFicha(ficha);
			if (!b){
				visualizacion.mostrarMensajeError("Error en base de datos", "La ficha no se ha borrado o no existe");
			}
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba guardar datos del visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al guardar datos el visualizador de Secretaria en accion semantica 'guardaAgenda'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}

	// Ejemplo de accion semantica mas compleja
	// OJO: Cada vez que se quiera enviar una traza hay que meterla en un bloque try catch
	// tal como se ve aqui	
	public void inserta(String nombre, String apell1, String telf) {
		boolean ok = false;
		
		// Hay que crear un objeto con los datos para enviar con el evento
		//infoFicha datos = new infoFicha(nombre,apell1,telf);
		
		try {
			Persistencia = (ItfUsoPersistenciaFicha) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaFicha1");
			//ok = Persistencia1.compruebaUsuario(datos.tomaUsuario(),datos.tomaPassword());
			ok=true;
			
			try {
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Comprobando usuario...", 
															  InfoTraza.NivelTraza.debug));
			}catch(Exception e){e.printStackTrace();}
		}

		catch (Exception ex){
			try {
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema en la Persistencia1 al comprobar cita", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e){e.printStackTrace();}
		}
		try {
			// Hay que coger la instancia del agente para poder enviarle eventos
			agenteFicha = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
						
			if(ok){
				// Se envia el evento sin datos ya que no lo requiere
				agenteFicha.aceptaEvento(new EventoRecAgte("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha"));
				// Si hubiera que enviar datos con el evento se haria asi:
				// agenteFicha.aceptaEvento(new EventoRecAgte("correcto",infoFicha, this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha"));
			}
			
			// Aqui se pueden seguir haciendo cosas con la visualizacion, trazas, etc
		}
		catch (Exception e) {
			try {
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
			visualizacion.cerrarVisualizadorFicha();
			
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
			agenteFicha = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteFicha.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteFicha = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteFicha.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
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