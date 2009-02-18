package icaro.gestores.gestorRecursos.comportamiento;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Implementación por defecto de las acciones que se ejecutarán por parte del
 * gestor de recursos.
 * 
 * @author Felipe Polo
 * @created 3 de diciembre de 2007
 */

public class AccionesSemanticasGestorRecursos extends
		AccionesSemanticasAgenteReactivo {
	public static final int intentosCreacion = 3;
	/**
	 * Almacén de los nombres de los agentes que este gestor debe gestionar
	 */
	private Vector<String> nombresRecursosGestionados = new Vector<String>();

	/**
	 * Hebra para la monitorización
	 */
	private HebraMonitorizacion hebra;

	/**
	 * Tiempo de monitorización
	 */
	int tiempoParaNuevaMonitorizacion;

	/**
	 * Constructor por defecto
	 */
	public AccionesSemanticasGestorRecursos() {
		super();
		// obtenemos el repositorio de interfaces
		this.itfUsoRepositorio = RepositorioInterfaces.instance();
	}

	public void configurarGestor() {
		try {
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);
			tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal("gestores.comun.intervaloMonitorizacion"));
			
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"gestor_configurado",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("GestorRecursos: Hubo problemas al configurar el gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo problemas al configurar el gestor de recursos.",
					InfoTraza.NivelTraza.error));
		}

	}

	/**
	 * Crea la lista de recursos que posteriormente se va a utilizar para crear
	 * cada uno de los recursos
	 */
	public void listarRecursos() {
		try {
			 logger.debug("GestorRecursos: Listando los recursos definidos en la configuración.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Listando los recursos definidos en la configuración.",
					InfoTraza.NivelTraza.debug));
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);

			List<DescInstancia> lista = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS).getComponentesGestionados();

			Object[] parametros = new Object[] { lista, new Integer(0) };
			this.itfUsoAgente.aceptaEvento(new EventoInput("recursos_listados",
					parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("GestorRecursos: Hubo problemas al listar los recursos desde la configuración.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo problemas al listar los recursos desde la configuración.",
					InfoTraza.NivelTraza.error));
		}
	}

	/**
	 * crea el recurso que ocupa el lugar del insdice en la lista de recursos
	 * 
	 * @param lista
	 * @param indice
	 */
	public void crearRecurso(ArrayList<DescInstanciaRecursoAplicacion> lista, Integer indice) {
		Iterator<DescInstanciaRecursoAplicacion> iterador = lista.iterator();
		logger.debug("GestorRecursos: Creando recurso.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Creando recurso.",
				InfoTraza.NivelTraza.debug));
		int j = 0;
		String nombre;
		boolean error = false;
		boolean encontrado = false;
		DescInstanciaRecursoAplicacion recurso = null;

		while (iterador.hasNext() && (encontrado == false)) {
			recurso = iterador.next();
			if (j == indice.intValue()) {// es el que debemos arrancar
				encontrado = true;
				nombre = recurso.getId();
				try {
					logger.debug("GestorRecursos: Creando recurso " + nombre+ ".");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Creando recurso " + nombre+ ".",
							InfoTraza.NivelTraza.debug));
					crearUnRecurso(recurso);
					// si todo ha ido bien, debemos añadirlo a la lista de
					// objetos gestionados por el gestor
					logger.debug("GestorRecursos: Añadiendo recurso " + nombre+ " a la lista de objetos gestionados.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Añadiendo recurso " + nombre+ " a la lista de objetos gestionados.",
							InfoTraza.NivelTraza.debug));
					this.nombresRecursosGestionados.add(nombre);
				} catch (Exception ex) {
					logger.error("GestorRecursos: Hubo problemas al crear el recurso "+ nombre+ " desde la configuración. Recuperando errores de creación.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Hubo problemas al crear el recurso "+ nombre+ " desde la configuración. Recuperando errores de creación.",
							InfoTraza.NivelTraza.error));
					ex.printStackTrace();
					error = true;
				}

			} else
				j++;
		}
		try {
			Object[] parametros;
			if (error == false && encontrado == true) {
				parametros = new Object[] { lista,
						new Integer(indice.intValue() + 1) };
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"recurso_creado", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (error == false && encontrado == false)
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"recursos_creados", new Integer(0),
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			else if (error == true) {
				parametros = new Object[] { lista, recurso, indice };
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_en_creacion_recurso", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea un recurso y lo registra en el repositorio
	 * 
	 * @param recurso
	 */
	private void crearUnRecurso(DescInstanciaRecursoAplicacion recurso) throws Exception {
		try {

			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
					.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.CONFIGURACION);

			logger.debug("GestorRecursos: Construyendo recurso " + recurso.getId()+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Construyendo recurso " + recurso+ ".",
					InfoTraza.NivelTraza.debug));
			// Recurso de aplicación: local o remoto?
			
			
			DescInstanciaGestor descGestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS);
			String esteNodo = descGestorRecursos.getNodo().getNombreUso();


			String nodoDestino = recurso.getNodo().getNombreUso();
			int intentos = 0;
			boolean ok = false;

			if (nodoDestino.equals(esteNodo)) {
				FactoriaRecursoSimple.instance().crearRecursoSimple(recurso);
			} else {
				while (!ok) {
					++intentos;
					try {
						((FactoriaRecursoSimple) RepositorioInterfaces
								.instance()
								.obtenerInterfaz(
										NombresPredefinidos.FACTORIA_RECURSO_SIMPLE
												+ nodoDestino))
								.crearRecursoSimple(recurso);
						ok = true;
					} catch (Exception e) {
						e.printStackTrace();
						trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
								"Error al crear el recurso "
										+ recurso
										+ " en un nodo remoto. Se volverá a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino,
								InfoTraza.NivelTraza.error));
						logger
								.error("Error al crear el recurso "
										+ recurso
										+ " en un nodo remoto. Se volverá a intentar en "
										+ intentos
										+ " segundos...\n nodo origen: "
										+ esteNodo + "\t nodo destino: "
										+ nodoDestino);
						Thread.sleep(1000 * intentos);
						ok = false;
					}
				}
			}
			logger.debug("GestorRecursos: Recurso " + recurso + " creado.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Recurso " + recurso + " creado.",
					InfoTraza.NivelTraza.debug));

		} catch (Exception ex) {
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Error al crear el recurso " + recurso+ ".",
					InfoTraza.NivelTraza.error));
			logger.error("GestorRecursos: Error al crear el recurso " + recurso+ ".", ex);
			throw ex;
		}
	}

	/**
	 * Funcion que genera el evento para comenzar los reintentos de creacion de
	 * un recurso
	 * 
	 * @param recurso
	 *            el recurso que ha dado fallo en la creacion
	 * @param indice
	 *            para poder luego continuar con la creacion de los recursos
	 */
	public void recuperarErrorCreacionRecurso(List<DescInstanciaRecursoAplicacion> lista,
			DescInstanciaRecursoAplicacion recurso, Integer indice) {
		String idRecurso = recurso.getId();
		Object[] parametros = new Object[] { lista, recurso, intentosCreacion,
				indice };
		// por defecto no se implementan políticas de recuperación
		logger.debug("GestorRecursos: Comenzando los reintentos de creacion para el recurso "+ idRecurso + ".");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Comenzando los reintentos de creacion para el recurso "+ idRecurso + ".",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput("reintenta",
					parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se intenta crear un recurso el numero de veces que indiquen los
	 * reintentos hasta que se cree correctamente.
	 */
	public void reintentarCreacionRecurso(List<DescInstanciaRecursoAplicacion> lista, DescInstanciaRecursoAplicacion recurso,
			Integer reintento, Integer indice) {
		boolean fin_reintentos = false;
		boolean error = false;
		String idRecurso = recurso.getId();
		
		if (reintento.equals(0)) {
			fin_reintentos = true;
		}
		if (!fin_reintentos) { // se reintenta la creacion
			logger.debug("GestorRecursos: Reintento de creación " + reintento+ " del recurso " + idRecurso);
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Reintento de creación " + reintento
					+ " del recurso " + idRecurso,
					InfoTraza.NivelTraza.debug));
			try {

				logger.debug("GestorRecursos: Creando recurso " + idRecurso+ ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Creando recurso " + idRecurso+ ".",
						InfoTraza.NivelTraza.debug));
				
				// crearlos uno a uno dependiendo de su tipo
				crearUnRecurso(recurso);

				// si todo ha ido bien, debemos añadirlo a la lista de objetos
				// gestionados por el gestor
				logger.debug("GestorRecursos: Añadiendo recurso " + idRecurso	+ " a la lista de objetos gestionados.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Añadiendo recurso " + idRecurso	+ " a la lista de objetos gestionados.",
						InfoTraza.NivelTraza.debug));
				this.nombresRecursosGestionados.add(idRecurso);
			} catch (Exception ex) {
				logger.error("GestorRecursos: Hubo problemas al crear el recurso "+ idRecurso+ " desde la configuración. Recuperando errores de creación.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo problemas al crear el recurso "+ idRecurso+ " desde la configuración. Recuperando errores de creación.",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
				error = true;
			}
		}
		try {
			Object[] parametros;
			if (fin_reintentos) { // ya hay que decidir que se va a hacer una
									// vez que se sabe que no se puede crear el
									// recurso
				logger.debug("GestorRecursos: Agotados los intentos de crear el recurso "+ idRecurso + ". Continuando con la creación.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Agotados los intentos de crear el recurso "+ idRecurso + ". Continuando con la creación.",
						InfoTraza.NivelTraza.debug));
				parametros = new Object[] { lista, recurso,
						new Integer((reintento.intValue() - 1)), indice };
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"continua_creacion", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (error) {
				parametros = new Object[] { lista, recurso,
						new Integer((reintento.intValue() - 1)), indice };
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"reintento_error", parametros,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} else if (!error) {
				parametros = new Object[] { lista,
						new Integer((indice.intValue() + 1)) };
				this.itfUsoAgente.aceptaEvento(new EventoInput("reintento_ok",
						parametros, NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * arranca el siguiente recurso que especifique la configuracion.
	 */
	public void arrancarRecurso(Integer indice) {
		logger.debug("GestorRecursos: Arrancando recurso.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Arrancando recurso.",
				InfoTraza.NivelTraza.debug));
		boolean errorEnArranque = false;

		// seleccionamos el recurso que corresponde
		String nombre = (String) this.nombresRecursosGestionados
				.elementAt(indice.intValue());
		try {
			// recuperamos el interfaz de gestion del recurso
			logger.debug("GestorRecursos: Es necesario arrancar el recurso "+ nombre + ", recuperando interfaz de gestión.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Es necesario arrancar el recurso "+ nombre + ", recuperando interfaz de gestión.",
					InfoTraza.NivelTraza.debug));
			InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
			// arrancamos el recurso
			logger.debug("GestorRecursos: Arrancando el recurso " + nombre+ ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Arrancando el recurso " + nombre+ ".",
					InfoTraza.NivelTraza.debug));
			itfGesAg.arranca();
			logger.debug("GestorRecursos: Orden de arranque ha sido dada al recurso "+ nombre + ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Orden de arranque ha sido dada al recurso "+ nombre + ".",
					InfoTraza.NivelTraza.debug));
		} catch (Exception ex) {
			logger.error("GestorRecursos: Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ nombre + " en el gestor de recursos.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Hubo un problema al acceder al interfaz remoto mientras se arrancaba el recurso "	+ nombre + " en el gestor de recursos.",
					InfoTraza.NivelTraza.error));
			ex.printStackTrace();
			errorEnArranque = true;
		}
		if (errorEnArranque) { // ha ocurrido algún problema en el arranque del
								// recurso
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_en_arranque_recurso",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.error("GestorRecursos: Se produjo un error en el arranque del recurso "+ nombre + ".");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"Se produjo un error en el arranque del recurso "+ nombre + ".",
					InfoTraza.NivelTraza.error));
			
		} else {// el recurso ha sido arrancado
			if (indice.intValue() == (this.nombresRecursosGestionados.size() - 1)) { // ya
																						// no
																						// hay
																						// más
																						// recursos
																						// que
																						// arrancar
				logger.debug("GestorRecursos: Terminado proceso de arranque automático de recursos.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminado proceso de arranque automático de recursos.",
						InfoTraza.NivelTraza.debug));
				try {
					this.itfUsoAgente.aceptaEvento(new EventoInput(
							"recursos_arrancados_ok",
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
					this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
							"gestor_recursos_arrancado_ok",
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug("GestorRecursos: Gestor de recursos esperando peticiones.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Gestor de recursos esperando peticiones.",
						InfoTraza.NivelTraza.debug));

				// creo hebra de monitorización
				hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
						this.itfUsoAgente, "monitorizar");
				this.hebra.start();
			} else { // hay mas recursos que arrancar
				logger.debug("GestorRecursos: Terminado arranque recurso "+ nombre + ". Arrancando el siguiente recurso.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminado arranque recurso "+ nombre + ". Arrancando el siguiente recurso.",
						InfoTraza.NivelTraza.debug));
				try {
					this.itfUsoAgente.aceptaEvento(new EventoInput(
							"recurso_arrancado", new Integer(
									indice.intValue() + 1),
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
							NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Devuelve cierto si es necesario arrancar el recurso
	 * 
	 * @param nombreRecurso
	 * @return
	 */
	/*
	 * private boolean esNecesarioArrancar(String nombreRecurso) { Enumeration
	 * enume = configEspecifica.getListaRecursos().enumerateRecurso(); while
	 * (enume.hasMoreElements()) { Recurso item = (Recurso)enume.nextElement();
	 * if (nombreRecurso.equals(item.getNombre())) return
	 * item.getHayQueArrancarlo(); } logger.error("GestorRecursos: No se
	 * encontró ningún recurso con nombre "+nombreRecurso+" dentro de la lista
	 * de objetos gestionados."); throw new NullPointerException(); }
	 */
	/**
	 * Decide qué hacer en caso de fallos en los recursos.
	 */
	public void decidirTratamientoErrorIrrecuperable() {
		// el tratamiento será siempre cerrar todo el chiringuito
		logger.debug("GestorRecursos: Se decide cerrar el sistema ante un error crítico irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Se decide cerrar el sistema ante un error crítico irrecuperable.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"tratamiento_terminar_recursos_y_gestor_recursos",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * intenta arrancar los recursos que especifique la config y hayan dado
	 * problemas.
	 */
	public void recuperarErrorArranqueRecurso() {
		// por defecto no se implementan políticas de recuperación
		logger.debug("GestorRecursos: Fue imposible recuperar el error en el arranque de los recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Fue imposible recuperar el error en el arranque de los recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
					"error_en_arranque_gestores",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"imposible_recuperar_arranque",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Esta acción no hace nada.
	 */
	public void vacio() {
	}

	/**
	 * Elabora un informe del estado en el que se encuentran los recursos y lo
	 * envía al sistema de trazas.
	 */
	public void generarInformeErrorIrrecuperable() {
		// Producimos traza de un error
		logger.error("GestorRecursos: Finalizando gestor de recursos debido a un error irrecuperable.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Finalizando gestor de recursos debido a un error irrecuperable.",
				InfoTraza.NivelTraza.error));
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
					"peticion_terminar_todo",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
			this.itfUsoAgente.aceptaEvento(new EventoInput("informe_generado",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * da orden de terminar a un recurso
	 */
	public void finalizarRecurso() {
		// esto hay que recuperarlo de los parámetros
		logger.debug("GestorRecursos: finalizarRecurso():Este método no está implementado");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"finalizarRecurso():Este método no está implementado",
				InfoTraza.NivelTraza.debug));
		throw new UnsupportedOperationException();
	}

	/**
	 * Crea y arranca un recurso. Es necesario pasar las características del
	 * recurso a crear por parámetro.
	 */
	public void crearRecurso() {
		// esto hay que recuperarlo de los parámetros
		logger.debug("GestorRecursos: crearRecurso():Este método no está implementado");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"crearRecurso():Este método no está implementado",
				InfoTraza.NivelTraza.debug));
		throw new UnsupportedOperationException();
	}

	/**
	 * Monitoriza secuencialmente todos los recursos activos que estén definidos
	 * como necesarios en la monitorización.
	 */
	public void monitorizarRecursos() {
		System.out.println("GestorRecursos:Comienza ciclo monitorización.");

		boolean errorEncontrado = false;
		// recuperar todos los interfaces de gestión del repositorio que estamos
		// gestionando
		Enumeration enume = nombresRecursosGestionados.elements();
		while (enume.hasMoreElements() && !errorEncontrado) {
			String nombre = (String) enume.nextElement();

			try {

				ItfGestionRecursoSimple itfGes2 = (ItfGestionRecursoSimple) this.itfUsoRepositorio
						.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
								+ nombre);

				int monitoriz = itfGes2.obtenerEstado();

				// TODO poner condiciones de monitorizacion
				if (monitoriz == 0) {
					errorEncontrado = true;
					logger.debug("GestorRecursos:Recurso " + nombre	+ " está en estado erróneo o terminado.");
					trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
							"Recurso " + nombre	+ " está en estado erróneo o terminado.",
							InfoTraza.NivelTraza.debug));
				} else
					System.out.println("GestorRecursos:Recurso " + nombre
							+ " está ok.");

			} catch (Exception ex) {
				errorEncontrado = true;
				logger.error("GestorRecursos: Hubo errores al monitorizar.");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo errores al monitorizar.",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
			}
		}

		if (errorEncontrado)
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput(
						"error_al_monitorizar",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			try {
				this.itfUsoAgente.aceptaEvento(new EventoInput("recursos_ok",
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
						NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * Da orden de terminación a todos los recursos que se encuentran
	 * activos/arrancando
	 */
	public void terminarRecursosActivos() {
		logger.debug("GestorRecursos: Terminando los recursos que estén activos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Terminando los recursos que estén activos.",
				InfoTraza.NivelTraza.debug));
		// recorremos todos los recursos gestionados
		Enumeration enumRecursos = this.nombresRecursosGestionados.elements();
		String nombre = "";
		while (enumRecursos.hasMoreElements()) {
			try {
				nombre = (String) enumRecursos.nextElement();
				// sólo se terminan los recursos que se arranquen desde aquí.

				// para cada recurso, recuperamos su itf de gestion
				logger.debug("GestorRecursos: Recuperando Itf Gestión del recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Recuperando Itf Gestión del recurso "+ nombre + ".",
						InfoTraza.NivelTraza.debug));
				InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio
						.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
								+ nombre);
				// finalizamos el recurso
				logger.debug("GestorRecursos: Terminando el recurso " + nombre	+ ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Terminando el recurso " + nombre	+ ".",
						InfoTraza.NivelTraza.debug));
				itfGesAg.termina();
				logger.debug("GestorRecursos: Orden de terminación ha sido dada al recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Orden de terminación ha sido dada al recurso "+ nombre + ".",
						InfoTraza.NivelTraza.debug));
			} catch (Exception ex) {
				logger.error("GestorRecursos: Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminación al recurso "+ nombre + ".");
				trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
						"Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminación al recurso "+ nombre + ".",
						InfoTraza.NivelTraza.error));
				ex.printStackTrace();
			}
		}
		logger.debug("GestorRecursos: Finalizado proceso de terminación de todos los recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Finalizado proceso de terminación de todos los recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"recursos_terminados",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Intenta recuperar los errores detectados en la monitorización siguiendo
	 * la política definida para cada recurso.
	 */
	public void recuperarErrorAlMonitorizarRecursos() {
		// por defecto no se implementan políticas de recuperación
		logger.debug("GestorRecursos: No se pudo recuperar el error de monitorización.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"No se pudo recuperar el error de monitorización.",
				InfoTraza.NivelTraza.debug));
		try {
			this.itfUsoAgente.aceptaEvento(new EventoInput(
					"imposible_recuperar_error_monitorizacion",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * destruye los recursos que se crearon a lo largo del ciclo de vida de este
	 * recurso.
	 */
	public void terminarGestorRecursos() {
		// termina el gestor.
		// puede esperarse a que terminen los recursos
		logger.debug("GestorRecursos: Terminando gestor de recursos.");
		trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
				"Terminando gestor de recursos.",
				InfoTraza.NivelTraza.debug));
		try {
			this.hebra.finalizar(); // CUIDADO, SI FALLASE LA CREACION DE LOS
									// RECURSOS ESTA HEBRA
		} // NO ESTA INICIALIZADA
		catch (Exception e) {
			e.printStackTrace();
			logger.error("GestorOrganizacion: La hebra no ha podido ser finalizada porque no había sido creada.");
			trazas.aceptaNuevaTraza(new InfoTraza("GestorRecursos",
					"La hebra no ha podido ser finalizada porque no había sido creada.",
					InfoTraza.NivelTraza.error));
		}
		try {
			((InterfazGestion) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_RECURSOS))
					.termina();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
					"gestor_recursos_terminado",
					NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
					NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void clasificaError() {
	}
}
