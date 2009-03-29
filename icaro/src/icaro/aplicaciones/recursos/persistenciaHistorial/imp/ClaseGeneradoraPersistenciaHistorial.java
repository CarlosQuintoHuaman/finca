package icaro.aplicaciones.recursos.persistenciaHistorial.imp;

import java.sql.Connection;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.recursos.persistenciaHistorial.ItfUsoPersistenciaHistorial;
import icaro.aplicaciones.recursos.persistenciaHistorial.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaHistorial.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;



public class ClaseGeneradoraPersistenciaHistorial extends ImplRecursoSimple implements
		ItfUsoPersistenciaHistorial {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaHistorial(String id) throws Exception {
		
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
			
			c = AccesoBBDD.conectar("PersistenciaHistorial1");
			consulta = new ConsultaBBDD("PersistenciaHistorial1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	public boolean compruebaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		try {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaHistorial",
  				"Comprobando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaUsuario(usuario, password);
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			
			return false;
		}

	}
	
	public ArrayList<InfoVisita> getHistorial(String paciente) {
		return consulta.getHistorial(paciente);
	}
	
	public void setVisita(InfoVisita v) {
		consulta.setVisita(v);
	}
	
	public ArrayList<InfoPrueba> getPruebas(String paciente) {
		return consulta.getPruebas(paciente);
	}
	
	public void setPrueba(InfoPrueba p) {
		consulta.setPrueba(p);
	}
	
	public void borrarPrueba(InfoPrueba p) {
		consulta.borrarPrueba(p);
	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaHistorial",
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