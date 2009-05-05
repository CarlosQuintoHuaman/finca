package icaro.aplicaciones.recursos.persistencia.imp;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.persistencia.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistencia.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;



public class ClaseGeneradoraPersistencia extends ImplRecursoSimple implements
		ItfUsoPersistencia {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	public ClaseGeneradoraPersistencia(String id) throws Exception {
		
		super(id);
		
		try{
	      	trazas = (ItfUsoRecursoTrazas)ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
	      			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	      }catch(Exception e){
	    	 this.estadoAutomata.transita("error"); 
	      	System.out.println("No se pudo usar el recurso de trazas");
	    }
	      
		try {

			AccesoBBDD.crearEsquema(id);
			consulta = new ConsultaBBDD(id);
			

		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}

	public boolean compruebaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		try {
		trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Comprobando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaUsuario(usuario, password);
		} catch (Exception e) {
			e.printStackTrace();
			this.estadoAutomata.transita("error");
			
			return false;
		}

	}

	public boolean compruebaNombreUsuario(String usuario)
			throws ErrorEnRecursoException {
			trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Comprobando nombre de usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		return consulta.compruebaNombreUsuario(usuario);

	}

	public void insertaUsuario(String usuario, String password)
			throws ErrorEnRecursoException {
		trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
  				"Insertando usuario "+usuario,
  				InfoTraza.NivelTraza.debug));
		consulta.insertaUsuario(usuario, password);

	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza(this.getId(),
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