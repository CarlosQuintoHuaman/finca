package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;


import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasClasificadas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasEspecificas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ClasificadorVisual {

	private PanelTrazasClasificadas panelTrazasNiveles;
	//panel principal de trazas
	private List<InfoPanelEspecifico> listaInfoPanelesEspecificos; 
	//arraylist de informaci�n ventanas de trazas
	private List<PanelTrazasEspecificas> listaPanelesEspecificos;
	//arraylist que contiene los paneles visualizados
	private ControlTrazas controlador;
	
	public ClasificadorVisual(){
		
		List<String> listaElementosTrazables = getElementosTrazables();
		controlador = new ControlTrazas(this);
		//pasamos la lista de elementos trazables a la interfaz gr�fica para permitir su traza
		panelTrazasNiveles = new PanelTrazasClasificadas(listaElementosTrazables, controlador);
		panelTrazasNiveles.setVisible(true);
		
		//construimos los paneles espec�ficos para que vayan trazando
		listaInfoPanelesEspecificos = new LinkedList<InfoPanelEspecifico>();
		for(int i=0;i<listaElementosTrazables.size();i++){
			InfoPanelEspecifico panel = new InfoPanelEspecifico(listaElementosTrazables.get(i).toString(),"");
			//panel.setVisible(false);
			listaInfoPanelesEspecificos.add(panel);
		}
		
		listaPanelesEspecificos = new LinkedList<PanelTrazasEspecificas>();
		
	}
	
	public void setPanelPrincipal(PanelTrazasClasificadas p){this.panelTrazasNiveles = p;}
	public void setArrayInfoPaneles(List<InfoPanelEspecifico> array){this.listaInfoPanelesEspecificos = array;}
	public void setArrayPaneles(List<PanelTrazasEspecificas> array){this.listaPanelesEspecificos = array;}
	
	public PanelTrazasClasificadas getPanelPrincipal(){return this.panelTrazasNiveles;}
	public List<InfoPanelEspecifico> getArrayInfoPaneles(){return this.listaInfoPanelesEspecificos;}
	public List<PanelTrazasEspecificas> getArrayPaneles(){return this.listaPanelesEspecificos;}
	
	
	public void muestraTraza(InfoTraza traza){
		
		controlador.muestraTraza(traza);
	}
	
	private List<String> getElementosTrazables(){
		//Devuelve todos los elementos gestionados en la organizaci�n, con sus respectivos gestores.
		//Para obtener estos valores se lee de la configuraci�n.
			
			List<String> elementosTrazables = new LinkedList<String>();
			
			try{
				ItfUsoConfiguracion configuracionExterna = (ItfUsoConfiguracion)RepositorioInterfaces.instance().obtenerInterfaz(
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
					lista = ((DescInstanciaGestor) instancia).getComponentesGestionados();
					
					Iterator<DescInstancia> iteradorComponentes = lista.iterator();
					while(iteradorComponentes.hasNext()){
						DescInstancia instanciaComponente = (DescInstancia)iteradorComponentes.next();
						String nombreEntidad = instanciaComponente.getId();
						elementosTrazables.add(nombreEntidad);
						
					}
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return elementosTrazables;
	}
		
}
