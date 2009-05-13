package icaro.aplicaciones.recursos.visualizacionSecretaria.imp;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;

import icaro.aplicaciones.recursos.visualizacionSecretaria.ItfUsoVisualizadorSecretaria;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.swt.*;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

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
	private panelLlamada ventanaLlamada;
	private panelExtra ventanaExtra;
	private panelProximaCita ventanaPCita;
	
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
  	public ClaseGeneradoraVisualizacionSecretaria(String id) throws Exception{
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
  		this.ventanaAgendaUsuario = new panelAgenda(this);
  		ventanaAgendaUsuario.start();
  		this.ventanaCitaAgenda = new panelCita(this);
  		ventanaCitaAgenda.start();
  		this.ventanaLlamada = new panelLlamada(this);
  		ventanaLlamada.start();
  		this.ventanaExtra = new panelExtra(this);
  		ventanaExtra.start();
  		this.ventanaPCita = new panelProximaCita(this);
  		ventanaPCita.start();
  		
  		
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
	

	public void meteDatos(String fecha, ArrayList<DatosMedico> m, int num, String s){

		this.ventanaAgendaUsuario.meteDatos(fecha, m, num, s);
	}

	public void cerrarVisualizadorSecretaria() {
		//this.ventanaAgendaUsuario.ocultar();
		this.ventanaAgendaUsuario.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionFicha",
  				"Cerrando visualizador...",
  				InfoTraza.NivelTraza.debug));
		reiniciaVisualizadorSecretaria();
	}  

	
    public void reiniciaVisualizadorSecretaria() {
    	ventanaAgendaUsuario = new panelAgenda(this);
    	ventanaAgendaUsuario.start();
    }
	
	public void meteDatos(ArrayList<DatosCitaSinValidar> l){
		this.ventanaPCita.meteDatos(l);
	}
  
	public void meteDatosPacientes(ArrayList<InfoPaciente> l){
		this.ventanaCitaAgenda.rellenaTabla(l);
	}
	
	public void meteDatosPacientesE(ArrayList<InfoPaciente> l){
		this.ventanaExtra.rellenaTabla(l);
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

		this.ventanaCitaAgenda.mostrar(datos);
		//this.ventanaAgendaUsuario.mostrarCita();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
		
	}
	
	public void mostrarVisualizadorPCitas(String nombreAgente, String tipo){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaPCita.mostrar();
		//this.ventanaAgendaUsuario.mostrarCita();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
	}
	public void mostrarVisualizadorCita(String nombreAgente, String tipo){
		
			this.nombreAgenteControlador = nombreAgente;
			System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
			this.tipoAgenteControlador = tipo;

			this.ventanaCitaAgenda.mostrar();
			//this.ventanaAgendaUsuario.mostrarCita();
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
			"Mostrando visualizador...",
			InfoTraza.NivelTraza.debug));
			
		}
	
	public void mostrarVisualizadorLlamada(String nombreAgente, String tipo){
		//public void mostrarVisualizadorCita(String nombreAgente, String tipo){
			this.nombreAgenteControlador = nombreAgente;
			System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
			this.tipoAgenteControlador = tipo;

			this.ventanaLlamada.mostrar();
			//this.ventanaAgendaUsuario.mostrarCita();
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
			"Mostrando visualizador...",
			InfoTraza.NivelTraza.debug));
			
		}
	
	public void mostrarVisualizadorLlamada(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaLlamada.mostrar(datos);
		//this.ventanaAgendaUsuario.mostrarCita();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
	}
	
		public void mostrarVisualizadorExtra(String nombreAgente, String tipo){
		
			this.nombreAgenteControlador = nombreAgente;
			System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
			this.tipoAgenteControlador = tipo;

			this.ventanaExtra.mostrar();
			
			trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
			"Mostrando visualizador...",
			InfoTraza.NivelTraza.debug));
			
		}
	
	public void mostrarVisualizadorExtra(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaExtra.mostrar(datos);
		
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
	}
	
	public void cerrarVisualizadorCita() throws Exception {
		this.ventanaCitaAgenda.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionCita",
                "Cerrando visualizador...",
                InfoTraza.NivelTraza.debug));
     
      reiniciaVisualizadorCita();
		
	}
	
    public void reiniciaVisualizadorCita() {
    	ventanaCitaAgenda = new panelCita(this);
    	ventanaCitaAgenda.start();
    }
    
	public void cerrarVisualizadorLlamada() throws Exception {
		this.ventanaLlamada.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionLlamada",
                "Cerrando visualizador...",
                InfoTraza.NivelTraza.debug));
     
      reiniciaVisualizadorLlamada();
		
	}
	
    public void reiniciaVisualizadorLlamada() {
    	ventanaLlamada = new panelLlamada(this);
    	ventanaLlamada.start();
    }
    
    public void cerrarVisualizadorProximasCita(){
    	this.ventanaPCita.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionLlamada",
                "Cerrando visualizador...",
                InfoTraza.NivelTraza.debug));
     
      reiniciaVisualizadorPCitas();
    }
    
    public void reiniciaVisualizadorPCitas() {
    	ventanaPCita = new panelProximaCita(this);
    	ventanaPCita.start();
    }
    
    
	public void cerrarVisualizadorExtra() throws Exception {
		this.ventanaExtra.destruir();
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionExtra",
                "Cerrando visualizador...",
                InfoTraza.NivelTraza.debug));
     
      reiniciaVisualizadorExtra();
		
	}
	
    public void reiniciaVisualizadorExtra() {
    	ventanaExtra = new panelExtra(this);
    	ventanaExtra.start();
    }

    public void borrarCita(String nombreAgente,String tipo, DatosCitaSinValidar datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		//this.ventanaAgendaUsuario.borraCita(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
    public void insertaLlamada(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.insertaLlamada(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
    
    public void modificaLlamada(String nombreAgente,String tipo, DatosLlamada datAnt, DatosLlamada datPost){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.modificaLlamada(datAnt,datPost);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
    
    public void borrarLlamada(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.borraLlamada(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
    
        public void insertaExtra(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.insertaExtra(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
        
        public void modificaExtra(String nombreAgente,String tipo, DatosLlamada datAnt, DatosLlamada datPost){
    		this.nombreAgenteControlador = nombreAgente;
    		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
    		this.tipoAgenteControlador = tipo;

    		this.ventanaAgendaUsuario.modificaExtra(datAnt,datPost);
    		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
    		"Mostrando visualizador...",
    		InfoTraza.NivelTraza.debug));
        }
    
    public void borrarExtra(String nombreAgente,String tipo, DatosLlamada datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;

		this.ventanaAgendaUsuario.borraExtra(datos);
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
		"Mostrando visualizador...",
		InfoTraza.NivelTraza.debug));
    }
    public boolean comprobarInfoCita(String nombreAgente,String tipo, DatosCitaSinValidar datos){
		this.nombreAgenteControlador = nombreAgente;
		System.out.println("El nombre dado a la visualizacion es:"+nombreAgente);
		this.tipoAgenteControlador = tipo;
		trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
				"Mostrando visualizador...",
				InfoTraza.NivelTraza.debug));
		return this.ventanaAgendaUsuario.comprobarCita(datos);
		
    }
    
    public boolean estaLibre(HorasCita hora){
    	trazas.aceptaNuevaTraza(new InfoTraza("VisualizacionSecretaria",
    			"Mostrando visualizador...",
    			InfoTraza.NivelTraza.debug));
		 return this.ventanaAgendaUsuario.estaLibre(hora);
		
    }
			
}