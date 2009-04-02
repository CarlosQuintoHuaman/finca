package icaro.aplicaciones.recursos.visualizacionMedicamentos.imp;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.swt.PanelHistorial;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.ItfUsoVisualizadorMedicamentos;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.swt.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2007
 */

public class ClaseGeneradoraVisualizacionMedicamentos extends ImplRecursoSimple implements ItfUsoVisualizadorMedicamentos{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private PanelBusqueda ventanaBusquedaUsuario;
	private PanelNuevo ventanaNuevoUsuario;
	private PanelMedicamentos ventanaTabMed;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionMedicamentos(String id) throws Exception{
  		super(id);
  		try{
	      	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	  this.estadoAutomata.transita("error");
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
  		this.inicializa();
	}

  	
  	
  	
  	private void inicializa() {
  		this.ventanaBusquedaUsuario = new PanelBusqueda(this);
  		ventanaBusquedaUsuario.start();
  		
  		ventanaNuevoUsuario = new PanelNuevo(this);
  		ventanaNuevoUsuario.start();
  		
  		/*
                 ventanaAgendaUsuario.setPosicion(850,100);
                 */
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	
  	


	
	public String getNombreAgenteControlador() {
		return nombreAgenteControlador;
	}

	public void setNombreAgenteControlador(String nombreAgenteControlador) {
		this.nombreAgenteControlador = nombreAgenteControlador;
	}

	public String getTipoAgenteControlador() {
		return tipoAgenteControlador;
	}

	public void setTipoAgenteControlador(String tipoAgenteControlador) {
		this.tipoAgenteControlador = tipoAgenteControlador;
	}

	//METODOS PROPIOS
	
	public void mostrarTabMed(String nombreAgente, String tipo, final Composite c, final int estilo) {
				//ventanaTabMed = new PanelMedicamentos(this, c,estilo);
	}
	
	public void mostrarVisualizadorBusqueda(String nombreAgente, String tipo, String paciente) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		ventanaBusquedaUsuario.setPaciente(paciente);
		this.ventanaBusquedaUsuario.mostrar();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	
	public void mostrarVisualizadorNuevo(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaNuevoUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	
	public void cerrarVisualizadorNuevo() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaNuevoUsuario.destruir();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorNuevo();
	}
	
	public void reiniciaVisualizadorNuevo() {
		ventanaNuevoUsuario = new PanelNuevo(this);
  		ventanaNuevoUsuario.start();
  		System.out.println("Reiniciando...");
	}
	
	public void mostrarDatosMedicamentos(ArrayList<InfoMedicamento> m) {
		this.ventanaBusquedaUsuario.mostrarDatos(m);
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorBusqueda() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaBusquedaUsuario.destruir();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorBusqueda();
	}
	
	public void reiniciaVisualizadorBusqueda() {
		ventanaBusquedaUsuario = new PanelBusqueda(this);
  		ventanaBusquedaUsuario.start();
  		System.out.println("Reiniciando...");
	}
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	@Override
	public void termina() {
		this.ventanaBusquedaUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedicamentos",
	  				"Terminando recurso",
	  				InfoTraza.NivelTraza.debug));
			super.termina();
			
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			e.printStackTrace();
		}
	}
	
	// Aqui van los metodos no genericos		
		
}