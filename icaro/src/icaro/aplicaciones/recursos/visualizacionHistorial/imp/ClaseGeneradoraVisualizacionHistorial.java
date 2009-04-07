package icaro.aplicaciones.recursos.visualizacionHistorial.imp;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.ItfUsoVisualizadorHistorial;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.swt.*;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.swt.PanelMedico;
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
public class ClaseGeneradoraVisualizacionHistorial extends ImplRecursoSimple implements ItfUsoVisualizadorHistorial{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private PanelHistorial ventanaHistorialUsuario;
	private PanelLista ventanaListaUsuario;
	private PanelPrueba ventanaPruebaUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionHistorial(String id) throws Exception{
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
  		this.ventanaHistorialUsuario = new PanelHistorial(this);
  		ventanaHistorialUsuario.start();
 
  		ventanaListaUsuario = new PanelLista(this);
  		ventanaListaUsuario.start();
  		
  		ventanaPruebaUsuario = new PanelPrueba(this);
  		ventanaPruebaUsuario.start();
  		
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	

  	// Metodos para historial
  	
  	public void mostrarVisualizadorHistorial(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaHistorialUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorHistorial() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaHistorialUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorHistorial();
	}
	
	public void reiniciaVisualizadorHistorial() {
		ventanaHistorialUsuario = new PanelHistorial(this);
  		ventanaHistorialUsuario.start();
  		System.out.println("Reiniciando...");
	}
  
	public void mostrarDatosHistorial(InfoVisita historial) {
		ventanaHistorialUsuario.mostrarDatos(historial);
	}
	
	
	// Metodos para Lista
	
  	public void mostrarVisualizadorLista(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaListaUsuario.mostrar();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorLista() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaListaUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Cerrando visualizador Lista...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorLista();
	}
	
	public void reiniciaVisualizadorLista() {
		ventanaListaUsuario = new PanelLista(this);
  		ventanaListaUsuario.start();
  		System.out.println("Reiniciando...");
	}
	
	public void mostrarDatosLista(ArrayList<InfoVisita> historial) {
		ventanaListaUsuario.mostrarDatos(historial);
	}
	
  	public void mostrarVisualizadorPrueba(String nombreAgente, String tipo, InfoVisita v) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		ventanaPruebaUsuario.setPaciente(v);
		this.ventanaPruebaUsuario.mostrar();
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando visualizador Prueba...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorPrueba() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaPruebaUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Cerrando visualizador Prueba...",
  				InfoTraza.NivelTraza.debug));
		
		reiniciaVisualizadorPrueba();
	}
	
	public void reiniciaVisualizadorPrueba() {
		ventanaPruebaUsuario = new PanelPrueba(this);
  		ventanaPruebaUsuario.start();
  		System.out.println("Reiniciando Prueba...");
	}
	
	public void mostrarDatosPrueba(ArrayList<InfoPrueba> p) {
		ventanaHistorialUsuario.mostrarDatosPrueba(p);
	}
	
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) {
		ventanaHistorialUsuario.mostrarDatosMed(m);
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
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public boolean mostrarMensajePregunta(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    return	(messageBox.open() == SWT.OK);
	}
	
	@Override
	public void termina() {
		this.ventanaHistorialUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionHistorial",
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