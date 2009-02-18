package icaro.infraestructura.clasesGeneradoras;

import icaro.infraestructura.entidadesBasicas.interfaces.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivoSimple.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArranqueNodoSecundario {
	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ArranqueNodoSecundario.class);
	private static String nombreNodo= null;
	/**
	 * Método de arranque principal de la organización
	 * 
	 * @param args
	 *            Entrada: ruta completa hasta el fichero de configuración
	 */
	public static void main(String args[]) {
		
		
		
		// Crear el ORB a partir del HOST leido desde la configuracion.
		InputStream corba;
		Properties props = null;
		
		try {
			corba = new FileInputStream("./config/corba.properties");
			props = new Properties();

			props.load(corba);
			
			nombreNodo = (String) props.get("nombreNodo");
			
		} catch (Exception e) {
			logger.fatal("Error al leer el fichero ./config/corba.properties");
		}
		
		if (nombreNodo == null) {
			logger.fatal("Error al leer el nombre del nodo. Por favor compruebe que la propiedad nombreNodo en el fichero ./config/corba.properties es correcta");
			System.exit(1);
		}
		boolean ok = false;
		int intentos = 0;
		while (!ok) {
			
			try{
				++intentos;
			//registrar la factoria del patrón de agente reactivo
				RepositorioInterfaces.instance().registrarInterfaz(NombresPredefinidos.FACTORIA_AGENTE_REACTIVO+nombreNodo,
					FactoriaAgenteReactivo.instancia());
				logger.info("Registrando factoria de agente reactivo en el nodo "+ nombreNodo );
		
			//registrar la factoria del patrón de recurso simple
				RepositorioInterfaces.instance().registrarInterfaz(NombresPredefinidos.FACTORIA_RECURSO_SIMPLE+nombreNodo,
						FactoriaRecursoSimple.instance());
				logger.info("Registrando factoria recurso simple en el nodo "+ nombreNodo );
				// registrar la factoria del patrón de agente cognitivo
				RepositorioInterfaces.instance().registrarInterfaz(
						NombresPredefinidos.FACTORIA_AGENTE_COGNITIVO + nombreNodo,
						FactoriaAgenteCognitivo.instancia());
				logger.info("Registrando factoria agente cognitivo en el nodo "
						+ nombreNodo);
				ok = true;
			} catch (Exception e) {
				logger.error("Error al registrar las factorías en el nodo "+ nombreNodo+". Se volverá a intentar...");
				try {
				
					Thread.sleep(1000*intentos);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				ok = false;
		}
		}
		
		java.lang.Object sync = new java.lang.Object();
		
		synchronized (sync) {
			try {
				sync.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
