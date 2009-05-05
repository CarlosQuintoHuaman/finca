package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivo.comportamientoAlta;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoValidada;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *  Acciones Sem�nticas para un agente de aplicaci�n
 *
 *@author     Francisco J Garijo
 *@created   26 de Febreo de 2009
 */

public class AccionesSemanticasAgenteAplicacionAccesoOld extends AccionesSemanticasAgenteReactivo {
	
//	private ItfUsoRepositorioInterfaces itfUsoRepositorio ; lo tiene la superclase
    private ItfUsoVisualizadorAcceso visualizacionAcceso; //ventana para el acceso
	private ItfUsoVisualizadorAccesoAlta visualizacionAlta; //ventana para las altas
	private ItfUsoPersistencia persistencia;
//	private ItfUsoAgenteReactivo usoAgente ; // su propia interfaz e uso la tiene la superclase
//	private ItfUsoRecursoTrazas trazas; lo tiene la superclase
    private String  nombreRecVisualizacionAcceso = "VisualizacionAcceso1";
    private String  nombreRecVisualizacionAccesoAlta  = "VisualizacionAccesoAlta1";
    private String  nombreRecPersistencia  = "Persistencia1";

	public void arranque(){
       // Se obtienen las interfaces de los recursos que se necesitan
        try {

            visualizacionAcceso = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+nombreRecVisualizacionAcceso);
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAccesoOld.class.getName()).log(Level.SEVERE, null, ex);
             trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"NO se ha podido obtener la interfaz del visualizador de acceso",InfoTraza.NivelTraza.debug));
        }
        try {
            visualizacionAlta = (ItfUsoVisualizadorAccesoAlta) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+nombreRecVisualizacionAccesoAlta);
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAccesoOld.class.getName()).log(Level.SEVERE, null, ex);
             trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"NO se ha podido obtener la interfaz del visualizador de Alta",InfoTraza.NivelTraza.debug));
        }
        try {
            persistencia = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+nombreRecPersistencia);
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasAgenteAplicacionAccesoOld.class.getName()).log(Level.SEVERE, null, ex);
         trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"NO se ha podido obtener la interfaz de la persistencia ",InfoTraza.NivelTraza.debug));
        }

		try {
            visualizacionAcceso.mostrarVisualizadorAcceso(this.nombreAgente, NombresPredefinidos.TIPO_REACTIVO);
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,"Se acaba de mostrar el visualizador",InfoTraza.NivelTraza.debug));
		}
		
		catch (Exception ex) {
			logger.error("Ha habido un problema al abrir el visualizador de Acceso");
			trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema al abrir el visualizador de Acceso", 
															  InfoTraza.NivelTraza.error));
			}
			
	}
	
	public void valida(InfoAccesoSinValidar infoUsuario) {
		boolean ValidacionOK = false;
		
		try {
        ValidacionOK = persistencia.compruebaUsuario(infoUsuario.tomaUsuario(),infoUsuario.tomaPassword());
		trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, 
															  "Comprobando usuario...", 
															  InfoTraza.NivelTraza.debug));
			}
        catch(Exception e){e.printStackTrace();
        trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
															  "Ha habido un problema en la Persistencia1 al comprobar el usuario y el password",
															  InfoTraza.NivelTraza.error));
		}
// Segun el resultado obtenido de la validación enviamos eventos internos informando
// al automata para que transite y ejecute nuevas acciones
			if(ValidacionOK){
                try  {
                     Object[] datosEnvio = new Object[]{infoUsuario.tomaUsuario(), infoUsuario.tomaPassword()};
				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("usuarioValido",datosEnvio,this.nombreAgente,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+nombreAgente));
			}catch(Exception e){e.printStackTrace();}
                }
			else{
				try  {
                   
				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("usuarioNoValido",this.nombreAgente,this.nombreAgente));
                    }
                catch(Exception e){e.printStackTrace();}
                }
	}
		
	public void mostrarUsuarioAccede(String username, String password){

		InfoAccesoValidada dav =  new InfoAccesoValidada(username ,password);

		try{
			
			visualizacionAcceso.mostrarMensajeInformacion("AccesoCorrecto", "El usuario "+dav.tomaUsuario()+" ha accedido al sistema. \n A partir de aqu� deber�a continuar la aplicaci�n.");
			visualizacionAcceso.cerrarVisualizadorAcceso();

		}
		catch (Exception ex) {
			
			trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
															  "Ha habido un problema al abrir el visualizador de Acceso",
															  InfoTraza.NivelTraza.error));
		}
		pedirTerminacionGestorAgentes();
	}
	
	/****************************************************************************************************************/
	
	public void mostrarAltaUsuario(){
	//Se muestra el formulario para rellenar con los datos del nuevo usuario

		try {
        
			visualizacionAcceso.cerrarVisualizadorAcceso();
			
			visualizacionAlta.mostrarVisualizadorAccesoAlta();
            trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta",
															  "cerrando visualizadores", InfoTraza.NivelTraza.info));
		}
		catch (Exception e) {
			logger.error("Ha habido un problema en mostrarAltaUsuario");
            try {
                itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error", this.nombreAgente, this.nombreAgente));
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasAgenteAplicacionAccesoOld.class.getName()).log(Level.SEVERE, null, ex);

				trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta","Ha habido un problema en mostrarAltaUsuario", InfoTraza.NivelTraza.error));
			
		}
	}
}
	
	public void darAlta (String username, String password){
		
	//Se da de alta en el recurso de persistencia al nuevo usuario
		
		boolean ok = false;
		
		InfoAccesoSinValidar datos = new InfoAccesoSinValidar(username,password);
		try {
			
		ok = persistencia.compruebaNombreUsuario(datos.tomaUsuario());
		}
		
		catch (Exception ex){
			logger.error("Ha habido un problema en la persistencia al comprobar el usuario");
			trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema en la persistencia al comprobar el usuario", 
															  InfoTraza.NivelTraza.error));
			try{
			itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",nombreAgente,nombreAgente));
                }
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
                             }
                        }
		if(ok){ //El usuario esta en la base de datos
            try {
                //El usuario esta en la base de datos
            visualizacionAlta.mostrarMensajeError("Error", "El nombre de usuario elegido ya esta ocupado.Introduzca otro nombre");
            itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("incorrecto",nombreAgente,nombreAgente));
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasAgenteAplicacionAccesoOld.class.getName()).log(Level.SEVERE, null, ex);
            }

			}
			else{ //se introduce en la base de datos y se manda el evento 'correcto'
				try{
					persistencia.insertaUsuario(username, password);
				}
				catch(Exception exc){
					logger.error("Fallo al insertar un nuevo valor.");
			
					trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
																	  "Fallo al insertar un nuevo valor.", 
																	  InfoTraza.NivelTraza.error));
					try{
						itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",nombreAgente,nombreAgente));
					}
					catch(Exception exce){
						logger.error("Fallo al enviar un evento error.");
						exce.printStackTrace();
					}
                }
			Object[] datosEnvio = new Object[]{datos.tomaUsuario(), datos.tomaPassword()};
 
            try {
                itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("correcto", datosEnvio, nombreAgente, nombreAgente));
                }
            catch (Exception ex) {
               logger.error("Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso");
            trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta",
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso",
															  InfoTraza.NivelTraza.error));
                        }


                    }
		}
		
	
	public void mostrarNuevoUsuario (String username, String password){
		try{
			visualizacionAlta.mostrarMensajeAviso("Nueva alta.", "Usuario creado: "+username+"\nPassword: "+password);
			visualizacionAlta.cerrarVisualizadorAccesoAlta();
		}
		catch(Exception e){
			logger.error("Excepci�n en recurso de visualizaci�n para altas.");

				trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Excepci�n en recurso de visualizaci�n para altas.", 
															  InfoTraza.NivelTraza.error));
			try{
	//			agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
	//			(NombresPredefinidos.ITF_USO+nombreAgente);
				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",nombreAgente,nombreAgente));
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
 // Orden de cierre al  visualizador porque atendemos la peticion del usuario
            visualizacionAlta.mostrarMensajeAviso("confirmacion cierre", "Confirma la salida del sistema ?");
			visualizacionAlta.cerrarVisualizadorAccesoAlta();
			trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
														  "Terminando agente de alta", 
														  InfoTraza.NivelTraza.debug));
		}catch(Exception e2){e2.printStackTrace();}
	}
	
	public void pedirTerminacionGestorAgentes(){
		try {
		
		itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte("peticion_terminar_todo",nombreAgente,NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo");
	
			trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}
			try{
	
				itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("error",nombreAgente,nombreAgente));
			}
			catch(Exception exc){
				logger.error("Fallo al enviar un evento error.");
				exc.printStackTrace();
			}
	}
	
	public void clasificaError(){
	/*
	 *A partir de esta funci�n se debe decidir si el sistema se puede recuperar del error o no.
	 *En este caso la pol�tica es que todos los errores son cr�ticos.  
	 */
		try {
	//		agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
	//		(NombresPredefinidos.ITF_USO+nombreAgente);
			itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("errorIrrecuperable",nombreAgente,nombreAgente));

		}
		catch (Exception e) {
			logger.error("Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso");
			trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAlta", 
															  "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso", 
															  InfoTraza.NivelTraza.error));
			}
		}
}