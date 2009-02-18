package icaro.infraestructura.recursosOrganizacion.configuracion.imp;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ConstructorProperties;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.ComponenteGestionado;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Instancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.InstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;
import icaro.infraestructura.patronRecursoSimple.UsoRecursoException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ConstructorDescInstancias {
	ConfiguracionImp config;

	public ConstructorDescInstancias(ConfiguracionImp configuracionImp) {
		this.config = configuracionImp;
	}

	public DescInstanciaAgenteAplicacion construirDescInstanciaAgenteAplicacion(
			String id) throws UsoRecursoException {
		try {
			DescInstanciaAgenteAplicacion descAgente = new DescInstanciaAgenteAplicacion();
			descAgente.setId(id);
			// Obtener la instancia
			List<Instancia> instanciasAgentes = config.getDescOrganizacion()
					.getInstancias().getAgentesAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasAgentes.iterator();
			Instancia instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener el comportamiento
				descAgente.setDescComportamiento(config
						.getDescComportamientoAgente(instancia
								.getRefDescripcion()));
				// Obtener lista de propiedades
				descAgente.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo específico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo común de las instancias de agentes
					nodo = config.getDescOrganizacion().getInstancias()
							.getAgentesAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo común de todas las instancias
						nodo = config.getDescOrganizacion().getInstancias()
								.getNodoComun();
					}
				}
				descAgente.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
			}
			return descAgente;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripción de la instancia de agente con id "
							+ id);
		}

	}

	public DescInstanciaGestor construirDescInstanciaGestor(String id)
			throws UsoRecursoException {
		try {
			DescInstanciaGestor descGestor = new DescInstanciaGestor();
			descGestor.setId(id);
			// Obtener la instancia
			List<InstanciaGestor> instanciasGestores = config
					.getDescOrganizacion().getInstancias().getGestores()
					.getInstanciaGestor();
			Iterator<InstanciaGestor> iter = instanciasGestores.iterator();
			InstanciaGestor instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener el comportamiento
				descGestor.setDescComportamiento(config
						.getDescComportamientoAgente(instancia
								.getRefDescripcion()));
				// Obtener lista de propiedades
				descGestor.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo específico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo común de las instancias de gestores
					nodo = config.getDescOrganizacion().getInstancias()
							.getGestores().getNodoComun();
					if (nodo == null) {
						// obtener el nodo común de todas las instancias
						nodo = config.getDescOrganizacion().getInstancias()
								.getNodoComun();
					}
				}
				descGestor.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
				// obtener lista de componentes gestionados
				List<ComponenteGestionado> componentes = instancia
						.getComponentesGestionados().getComponenteGestionado();
				Iterator<ComponenteGestionado> iterator = componentes
						.iterator();
				ComponenteGestionado componente = null;
				List<DescInstancia> componentesGestionados = new ArrayList<DescInstancia>();
				while (iterator.hasNext()) {
					componente = iterator.next();
					DescInstancia inst = null;
					switch (componente.getTipoComponente()) {
					case GESTOR:
						inst = this.construirDescInstanciaGestor(componente
								.getRefId());
						break;
					case AGENTE_APLICACION:
						inst = this
								.construirDescInstanciaAgenteAplicacion(componente
										.getRefId());
						break;
					case RECURSO_APLICACION:
						inst = this
								.construirDescInstanciaRecursoAplicacion(componente
										.getRefId());

					}
					componentesGestionados.add(inst);
				}
				descGestor.setComponentesGestionados(componentesGestionados);

			} else
				throw new UsoRecursoException(
						"Error al interpretar la descripción de la instancia de gestor con id "
								+ id);
			return descGestor;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripción de la instancia de gestor con id "
							+ id);
		}
	}

	public DescInstanciaRecursoAplicacion construirDescInstanciaRecursoAplicacion(
			String id) throws UsoRecursoException {
		try {
			DescInstanciaRecursoAplicacion descInstanciaRecursoAplicacion = new DescInstanciaRecursoAplicacion();
			descInstanciaRecursoAplicacion.setId(id);
			// Obtener la instancia
			List<Instancia> instanciasRecursos = config.getDescOrganizacion()
					.getInstancias().getRecursosAplicacion().getInstancia();
			Iterator<Instancia> iter = instanciasRecursos.iterator();
			Instancia instancia = null;
			boolean encontrado = false;
			while (iter.hasNext() && !encontrado) {
				instancia = iter.next();
				if (instancia.getId().equals(id)) {
					encontrado = true;
				}
			}
			if (encontrado) {
				// Obtener descripcion
				descInstanciaRecursoAplicacion.setDescRecurso(config
						.getDescRecursoAplicacion(instancia
								.getRefDescripcion()));
				// Obtener lista de propiedades
				descInstanciaRecursoAplicacion.setPropiedades(ConstructorProperties
						.obtenerProperties(instancia.getListaPropiedades()));
				// obtener el nodo específico
				Nodo nodo = instancia.getNodoEspecifico();
				if (nodo == null) {
					// obtener el nodo común de las instancias de recursos de aplicación
					nodo = config.getDescOrganizacion().getInstancias()
							.getRecursosAplicacion().getNodoComun();
					if (nodo == null) {
						// obtener el nodo común de todas las instancias
						nodo = config.getDescOrganizacion().getInstancias()
								.getNodoComun();
					}
				}
				descInstanciaRecursoAplicacion.setNodo(nodo);
				if (nodo == null) {
					throw new UsoRecursoException(
							"Error al leer el nodo de la instancia de agente con id "
									+ id);
				}
			}
			return descInstanciaRecursoAplicacion;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsoRecursoException(
					"Error al interpretar la descripción de la instancia de agente con id "
							+ id);
		}
	}

}
