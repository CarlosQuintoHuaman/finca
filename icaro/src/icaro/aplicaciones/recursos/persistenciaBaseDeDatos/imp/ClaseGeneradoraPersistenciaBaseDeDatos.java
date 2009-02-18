package icaro.aplicaciones.recursos.persistenciaBaseDeDatos.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import icaro.aplicaciones.recursos.persistenciaBaseDeDatos.ItfUsoPersistenciaBaseDeDatos;
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

public class ClaseGeneradoraPersistenciaBaseDeDatos extends ImplRecursoSimple implements ItfUsoPersistenciaBaseDeDatos{

	private static final long serialVersionUID = 1L;

	//Informaciï¿½n del agente
	private String nombreAgenteControlador;
	private String tipoAgenteControlador;
	
	private ItfUsoRecursoTrazas trazas; //trazas del sistema
	
	// Variables especificas para la gestion de la BD
	private Connection con;
    private Statement st;
    private ResultSet rs;
    
    String userName = "root";
    String password = "";
    String ip = "localhost";
    String dbName = "doctoris";

	
  	public ClaseGeneradoraPersistenciaBaseDeDatos(String id) throws Exception{
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
  		
  		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaBaseDeDatos",
  				"Inicializando recurso",
  				InfoTraza.NivelTraza.debug));
  	}
  	
  	
	public boolean abrirConexion(String usuario, String password) throws Exception {
		// Creamos la conexion con la BD
        boolean conectado = false;
        //intentandoConectar = true;
        String url = "jdbc:mysql://"+ ip +"/" + dbName;

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = DriverManager.getConnection(url, userName, password);
        
        } catch (SQLException e) {
                System.err.println("Database :: Error en conexión: " + e.getMessage() +
                                "\n - IP:         " + ip + 
                                "\n - Usuario:    " + usuario + 
                                "\n - Contraseña: " + password);
        } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Database :: Error al instanciar el conector MySQL: " + e.getMessage());
        }

        conectado = true;
        //intentandoConectar = false;


        return conectado;
	}
	
	
	
	public boolean cerrarConexion() throws Exception {
		con.close();
		
		return con.isClosed();
	}
	
	
	public Object[] enviarConsulta(String consulta) {

		return null;
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
		
		trazas.aceptaNuevaTraza(new InfoTraza("persistenciaBaseDeDatos",
  				"Mostrando mensaje de informacion",
  				InfoTraza.NivelTraza.debug));
		
		MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	    
	}
  
	public void mostrarMensajeAviso(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("persistenciaBaseDeDatos",
  				"Mostrando mensaje de aviso",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_WARNING);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}
	
	public void mostrarMensajeError(String titulo,String mensaje) {
      	trazas.aceptaNuevaTraza(new InfoTraza("persistenciaBaseDeDatos",
  				"Mostrando mensaje de error",
  				InfoTraza.NivelTraza.debug));
      	
      	MessageBox messageBox = new MessageBox (new Shell(), SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_ERROR);
		messageBox.setText (titulo);
		messageBox.setMessage (mensaje);
	    messageBox.open();
	}	
	
	public void termina() {
		
		try {
			trazas.aceptaNuevaTraza(new InfoTraza("persistenciaBaseDeDatos",
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