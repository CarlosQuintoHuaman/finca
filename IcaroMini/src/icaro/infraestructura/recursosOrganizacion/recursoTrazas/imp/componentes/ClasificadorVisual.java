package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;


import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.NotificacionesRecTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasClasificadas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasEspecificas;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;


public class ClasificadorVisual {

	private PanelTrazasClasificadas panelTrazasNiveles;
	//panel principal de trazas
	private HashMap tablaInfoPanelesEspecificos;
	//arraylist de informaci�n ventanas de trazas
	private HashMap tablaPanelesEspecificos;
	//arraylist que contiene los paneles visualizados
    private  List<String> listaElementosTrazables;
	private NotificacionesRecTrazas notificador;

	
	public ClasificadorVisual(NotificacionesRecTrazas notifEventos){
		
//		List<String> listaElementosTrazables = getElementosTrazables();
//      TreeSet   listaElementosTrazables = getElementosTrazables();
//		notificador = new NotificacionesRecTrazas(this);
        notificador = notifEventos;
        listaElementosTrazables = new LinkedList<String>() ;
        tablaInfoPanelesEspecificos = new HashMap () ;
        tablaPanelesEspecificos = new HashMap () ;
		//pasamos la lista de elementos trazables a la interfaz gr�fica para permitir su traza
		panelTrazasNiveles = new PanelTrazasClasificadas( this,notificador );

        panelTrazasNiveles.setVisible(true);
	/*
		//construimos los paneles especificos para que vayan trazando
		listaInfoPanelesEspecificos = new LinkedList<InfoPanelEspecifico>();
		for(int i=0;i<listaElementosTrazables.size();i++){
			InfoPanelEspecifico panel = new InfoPanelEspecifico(listaElementosTrazables.get(i).toString(),"");
			//panel.setVisible(false);
			listaInfoPanelesEspecificos.add(panel);
		}
		
		listaPanelesEspecificos = new LinkedList<PanelTrazasEspecificas>();
	*/
	}


    public void visualizarElementosTrazables (List<String> listaElementosTrazables){
     //     tablaInfoPanelesEspecificos = getElementosTrazables();
      if (listaElementosTrazables != null){
         tablaInfoPanelesEspecificos = construirPanelesEspecificos( listaElementosTrazables);
      }
          /*
       //construimos los paneles especificos para que vayan trazando
      tablaInfoPanelesEspecificos = new HashMap();

      Iterator<String> iterador = conjuntoElementosTrazables.iterator();
      while(iterador.hasNext()){
          String identEntidad =  iterador.next();
        InfoPanelEspecifico panel = new InfoPanelEspecifico(identEntidad,"");
			//panel.setVisible(false);
			tablaInfoPanelesEspecificos.put(identEntidad, panel);
      }
      */
       tablaPanelesEspecificos = new HashMap();
    }
	
	public void setPanelPrincipal(PanelTrazasClasificadas p){this.panelTrazasNiveles = p;}
	public void setArrayInfoPaneles(HashMap tabla){this.tablaInfoPanelesEspecificos = tabla;}
	public void setArrayPaneles(HashMap tabla){this.tablaPanelesEspecificos = tabla;}
	
	public PanelTrazasClasificadas getPanelPrincipal(){return this.panelTrazasNiveles;}
	public HashMap getArrayInfoPaneles(){return this.tablaInfoPanelesEspecificos;}
	public HashMap getArrayPaneles(){return this.tablaPanelesEspecificos;}
	


    public void cerrarVentanas() {
		//elimino todas las ventanas
		this.getPanelPrincipal().dispose();
	//	HashMap tablaPaneles = this.getArrayPaneles();
        if (tablaPanelesEspecificos !=null ){
        String identPanel="";
	    Set conjIdentPanels = tablaPanelesEspecificos.keySet();
        Iterator<String> iter = conjIdentPanels.iterator();
	    while (iter.hasNext()) {
	       identPanel = iter.next();
            PanelTrazasEspecificas panel = (PanelTrazasEspecificas) tablaPanelesEspecificos.get(identPanel);
            panel.dispose();
                }
            }
       }
  private HashMap construirPanelesEspecificos(List<String> listaElementosTrazables){
      // Se crea una lista de paneles y una tabla con las descripciones de
      // los paneles donde el identificador del panel es el identificador que se pasa en la lista
    HashMap tablaInfoPaneles = null;
      if (listaElementosTrazables != null){
         //construimos los paneles especificos para que se pueda  trazar en ellos
         tablaInfoPaneles = new HashMap();
//      tablaPanelesEspecificos = new HashMap();
            Iterator<String> iterador = listaElementosTrazables.iterator();
            while(iterador.hasNext()){
                String identEntidad =  iterador.next();
                InfoPanelEspecifico panel = new InfoPanelEspecifico(identEntidad,"");
                //panel.setVisible(false);
                tablaInfoPaneles.put(identEntidad, panel);
            }
          }
      return tablaInfoPaneles;
  }
public void muestraTraza(InfoTraza traza) {
		// actualiza la nueva traza en todos los paneles

//		this.getPanelPrincipal().muestraMensaje(traza);
        panelTrazasNiveles.muestraMensaje(traza);
  //      if (tablaInfoPanelesEspecificos != null ) {
        String identElementTraza = traza.getNombre();
        if (tablaInfoPanelesEspecificos.containsKey(identElementTraza)){
          InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(identElementTraza);
            panelInfoActual.setContenido(panelInfoActual.getContenido()
						+ getMensajeNormalizado(traza) + "\n");
          PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(identElementTraza);
                panelActual.muestraMensaje(traza);
                }
                else    {
               // creamos el info panel y visualizamos la ventana
                    InfoPanelEspecifico panel = new InfoPanelEspecifico(identElementTraza,"");
                    tablaInfoPanelesEspecificos.put(identElementTraza, panel);
                    PanelTrazasEspecificas panelEsp = new PanelTrazasEspecificas(identElementTraza, "");						
                    tablaPanelesEspecificos.put(identElementTraza, panelEsp);
                    panelTrazasNiveles.visualizarElementoTrazable( identElementTraza);
 //                   muestraVentanaEspecifica(identElementTraza);

      // Creamos la descripcion y la ventana para la traza especifica
   //       InfoTraza trazaError = new InfoTraza ("recurso de Trazas","No se ha encontrado la entidad " +identElementTraza,InfoTraza.NivelTraza.error);
   //         this.getPanelPrincipal().muestraMensaje(trazaError);
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

//       tablaInfoPanelesEspecificos = this.clasificador.getArrayPaneles();
//       tablaPanelesEspecificos = this.clasificador.getArrayPaneles();

       if (tablaInfoPanelesEspecificos.containsKey(nombreVentana)){
        //Obtenemos el identificador y e contenido
        InfoPanelEspecifico panelInfoActual = (InfoPanelEspecifico) tablaInfoPanelesEspecificos.get(nombreVentana);

       if (!tablaPanelesEspecificos.containsKey(nombreVentana)){
         // Debemos crear la ventana y actualizar la tabla
          PanelTrazasEspecificas panel = new PanelTrazasEspecificas(
							panelInfoActual.getIdentificador(), panelInfoActual
									.getContenido());
            tablaPanelesEspecificos.put(nombreVentana, panel);
                    }
                else {
                PanelTrazasEspecificas panel = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(nombreVentana);
					double a = Math.random() * 700;
					panel.setLocation((int) a, 550);
					panel.setVisible(true);	
                    }
            }
       }
    public void cierraVentanaPrincipal() {
		this.panelTrazasNiveles.setVisible(false);

	}

	public void cierraVentanaEspecifica(String nombreVentana) {


       if (tablaPanelesEspecificos.containsKey(nombreVentana)){
            PanelTrazasEspecificas panelActual = (PanelTrazasEspecificas)tablaPanelesEspecificos.get(nombreVentana);
                panelActual.setVisible(false);

			}
}
    /*
	private HashMap getElementosTrazables(){
		//Devuelve todos los elementos gestionados en la organizaci�n, con sus respectivos gestores.
		//Para obtener estos valores se lee de la configuraci�n.
		// Construye la tabla con los descripcion de los paneles
			TreeSet elementosTrazables = new TreeSet();
			
			try{
				ItfUsoConfiguracion configuracionExterna = (ItfUsoConfiguracion)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
				
				
				//a�ado el gestor de organizaci�n y el resto de gestores que est�n debajo suya
				elementosTrazables.add(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
				DescInstanciaGestor gestorOrganizacion = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
				List<DescInstancia> instancias = gestorOrganizacion.getComponentesGestionados();

				
				//a la vez, a�adimos las entidades gestionadas por los gestores
				Iterator<DescInstancia> iterador = instancias.iterator();
				
				List<DescInstancia> lista = null;
				while(iterador.hasNext()){
					DescInstancia instancia = iterador.next();
					String nombreGestor = instancia.getId();
					elementosTrazables.add(nombreGestor);
                     InfoPanelEspecifico panel = new InfoPanelEspecifico(nombreGestor,"");
			//panel.setVisible(false);
			tablaInfoPanelesEspecificos.put(nombreGestor, panel);
					lista = ((DescInstanciaGestor) instancia).getComponentesGestionados();
					
					Iterator<DescInstancia> iteradorComponentes = lista.iterator();
					while(iteradorComponentes.hasNext()){
						DescInstancia instanciaComponente = (DescInstancia)iteradorComponentes.next();
						String nombreEntidad = instanciaComponente.getId();
						elementosTrazables.add(nombreEntidad);
						panel = new InfoPanelEspecifico(nombreEntidad,"");
			//panel.setVisible(false);
			tablaInfoPanelesEspecificos.put(nombreEntidad, panel);
					}
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return tablaInfoPanelesEspecificos;
	}
*/
}
