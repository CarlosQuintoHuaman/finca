package icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp;

import icaro.aplicaciones.recursos.visualizacionAccesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.gui.PanelAccesoAltaUsuario;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import javax.swing.JOptionPane;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ClaseGeneradoraVisualizacionAccesoAlta extends ImplRecursoSimple implements ItfUsoVisualizadorAccesoAlta {

	private static final long serialVersionUID = 1L;

	//Interfaz de uso del agente de acceso
	private ItfUsoAgenteReactivo itfUsoAgenteAcceso;
	
	//Ventana que gestiona este visualizador
	private PanelAccesoAltaUsuario ventanaAccesoUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	private String nombreAgenteAreportar = "AgenteAplicacionAccesoAlta1";
    private String tipoAgenteAreportar = "Reactivo" ;
    private String nombredeEsteRecurso;
    private String tipoAgenteaReportar;
  	public ClaseGeneradoraVisualizacionAccesoAlta(String id) throws Exception{
  		super(id);
  		 nombredeEsteRecurso = id;
        try{
	      	trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	  this.estadoAutomata.transita("error");
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
  		this.inicializa();
	}

  	
    
  	
  	
  	private void inicializa() throws Exception {
  		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  		this.ventanaAccesoUsuario = new PanelAccesoAltaUsuario(this);
  	}
  	
  	
	/**
	 * @return Returns the itfUsoAgenteAcceso.
	 */
	public ItfUsoAgenteReactivo getItfUsoAgenteAcceso() {
		return itfUsoAgenteAcceso;
	}
	
	public ItfUsoRepositorioInterfaces getItfUsoRepositorioInterfaces() {
		return itfUsoRepositorioInterfaces;
	}

	/**
	 * @param itfUsoAgenteAcceso The itfUsoAgenteAcceso to set.
	 */
	public void setItfUsoAgenteAcceso(ItfUsoAgenteReactivo itfUsoAgenteAcceso) {
		this.itfUsoAgenteAcceso = itfUsoAgenteAcceso;
	}
	
	
	public void mostrarVisualizadorAccesoAlta() {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando recurso...",
  				InfoTraza.NivelTraza.debug));
		this.ventanaAccesoUsuario.mostrar();
	}
 
	public void cerrarVisualizadorAccesoAlta() {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Cerrando recurso...",
  				InfoTraza.NivelTraza.debug));
		this.ventanaAccesoUsuario.ocultar();
	}  
  
	public void mostrarMensajeInformacion(String titulo,String mensaje) {
	/*Muestra el mensaje y avisa al gestor para finalizar*/
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de informaci�n",
  				InfoTraza.NivelTraza.debug));
		JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.INFORMATION_MESSAGE);
		
		/*try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz("AgenteAccesoUso");
			itfUsoAgente.aceptaEvento(new EventoInput("termina","AgenteAccesoUso","AgenteAccesoUso"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
		*/
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.WARNING_MESSAGE);
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
		trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	JOptionPane.showMessageDialog(ventanaAccesoUsuario,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
	}	
	
	@Override
	public void termina() {
		this.ventanaAccesoUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
	  				"Terminando recurso",
	  				InfoTraza.NivelTraza.debug));
			super.termina();
			
			
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
public String getNombreAgenteAReportar() {
		return nombreAgenteAreportar;
	}
public String getNombredeEsteRecurso() {
		return nombredeEsteRecurso;
	}
	/**
	 * @param nombreAgenteAcceso
	 * @uml.property  name="nombreAgenteAcceso"
	 */
	public void setNombreAgenteAreportar(String nombreAgenteAcceso) {
		this.nombreAgenteAreportar = nombreAgenteAcceso;
	}

	/**
	 * @return
	 * @uml.property  name="tipoAgenteAcceso"
	 */
	public String getTipoAgenteAreportar() {
		return tipoAgenteAreportar;
	}

	/**
	 * @param tipoAgenteAcceso
	 * @uml.property  name="tipoAgenteAcceso"
	 */
	public void setTipoAgenteAreportar(String tipoAgenteaReportar) {
		this.tipoAgenteaReportar = tipoAgenteaReportar;
	}
public void informeError(String msgError) {
		this.estadoAutomata.transita("error");
        trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso,
	  				"se produjo un error al enviar un evento ",
	  				InfoTraza.NivelTraza.debug));
	}
}