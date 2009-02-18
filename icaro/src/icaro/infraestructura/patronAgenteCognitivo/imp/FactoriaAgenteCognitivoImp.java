/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteCognitivo.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;



/**
 * 
 * @author carf
 */
public class FactoriaAgenteCognitivoImp extends FactoriaAgenteCognitivo {

	@Override
	public void crearAgenteCognitivo(String nombreAgente)
			throws Exception {
		ItfUsoRepositorioInterfaces repositorio = RepositorioInterfaces.instance();
		ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) repositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
		
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,"FactoriaAgenteCognitivo: Creando agente "+nombreAgente,NivelTraza.debug));
		AgenteCognitivoImp agente = new AgenteCognitivoImp(nombreAgente);
		
		trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,"FactoriaAgenteCognitivo: Registrando agente "+nombreAgente,NivelTraza.debug));
		repositorio.registrarInterfaz(NombresPredefinidos.ITF_USO+nombreAgente, (ItfUsoAgenteCognitivo)agente );
		repositorio.registrarInterfaz(NombresPredefinidos.ITF_GESTION+nombreAgente, (InterfazGestion)agente);
		
	}

}
