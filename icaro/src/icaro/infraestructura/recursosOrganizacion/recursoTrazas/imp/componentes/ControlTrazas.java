package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasEspecificas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.List;







public class ControlTrazas {

	ClasificadorVisual clasificador;

	public ControlTrazas(ClasificadorVisual c) {
		this.clasificador = c;

	}

	public void muestraTraza(InfoTraza traza) {
		// actualiza la nueva traza en todos los paneles

		this.clasificador.getPanelPrincipal().muestraMensaje(traza);
		List<InfoPanelEspecifico> listaInfoPaneles = this.clasificador
				.getArrayInfoPaneles();
		boolean encontrado = false;

		for (int i = 0; (i < listaInfoPaneles.size()) && (!encontrado); i++) {
			InfoPanelEspecifico panelInfoActual = listaInfoPaneles.get(i);
			if (panelInfoActual.getIdentificador().equals(traza.getNombre())) {
				// si encuentro la informaci�n del agente solicitado, a�ado la
				// nueva traza

				panelInfoActual.setContenido(panelInfoActual.getContenido()
						+ getMensajeNormalizado(traza) + "\n");

				List<PanelTrazasEspecificas> listaPaneles = this.clasificador
						.getArrayPaneles();
				boolean encontrado2 = false;
				for (int j = 0; (j < listaPaneles.size()) && (!encontrado2); j++) {
					PanelTrazasEspecificas panelActual = listaPaneles.get(j);
					if (panelActual.getIdentificador()
							.equals(traza.getNombre())) {
						panelActual.muestraMensaje(traza);
						encontrado2 = true;
					}
				}
				encontrado = true;
			}
		}

	}

	private String getMensajeNormalizado(InfoTraza traza) {
		String nivel = "";

		if (traza.getNivel() == InfoTraza.NivelTraza.debug) {
			nivel = "DEBUG";
		} else if (traza.getNivel() == InfoTraza.NivelTraza.info) {
			nivel = "INFO";
		} else if (traza.getNivel() == InfoTraza.NivelTraza.error) {
			nivel = "ERROR";
		} else { // fatal
			nivel = "FATAL";
		}

		return nivel + " : " + traza.getMensaje();

	}

	public void muestraVentanaEspecifica(String nombreVentana) {

		List<InfoPanelEspecifico> listaInfoPaneles = this.clasificador
				.getArrayInfoPaneles();
		List<PanelTrazasEspecificas> listaPaneles = this.clasificador
				.getArrayPaneles();

		boolean encontrado = false;

		for (int i = 0; (i < listaInfoPaneles.size()) && (!encontrado); i++) {
			InfoPanelEspecifico panelActual = listaInfoPaneles.get(i);
			if (panelActual.getIdentificador().equals(nombreVentana)) {
				// si encuentro la informaci�n del agente solicitado
				if (!existePanel(nombreVentana)) {
					// creo la ventana si no est� ya creada
					PanelTrazasEspecificas panel = new PanelTrazasEspecificas(
							panelActual.getIdentificador(), panelActual
									.getContenido(), this);
					double a = Math.random() * 700;
					panel.setLocation((int) a, 550);
					panel.setVisible(true);
					listaPaneles.add(panel);
				}
				encontrado = true;
			}
		}
	}

	private boolean existePanel(String nombre) {
		List<PanelTrazasEspecificas> listaPaneles = this.clasificador
				.getArrayPaneles();
		boolean encontrado = false;

		for (int i = 0; (i < listaPaneles.size()) && (!encontrado); i++) {
			PanelTrazasEspecificas panelActual = listaPaneles.get(i);
			if (panelActual.getIdentificador().equals(nombre)) {
				encontrado = true;
			}
		}

		return encontrado;

	}

	public void cierraVentanaPrincipal() {
		this.clasificador.getPanelPrincipal().setVisible(false);

	}

	public void cierraVentanaEspecifica(String nombreVentana) {
		List<PanelTrazasEspecificas> listaPaneles = this.clasificador
				.getArrayPaneles();
		boolean encontrado = false;
		int i;

		for (i = 0; (i < listaPaneles.size()) && (!encontrado); i++) {
			PanelTrazasEspecificas panelActual = listaPaneles.get(i);
			if (panelActual.getIdentificador().equals(nombreVentana)) {
				panelActual.setVisible(false);
				encontrado = true;
			}
		}
		if (encontrado)// lo elimino de la lista
			listaPaneles.remove(i - 1);
	}

	public void pedirTerminacionOrganizacion() {
		try {
			ItfUsoAgenteReactivo gestorOrganizacion = (ItfUsoAgenteReactivo)RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestorOrganizacion.aceptaEvento(new EventoInput("peticion_terminar_todo_usuario", NombresPredefinidos.RECURSO_TRAZAS, NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
