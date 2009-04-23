package icaro.aplicaciones.recursos.persistenciaAdmin.imp;

import java.sql.Connection;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin.InfoUsuario;
import icaro.aplicaciones.recursos.persistenciaAdmin.ItfUsoPersistenciaAdmin;
import icaro.aplicaciones.recursos.persistenciaAdmin.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaAdmin.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;


/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class ClaseGeneradoraPersistenciaAdmin extends ImplRecursoSimple implements
		ItfUsoPersistenciaAdmin {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaAdmin(String id) throws Exception {
		
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
			
			c = AccesoBBDD.conectar("PersistenciaAdmin1");
			consulta = new ConsultaBBDD("PersistenciaAdmin1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaAdmin",
  				"Terminando recurso",
  				InfoTraza.NivelTraza.debug));
		AccesoBBDD.desconectar();
		try {
			super.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public ArrayList<InfoUsuario> getUsuarios() throws Exception {
		return consulta.getUsuarios();
	}

	public void optimizar() throws Exception {
		consulta.optimizar();
	}
}