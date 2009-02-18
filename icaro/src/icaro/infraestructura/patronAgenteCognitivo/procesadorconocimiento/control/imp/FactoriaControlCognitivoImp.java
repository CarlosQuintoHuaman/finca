package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.imp;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.ControlCognitivoAbstracto;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.control.FactoriaControlCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.FactoriaGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.gestortareas.ItfGestorTareas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.FactoriaAdaptadorMotorReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfMotorReglas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FactoriaControlCognitivoImp extends FactoriaControlCognitivo {

	private Log log = LogFactory.getLog(FactoriaControlCognitivoImp.class);
	@Override
	public ControlCognitivoAbstracto crearControlCognitivo(
			AgenteCognitivo agente) throws Exception {
		String nombreAgente = agente.getNombre();
		ItfUsoRepositorioInterfaces repositorio = RepositorioInterfaces
		.instance();
		/*
		ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces
				.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO
								+ NombresPredefinidos.CONFIGURACION);
*/
		
		String nombreAgenteEnMinusculas = "a"+ nombreAgente.substring(1);
	
		String nombrePaquete = "/organizacion/agentes/aplicacion/"+ nombreAgenteEnMinusculas+"/";
		String ficheroReglas = "/reglas/"+nombreAgenteEnMinusculas+".drl";
	
		InputStream reglas = null;
		try {
		reglas = this.getClass().getResourceAsStream(ficheroReglas);
		
		if (reglas!=null) {
		ItfMotorReglas motorReglas = FactoriaAdaptadorMotorReglas.instancia()
				.crearAdaptadorMotorReglas(agente);
		ItfGestorTareas gestorTareas = FactoriaGestorTareas.instancia()
				.crearGestorTareas(agente, motorReglas);
		log.info("Compilando reglas...");
		motorReglas.compilarReglas(reglas);
		motorReglas.agregarVariableGlobal("gestorTareas", gestorTareas);
		log.info("Reglas compiladas");
		return new ControlCognitivoImp(motorReglas, gestorTareas);
		} else {
			log.error("Error al compilar reglas del agente "+nombreAgente +". Asegurese de que el fichero "+nombreAgente+".drl no tiene errores sintácticos y que el directorio reglas se encuentre en el classpath");
			throw new Exception();
		}
		} catch (Exception e) {
			log.error("Error al leer compilar reglas del agente "+nombreAgente,e);
			throw e;				
		}
	}

}
