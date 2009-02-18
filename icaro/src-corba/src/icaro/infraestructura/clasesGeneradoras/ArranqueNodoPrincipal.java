package icaro.infraestructura.clasesGeneradoras;


import icaro.infraestructura.corba.ORBDaemonExec;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivoSimple.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.UsoRecursoException;
import icaro.infraestructura.recursosOrganizacion.configuracion.Configuracion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.RecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArranqueNodoPrincipal {
	private static final long serialVersionUID = 1L;

	/**
	 * Método de arranque principal de la organización
	 * 
	 * @param args
	 *            Entrada: ruta completa hasta el fichero de configuración
	 */

	private static Log logger = LogFactory.getLog(ArranqueNodoPrincipal.class);
	private static String nombreNodo,nombreGestor, port, host;
	private static ItfUsoConfiguracion itfConfig;
	private final static String CORBA_PROPERTIES = "./config/corba.properties";
	

	public static void main(String args[]) {
		
		
		leerPropiedades();
		
		arranqueORB();

		crearConfiguracion(args==null ? null :args[0]);
		
		crearRecursoTrazas();

   		registrarInterfaces();

		arrancarGestorOrganizacion();


	}
	private static void leerPropiedades(){
		
		//recoger el nombre del nodo.
		
		// Crear el ORB a partir del HOST leido desde la configuracion.
		InputStream corba;
		Properties props = null;
		
		try {
			corba = new FileInputStream(CORBA_PROPERTIES);
			props = new Properties();

			props.load(corba);
			
			host = (String) props.get("org.omg.CORBA.ORBInitialHost");
			port= (String) props.get("org.omg.CORBA.ORBInitialPort");
			nombreNodo = (String) props.get("nombreNodo");
			
		} catch (Exception e) {
			logger.fatal("Error al leer el fichero ./config/corba.properties");
			System.exit(1);
		}
	}

	/*
	 * Crea una instancia de la configuración, recoge el nombre del gestor de la organización y lee el nombre del nodo a partir del entorno de ejecución del mismo.
	 */
	private static void crearConfiguracion(String descripcionXMLAlternativo) {
		if (descripcionXMLAlternativo == null)
			itfConfig = Configuracion.instance();
		else
			itfConfig = Configuracion.instance(descripcionXMLAlternativo);
		//obtener nombre del nodo:
		nombreGestor = NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
		String nombreNodoConfig;
		try {
			nombreNodoConfig = itfConfig.getDescInstanciaGestor(nombreGestor).getNodo().getNombreUso();
			if (!nombreNodo.equals(nombreNodoConfig)) {
				logger.fatal("El nombre del nodo definido en " +CORBA_PROPERTIES + "("+ nombreNodo+") " +
						"debe coincidir con el nombre definido el la descripcion de la organización ("+ nombreNodoConfig+ ".");
				System.exit(1);
			}
			RepositorioInterfaces.instance().registrarInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION, itfConfig);

		} catch (UsoRecursoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void crearRecursoTrazas() {
		try{
			ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces
			.instance();
			RecursoTrazas recursoTrazas = RecursoTrazas.instance();
			
			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_USO
							+ NombresPredefinidos.RECURSO_TRAZAS, 
					recursoTrazas);
			repositorioInterfaces.registrarInterfaz(
					NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.RECURSO_TRAZAS, 
					recursoTrazas);
		
	} catch(Exception e){
		System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
		e.printStackTrace();
		//no es error crítico
	}
	}
	/*
	 * Arranca el demonio orbd, que se encarga de crear el NamingService y la infraestructura del ORB.
	 */
	private static void arranqueORB() {
		ORBDaemonExec orbd = new ORBDaemonExec(host, port);
		try {
			orbd.start();
			if (orbd.getResult()==0)
				logger.info("Arrancado el demonio orbd");
			else
				logger.fatal("Error al arrancar el demonio orbd");
		} catch (Exception e) {
			logger.fatal("Error al arrancar el demonio orbd");
			System.exit(1);
		}

	}

	/*
	 * Registra en el repositorio de interfaces:
	 *       -interfaz de uso de la configuración
	 *       -interfaz de la factoría de patrón de agente reactivo de este nodo
	 *       -interfaz de la factoría de patrón de recurso simple de este nodo
	 */
	private static void registrarInterfaces() {
		
		boolean ok = false;
		int intentos = 0;
		while (!ok) {
			try {
				++intentos;
				
				// registrar la factoria del patrón de agente reactivo
				RepositorioInterfaces.instance().registrarInterfaz(
						NombresPredefinidos.FACTORIA_AGENTE_REACTIVO + nombreNodo,
						FactoriaAgenteReactivo.instancia());
				logger.info("Registrando factoria de agente reactivo en el nodo "
						+ nombreNodo);
				// registrar la factoria del patrón de recurso simple
				RepositorioInterfaces.instance().registrarInterfaz(
						NombresPredefinidos.FACTORIA_RECURSO_SIMPLE + nombreNodo,
						FactoriaRecursoSimple.instance());
				logger.info("Registrando factoria recurso simple en el nodo "
						+ nombreNodo);
				// registrar la factoria del patrón de agente cognitivo
				RepositorioInterfaces.instance().registrarInterfaz(
						NombresPredefinidos.FACTORIA_AGENTE_COGNITIVO + nombreNodo,
						FactoriaAgenteCognitivo.instancia());
				logger.info("Registrando factoria agente cognitivo en el nodo "
						+ nombreNodo);
				
				
				ok = true;
			} catch (Exception e) {
				logger.error("Error al registrar las factorías en el nodo "+ nombreNodo+". Se volverá a intentar...");
				ok = false;
				
				try {
					Thread.sleep(1000*intentos);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/*
	 * Recoge el nombre leído desde la configuración, crea una instancia del patrón y arranca el gestor de la organización.
	 */
	private static void arrancarGestorOrganizacion() {
		try {
			DescInstanciaAgente descGestor = itfConfig.getDescInstanciaGestor(nombreGestor);
			// creo el agente gestor de organización
			
			FactoriaAgenteReactivo.instancia().crearAgenteReactivo(descGestor);
			ItfGestionAgenteReactivo gestorOrg = (ItfGestionAgenteReactivo) RepositorioInterfaces
					.instance()
					.obtenerInterfaz(
							NombresPredefinidos.ITF_GESTION
									+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			// arranco la organización
			gestorOrg.arranca();
		} catch (Exception ex) {
			logger
					.fatal("Error al arrancar el gestor de organización.");
			System.exit(1);
		}

	}

	/*
	 * Espera peticiones a las factorías registradas en este nodo.
	 */
/*	private static void esperarPeticiones() {
		java.lang.Object sync = new java.lang.Object();
		synchronized (sync) {
			try {
				sync.wait();
			} catch (InterruptedException e) {
				logger.error("Error al esperar peticiones");
			}
		}

	}
*/
}
