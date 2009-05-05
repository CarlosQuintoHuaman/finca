package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.*;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasEspecificas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.List;
import java.util.TreeSet;
import java.util.HashMap;






public class NotificacionesRecTrazas {

	/**
	 * @uml.property  name="clasificador"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="controlador:icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual"
	 */
	ClasificadorVisual clasificador;
    TreeSet ConjIdentifPaneles;
    HashMap tablaInfoPanelesEspecificos;
    HashMap tablaPanelesEspecificos;
	public NotificacionesRecTrazas() {
	//	this.clasificador = c;
//        TreeSet ConjIdentifPaneles = new TreeSet();
	}
public void pedirTerminacionOrganizacion() {
		try {
			ItfUsoAgenteReactivo gestorOrganizacion = (ItfUsoAgenteReactivo)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestorOrganizacion.aceptaEvento(new EventoRecAgte("peticion_terminar_todo_usuario", NombresPredefinidos.RECURSO_TRAZAS, NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    /*
	public void muestraTraza(InfoTraza traza) {
		// actualiza la nueva traza en todos los paneles

		this.clasificador.getPanelPrincipal().muestraMensaje(traza);
        tablaInfoPanelesEspecificos = this.clasificador.getArrayInfoPaneles();
        tablaPanelesEspecificos = this.clasificador.getArrayPaneles();
        if (tablaInfoPanelesEspecificos != null ) {
        String identElementTraza = traza.getNombre();
        if (tablaInfoPanelesEspecificos.containsKey(identElementTraza)){
          InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(identElementTraza);
            panelInfoActual.setContenido(panelInfoActual.getContenido()
						+ getMensajeNormalizado(traza) + "\n");
          PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(identElementTraza);
                panelActual.muestraMensaje(traza);
                }
                else    {
          InfoTraza trazaError = new InfoTraza ("recurso de Trazas","No se ha encontrado la entidad " +identElementTraza,InfoTraza.NivelTraza.error);
            this.clasificador.getPanelPrincipal().muestraMensaje(trazaError);
         }
        }

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
public void muestraNuevaTraza(InfoTraza traza) {
		// actualiza la nueva traza en todos los paneles

		this.clasificador.getPanelPrincipal().muestraMensaje(traza);
		tablaInfoPanelesEspecificos = this.clasificador.getArrayPaneles();
        tablaPanelesEspecificos = this.clasificador.getArrayPaneles();
        String identElementTraza = traza.getNombre();
        if (tablaInfoPanelesEspecificos.containsKey(identElementTraza)){
          InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(identElementTraza);
            panelInfoActual.setContenido(panelInfoActual.getContenido()
						+ getMensajeNormalizado(traza) + "\n");
          PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(identElementTraza);
                panelActual.muestraMensaje(traza);
                }
                else    {
          InfoTraza trazaError = new InfoTraza ("recurso de Trazas","No se ha encontrado la entidad " +identElementTraza,InfoTraza.NivelTraza.error);
            this.clasificador.getPanelPrincipal().muestraMensaje(trazaError);

        }
   /*
        if ConjIdentifPaneles.contains(traza.getNombre())
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

//		List<InfoPanelEspecifico> listaInfoPaneles = this.clasificador
//				.getArrayInfoPaneles();
//		List<PanelTrazasEspecificas> listaPaneles = this.clasificador
//				.getArrayPaneles();

       tablaInfoPanelesEspecificos = this.clasificador.getArrayPaneles();
       tablaPanelesEspecificos = this.clasificador.getArrayPaneles();

       if (tablaInfoPanelesEspecificos.containsKey(nombreVentana)){
        //Obtenemos el identificador y e contenido
        InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(nombreVentana);

       if (!tablaPanelesEspecificos.containsKey(nombreVentana)){
         // Debemos crear la ventana y actualizar la tabla
          PanelTrazasEspecificas panel = new PanelTrazasEspecificas(
							panelInfoActual.getIdentificador(), panelInfoActual
									.getContenido());
					double a = Math.random() * 700;
					panel.setLocation((int) a, 550);
					panel.setVisible(true);
					tablaPanelesEspecificos.put(nombreVentana,panel);
           	}
        }
  /*   }
//		boolean encontrado = false;

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
/*
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

       tablaPanelesEspecificos = this.clasificador.getArrayPaneles();

       if (tablaPanelesEspecificos.containsKey(nombreVentana)){
            PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(nombreVentana);
                panelActual.setVisible(false);

			}

   /*
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
*/
	
}
