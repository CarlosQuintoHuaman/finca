package icaro.aplicaciones.agentes.agenteAplicacionHistorialReactivo.comportamiento;


import java.sql.Timestamp;
import java.util.Date;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.ItfUsoVisualizadorHistorial;
import icaro.aplicaciones.recursos.persistenciaHistorial.ItfUsoPersistenciaHistorial;
import icaro.aplicaciones.recursos.persistenciaMedicamentos.ItfUsoPersistenciaMedicamentos;
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
public class AccionesSemanticasAgenteAplicacionHistorial extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorHistorial visualizacion;
	private ItfUsoPersistenciaHistorial persistencia;
	private ItfUsoAgenteReactivo agenteHistorial;
	private ItfUsoPersistenciaMedicamentos persistenciaMed;

	String paciente;
	Timestamp fecha;
	
	/**
	 * Muestra la ventana historial para una visita concreta 
	 * @param paciente
	 * @param fecha Fecha de la visita
	 */
	public void pintaVentanaHistorial(String paciente, Timestamp fecha, Integer estado){
		
		try {
			this.paciente = paciente;
			this.fecha = fecha;
			
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionHistorial1");
			
			visualizacion.mostrarVisualizadorHistorial(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			//Y la persistencia
			persistencia = (ItfUsoPersistenciaHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaHistorial1");
			
			visualizacion.mostrarDatosHistorial(persistencia.getHistorial(paciente, fecha));
			visualizacion.mostrarDatosPrueba(persistencia.getPruebas(paciente, fecha));
			
			persistenciaMed = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			visualizacion.mostrarDatosMed(persistenciaMed.getMedicamentos(paciente, fecha));
			
			if (estado != -1)
				persistencia.cambiarEstado(paciente, fecha, estado);
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Historial en accion semantica 'pintaVentanaHistorial()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Muestra la lista de visitas en el historial de un paciente
	 * @param paciente
	 */
	public void pintaVentanaLista(String paciente) {
		try {
			visualizacion = (ItfUsoVisualizadorHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionHistorial1");
			
			persistencia = (ItfUsoPersistenciaHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaHistorial1");
			
			visualizacion.mostrarVisualizadorLista(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			visualizacion.mostrarDatosLista(persistencia.getHistorial(paciente));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Muestra la ventana para añadir una Prueba nueva al historial de un paciente
	 * @param v Visita a la cual se va a añadir la prueba
	 */
	public void pintaVentanaPrueba(InfoVisita v) {
		try {
			visualizacion = (ItfUsoVisualizadorHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionHistorial1");
			
			visualizacion.mostrarVisualizadorPrueba(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, v);
			//visualizacion.mostrarDatosLista(persistencia.getHistorial(paciente));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Guarda una visita en la BD
	 * @param v Datos a guardar
	 */
	public void guardarVisita(InfoVisita v) {
		persistencia.setVisita(v);
	}
	
	/**
	 * Guarda una Prueba en la BD
	 * @param p Datos a guardar
	 */
	public void guardarPrueba(InfoPrueba p) {
		persistencia.setPrueba(p);
		visualizacion.mostrarDatosPrueba(persistencia.getPruebas(p.getPaciente(), p.getFecha()));
	}
	
	/**
	 * Borra una prueba de la BD
	 * @param p Prueba a borrar
	 */
	public void borrarPrueba(InfoPrueba p) {
		persistencia.borrarPrueba(p);
		visualizacion.mostrarDatosPrueba(persistencia.getPruebas(p.getPaciente(), p.getFecha()));
	}
	
	/**
	 * Borra un medicamento de la BD
	 * @param m Medicamento a borrar
	 * @throws Exception
	 */
	public void borrarMedicamento(InfoMedicamento m) throws Exception {
		persistencia.borrarMedicamento(m);
		visualizacion.mostrarDatosMed(persistenciaMed.getMedicamentos(paciente, fecha));
	}	
	
	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			visualizacion.cerrarVisualizadorHistorial();
			
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
			agenteHistorial = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteHistorial.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteHistorial = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteHistorial.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
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