package icaro.aplicaciones.recursos.persistenciaMedico.imp;

import java.sql.Connection;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.recursos.persistenciaMedico.ItfUsoPersistenciaMedico;
import icaro.aplicaciones.recursos.persistenciaMedico.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaMedico.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;



public class ClaseGeneradoraPersistenciaMedico extends ImplRecursoSimple implements
		ItfUsoPersistenciaMedico {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaMedico(String id) throws Exception {
		
		super(id);
		
		try{
	      	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	 this.estadoAutomata.transita("error"); 
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
	      
		try {

			//AccesoBBDD.crearEsquema(id);
			// Conectamos a la BD y nos devuelve un puntero a la conexion. Aqui seguramente no hace falta
			// pero lo pongo solo por referencia
			
			// MUY IMPORTANTE: El id que se pasa como parametro deberia ser algo del estilo "PersistenciaAlgo1"
			// Si este nombre esta mal va a petar
			
			c = AccesoBBDD.conectar("PersistenciaMedico1");
			consulta = new ConsultaBBDD("PersistenciaMedico1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	public boolean compruebaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		try {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaMedico",
  				"Comprobando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaUsuario(usuario, password);
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			
			return false;
		}

	}
	
	public ArrayList<InfoPaciente> getPacientes() {
		return consulta.getPacientes();
	}
	
	public ArrayList<InfoCita> getCitas() {
		return consulta.getCitas();
	}

	public boolean compruebaNombreUsuario(String usuario)
			throws ErrorEnRecursoException {
			trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaMedico",
  				"Comprobando nombre de usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaNombreUsuario(usuario);

	}

	public void insertaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaMedico",
  				"Insertando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		consulta.insertaUsuario(usuario, password);

	}


	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaMedico",
  				"Terminando recurso",
  				InfoTraza.NivelTraza.debug));
		AccesoBBDD.desconectar();
		try {
			super.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}