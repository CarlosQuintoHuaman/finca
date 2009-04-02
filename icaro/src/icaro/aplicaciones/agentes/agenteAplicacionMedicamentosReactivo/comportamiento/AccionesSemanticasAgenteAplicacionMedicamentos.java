package icaro.aplicaciones.agentes.agenteAplicacionMedicamentosReactivo.comportamiento;


import java.sql.Timestamp;
import java.util.ArrayList;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.ItfUsoVisualizadorHistorial;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.ItfUsoVisualizadorMedicamentos;
import icaro.aplicaciones.recursos.persistenciaMedicamentos.ItfUsoPersistenciaMedicamentos;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;


public class AccionesSemanticasAgenteAplicacionMedicamentos extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorMedicamentos visualizacion;
	private ItfUsoPersistenciaMedicamentos persistencia;
	private ItfUsoAgenteReactivo agenteMedicamentos;

	// Variables especificas (no de icaro)
	String paciente;
	Timestamp fecha;
	
	// Ejemplo de accion semantica sencilla
	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoInput. El nombre de este metodo debe corresponder con el nombre
	// de alguna accion definida en el automata


	public void pintaTabMed(final CTabFolder c, final java.lang.Integer estilo) {
		try {
			visualizacion = (ItfUsoVisualizadorMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionMedicamentos1");
			
			System.out.println("AQUI ESTAMOS");
			
			persistencia = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			
			
			visualizacion.mostrarTabMed("AgenteAplicacionMedicamentos1", NombresPredefinidos.TIPO_REACTIVO, c, estilo);
			//visualizacion.mostrarDatosMedicamentos(persistencia.getMedicamentos());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pintaVentanaBusqueda(String paciente, Timestamp fecha) {
		try {
			this.paciente = paciente;
			this.fecha = fecha;
			
			visualizacion = (ItfUsoVisualizadorMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionMedicamentos1");
			
			persistencia = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			
			visualizacion.mostrarVisualizadorBusqueda(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, paciente);
			visualizacion.mostrarDatosMedicamentos(persistencia.getMedicamentos());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pintaVentanaNuevo() {
		try {
			persistencia = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			
			visualizacion = (ItfUsoVisualizadorMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionMedicamentos1");
			
			visualizacion.mostrarVisualizadorNuevo(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			//visualizacion.mostrarDatosLista(persistencia.getMedicamentos(paciente));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarMedicamento(String p, InfoMedicamento m) {
		try {
			ItfUsoVisualizadorHistorial vh = (ItfUsoVisualizadorHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionHistorial1");
			
			persistencia.asignaMedicamento(p,m,fecha);
			vh.mostrarDatosMed(persistencia.getMedicamentos(paciente, fecha));
			//visualizacion.mostrarDatosMedicamentos(persistencia.getMedicamentos());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertarMedicamento(InfoMedicamento m) {
		try {
			persistencia.insertaMedicamento(m);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarMedicamentos(String origen) {
		try {
			
			persistencia = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			
			ArrayList<InfoMedicamento> m = persistencia.getMedicamentos();
			
			ItfUsoAgenteReactivo agenteOrigen = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+origen);
			
			agenteOrigen.aceptaEvento(new EventoInput("mostrarDatosMed", m, this.nombreAgente, origen));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void borrarMedicamento(String p, Timestamp t, InfoMedicamento m) {
		try {
			persistencia = (ItfUsoPersistenciaMedicamentos) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaMedicamentos1");
			
			persistencia.borrarMedicamento(p, t, m);
			
			ItfUsoVisualizadorHistorial vh = (ItfUsoVisualizadorHistorial) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionHistorial1");
			
			vh.mostrarDatosMed(persistencia.getMedicamentos(paciente, fecha));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	// Los siguientes 3 metodos suelen estar siempre en todos los automatas
	public void terminacion() {
		try {
			// Aqui se hacen las cosas, por ejemplo esto
			//visualizacion.cerrarVisualizadorMedicamentos();
			
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
			agenteMedicamentos = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteMedicamentos.aceptaEvento(new EventoInput("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteMedicamentos = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteMedicamentos.aceptaEvento(new EventoInput("error",this.nombreAgente,this.nombreAgente));
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