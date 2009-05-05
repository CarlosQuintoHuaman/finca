package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.*;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

public class ComprobadorDescripciones {



    private static ComprobadorDescripciones instance;

	//private ItfUsoRecursoTrazas trazas;

	private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

	private List<InfoTraza> errores;

	public ComprobadorDescripciones() {
		errores = new LinkedList<InfoTraza>();
		/*
		try {
			

			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO
									+ NombresPredefinidos.RECURSO_TRAZAS);
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Comprobando descripciones de los comportamientos...",
					NivelTraza.info);
			trazas.aceptaNuevaTraza(traza);



		} catch (Exception e) {
			logger.error("Error al acceder al repositorio de interfaces", e);
		}
*/
	}

	public static ComprobadorDescripciones instance() {
		if (instance == null)
			instance = new ComprobadorDescripciones();
		return instance;
	}

	public void comprobar(DescOrganizacion descOrganizacion) throws Exception {
		DescripcionComponentes componentes = descOrganizacion
				.getDescripcionComponentes();
		// comprobar gestores
		List<DescComportamientoAgente> gestores = componentes
				.getDescComportamientoAgentes().getDescComportamientoGestores()
				.getDescComportamientoAgente();
		for (DescComportamientoAgente gestor : gestores)
			comprobarGestor(gestor);

		// comprobar agentes de aplicacion
		List<DescComportamientoAgente> agentesAplicacion = componentes
				.getDescComportamientoAgentes()
				.getDescComportamientoAgentesAplicacion()
				.getDescComportamientoAgente();
		for (DescComportamientoAgente agenteAplicacion : agentesAplicacion)
			comprobarAgenteAplicacion(agenteAplicacion);
		// comprobar recursos de aplicaci�n
		List<DescRecursoAplicacion> recursosAplicacion = componentes
				.getDescRecursosAplicacion().getDescRecursoAplicacion();
		for (DescRecursoAplicacion recursoAplicacion : recursosAplicacion)
			comprobarRecursoAplicacion(recursoAplicacion);

		if (errores.size() > 0) {
			
			for (InfoTraza traza : errores) {
			//	trazas.aceptaNuevaTraza(traza);
				logger.error(traza);
			}
			throw new Exception();
		}

	}

	public void comprobarGestor(DescComportamientoAgente agente) {
		String nombreAgente = agente.getNombreComportamiento();
		TipoAgente tipo = agente.getTipo();
		// paquete de comportamiento por defecto:
		RolAgente rol = agente.getRol();
		String comportamientoPorDefecto = null;
		if (rol == RolAgente.GESTOR)
			comportamientoPorDefecto = NombresPredefinidos.PAQUETE_GESTORES
					+ "." + primeraMinuscula(nombreAgente)+".comportamiento";

		String comportamientoEspecificado = agente.getLocalizacionComportamiento();
		boolean encontrado = false;
		if (comportamientoEspecificado != null) {
			if (tipo == TipoAgente.REACTIVO)
				encontrado = comprobarAgenteReactivo(
						comportamientoEspecificado, nombreAgente, rol.value());
			else if (tipo == TipoAgente.REACTIVO_SCXML)
				encontrado = comprobarAgenteReactivoSCXML(
						comportamientoEspecificado, nombreAgente, rol.value());
			else if (tipo == TipoAgente.COGNITIVO
					|| tipo == TipoAgente.COGNITIVO_CON_EXPECTATIVAS)
				encontrado = comprobarAgenteCognitivo(
						comportamientoEspecificado, nombreAgente, rol.value());
		}
		// no se especifica localizacion del comportamiento o el
		// especificado no se encuentra. Se usa el
		// comportamiento por defecto:
		if (comportamientoEspecificado == null || !encontrado) {
			if (tipo == TipoAgente.REACTIVO){
				encontrado = comprobarAgenteReactivo(comportamientoPorDefecto,
						nombreAgente, rol.value());
                   if  (encontrado) agente.setLocalizacionComportamiento(comportamientoPorDefecto);}
			else if (tipo == TipoAgente.REACTIVO_SCXML)
				encontrado = comprobarAgenteReactivoSCXML(
						comportamientoPorDefecto, nombreAgente, rol.value());
			else if (tipo == TipoAgente.COGNITIVO
					|| tipo == TipoAgente.COGNITIVO_CON_EXPECTATIVAS)
				encontrado = comprobarAgenteCognitivo(comportamientoPorDefecto,
						nombreAgente, rol.value());
		}
	}

    public void comprobarAgenteAplicacion(DescComportamientoAgente agente) {
		String nombreAgente = agente.getNombreComportamiento();
		TipoAgente tipo = agente.getTipo();
		// paquete de comportamiento por defecto:
		RolAgente rol = agente.getRol();
		String comportamientoPorDefecto = null;
		if (rol == RolAgente.AGENTE_APLICACION)
			comportamientoPorDefecto = NombresPredefinidos.PAQUETE_AGENTES_APLICACION
					+ "." + primeraMinuscula(nombreAgente) + tipo.value()+".comportamiento";
		String comportamientoEspecificado = agente
				.getLocalizacionComportamiento();
		boolean encontrado = false;
		if (comportamientoEspecificado != null) {
			if (tipo == TipoAgente.REACTIVO)
				encontrado = comprobarAgenteReactivo(
						comportamientoEspecificado, nombreAgente, rol.value());
			else if (tipo == TipoAgente.REACTIVO_SCXML)
				encontrado = comprobarAgenteReactivoSCXML(
						comportamientoEspecificado, nombreAgente, rol.value());
			else if (tipo == TipoAgente.COGNITIVO
					|| tipo == TipoAgente.COGNITIVO_CON_EXPECTATIVAS)
				encontrado = comprobarAgenteCognitivo(
						comportamientoEspecificado, nombreAgente, rol.value());
		}
		// no se especifica localizacion del comportamiento o el
		// especificado no se encuentra. Se usa el
		// comportamiento por defecto:
		if (comportamientoEspecificado == null || !encontrado) {
			if (tipo == TipoAgente.REACTIVO)
				encontrado = comprobarAgenteReactivo(comportamientoPorDefecto,
						nombreAgente, rol.value());
			else if (tipo == TipoAgente.REACTIVO_SCXML)
				encontrado = comprobarAgenteReactivoSCXML(
						comportamientoPorDefecto, nombreAgente, rol.value());
			else if (tipo == TipoAgente.COGNITIVO
					|| tipo == TipoAgente.COGNITIVO_CON_EXPECTATIVAS)
				encontrado = comprobarAgenteCognitivo(comportamientoPorDefecto,
						nombreAgente, rol.value());
		}
	}

	public boolean comprobarAgenteReactivo(String rutaComportamiento,
			String nombreAgente, String rolAgente) {
		boolean encontrado = false;
		// acciones semanticas:
		String claseAccionesSemanticas = rutaComportamiento + "."
				+ NombresPredefinidos.PREFIJO_CLASE_ACCIONES_SEMANTICAS
				+ nombreAgente;
		if (existeClase(claseAccionesSemanticas)) {
			encontrado = true;

			/*
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Acciones semanticas leidas para el "
							+ rolAgente.toLowerCase() + nombreAgente + ": "
							+ claseAccionesSemanticas, NivelTraza.debug);
			trazas.aceptaNuevaTraza(traza);
			*/
			logger.debug(NombresPredefinidos.CONFIGURACION
					+ ": Acciones semanticas leidas para el "
					+ rolAgente.toLowerCase() +" "+ nombreAgente + ": "
					+ claseAccionesSemanticas);
		} else {
			encontrado = false;
			InfoTraza traza = new InfoTraza(
					NombresPredefinidos.CONFIGURACION,
					"Error al leer la clase de acciones semanticas "
							+ claseAccionesSemanticas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del "
							+ rolAgente.toLowerCase()+" "+ nombreAgente
							+ " en la descripcion de la organizacion.",
					NivelTraza.fatal);
			logger
					.fatal(NombresPredefinidos.CONFIGURACION
							+ ": Error al leer la clase de acciones semanticas "
							+ claseAccionesSemanticas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del ges"
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.");
			errores.add(traza);
		}

		// automata:
		String rutaAutomata = "/" + rutaComportamiento.replace(".", "/")
				+ "/automata.xml";
		if (existeRecursoClasspath(rutaAutomata)) {
			encontrado &= true;
			logger.debug(NombresPredefinidos.CONFIGURACION
					+ ": Ruta automata del " + rolAgente.toLowerCase()
					+ nombreAgente + ": " + rutaAutomata);
			/*
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Ruta automata del " + rolAgente.toLowerCase()
							+ nombreAgente + ": " + rutaAutomata,
					NivelTraza.debug);
			trazas.aceptaNuevaTraza(traza);
			*/
		} else {
			encontrado &= false;
			logger
					.fatal(NombresPredefinidos.CONFIGURACION
							+ ": Error al leer el automata "
							+ rutaAutomata
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique otra localizacion del comportamiento del "
							+ rolAgente.toLowerCase()+ nombreAgente
							+ " en la descripcion de la organizacion.");
			InfoTraza traza = new InfoTraza(
					NombresPredefinidos.CONFIGURACION,
					"Error al leer el automata "
							+ rutaAutomata
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique otra localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.",
					NivelTraza.fatal);
			errores.add(traza);
		}
		return encontrado;
	}

	public boolean comprobarAgenteReactivoSCXML(String rutaComportamiento,
			String nombreAgente, String rolAgente) {
		boolean encontrado = false;
		// acciones semanticas:
		String rutaAccionesSemanticas = "/"
				+ rutaComportamiento.replace(".", "/")
				+ "/acciones/acciones.xml";

		if (existeRecursoClasspath(rutaAccionesSemanticas)) {
			encontrado = true;
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Acciones semanticas leidas para el "
							+ rolAgente.toLowerCase() + nombreAgente + ": "
							+ rutaAccionesSemanticas, NivelTraza.debug);
			/*
			trazas.aceptaNuevaTraza(traza);
			logger.debug(NombresPredefinidos.CONFIGURACION
					+ ": Acciones semanticas leidas para el "
					+ rolAgente.toLowerCase() + nombreAgente + ": "
					+ rutaAccionesSemanticas);
			*/
		} else {
			encontrado = false;
			InfoTraza traza = new InfoTraza(
					NombresPredefinidos.CONFIGURACION,
					"Error al leer el fichero de acciones semanticas "
							+ rutaAccionesSemanticas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique la localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.",
					NivelTraza.fatal);
			logger
					.fatal(NombresPredefinidos.CONFIGURACION
							+ ": Error al leer la clase de acciones semanticas "
							+ rutaAccionesSemanticas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique la localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.");
			errores.add(traza);
		}

		// automata:
		String rutaAutomata = "/" + rutaComportamiento.replace(".", "/")
				+ "/automata.xml";
		if (existeRecursoClasspath(rutaAutomata)) {
			encontrado &= true;
			logger.debug(NombresPredefinidos.CONFIGURACION
					+ ": Ruta automata del " + rolAgente.toLowerCase()
					+ nombreAgente + ": " + rutaAutomata);
			/*
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Ruta automata del " + rolAgente.toLowerCase()
							+ nombreAgente + ": " + rutaAutomata,
					NivelTraza.debug);
			trazas.aceptaNuevaTraza(traza);
			*/
		} else {
			encontrado &= false;
			logger
					.fatal(NombresPredefinidos.CONFIGURACION
							+ ": Error al leer el automata "
							+ rutaAutomata
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique la localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.");
			InfoTraza traza = new InfoTraza(
					NombresPredefinidos.CONFIGURACION,
					"Error al leer el automata "
							+ rutaAutomata
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique la localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.",
					NivelTraza.fatal);
			errores.add(traza);
		}
		return encontrado;
	}

	public boolean comprobarAgenteCognitivo(String rutaComportamiento,
			String nombreAgente, String rolAgente) {
		boolean encontrado = false;
		String rutaReglas = "/" + rutaComportamiento
				+ "/procesoResolucionObjetivos/reglas.drl";
		if (existeRecursoClasspath(rutaReglas)) {
			encontrado = true;
			logger.debug(NombresPredefinidos.CONFIGURACION
					+ ": Ruta reglas del " + rolAgente.toLowerCase()
					+ nombreAgente + ": " + rutaReglas);
			/*
			InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
					"Ruta reglas del " + rolAgente.toLowerCase() + nombreAgente
							+ ": " + rutaReglas, NivelTraza.debug);
			trazas.aceptaNuevaTraza(traza);
			*/
		} else {
			encontrado = false;
			logger
					.fatal(NombresPredefinidos.CONFIGURACION
							+ ": Error al leer las reglas "
							+ rutaReglas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique otra localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.");
			InfoTraza traza = new InfoTraza(
					NombresPredefinidos.CONFIGURACION,
					"Error al leer las reglas "
							+ rutaReglas
							+ " del "
							+ rolAgente.toLowerCase()
							+ " "+nombreAgente
							+ ". Compruebe que el fichero existe o especifique la localizacion del comportamiento del "
							+ rolAgente.toLowerCase()
							+ " en la descripcion de la organizacion.",
					NivelTraza.fatal);
			errores.add(traza);
		}
		return encontrado;
	}

	public boolean comprobarRecursoAplicacion(
			DescRecursoAplicacion recursoAplicacion) {
		boolean encontrada = false;
		String nombreRecurso = recursoAplicacion.getNombre();
		String claseGeneradoraPorDefecto = NombresPredefinidos.PAQUETE_RECURSOS_APLICACION
				+ "." + primeraMinuscula(nombreRecurso) + ".imp."
				+ NombresPredefinidos.PREFIJO_CLASE_GENERADORA_RECURSO
				+ nombreRecurso;
		String claseGeneradoraEspecificada = recursoAplicacion.getLocalizacionClaseGeneradora();
		if (claseGeneradoraEspecificada != null) {
			if (existeClase(claseGeneradoraEspecificada)) {
				encontrada = true;
				/*
				InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
						"Clase generadora leida para el recurso "
								+ nombreRecurso + ": "
								+ claseGeneradoraEspecificada, NivelTraza.debug);
				
				trazas.aceptaNuevaTraza(traza);
				*/
				logger.debug(NombresPredefinidos.CONFIGURACION
						+ ": Clase generadora leida para el recurso "
						+ nombreRecurso + ": "
						+ claseGeneradoraEspecificada);
			} else {
				encontrada = false;
				InfoTraza traza = new InfoTraza(
						NombresPredefinidos.CONFIGURACION,
						"Error al leer la clase generadora "
								+ claseGeneradoraEspecificada
								+ " del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del recurso" +nombreRecurso
								+ " en la descripcion de la organizacion.",
						NivelTraza.fatal);
				logger
						.fatal(NombresPredefinidos.CONFIGURACION
								+ ": Error al leer la clase generadora "
								+ claseGeneradoraEspecificada
								+ " del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique otra localizacion del comportamiento del recurso" +nombreRecurso
								+ " en la descripcion de la organizacion.");
				errores.add(traza);
			}
		}
		if (claseGeneradoraEspecificada == null || !encontrada) {
			if (existeClase(claseGeneradoraPorDefecto)) {
				encontrada = true;
                recursoAplicacion.setLocalizacionClaseGeneradora(claseGeneradoraPorDefecto);
				/*
				InfoTraza traza = new InfoTraza(NombresPredefinidos.CONFIGURACION,
						"Clase generadora leida para el recurso "
								+ nombreRecurso + ": "
								+ claseGeneradoraPorDefecto, NivelTraza.debug);
				trazas.aceptaNuevaTraza(traza);
				*/
				logger.debug(NombresPredefinidos.CONFIGURACION
						+ ": Clase generadora leida para el recurso "
						+ nombreRecurso + ": "
						+ claseGeneradoraPorDefecto);
			} else {
				encontrada = false;
				InfoTraza traza = new InfoTraza(
						NombresPredefinidos.CONFIGURACION,
						"Error al leer la clase generadora "
								+ claseGeneradoraPorDefecto
								+ "del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique la localizacion del comportamiento del recurso" +nombreRecurso
								+ "en la descripcion de la organizacion.",
						NivelTraza.fatal);
				logger
						.fatal(NombresPredefinidos.CONFIGURACION
								+ ": Error al leer la clase generadora "
								+ claseGeneradoraPorDefecto
								+ "del recurso"
								+ nombreRecurso
								+ ". Compruebe que la clase existe o especifique la localizacion del comportamiento del recurso" +nombreRecurso
								+ "en la descripcion de la organizacion.");
				errores.add(traza);
			}			
		}
		
		return encontrada;
	}

	public boolean existeClase(String rutaClase) {
		
		Class clase;
		try {
			clase = Class.forName(rutaClase);
			logger.debug(rutaClase+"?  OK");
			return (clase != null);
		} catch (ClassNotFoundException e) {
			logger.debug(rutaClase+"?  null");
			return false;
		}
	}

	public boolean existeRecursoClasspath(String recursoClassPath) {
		
		InputStream input = this.getClass().getResourceAsStream(
				recursoClassPath);
		logger.debug(recursoClassPath+"?"+ ((input != null) ? "  OK" : "  null"));
		return (input != null);
	}

	private String primeraMinuscula(String nombre) {
		String firstChar = nombre.substring(0, 1);
		return nombre.replaceFirst(firstChar, firstChar.toLowerCase());
	}
}
