package icaro.aplicaciones.recursos.visualizacionSecretaria.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.recursos.visualizacionSecretaria.ItfUsoVisualizadorSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt.*;
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

public class ClaseGeneradoraVisualizacionSecretaria extends ImplRecursoSimple implements ItfUsoVisualizadorSecretaria{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private panelAgenda ventanaAgendaUsuario;
	private panelCita ventanaCitaAgenda;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionSecretaria(String id) throws Exception{
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
  		this.ventanaAgendaUsuario = new panelAgenda(this);
  		ventanaAgendaUsuario.start();

  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionAgenda",
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

	public void mostrarVisualizadorSecretaria(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
                System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaAgendaUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorSecretaria() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaAgendaUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}  
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Mostrando mensaje de informaci�n",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
		/*JOptionPane.showMessageDialog(ventanaAgendaUsuario,mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
		
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha");
			itfUsoAgente.aceptaEvento(new EventoInput("termina","VisualizadorFicha",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Ficha"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
		*/
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
		this.ventanaAgendaUsuario.destruir();
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

	public void mostrarVisualizadorCita(String nombreAgente, String tipo, DatosCitaSinValidar datos){
	//public void mostrarVisualizadorCita(String nombreAgente, String tipo){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.mostrarCita(datos);
		//this.ventanaAgendaUsuario.mostrarCita();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
		
	}
	
	public void cerrarVisualizadorCita() throws Exception {
		this.ventanaAgendaUsuario.cerrarCita();
		
	}

    public void comprobarInfoCita(String nombreAgente,String tipo, DatosCitaSinValidar datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.comprobarCita(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }


	@Override
	public void mostrarVisualizadorCita(String nombreAgente, String tipo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
		
		
}