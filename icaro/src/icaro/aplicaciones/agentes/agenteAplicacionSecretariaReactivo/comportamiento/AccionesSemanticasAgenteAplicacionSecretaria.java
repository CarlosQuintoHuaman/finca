package icaro.aplicaciones.agentes.agenteAplicacionSecretariaReactivo.comportamiento;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoValidados;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.ItfUsoVisualizadorSecretaria;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
import icaro.aplicaciones.recursos.persistenciaHistorial.ItfUsoPersistenciaHistorial;
import icaro.aplicaciones.recursos.persistenciaSecretaria.ItfUsoPersistenciaSecretaria;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import icaro.util.util;


public class AccionesSemanticasAgenteAplicacionSecretaria extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorSecretaria visualizacion;
	private ItfUsoPersistenciaSecretaria persistencia;
	private ItfUsoAgenteReactivo agenteSecretaria;
	

	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoInput. El nombre de este metodo debe corresponder con el nombre
	// de alguna accion definida en el automata
	
	/**
	 * Accion que nos permite sacar de la persistencia los datos de la agenda de la secretaria que se pasa por parametro. Y mostrarlos en la ventana de la agenda
	 * @param secretaria	:: Nombre de la secretraria
	 */
	public void pintaVentanaSecretaria(String secretaria){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			util f=new util();
			//Fecha actual
			String fecha=f.getStrDateSQL();
			ArrayList<String> l=new ArrayList<String>();
			//Consulta que me devuelve la lista de medicos para los que trabaja la secretaria
			l=persistencia.getMedicos(secretaria);
			//Consulta que me devuelve la lista de citas para cada uno de los medicos para los que trabaja la secretaria
			ArrayList<DatosMedico> lm1=persistencia.getCitas(fecha, l);
			int num=l.size();
			//muestra ventana
			visualizacion.mostrarVisualizadorSecretaria(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			//mete datos en la ventana que se acaba de mostrar
			visualizacion.meteDatos(fecha,lm1, num, secretaria);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador con datos",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Secretaria en accion semantica 'pintaVentanaSecretaria()'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	
	
	/**
	 * Esta funcion rellena la agenda de una secretaria  para una fecha concreta
	 * @param fecha		:: fecha sobre la que queremos hacer la consulta
	 * @param s			:: nombre de la secretaria
	 */
	public void rellenaAgendaSecretaria(String fecha, String s){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			ArrayList<String> l=new ArrayList<String>();
			//Consulta que me devuelve la lista de medicos para los que trabaja la secretaria
			l=persistencia.getMedicos(s);
			//Consulta que me devuelve la lista de citas para cada uno de los medicos para los que trabaja la secretaria
			ArrayList<DatosMedico> lm1=persistencia.getCitas(fecha, l);
			int num=l.size();
			//mete datos en la ventana de la agenda
			visualizacion.meteDatos(fecha,lm1, num,s);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de rellenar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al rellenar el visualizador de Secretaria en accion semantica 'rellenaAgendaSecretaria'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	/**
	 * Guarda todos los datos de una agenda de una determinada secretaria y dia
	 * @param datos		:: contiene todos los datos de un dia para una determinada secretaria
	 */
	public void guardaAgenda(DatosSecretaria datos){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			//Manda los datos a la persistencia
			boolean b=persistencia.meteAgenda(datos);
			if (!b){
				visualizacion.mostrarMensajeError("Error en base de datos", "Los datos modificados no se han guardado");
			}
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba guardar datos del visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al guardar datos el visualizador de Secretaria en accion semantica 'guardaAgenda'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	/**
	 * Buscamos para un determinado paciente las citas que tienes posteriores al dia de hoy
	 * @param nom		:: nombre del paciente
	 * @param telf		:: telefono del paciente
	 */
	public void buscaPaciente(String nom, String telf){
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			ArrayList<DatosCita> l=new ArrayList<DatosCita>();
			util f=new util();
			//fecha actual
			String fecha=f.getStrDateSQL();
			//Saca de la persistencia los datos de las citas del paciente que cumplan parametros
			l=persistencia.getPaciente(nom,telf, fecha);
			//Mete datos en la ventana de proximas Cita con los resultados de la consulta de persistencia
			visualizacion.meteDatos(l); 
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al buscarPaciente'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Pinta la ventana de cita mostrando en los campos que corresponda los datos que se le pasan como parametro
	 * @param datos		:: Datos con los que rellenar la cita(nombre, apellido, telefono, hora)
	 */
	public void pintaVentanaCita(DatosCitaSinValidar datos){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorCita(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Cita en accion semantica 'pintaVentanaCita)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	
	/**
	 * Pinta la ventana de cita vacia
	 */
	public void pintaVentanaCita(){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorCita(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Cita en accion semantica 'pintaVentanaCita'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Pinta la ventana de llamadas vacia
	 */
	public void pintaVentanaLlamada(){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador Llamada",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Llamada en accion semantica 'pintaVentanaLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	/**
	 * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
     */
	public void pintaVentanaLlamada(DatosLlamada datos){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador Llamada",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Llamada en accion semantica 'pintaventanaLlamada(datos)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	
	/**
	 * Pinta la ventana de extras vacia
	 */
	public void pintaVentanaExtra(){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de extra en accion semantica 'pintaVentanaExtra()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * Pinta la ventana de proximas citas vacia
	 */
	public void pintaVentanaPCitas(){
	
	try {
		visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
		(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
		visualizacion.mostrarVisualizadorPCitas(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
		
		trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador de proximas citas",InfoTraza.NivelTraza.debug));
	}

	catch (Exception ex) {
		try {
		ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
				NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
				trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
													  "Ha habido un problema al abrir el visualizador de proximas citas en accion semantica 'pintaVentanaPCitas()'", 
													  InfoTraza.NivelTraza.error));
		}catch(Exception e){e.printStackTrace();}
	}
}
	/**
	 * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar el extra(nombre, mensaje, telefono, Espaciente, hora)
     */
	public void pintaVentanaExtra(DatosLlamada datos){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de extra en accion semantica 'pintaventanaextra(datos)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	/**
	 * METODO EJEMPLO ANTIGUO
	 * Comprueba una cita en la visualizacion
	 * @param datos
	 */
	public void inserta(DatosCitaSinValidar datos) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.comprobarInfoCita(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoCita en accion semantica 'inserta()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia
		try {
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			//visualizacion.mostrarDatos(persistencia.getHistorial(paciente));
			//ok = Persistencia.InsertaCita(datos.tomaUsuario(),datos.tomaPassword());
			ok=true;
			
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Comprobando cita...", 
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
		//envio evento cita correcta
		try {
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.getApell2(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			
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
	/**
	 * borra una llamada (la que se pasa por parametro) de la visualizacion y de la persistencia
	 * @param datos		:: datos de una llamada a insertar
	 */
	public void borrarLlamada(DatosLlamada datos) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.borrarLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'borrarLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	
	/**
	 * inserta una llamada (la que se pasa por parametro) en la visualizacion y en la persistencia
	 * @param datos		:: datos de una llamada a insertar
	 */
	public void insertaLlamada(DatosLlamada datos) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.insertaLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'insertaLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	
	/**
	 * modifica una llamada (la que se pasa por parametro) en la visualizacion y en la persistencia
	 * @param datos		:: datos de una llamada a modificar
	 */
	public void modificaLlamada(DatosLlamada dAnt, DatosLlamada dPost) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.modificaLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, dAnt,dPost);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'insertaLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	/**
	 * modifica un extra (la que se pasa por parametro) en la visualizacion y en la persistencia
	 * @param datos		:: datos de un extra a modificar
	 */
	public void modificaExtra(DatosLlamada dAnt, DatosLlamada dPost) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.modificaExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, dAnt,dPost);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'insertaLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	
	/**
	 * borra un extra (la que se pasa por parametro) en la visualizacion y en la persistencia
	 * @param datos		:: datos de una extra a borrar
	 */
	public void borrarExtra(DatosLlamada datos) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.borrarExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar el extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoextra en accion semantica 'borrarextra()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	
	/**
	 * inserta un extra (la que se pasa por parametro) en la visualizacion y en la persistencia
	 * @param datos		:: datos de un extra a insertar
	 */
	public void insertaExtra(DatosLlamada datos) {
		boolean ok = false;
		ok=true;
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.insertaExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar el extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoextra en accion semantica 'insertaextra()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

	}
	
	public void terminacion() {
		try {
			visualizacion.cerrarVisualizadorSecretaria();
			
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Terminando agente: "+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha1", 
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			agenteSecretaria.aceptaEvento(new EventoInput("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteSecretaria.aceptaEvento(new EventoInput("error",this.nombreAgente,this.nombreAgente));
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