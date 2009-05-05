package icaro.aplicaciones.recursos.persistenciaLogin.imp;

import java.sql.Connection;
import icaro.aplicaciones.recursos.persistenciaLogin.ItfUsoPersistenciaLogin;
import icaro.aplicaciones.recursos.persistenciaLogin.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaLogin.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;


/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class ClaseGeneradoraPersistenciaLogin extends ImplRecursoSimple implements
		ItfUsoPersistenciaLogin {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaLogin(String id) throws Exception {
		
		super(id);
		
		try{
	      	trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
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
			
			c = AccesoBBDD.conectar("PersistenciaLogin1");
			consulta = new ConsultaBBDD("PersistenciaLogin1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	public String compruebaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		try {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaLogin",
  				"Comprobando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
			if (consulta.compruebaUsuario(usuario, password))
				return consulta.tipoUsuario(usuario);
		
			return "false";
		
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			
			return "false";
		}

	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaLogin",
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