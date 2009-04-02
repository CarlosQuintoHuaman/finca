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

	
	public void pintaVentanaSecretaria(String secretaria){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			util f=new util(); 
			String fecha=f.getStrDateSQL();
			ArrayList<String> l=new ArrayList<String>();
			l=persistencia.getMedicos(secretaria);
			ArrayList<DatosMedico> lm1=persistencia.getCitas(fecha, l);
			int num=l.size();
			
			visualizacion.mostrarVisualizadorSecretaria(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
			visualizacion.meteDatos(fecha,lm1, num, secretaria);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
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
	
	public void rellenaAgendaSecretaria(String fecha, String s){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			ArrayList<String> l=new ArrayList<String>();
			l=persistencia.getMedicos(s);
			ArrayList<DatosMedico> lm1=persistencia.getCitas(fecha, l);
			int num=l.size();
			visualizacion.meteDatos(fecha,lm1, num,s);
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Secretaria en accion semantica 'rellenaAgendaSecretaria'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void guardaAgenda(DatosSecretaria datos){
		
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			
			
			boolean b=persistencia.meteAgenda(datos);
			if (!b){
				visualizacion.mostrarMensajeError("Error en base de datos", "Los datos modificados no se han guardado");
			}
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}

		catch (Exception ex) {
			try {
			ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
					NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
					trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
														  "Ha habido un problema al abrir el visualizador de Secretaria en accion semantica 'rellenaAgendaSecretaria'", 
														  InfoTraza.NivelTraza.error));
					ex.printStackTrace();
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public void buscaPaciente(String nom, String telf){
		try {
			visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
			
			persistencia = (ItfUsoPersistenciaSecretaria) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaSecretaria1");
			ArrayList<DatosCita> l=new ArrayList<DatosCita>();
			util f=new util(); 
			String fecha=f.getStrDateSQL();
			l=persistencia.getPaciente(nom,telf, fecha);
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
														  "Ha habido un problema al abrir el visualizador de Cita en accion semantica 'arranque()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}	
	
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
														  "Ha habido un problema al abrir el visualizador de Cita en accion semantica 'arranque()'", 
														  InfoTraza.NivelTraza.error));
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
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

public void pintaVentanaPCitas(){
	
	try {
		visualizacion = (ItfUsoVisualizadorSecretaria) itfUsoRepositorio.obtenerInterfaz
		(NombresPredefinidos.ITF_USO+"VisualizacionSecretaria1");
		visualizacion.mostrarVisualizadorPCitas(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
		
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
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
	}
	
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
	}
	
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
	}
	
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
	}
	
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
	}
	
	public void insertaExtra(DatosLlamada datos) {
		boolean ok = false;
		
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
/*		try {
			Persistencia1 = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
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
			agenteSecretaria = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+this.nombreAgente);
			Object[] datosEnvio = new Object[]{datos.tomaNombre(), datos.tomaApell1(),datos.tomaTelf()};
			if(ok){
				agenteSecretaria.aceptaEvento(new EventoInput("correcto",this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Secretaria"));
			}
			
			visualizacion.cerrarVisualizadorCita();
			
			//trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Ejecutando accion: darAlta",InfoTraza.NivelTraza.debug));
		}
		catch (Exception e) {
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Ha habido un problema enviar el evento correcto al agente", 
															  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
		}*/
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
			/*ItfUsoAgenteReactivo itfUsoGestorOrgan = (ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz
			("Itf_Uso_Gestor_Organizacion");
			itfUsoGestorOrgan.aceptaEvento(new EventoInput("terminar_gestores_y_gestor_organizacion","AgenteFichaUso","AgenteFichaUso"));*/
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