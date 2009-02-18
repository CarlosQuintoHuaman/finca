/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.gestores.gestorOrganizacion.comportamiento;


//import icaro.infraestructura.corba.ORBDaemonExec;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Clase que contiene las acciones necesarias para el gestor de la organizaci�n
 * 
 * @author Felipe Polo
 * @created 3 de Diciembre de 2007
 */
public class AccionesSemanticasGestorOrganizacion extends
		AccionesSemanticasAgenteReactivo {
	// Tiempo que fijaremos para las monitorizaciones c�clicas
	protected long tiempoParaNuevaMonitorizacion;

	/**
	 * Hebra para que inyecte eventos de monitorizaci�n cada cierto tiempo
	 */
	private HebraMonitorizacion hebra;

	public AccionesSemanticasGestorOrganizacion() {
		try {
			// creo el repositorio de interfaces
			this.itfUsoRepositorio = RepositorioInterfaces.instance();
		} catch (Exception ex) {
			 logger.fatal("GestorOrganizaci�n: Fue imposible crear el repositorio de interfaces.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear el repositorio de interfaces.",
					InfoTraza.NivelTraza.fatal));
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Establece la configuraci�n para el gestor de Organizaci�n
	 */
	public void configurarGestor() {
		try {
			/*
			 * En esta acci�n sem�ntica se configura todo aquello que sea
			 * necesario a partir del archivo xml
			 */

			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);

			tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal("gestores.comun.intervaloMonitorizacion"));
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"gestor_configurado",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorRecursos: Hubo problemas al configurar el gestor de Organizacion.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Hubo problemas al configurar el gestor de Organizacion.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea el gestor de agentes y el gestor de recursos
	 */
	public void crearGestores() {
		try {
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);

			// creo los gestores
			 logger.debug("GestorOrganizaci�n: Creando gestor de agentes...");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Creando gestor de agentes ...",
					InfoTraza.NivelTraza.debug));
			
			// Gestor de Agentes: local o remoto?
			DescInstanciaGestor descGestor = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			String esteNodo = descGestor.getNodo().getNombreUso();

			DescInstanciaGestor gestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String nodoDestino = gestorAgentes.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;
			

			if (nodoDestino.equals(esteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						gestorAgentes);
			} else {
				while (!ok) {
					++intentos;
					try {
						((FactoriaAgenteReactivo) RepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino))
								.crearAgenteReactivo(gestorAgentes);
						ok = true;
					} catch (Exception e) {
						trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
								"Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear el agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);

						Thread.sleep(1000 * intentos);

						ok = false;
					}
				}
			}

			logger.debug("GestorOrganizaci�n: Gestor de agentes creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestor de agentes creado.",
					InfoTraza.NivelTraza.debug));

			Set<Object> conjuntoEventos = new HashSet<Object>();
			conjuntoEventos.add(EventoInput.class);

			// indico a qui�n debe reportar
		
			((ItfGestionAgenteReactivo) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.setGestorAReportar(
							NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, conjuntoEventos);

			logger.debug("GestorOrganizaci�n: Creando gestor de recursos ...");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Creando gestor de recursos ...",
					InfoTraza.NivelTraza.debug));
			
			
			
			// Gestor de recursos: local o remoto?
			
			DescInstanciaAgente gestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS); 
			 nodoDestino = gestorRecursos.getNodo().getNombreUso();
			if (nodoDestino.equals(esteNodo)) {
				FactoriaAgenteReactivo.instancia().crearAgenteReactivo(
						gestorRecursos);
			} else {
				intentos = 0;
				ok = false;
				while (!ok) {
					++intentos;
					try {
						((FactoriaAgenteReactivo) RepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_AGENTE_REACTIVO
												+ nodoDestino))
								.crearAgenteReactivo(gestorRecursos);
						ok = true;
					} catch (Exception e) {
						trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
								"Error al crear agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear agente "
										+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS
										+ " en un nodo remoto. Se volver� a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);

						Thread.sleep(1000 * intentos);
						ok = false;
					}
				}
			}
			logger.debug("GestorOrganizaci�n: Gestor de recursos creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestor de recursos creado.",
					InfoTraza.NivelTraza.debug));

			// indico a qui�n debe reportar
			((ItfGestionAgenteReactivo) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.setGestorAReportar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION
									, conjuntoEventos);

			logger.debug("GestorOrganizaci�n: Gestores registrados correctamente.");
			
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Gestores registrados correctamente.",
					InfoTraza.NivelTraza.debug));

			this.itfUsoAgente.aceptaEvento(new EventoInput("gestores_creados",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));

		} catch (Exception e) {
			logger.error("GestorOrganizaci�n: Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",e);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible crear los gestores de agentes y recursos en el gestor de la organizaci�n",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_en_creacion_gestores",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorAgentes() {

		logger.debug("GestorOrganizaci�n: Arrancando Gestor de Agentes.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Arrancando Gestor de Agentes.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.arranca();
			// this.itfUsoAgente.aceptaEvento(new
			// EventoInput("gestor_agentes_arrancado_ok"));

		} catch (Exception e) {
			logger.error("GestorOrganizaci�n: Fue imposible arrancar el Gestor de Agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Agentes.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_en_arranque_gestor_agentes",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void arrancarGestorRecursos() {

		logger.debug("GestorOrganizaci�n: Arrancando Gestor de Recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Arrancando Gestor de Recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.arranca();
			// this.itfUsoAgente.aceptaEvento(new
			// EventoInput("gestor_recursos_arrancado_ok"));
		} catch (Exception e) {
			logger.error("GestorOrganizaci�n: Fue imposible arrancar el Gestor de Recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"Fue imposible arrancar el Gestor de Recursos.",
					InfoTraza.NivelTraza.error));
			e.printStackTrace();
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_en_arranque_gestor_recursos",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void gestoresArrancadosConExito() {
		// creo hebra de monitorizaci�n
		hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
				this.itfUsoAgente, "monitorizar");
		this.hebra.start();
		logger.debug("GestorOrganizaci�n: Gestor de la organizaci�n esperando peticiones.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Gestor de la organizaci�n esperando peticiones.",
				InfoTraza.NivelTraza.debug));
	}

	/**
	 * Decide qu� hacer en caso de fallos en el gestor de agentes y/o en el
	 * gestor de recursos
	 */
	public void decidirTratamientoErrorIrrecuperable() {
            
		// el tratamiento ser� siempre cerrar todo el chiringuito
		logger.debug("GestorOrganizaci�n: Se decide cerrar el sistema ante un error cr�tico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se decide cerrar el sistema ante un error cr�tico irrecuperable.",
				InfoTraza.NivelTraza.debug));
                trazas.mostrarMensajeError("Error irrecuperable. Esperando por su solicitud de terminación");
		/*
                try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"tratamiento_terminar_gestores_y_gestor_organizacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
                
	}

	/**
	 * intenta arrancar el gestor de agentes y/o el gestor de recursos si alguno
	 * ha dado problemas en el arranque.
	 */
	public void recuperarErrorArranqueGestores() {
		// por defecto no se implementan pol�ticas de recuperaci�n
		logger.debug("GestorOrganizaci�n: Fue imposible recuperar el error en el arranque de los gestores.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Fue imposible recuperar el error en el arranque de los gestores.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"imposible_recuperar_arranque",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Elabora un informe del estado en el que se encuentran el gestor de
	 * agentes y el gestor de recursos y lo env�a al sistema de trazas.
	 */
	public void generarInformeErrorIrrecuperable() {
		// Producimos traza de un error
		logger.debug("GestorOrganizaci�n: Finalizando gestor de la organizaci�n debido a un error irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Finalizando gestor de la organizaci�n debido a un error irrecuperable.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput("informe_generado",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Monitoriza al gestor de recursos y al gestor de agentes.
	 */
	public void monitorizarGestores() {
		// monitorizamos los dos gestores en serie
		// if(DEBUG) System.out.println("GestorOrganizaci�n: Iniciando ciclo de
		// monitorizaci�n");
		boolean errorAlMonitorizar = false;
		int monitAgentes = 0;
		int monitRecursos = 0;
		try {
			monitAgentes = ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES))
					.obtenerEstado();
			monitRecursos = ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.obtenerEstado();

			// � hay problemas con el gestor de agentes ?
			errorAlMonitorizar = ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)
					|| (monitAgentes == InterfazGestion.ESTADO_TERMINADO) || (monitAgentes == InterfazGestion.ESTADO_TERMINANDO));

			if (errorAlMonitorizar) {

				logger.debug("GestorOrganizaci�n: Error al monitorizar gestores");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"Error al monitorizar gestores",
						InfoTraza.NivelTraza.debug));
				if ((monitAgentes == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE)
						|| (monitAgentes == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE)) {
					logger.error("GestorOrganizaci�n: El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes);
					trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"El GESTOR DE AGENTES ha fallado. Su estado es "+ monitAgentes,
						InfoTraza.NivelTraza.error));
				}
				else{
					logger.error("GestorOrganizaci�n: El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos);
					trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
						"El GESTOR DE RECURSOS ha fallado. Su estado es "+ monitRecursos,
						InfoTraza.NivelTraza.error));
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				}
			} 
			else {
				this.itfUsoAgente.aceptaEvento(new EventoInput("gestores_ok",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				// if(DEBUG) System.out.println("GestorOrganizaci�n:
				// Monitorizaci�n de los gestores ok");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
						NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Da orden de terminaci�n al gestor de agentes si est� activos.
	 */
	public void terminarGestorAgentes() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizaci�n: Terminando gestor de agentes");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de agentes",
				InfoTraza.NivelTraza.debug));
		InterfazGestion gestorAgentes;
		try {
			gestorAgentes = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			gestorAgentes.termina();

			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_agentes",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

		} catch (Exception ex) {
			logger.debug("GestorOrganizaci�n: No se pudo acceder al gestor de agentes.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de agentes.",
					InfoTraza.NivelTraza.debug));
			ex.printStackTrace();
		}
	}

	/**
	 * Da orden de terminaci�n al gestor de recursos si est� activos.
	 */
	public void terminarGestorRecursos() {
		// mandamos la orden de terminar a los gestores
		logger.debug("GestorOrganizaci�n: Terminando gestor de recursos");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de recursos",
				InfoTraza.NivelTraza.debug));
		InterfazGestion gestorRecursos;
		try {
			gestorRecursos = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			gestorRecursos.termina();

			// timeout de 5 segundos
			this.generarTimeOut(2000, "timeout_gestor_recursos",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
		} catch (Exception ex) {
			logger.debug("GestorOrganizaci�n: No se pudo acceder al gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
					"No se pudo acceder al gestor de recursos.",
					InfoTraza.NivelTraza.debug));
			ex.printStackTrace();
		}

	}

	public void procesarPeticionTerminacion() {
		logger.debug("GestorOrganizaci�n: Procesando la petici�n de terminaci�n");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Procesando la petici�n de terminaci�n",
				InfoTraza.NivelTraza.debug));
		
		trazas.pedirConfirmacionTerminacionAlUsuario();
		
		/*try {
			// this.itfUsoAgente.aceptaEvento(new
			// EventoInput("termina",null,null));
			
		
			ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestion.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void comenzarTerminacionConfirmada() {
		logger.debug("GestorOrganizaci�n: Comenzando terminaci�n de la organizaci�n...");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Comenzando la terminaci�n de la organizaci�n...",
				InfoTraza.NivelTraza.info));
		try {
			ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestion.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	/**
	 * Da orden de terminaci�n al gestor de agentes y/o al gestor de recursos si
	 * est�n activos.
	 */
	/*
	 * public void terminarGestoresActivos() { // mandamos la orden de terminar
	 * a los gestores logger.debug("GestorOrganizaci�n: Terminando los
	 * gestores"); try {
	 * ((InterfazGestion)this.itfUsoRepositorio.obtenerInterfaz(NombresInterfaces.ITF_GES_GESTOR_AGENTES)).termina(); }
	 * catch (Exception ex) { logger.debug("GestorOrganizaci�n: No se pudo
	 * acceder al gestor de agentes."); ex.printStackTrace(); } try {
	 * ((InterfazGestion)this.itfUsoRepositorio.obtenerInterfaz(NombresInterfaces.ITF_GES_GESTOR_RECURSOS)).termina(); }
	 * catch (Exception ex) { logger.debug("GestorOrganizaci�n: No se pudo
	 * acceder al gestor de recursos."); ex.printStackTrace(); } try {
	 * this.itfUsoAgente.aceptaEvento(new
	 * EventoInput("gestores_terminados",null,null)); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	/**
	 * Intenta recuperar los errores detectados en la monitorizaci�n siguiendo
	 * la pol�tica definida para cada gestor.
	 */
	public void recuperarErrorAlMonitorizarGestores() {
		// por defecto no se implementan pol�ticas de recuperaci�n
		logger.debug("GestorOrganizaci�n: No se pudo recuperar el error de monitorizaci�n.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"No se pudo recuperar el error de monitorizaci�n.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"imposible_recuperar_error_monitorizacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * destruye los recursos que se crearon a lo largo del ciclo de vida del
	 * gestor de la organizaci�n-
	 */
	public void terminarGestorOrganizacion() {
		// termina el gestor.
		// puede esperarse a que terminen los dos gestores para mayor seguridad
		logger.debug("GestorOrganizaci�n: Terminando gestor de la organizaci�n y los recursos de la infraestructura.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Terminando gestor de la organizaci�n y los recursos de la infraestructura.",
				InfoTraza.NivelTraza.debug));
		try {
			// se acaba con los recursos de la organizaci�n que necesiten ser
			// terminados
			((ItfGestionRecursoSimple) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.RECURSO_TRAZAS)).termina();

			// y a continuaci�n se termina el gestor de organizaci�n
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION))
					.termina();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("GestorOrganizaci�n: Cerrando sistema.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Cerrando sistema.",
				InfoTraza.NivelTraza.debug));
		if (this.hebra != null)
			this.hebra.finalizar();
		/*
		if (ORBDaemonExec.finalInstance() != null) {
			ORBDaemonExec.finalInstance().finalize();
		}
		*/
	}

	public void clasificaError() {
	}

	public void tratarTerminacionNoConfirmada() {
		logger.debug("Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorOrganizacion",
				"Se ha recibido un evento de timeout debido a que un gestor no ha confirmado la terminaci�n. Se procede a continuar la terminaci�n del sistema",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"continuaTerminacion",
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}