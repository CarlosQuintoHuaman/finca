package icaro.aplicaciones.recursos.persistenciaFicha.imp;

import java.sql.Connection;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.recursos.persistenciaFicha.ItfUsoPersistenciaFicha;
import icaro.aplicaciones.recursos.persistenciaFicha.imp.ErrorEnRecursoException;
import icaro.aplicaciones.recursos.persistenciaFicha.imp.util.AccesoBBDD;
import icaro.aplicaciones.recursos.persistenciaFicha.imp.util.ConsultaBBDD;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;



public class ClaseGeneradoraPersistenciaFicha extends ImplRecursoSimple implements
		ItfUsoPersistenciaFicha {

	private static final long serialVersionUID = 1L;
	private ItfUsoRecursoTrazas trazas;
	private ConsultaBBDD consulta;

	private Connection c;
	
	public ClaseGeneradoraPersistenciaFicha(String id) throws Exception {
		
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
			
			c = AccesoBBDD.conectar("PersistenciaFicha1");
			consulta = new ConsultaBBDD("PersistenciaFicha1");
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			throw e;
		}

	}
	
	public boolean meteFicha(DatosFicha original, DatosFicha fichaN){
		return consulta.meteFicha(original,fichaN);
	}
	
	public boolean borraFicha(DatosFicha ficha){
		return consulta.borraFicha(ficha);
	}

	public DatosFicha getFicha(DatosCita datos){
		return consulta.getFicha(datos);
	}

	@Override
	public void termina() {
		trazas.aceptaNuevaTraza(new InfoTraza("PersistenciaFicha",
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