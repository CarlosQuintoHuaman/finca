package icaro.aplicaciones.recursos.visualizacionMensajeria.imp;

import icaro.aplicaciones.recursos.visualizacionMensajeria.ItfUsoVisualizadorMensajeria;
import icaro.aplicaciones.recursos.visualizacionMensajeria.imp.swt.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class ClaseGeneradoraVisualizacionMensajeria extends ImplRecursoSimple implements ItfUsoVisualizadorMensajeria{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private PanelMensaje ventanaMensajeNuevo;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionMensajeria(String id) throws Exception{
  		super(id);
  		try{
	      	trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	  this.estadoAutomata.transita("error");
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
  		this.inicializa();
	}

  	
  	
  	
  	private void inicializa() {
  		this.ventanaMensajeNuevo = new PanelMensaje(this);
  		ventanaMensajeNuevo.start();
  		/*
                 ventanaAgendaUsuario.setPosicion(850,100);
                 */
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	

  	public void mostrarVisualizadorMensajeNuevo(String nombreAgente, String tipo, String usuario) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaMensajeNuevo.mostrar(usuario);
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Mostrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorMensajeNuevo() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaMensajeNuevo.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Cerrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorMensajeria();
	}
	
	public void reiniciaVisualizadorMensajeria() {
		ventanaMensajeNuevo = new PanelMensaje(this);
  		ventanaMensajeNuevo.start();
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
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	@Override
	public void termina() {
		this.ventanaMensajeNuevo.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMensajeria",
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