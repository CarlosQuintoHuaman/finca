package icaro.aplicaciones.recursos.visualizacionMedico.imp;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.recursos.persistenciaMedico.ItfUsoPersistenciaMedico;
import icaro.aplicaciones.recursos.visualizacionMedico.ItfUsoVisualizadorMedico;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.swt.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ClaseGeneradoraVisualizacionMedico extends ImplRecursoSimple implements ItfUsoVisualizadorMedico{

	private static final long serialVersionUID = 1L;

	//Informaci�n del agente de Ficha
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	//Ventana que gestiona este visualizador
	private PanelMedico ventanaMedicoUsuario;
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
	// Persistencia
	private ItfUsoPersistenciaMedico p;
	//private AccesoBBDD bd;
	//private ConsultaBBDD consultabd = new ConsultaBBDD("PersistenciaMedico1");
	
	// Resto de variables
	ArrayList<InfoPaciente> pacientes;
	ArrayList<InfoCita> citas;
	
  	public ClaseGeneradoraVisualizacionMedico(String id) throws Exception{
  		super(id);
  		try{
	      	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	  this.estadoAutomata.transita("error");
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
	   
		// MUY IMPORTANTE: El id que se pasa como parametro deberia ser algo del estilo "PersistenciaAlgo1"
		// Si este nombre esta mal va a petar
	    p = (ItfUsoPersistenciaMedico)RepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+"PersistenciaMedico1");

	    //Connection c = bd.conectar("PersistenciaMedico1");
	    // Ejemplo de consulta
	    //consultabd.compruebaUsuario("prueba", "p");
	    //Hay que usar la interfaz de uso
	    p.compruebaUsuario("prueba", "p");
	    
	    pacientes = p.getPacientes();
		citas = p.getCitas();
		
  		this.inicializa();
	}

  	
  	
  	
  	private void inicializa() {
  		this.ventanaMedicoUsuario = new PanelMedico(this);
  		ventanaMedicoUsuario.start();
 
  		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}


	

	public void mostrarVisualizadorMedico(String nombreAgente, String tipo) {
		this.nombreAgenteControlador = nombreAgente;
        System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
   
		this.ventanaMedicoUsuario.mostrar();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Mostrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
 
	public void cerrarVisualizadorMedico() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaMedicoUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
	}
	
	public void reiniciaVisualizadorMedico() {
		ventanaMedicoUsuario = new PanelMedico(this);
  		ventanaMedicoUsuario.start();
	}
  
	public ArrayList<InfoPaciente> getPacientes() {
		return pacientes;
	}
	
	public ArrayList<InfoCita> getCitas() {
		return citas;
	}

	public void setCitas(ArrayList<InfoCita> citas) {
		this.citas = citas;
	}

	public void mostrarTabMed(Composite c) throws Exception {
		ventanaMedicoUsuario.mostrarTabMed(c);
	}

	// Metodos genericos
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
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	@Override
	public void termina() {
		this.ventanaMedicoUsuario.destruir();
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionMedico",
	  				"Terminando recurso",
	  				InfoTraza.NivelTraza.debug));
			super.termina();
			
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			e.printStackTrace();
		}
	}




	@Override
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) throws Exception {
		ventanaMedicoUsuario.mostrarDatosMed(m);
	}
	
	// Aqui van los metodos no genericos		
		
}