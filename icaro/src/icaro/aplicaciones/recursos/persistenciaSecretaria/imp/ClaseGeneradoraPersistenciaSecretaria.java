package icaro.aplicaciones.recursos.persistenciaSecretaria.imp;

import java.sql.Connection;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
//import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;

import icaro.aplicaciones.recursos.persistenciaSecretaria.ItfUsoPersistenciaSecretaria;
import icaro.aplicaciones.recursos.persistenciaSecretaria.imp.ErrorEnRecursoException;
import icaro.aplicaciones.recursos.persistenciaSecretaria.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaSecretaria.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;



public class ClaseGeneradoraPersistenciaSecretaria extends ImplRecursoSimple implements
		ItfUsoPersistenciaSecretaria {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaSecretaria(String id) throws Exception {
		
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
			
			c = AccesoBBDD.conectar("PersistenciaSecretaria1");
			consulta = new ConsultaBBDD("PersistenciaSecretaria1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	public boolean compruebaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		try {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaSecretaria",
  				"Comprobando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaUsuario(usuario, password);
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			
			return false;
		}

	}
	
/*	public ArrayList<InfoPaciente> getPacientes() {
		return consulta.getPacientes();
	}*/
	
	public ArrayList<DatosMedico> getCitas(String fecha, ArrayList<String> l) {
		return consulta.getCitas(fecha, l);
	}
	public ArrayList<String> getMedicos(String s){
		return consulta.getMedicos(s);
	}
	
	public ArrayList<DatosCita> getPaciente(String nom,String telf, String fecha){
		return consulta.getPaciente(nom,telf,fecha);
	}
	
	public boolean meteAgenda(DatosSecretaria s){
		return consulta.meteAgenda(s);
	}
/*	public boolean compruebaNombreUsuario(String usuario)
			throws ErrorEnRecursoException {
			trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaSecretaria",
  				"Comprobando nombre de usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		try {
			return consulta.compruebaNombreUsuario(usuario);
		} catch (icaro.aplicaciones.recursos.persistenciaMedico.imp.ErrorEnRecursoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

	/*public void insertaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaSecretaria",
  				"Insertando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		try {
			//consulta.insertaUsuario(usuario, password);
		} catch (ErrorEnRecursoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
	
	public void insertaCita(DatosCitaSinValidar cita)
	throws ErrorEnRecursoException {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaSecretaria",
			"Insertando cita "+cita,
			InfoTraza.NivelTraza.debug));
		//consulta.insertaCita(cita);

}


	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaSecretaria",
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