package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.RolAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;



/**
 * Produce instancias del patrón
 * 
 * @author Felipe Polo
 * @created 30 de noviembre de 2007
 */

public class FactoriaAgenteReactivoImp extends FactoriaAgenteReactivo {

	/*
	 * Crea una instancia del patrón que crea un agente reactivo a partir de las
	 * acciones semánticas, el autómata que define el comportamiento y el nombre
	 * del agente.
	 */
	public void crearAgenteReactivo(DescInstanciaAgente descInstanciaAgente) {
		try {
			
			String nombreInstancia = descInstanciaAgente.getId();
			DescComportamientoAgente agente = descInstanciaAgente.getDescComportamiento();
			String nombreComportamiento = agente.getNombreComportamiento();
			boolean esGestor = agente.getRol() == RolAgente.GESTOR;
			
			//se obtienen los parámetros necesarios
			AccionesSemanticasAgenteReactivo acciones = obtenerAcciones(descInstanciaAgente);
			String rutaTabla = obtenerRutaTablaTransiciones(descInstanciaAgente);
			
			//se crea la instancia del patrón y se registran sus interfaces
			AgenteReactivoAbstracto patron = new AgenteReactivoImp(acciones, rutaTabla,nombreInstancia);
			
			acciones.setItfUsoAgenteReactivo(patron);
			
			RepositorioInterfaces.instance().registrarInterfaz(
					NombresPredefinidos.ITF_GESTION + nombreInstancia, (ItfGestionAgenteReactivo)patron);
			RepositorioInterfaces.instance().registrarInterfaz(
					NombresPredefinidos.ITF_USO + nombreInstancia, (ItfUsoAgenteReactivo)patron);
			
			// activamos trazas pesadas
			patron.setDebug(false);

		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	
	/*
	 * Este método crea un agente reactivo a partir de un fichero
	 * donde se especifica su configuración
	 */

//	public void crearAgenteReactivoDesdeFichero(String fichConfig) {
//		try {
//			ConfiguracionGlobal config = leerConfig(fichConfig);
//			
//			AccionesSemanticasAgenteReactivo acciones = obtenerAccionesGlobal(config);
//			String rutaTabla = obtenerRutaTablaTransiciones(config);
//			String nombre = obtenerNombre(config);
//			
//			AgenteReactivoAbstracto patron = new AgenteReactivoImp(
//					acciones, rutaTabla,
//					nombre);
//			
//			RepositorioInterfaces.instance().registrarInterfaz(
//					NombresPredefinidos.ITF_GESTION + nombre, (ItfGestionAgenteReactivo)patron);
//			RepositorioInterfaces.instance().registrarInterfaz(
//					NombresPredefinidos.ITF_USO + nombre, (ItfUsoAgenteReactivo)patron);
//
//			
//			acciones.setConfiguracionGlobal(config);
//			acciones.setItfUsoAgenteReactivo(patron);
//			
//			String archivoLog = "log/log.log";
//		    String nivelLog = "debug";
//			patron.setParametrosLoggerAgReactivo(archivoLog, nivelLog);
//
//			
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//	}
	
	/*************************************** Métodos auxiliares para leer a partir de la configuración ***************************************/
//	
//	private static ConfiguracionGlobal leerConfig(String ficheroConfig) {
//		ConfiguracionGlobal conf = null;
//		try {
//			FileReader file = new FileReader(ficheroConfig);
//			conf = ConfiguracionGlobal.unmarshal(file);
//		} catch (FileNotFoundException ex) {
//			System.err.println("El fichero " + ficheroConfig + " no existe");
//			ex.printStackTrace();
//		} catch (Exception ex2) {
//			System.err
//					.println("El fichero "
//							+ ficheroConfig
//							+ " no es un fichero de configuración válido. Verifique que la sintaxis del fichero es correcta y compatible con el fichero XML-SCHEMA que le corresponde.");
//			ex2.printStackTrace();
//		}
//		return conf;
//	}

	private String normalizarRuta(String ruta){
	/*Esta función cambia la primera letra del nombre y la pone en minúsculas*/
		String primero = ruta.substring(0,1).toLowerCase(); //obtengo el primer carácter en minúsculas
		String rutaNormalizada = primero + ruta.substring(1, ruta.length()); 
		
		return rutaNormalizada;
	}
	private AccionesSemanticasAgenteReactivo obtenerAcciones(DescInstanciaAgente instAgente) { 
	
		DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
		String ruta =  null;
		if (comportamiento instanceof DescComportamientoAgenteReactivo) {
			DescComportamientoAgenteReactivo comportamientoReactivo = (DescComportamientoAgenteReactivo) comportamiento;
			ruta = comportamientoReactivo.getRutaAcciones();
		} else {
			ruta = "icaro.";
			if(comportamiento.getRol() == RolAgente.AGENTE_APLICACION) {
				String paquete = normalizarRuta(comportamiento.getNombreComportamiento());
				ruta += "aplicaciones.agentes."+paquete+"Reactivo.comportamiento.AccionesSemanticas"+comportamiento.getNombreComportamiento();
			}
			else if(comportamiento.getRol() == RolAgente.GESTOR) {
				String paquete = normalizarRuta(comportamiento.getNombreComportamiento());
				ruta += "gestores."+paquete+".comportamiento.AccionesSemanticas"+comportamiento.getNombreComportamiento();
			}
		}

		String nombre = instAgente.getId();
		
		try {
			Class claseAcciones = Class.forName(ruta);
			AccionesSemanticasAgenteReactivo acciones = (AccionesSemanticasAgenteReactivo)claseAcciones.newInstance();
			acciones.setNombreAgente(nombre);
			return acciones;
		} catch (InstantiationException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones del gestor de recursos, no puede instanciarse.");
		} catch (IllegalAccessException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones del gestor de recursos, no tiene un constructor sin parámetros.");
		} catch (ClassNotFoundException ex) {
			System.err
					.println("La clase "
							+ ruta
							+ "que debe implementar las acciones semánticas, no existe.");
		}
		// si falla algo, devuelvo un null
		return null;
	}

	/**
	 * Lee del fichero de config la ruta de la tabla de transiciones
	 * 
	 * @param ficheroConfig
	 * @return
	 */
	private String obtenerRutaTablaTransiciones(DescInstanciaAgente instAgente) {
		DescComportamientoAgente comportamiento = instAgente.getDescComportamiento();
		String ruta =  null;
		if (comportamiento instanceof DescComportamientoAgenteReactivo) {
			DescComportamientoAgenteReactivo comportamientoReactivo = (DescComportamientoAgenteReactivo) comportamiento;
			ruta = comportamientoReactivo.getRutaAutomata();
		} else {
			ruta = "/icaro/";
			if(comportamiento.getRol() == RolAgente.AGENTE_APLICACION) {
				String paquete = normalizarRuta(comportamiento.getNombreComportamiento());
				ruta += "aplicaciones/agentes/"+paquete+"Reactivo/comportamiento/automata.xml";
			}
			else if(comportamiento.getRol() == RolAgente.GESTOR) {
				String paquete = normalizarRuta(comportamiento.getNombreComportamiento());
				ruta += "gestores/"+paquete+"/comportamiento/automata.xml";
			}
		}
		return ruta;
		/*
		String ruta = "./automatas/TablaEstados"+nombreAgente+".xml";
		return ruta;
		*/
	}
	
	/**
	 * Lee del fichero de config el nombre del agente
	 * 
	 * @param ficheroConfig
	 * @return
	 */
//	private String obtenerNombre(ConfiguracionGlobal config) {
//		String nombre = config.getNombre();
//		return nombre;
//	}

}