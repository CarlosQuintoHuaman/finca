package icaro.infraestructura.recursosOrganizacion.configuracion.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ConstructorProperties;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescOrganizacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.UsoRecursoException;
import icaro.infraestructura.recursosOrganizacion.configuracion.Configuracion;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;


/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:43
 */
public class ConfiguracionImp extends Configuracion {

	private DescOrganizacion descOrganizacion;
	

	

	private File schema;
	private File xmlDescripcion;

	private static final String DESCRIPCION_SCHEMA = "./schemas/DescripcionOrganizacionSchema.xsd";
	private static final String DESCRIPCION_XML_POR_DEFECTO = "descripcionAcceso.xml";
	private static final String RUTA_DESCRIPCIONES = "./config/icaro/aplicaciones/descripcionOrganizaciones/";
	private static final String PAQUETE_JAXB = "icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb";
	private String descXML = RUTA_DESCRIPCIONES+DESCRIPCION_XML_POR_DEFECTO;

	private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());
	
	private ConstructorDescInstancias constructorDescInstancias;
	private Properties properties;

	public ConfiguracionImp(String descAlternativa) {
		super(NombresPredefinidos.CONFIGURACION);
		descXML = RUTA_DESCRIPCIONES+descAlternativa;
		creacionConfiguracion();
	}

	public ConfiguracionImp() {
		super(NombresPredefinidos.CONFIGURACION);
		creacionConfiguracion();
	}

	public void creacionConfiguracion() {
		// Lectura de los ficheros (esquema y xml)
		schema = new File(DESCRIPCION_SCHEMA);
		//URL url = this.getClass().getResource(descXML);
		try { 
		xmlDescripcion = new File(descXML);
		} catch(Exception e) {
			logger.fatal("No se ha encontrado el fichero de descripcion ./config/"+descXML+". Compruebe la ruta y el nombre del fichero.",e);
			throw new RuntimeException();
		}
		if (!xmlDescripcion.exists()) {
			logger.fatal("No se ha encontrado el fichero de descripcion:"
					+ "\n\t\t\t" + xmlDescripcion.getAbsolutePath()
					+ ".\n Compruebe la ruta y el nombre del fichero.");
			throw new RuntimeException();
		}
		if (!schema.exists()) {
			logger
					.fatal("Error. No se ha encontrado el esquema del fichero de descripcion:"
							+ "\n\t\t\t"
							+ schema.getAbsolutePath()
							+ ".\n Compruebe la ruta y el nombre del fichero.");
			throw new RuntimeException();
		}

		// Validaci�n del fichero xml:
		SchemaValidator validator = new SchemaValidator(schema);

		if (validator.validate(xmlDescripcion)) {

			// Interpretaci�n del xml con JAXB
			try {
				JAXBContext jc = JAXBContext
						.newInstance(PAQUETE_JAXB);
				Unmarshaller unmarshaller;
				unmarshaller = jc.createUnmarshaller();
				descOrganizacion = (DescOrganizacion) ((JAXBElement) unmarshaller
						.unmarshal(new FileInputStream(xmlDescripcion))).getValue();
			} catch (Exception e) {

				logger.fatal(
						"Error al interpretar la descripcion de la organizacion que se encuentra en:"
								+ xmlDescripcion.getAbsolutePath(), e);
				throw new RuntimeException();
			}
			
			try {
				// Actualizacion de los nombres predefinidos:
				NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION = this.descOrganizacion
						.getInstancias().getGestores().getInstanciaGestor()
						.get(0).getId();
				NombresPredefinidos.NOMBRE_GESTOR_AGENTES = this.descOrganizacion
						.getInstancias().getGestores().getInstanciaGestor()
						.get(1).getId();
				NombresPredefinidos.NOMBRE_GESTOR_RECURSOS = this.descOrganizacion
						.getInstancias().getGestores().getInstanciaGestor()
						.get(2).getId();
			} catch (Exception e) {
				logger
						.fatal(
								"Error al actualizar los nombres predefinidos de los gestores. Compruebe que existan, en la descripci�n de la organizaci�n, las descripciones de las instancias de los tres gestores (g.organizaci�n, g.agentes y g.recursos)",
								e);
			}
			
			/*TODO
			 * Verificar que el c�digo sea consistente con los comportamientos descritos en la configuraci�n.
			 */
			
			constructorDescInstancias = new ConstructorDescInstancias(this);
			properties = ConstructorProperties.obtenerProperties(descOrganizacion.getPropiedadesGlobales().getListaPropiedades());
		}

		else {
			logger
					.fatal("\n\nEl fichero de descripcion del XML "
							+ xmlDescripcion.getAbsolutePath()
							+ "  no es valido. Compruebe la sintaxis de la descripcion y vuelva a intentarlo");
			throw new RuntimeException();
		}

	}

	public DescComportamientoAgente getDescComportamientoAgente(String nombre)
			throws UsoRecursoException {
		// buscar entre los gestores
		Iterator<DescComportamientoAgente> iter = this.descOrganizacion
				.getDescripcionComponentes().getDescComportamientoAgentes()
				.getDescComportamientoGestores().getDescComportamientoAgente()
				.iterator();
		boolean encontrado = false;
		DescComportamientoAgente desc;
		while (iter.hasNext()) {
			desc = iter.next();
			if (desc.getNombreComportamiento().equals(nombre))
				return desc;
		}
		iter = this.descOrganizacion.getDescripcionComponentes()
				.getDescComportamientoAgentes()
				.getDescComportamientoAgentesAplicacion()
				.getDescComportamientoAgente().iterator();
		// buscar entre los agentes de aplicaci�n
		while (iter.hasNext()) {
			desc = iter.next();
			if (desc.getNombreComportamiento().equals(nombre))
				return desc;
		}
		throw new UsoRecursoException(
				"El comportamiento de nombre "
						+ nombre
						+ "no se encuentra en la descripcion de comportamiento de gestores ni en la descripci�n de comportamiento de agentes de aplicaci�n");
	}

	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre)
			throws UsoRecursoException {
		Iterator<DescRecursoAplicacion> iter = this.descOrganizacion.getDescripcionComponentes().getDescRecursosAplicacion().getDescRecursoAplicacion().iterator();
		DescRecursoAplicacion desc;
		while (iter.hasNext()) {
			desc = iter.next();
			if (desc.getNombre().equals(nombre))
				return desc;
		}
		throw new UsoRecursoException("La descripci�n de recurso de aplicaci�n de nombre "+ nombre
						+ "no se encuentra en la descripcion de comportamiento de recursos de aplicaci�n");
	}

	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(
			String id) throws UsoRecursoException {
		return constructorDescInstancias
				.construirDescInstanciaAgenteAplicacion(id);
	}

	public DescInstanciaGestor getDescInstanciaGestor(String id)
			throws UsoRecursoException {
		return constructorDescInstancias.construirDescInstanciaGestor(id);
	}

	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(
			String id) throws UsoRecursoException {
		return constructorDescInstancias
				.construirDescInstanciaRecursoAplicacion(id);
	}

	public String getValorPropiedadGlobal(String atributo)
			throws UsoRecursoException {
		return properties.getProperty(atributo);
		
	}
	
	public DescOrganizacion getDescOrganizacion() {
		return descOrganizacion;
	}	

	/*
	 * public DescAgenteAplicacion getDescAgenteAplicacion(String nombre) { if
	 * (nombre.matches(NombresPredefinidos.EXPR_REG_NOMBRE_AGENTE)) {
	 * Iterator<DescAgenteAplicacion> iter = descOrganizacion
	 * .getDescripcionAgentesAplicacion() .getDescAgenteAplicacion().iterator();
	 * boolean encontrado = false; DescAgenteAplicacion descAgente = null; while
	 * (iter.hasNext() && !encontrado) { descAgente = iter.next(); if
	 * (descAgente.getNombre().equals(nombre)) encontrado = true; else
	 * descAgente = null; } if (descAgente == null)
	 * logger.info("El agente de nombre "+ nombre+
	 * " no se encuentra en la descripcion de la organizaci�n"); return
	 * descAgente; } else { logger.info("El nombre "+ nombre+
	 * " no es v�lido para un agente. Expresion regular del nombre de un agente: "
	 * + NombresPredefinidos.EXPR_REG_NOMBRE_AGENTE); return null; }
	 * 
	 * }
	 * 
	 * 
	 * public DescripcionGestor getDescripcionGestor(String nombre) { if
	 * (nombre.matches(NombresPredefinidos.EXPR_REG_NOMBRE_GESTOR)) { // �es el
	 * gestor de organizacion? DescripcionGestor descGestor = null;
	 * Iterator<DescGestorGestionado> iter; boolean encontrado = false; if
	 * (nombre .startsWith(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION)) {
	 * descGestor = descOrganizacion.getDescripcionGestores()
	 * .getDescGestorOrganizacion(); if (descGestor.getNombre().equals(nombre))
	 * return descGestor; } // �es un gestor de agentes? else if (nombre
	 * .startsWith(NombresPredefinidos.NOMBRE_GESTOR_AGENTES)) { iter =
	 * descOrganizacion.getDescripcionGestores()
	 * .getDescGestoresAgentes().getDescGestorAgentes() .iterator();
	 * 
	 * while (iter.hasNext() && !encontrado) { descGestor = iter.next(); if
	 * (descGestor.getNombre().equals(nombre)) encontrado = true; else
	 * descGestor = null; } // �es un gestor de recursos? } else if (nombre
	 * .startsWith(NombresPredefinidos.NOMBRE_GESTOR_RECURSOS)) {
	 * 
	 * iter = descOrganizacion.getDescripcionGestores()
	 * .getDescGestoresRecursos().getDescGestorRecursos() .iterator(); while
	 * (iter.hasNext() && !encontrado) { descGestor = iter.next(); if
	 * (descGestor.getNombre().equals(nombre)) encontrado = true; else
	 * descGestor = null; } }
	 * 
	 * if (descGestor == null) logger .info("El gestor de nombre " + nombre +
	 * " no se encuentra en la descripcion de la organizaci�n"); return
	 * descGestor; } else { logger .info("El nombre " + nombre +
	 * " no es valido para un gestor. Expresion regular del nombre de un agente: "
	 * + NombresPredefinidos.EXPR_REG_NOMBRE_GESTOR); return null; }
	 * 
	 * }
	 * 
	 * 
	 * public DescRecurso getDescripcionRecurso(String nombre) {
	 * Iterator<DescRecurso> iter = descOrganizacion.getDescripcionRecursos()
	 * .getDescRecurso().iterator(); boolean encontrado = false; DescRecurso
	 * descRecurso = null; while (iter.hasNext() && !encontrado) { descRecurso =
	 * iter.next(); if (descRecurso.getNombre().equals(nombre)) encontrado =
	 * true; else descRecurso = null; }
	 * 
	 * if (descRecurso == null) { logger.info("El recurso de nombre " + nombre +
	 * " no se encuentra en la descripci�n de la organizacion"); }
	 * 
	 * return descRecurso; }
	 * 
	 * 
	 * public EntornoEjecucion getEntornoEjecucionAgente(String nombre) {
	 * EntornoEjecucion entorno = null; if
	 * (nombre.matches(NombresPredefinidos.EXPR_REG_NOMBRE_AGENTE)) {
	 * ConstructorEntornoEjecucion constEntorno = new
	 * ConstructorEntornoEjecucion( descOrganizacion); entorno =
	 * constEntorno.construirEntornoAgente(nombre); } else {
	 * logger.info("El nombre " + nombre +
	 * " no es valido para un agente. Expresion regular del nombre de un agente: "
	 * + NombresPredefinidos.EXPR_REG_NOMBRE_AGENTE); } return entorno; }
	 * 
	 * 
	 * public EntornoEjecucionEspecificoGestor getEntornoEjecucionGestor( String
	 * nombre) { EntornoEjecucionEspecificoGestor entorno = null; if
	 * (nombre.matches(NombresPredefinidos.EXPR_REG_NOMBRE_GESTOR)) {
	 * ConstructorEntornoEjecucionGestor constEntorno = new
	 * ConstructorEntornoEjecucionGestor( descOrganizacion); entorno =
	 * constEntorno.construirEntornoGestor(nombre); } else { logger
	 * .info("El nombre " + nombre +
	 * " no es valido para un gestor. Expresion regular del nombre de un agente: "
	 * + NombresPredefinidos.EXPR_REG_NOMBRE_AGENTE); } return entorno; }
	 * 
	 * 
	 * public EntornoEjecucion getEntornoEjecucionRecurso(String nombre) {
	 * EntornoEjecucion entorno = null; ConstructorEntornoEjecucion constEntorno
	 * = new ConstructorEntornoEjecucion( descOrganizacion); entorno =
	 * constEntorno.construirEntornoRecurso(nombre); return entorno; }
	 * 
	 * 
	 * public String getValorPropiedad(String atributo) { return (String)
	 * properties.get(atributo); }
	 * 
	 * 
	 * public String getNombreGestorOrganizacion() { return
	 * descOrganizacion.getDescripcionGestores()
	 * .getDescGestorOrganizacion().getNombre(); }
	 */

}