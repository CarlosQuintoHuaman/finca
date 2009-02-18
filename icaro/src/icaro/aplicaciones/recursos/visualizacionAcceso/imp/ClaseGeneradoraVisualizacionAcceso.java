package icaro.aplicaciones.recursos.visualizacionAcceso.imp;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.gui.PanelAccesoUsuario;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import javax.swing.JOptionPane;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ClaseGeneradoraVisualizacionAcceso extends ImplRecursoSimple implements ItfUsoVisualizadorAcceso {

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de acceso
	private String nombreAgenteAcceso;
	private String tipoAgenteAcceso;
	
	//Ventana que gestiona este visualizador
	private PanelAccesoUsuario ventanaAccesoUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionAcceso(String id) throws Exception{
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
  		this.ventanaAccesoUsuario = new PanelAccesoUsuario(this);
  		ventanaAccesoUsuario.setPosicion(850,100);
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	
  	


	
	public String getNombreAgenteAcceso() {
		return nombreAgenteAcceso;
	}

	public void setNombreAgenteAcceso(String nombreAgenteAcceso) {
		this.nombreAgenteAcceso = nombreAgenteAcceso;
	}

	public String getTipoAgenteAcceso() {
		return tipoAgenteAcceso;
	}

	public void setTipoAgenteAcceso(String tipoAgenteAcceso) {
		this.tipoAgenteAcceso = tipoAgenteAcceso;
	}

	public void mostrarVisualizadorAcceso(String nombreAgente, String tipo) {
		this.nombreAgenteAcceso = nombreAgente;
                System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteAcceso = tipo;
   
		this.ventanaAccesoUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorAcceso() {
		this.ventanaAccesoUsuario.ocultar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}  
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Mostrando mensaje de informaci�n",
  				InfoTraza.NivelTraza.debug));
		JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
		/*
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso");
			itfUsoAgente.aceptaEvento(new EventoInput("termina","VisualizadorAcceso",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Acceso"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
		*/
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.WARNING_MESSAGE);
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
	}	
	
	@Override
	public void termina() {
		this.ventanaAccesoUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAcceso",
	  				"Terminando recurso",
	  				InfoTraza.NivelTraza.debug));
			super.termina();
			
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			e.printStackTrace();
		}
	}
		
		
}