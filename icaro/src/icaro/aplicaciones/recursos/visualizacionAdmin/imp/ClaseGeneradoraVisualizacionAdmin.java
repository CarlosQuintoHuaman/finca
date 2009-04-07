package icaro.aplicaciones.recursos.visualizacionAdmin.imp;

import icaro.aplicaciones.recursos.visualizacionAdmin.ItfUsoVisualizadorAdmin;
import icaro.aplicaciones.recursos.visualizacionAdmin.imp.swt.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class ClaseGeneradoraVisualizacionAdmin extends ImplRecursoSimple implements ItfUsoVisualizadorAdmin{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private PanelAdmin ventanaAdminUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionAdmin(String id) throws Exception{
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
  		this.ventanaAdminUsuario = new PanelAdmin(this);
  		ventanaAdminUsuario.start();
  		/*
                 ventanaAgendaUsuario.setPosicion(850,100);
                 */
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	

  	public void mostrarVisualizadorAdmin(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaAdminUsuario.mostrar();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Mostrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorAdmin() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaAdminUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Cerrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorAdmin();
	}
	
	public void reiniciaVisualizadorAdmin() {
		ventanaAdminUsuario = new PanelAdmin(this);
  		ventanaAdminUsuario.start();
  		System.out.println("Reiniciando...");
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

	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	@Override
	public void termina() {
		this.ventanaAdminUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAdmin",
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