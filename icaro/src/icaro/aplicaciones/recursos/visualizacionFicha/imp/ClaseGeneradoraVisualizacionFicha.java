package icaro.aplicaciones.recursos.visualizacionFicha.imp;


import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.recursos.visualizacionFicha.ItfUsoVisualizadorFicha;

import icaro.aplicaciones.recursos.visualizacionFicha.*;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.swt.*;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt.panelCita;
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
 *@author     F Garijo
 *@created    20 de noviembre de 2007
 */

public class ClaseGeneradoraVisualizacionFicha extends ImplRecursoSimple implements ItfUsoVisualizadorFicha{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private panelFicha ventanaFichaUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionFicha(String id) throws Exception{
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
  		this.ventanaFichaUsuario = new panelFicha(this);
  		ventanaFichaUsuario.start();
  		/*
                 ventanaAgendaUsuario.setPosicion(850,100);
                 */
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
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

	public void mostrarVisualizadorFicha(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaFichaUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	
	public void mostrarVisualizadorFichaBD(String nombreAgente, String tipo, DatosFicha ficha) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaFichaUsuario.mostrar(ficha);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	
	public void mostrarVisualizadorFicha(String nombreAgente, String tipo, DatosCita datos) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaFichaUsuario.mostrar(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorFicha() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaFichaUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	      reiniciaVisualizadorFicha();
			
	}
	
    public void reiniciaVisualizadorFicha() {
    	ventanaFichaUsuario = new panelFicha(this);
    	ventanaFichaUsuario.start();
    }  
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
	
	public boolean mostrarMensajeAvisoC(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK |SWT.CANCEL| SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
		if (messageBox.open() == SWT.OK){
			return true;
		}else
			return false;

	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeAvisoConfirmacion(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK |SWT.CANCEL| SWT.ICON_QUESTION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	@Override
	public void termina() {
		this.ventanaFichaUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
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