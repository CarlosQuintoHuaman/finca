/*
 * MotorReglasDrools.java
 *
 * Creado 18 de abril de 2007, 11:56
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfMotorReglas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;

/**
 * 
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class MotorReglasDrools implements ItfMotorReglas {
	private WorkingMemory workingMemory;
	private AgenteCognitivo agente;
	
	private Log log = LogFactory.getLog(MotorReglasDrools.class);
	private ItfUsoRecursoTrazas trazas;

	public MotorReglasDrools(AgenteCognitivo agente) {
		this.agente = agente;
		try {
			trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		} catch (Exception e) {	e.printStackTrace(); }
	}

	public void dispararReglas() {
		workingMemory.fireAllRules();
	}

	public void compilarReglas(InputStream fichero) throws Exception {
		PackageBuilderConfiguration conf = new PackageBuilderConfiguration();
		conf.setJavaLanguageLevel("1.5");
		PackageBuilder builder = new PackageBuilder(conf);
		try {
			builder.addPackageFromDrl(new InputStreamReader(fichero));
			org.drools.rule.Package packRules = builder.getPackage();
			RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			ruleBase.addPackage(packRules);
			workingMemory = ruleBase.newWorkingMemory();
			WMLogger wmlogger = new WMLogger(agente);
			ALogger alogger = new ALogger(agente);
			workingMemory.addEventListener(wmlogger);
			workingMemory.addEventListener(alogger);
			trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: Reglas compiladas correctamente. " ,NivelTraza.info));
		} catch (Exception e) {
			trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: Error al compilar las reglas. " + e,NivelTraza.error));
		}
	}

	public void agregarHecho(Object objeto) {
		trazas.aceptaNuevaTraza(new InfoTraza(
							agente.getNombre(),
							"Motor de reglas: nuevo hecho agregado: "+ objeto,
							NivelTraza.info));
		workingMemory.assertObject(objeto);
		workingMemory.fireAllRules();
	}

	public void agregarVariableGlobal(String nombre, Object object) {
		try {
		workingMemory.setGlobal(nombre, object);
		} catch(NullPointerException ex) {
			log.error("Error al compilar las reglas del agente "+agente.getNombre(), ex);
			trazas.aceptaNuevaTraza(new InfoTraza(agente.getNombre(),"Motor de reglas: Error al compilar las reglas. ",NivelTraza.error));
		}
	}

}
