package icaro.aplicaciones.agentes.agenteAplicacionSecretariaReactivo.comportamiento;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.ItfUsoVisualizadorSecretaria;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia; 
import icaro.aplicaciones.recursos.persistenciaHistorial.ItfUsoPersistenciaHistorial;
import icaro.aplicaciones.recursos.persistenciaSecretaria.ItfUsoPersistenciaSecretaria;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.util.util;


public class AccionesSemanticasAgenteAplicacionSecretaria extends AccionesSemanticasAgenteReactivo {
	
	private ItfUsoVisualizadorSecretaria visualizacion;
	private ItfUsoPersistenciaSecretaria persistencia;
	private ItfUsoAgenteReactivo agenteSecretaria;
	

	// NOTA: Recordar que estas acciones estan definidas en el automata y son llamadas al
	// recibir un EventoRecAgte. El nombre de este metodo debe corresponder con el nombre
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
			ArrayList<DatosMedico> l=new ArrayList<DatosMedico>();
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
			
			ArrayList<DatosMedico> l=new ArrayList<DatosMedico>();
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
			ArrayList<DatosCitaSinValidar> l=new ArrayList<DatosCitaSinValidar>();
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
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al buscarPaciente'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Buscamos todos los pacientes para la cita
	 */
	public void getPacientes(){
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			ArrayList<InfoPaciente> l=new ArrayList<InfoPaciente>();
			util f=new util();
			//fecha actual
			String fecha=f.getStrDateSQL();
			//Saca de la persistencia los datos de las citas del paciente que cumplan parametros
			l=persistencia.getPacientes();
			//Mete datos en la ventana de Cita con los resultados de la consulta de persistencia
			visualizacion.meteDatosPacientes(l); 
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al buscarPaciente'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Buscamos todos los pacientes para los extras
	 */
	public void getPacientesE(){
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			ArrayList<InfoPaciente> l=new ArrayList<InfoPaciente>();
			util f=new util();
			//fecha actual
			String fecha=f.getStrDateSQL();
			//Saca de la persistencia los datos de las citas del paciente que cumplan parametros
			l=persistencia.getPacientes();
			//Mete datos en la ventana de Cita con los resultados de la consulta de persistencia
			visualizacion.meteDatosPacientesE(l); 
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al buscarPaciente'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	/**
	 * Buscamos todos los pacientes para las llamadas
	 */
	public void getPacientesL(){
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			ArrayList<InfoPaciente> l=new ArrayList<InfoPaciente>();
			util f=new util();
			//fecha actual
			String fecha=f.getStrDateSQL();
			//Saca de la persistencia los datos de las citas del paciente que cumplan parametros
			l=persistencia.getPacientes();
			//Mete datos en la ventana de Cita con los resultados de la consulta de persistencia
			visualizacion.meteDatosPacientesL(l); 
		}

		catch (Exception ex) {
			try {
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
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Llamada en accion semantica 'pintaventanaLlamada(datos)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	
	/**
	 * Pinta la ventana de extras vacia
	 */
	public void pintaVentanaExtraVacia(DatosLlamada d){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorExtraVacia(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO,d);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de extra en accion semantica 'pintaVentanaExtra()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}

	/**
	 * Pinta la ventana de llamadas vacia
	 */
	public void pintaVentanaLlamadaVacia(DatosLlamada d){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			visualizacion.mostrarVisualizadorLlamadaVacia(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO,d);
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador extra",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
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
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de extra en accion semantica 'pintaventanaextra(datos)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	/**
	 * 
	 * Comprueba una cita en la visualizacion y si es correcta la manda a la persistencia
	 * @param datos		:: Datos de la cita a comprobar.
	 */
	public void inserta(DatosCitaSinValidar datos) {
		boolean ok = true;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			ok=visualizacion.comprobarInfoCita(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);;
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoCita en accion semantica 'inserta(datos)'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		try {
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			//Una vez comprobado todo correcto se manda a persistencia
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			if(ok){
				persistencia.setCita(datos);
				visualizacion.cerrarVisualizadorCita();
				agenteSecretaria.aceptaEvento(new EventoRecAgte("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria1"));
			}
			else{
				ok=false;
				agenteSecretaria.aceptaEvento(new EventoRecAgte("cancelar",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria1"));
				//visualizacion.mostrarMensajeError("Error","Cita incorrecta. Vuelva a intertarlo");
			}
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * 
	 * Comprueba una cita en la visualizacion y si es correcta la manda a la persistencia
	 * @param datos		:: Datos de la cita a comprobar.
	 */
	public void pegarCita(DatosCitaSinValidar datos) {
		boolean ok = true;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			 persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			datos.setNuevo(false);
			persistencia.setCita(datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de pegar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar pegarCita en la accion semantica pegarCita'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	/**
	 * borra una cita (la que se pasa por parametro) de la visualizacion y de la persistencia
	 * @param datos		:: datos de una cita a borrar
	 */
	public void borrarCita(DatosCitaSinValidar datos) {
		boolean ok = false;
		
		//Se lo mando a panel agenda para que lo compruebe
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			persistencia.borraCita(datos);
			//visualizacion.borrarCita(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, datos);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'borrarLlamada()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
		
		//Una vez comprobado todo correcto se manda a persistencia

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
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar borrar llamada",InfoTraza.NivelTraza.debug));
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			persistencia.borraExtra(datos);
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoCita en accion semantica 'borrarCita()'", 
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
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			persistencia.setExtra(datos,datos);
		}

		catch (Exception ex) {
			try {
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
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			visualizacion.modificaLlamada(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, dAnt,dPost);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
			
			persistencia.setExtra(dAnt,dPost);
		}

		catch (Exception ex) {
			try {
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al comprobar infoLlamada en accion semantica 'modificaLlamada()'", 
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
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			visualizacion.modificaExtra(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO, dAnt,dPost);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de comprobar la Cita",InfoTraza.NivelTraza.debug));
			
			persistencia.setExtra(dAnt,dPost);
		}

		catch (Exception ex) {
			try {
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
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			persistencia.borraExtra(datos);
		}

		catch (Exception ex) {
			try {
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
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			persistencia.setExtra(datos,datos);
			
		}

		catch (Exception ex) {
			try {
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
			agenteSecretaria.aceptaEvento(new EventoRecAgte("errorIrrecuperable",this.nombreAgente,this.nombreAgente));

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
				agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+this.nombreAgente);
				agenteSecretaria.aceptaEvento(new EventoRecAgte("error",this.nombreAgente,this.nombreAgente));
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