package icaro.aplicaciones.recursos.persistenciaMensajeria.imp;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.persistenciaMedicamentos.ItfUsoPersistenciaMedicamentos;
import icaro.aplicaciones.recursos.persistenciaMensajeria.ItfUsoPersistenciaMensajeria;
import icaro.aplicaciones.recursos.persistenciaMensajeria.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaMensajeria.imp.util.ConsultaBBDD;
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
public class ClaseGeneradoraPersistenciaMensajeria extends ImplRecursoSimple implements
		ItfUsoPersistenciaMensajeria {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaMensajeria(String id) throws Exception {
		
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
			
			c = AccesoBBDD.conectar("PersistenciaMedicamentos1");
			consulta = new ConsultaBBDD("PersistenciaMedicamentos1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}
	
	public ArrayList<InfoMensaje> getMensajes(String usuario) {
		return consulta.getMensajes(usuario);
	}
	
	public void eliminarMensaje(InfoMensaje m) throws Exception {
		consulta.eliminarMensaje(m);
	}

	public void insertaMensaje(InfoMensaje m) throws Exception {
		consulta.insertaMensaje(m);
	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaMedicamentos",
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