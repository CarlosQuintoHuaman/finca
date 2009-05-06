package icaro.aplicaciones.agentes.agenteAplicacionMedicoReactivo.comportamiento;


import java.util.ArrayList;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.ItfUsoVisualizadorMedicamentos;
import icaro.aplicaciones.recursos.visualizacionMedico.ItfUsoVisualizadorMedico;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
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
public class AccionesSemanticasAgenteAplicacionMedico extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorMedico visualizacion;
	private ItfUsoPersistencia PersistenciaMedico;
	private ItfUsoAgenteReactivo agenteMedico;

	/**
	 * Muestra la ventana del medico
	 */
	public void pintaVentanaMedico(String usuario) {

		try {
			//Se obtiene el visualizador
			visualizacion = (ItfUsoVisualizadorMedico) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionMedico1");
			
			// Ejemplo de algo que podemos hacer con el
			visualizacion.mostrarVisualizadorMedico(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, usuario);
			
			// Ejemplo de como enviar una traza para asi hacer un seguimiento en la ventana de trazas
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Medico en accion semantica 'pintaVentanaMedico()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * - En fase experimental - Carga el contenido de una ventana usando un Composite externo
	 * @param c Composite a mostrar
	 */
	public void pintaTabMed(Composite c) {
		try {
			visualizacion.mostrarTabMed(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Muestra los lista de medicamentos en el sistema
	 * @param m
	 */
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) {
		try {
			visualizacion.mostrarDatosMed(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarMensajes(ArrayList<InfoMensaje> m) {
		try {
			visualizacion.mostrarMensajes(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			visualizacion.cerrarVisualizadorMedico();
			
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
			agenteMedico = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteMedico.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteMedico = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteMedico.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
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