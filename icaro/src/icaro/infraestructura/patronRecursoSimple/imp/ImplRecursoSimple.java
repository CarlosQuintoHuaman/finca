package icaro.infraestructura.patronRecursoSimple.imp;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.recursos.AutomataCicloVidaRecurso;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.ConfiguracionTrazas;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

import org.apache.log4j.Logger;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ImplRecursoSimple
		implements ItfUsoRecursoSimple, ItfGestionRecursoSimple {

	private static final long serialVersionUID = 1L;
	
	public final static String fichero_automata_ciclo_vida = "/icaro/infraestructura/entidadesBasicas/componentesBasicos/automata/recursos/TablaEstadosCicloVidaRecursos.xml";
	
	/**
	 * @uml.property  name="itfUsoRepositorioInterfaces"
	 * @uml.associationEnd  
	 */
	protected icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	//protected int estado;
	/**
	 * @uml.property  name="estadoAutomata"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected AutomataCicloVidaRecurso estadoAutomata;
	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	protected Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	/**
	 * @uml.property  name="id"
	 */
	protected String id;
	
	/**
	 * @return
	 * @uml.property  name="id"
	 */
	public String getId() {
		return id;
	}


	/**
	 * @param id
	 * @uml.property  name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}


	
	public ImplRecursoSimple(String idRecurso) {
		this.estadoAutomata = new AutomataCicloVidaRecurso(fichero_automata_ciclo_vida, 2);
		this.id = idRecurso;
	}


	public void arranca() {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.estadoAutomata.transita("arrancar");
		this.estadoAutomata.transita("ok");
	}
/*	
	public void arrancaConEvento() {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.estadoAutomata.transita("arrancar");
		this.estadoAutomata.transita("ok");
	}
	
	public void arrancaConInput(String nombreInput) {
		//this.estado = InterfazGestion.ESTADO_ACTIVO;
		this.estadoAutomata.transita(nombreInput);
		this.estadoAutomata.transita("ok");
	}
*/
	public void para() {
		throw new UnsupportedOperationException();
	}

	public void termina()  {
		//this.estado = InterfazGestion.ESTADO_TERMINADO;
		this.estadoAutomata.transita("terminar");
		this.estadoAutomata.transita("ok");
	}

	public void continua() {
		throw new UnsupportedOperationException();
	}

	/*public int monitorizacion() throws RemoteException {
		return this.estado;
	}*/
	//Este método no se ejecuta, se mantiene por el patrón anterior
	public int obtenerEstado() {
		String estadoAutomata = this.estadoAutomata.monitorizar();
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_ACTIVO)
				|| estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_ARRANCADO))
			return InterfazGestion.ESTADO_ACTIVO;
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_TERMINADO))
			return InterfazGestion.ESTADO_TERMINADO;
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_TERMINANDO))
			return InterfazGestion.ESTADO_TERMINANDO;
	
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_CREADO))
			return InterfazGestion.ESTADO_CREADO;
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_ERROR))
			return InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE;
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_FALLO_TEMPORAL))
			return InterfazGestion.ESTADO_ERRONEO_RECUPERABLE;
		if (estadoAutomata.equals(AutomataCicloVidaRecurso.ESTADO_PARADO))
			return InterfazGestion.ESTADO_PARADO;
		return InterfazGestion.ESTADO_OTRO;
	}
	
	/*public String obtenerEstado() {
		return this.estadoAutomata.monitorizar();
	}*/
	
	/**
	 * @param itfUsoRepositorioInterfaces
	 * @uml.property  name="itfUsoRepositorioInterfaces"
	 */
	public void setItfUsoRepositorioInterfaces(ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces) {
		this.itfUsoRepositorioInterfaces = itfUsoRepositorioInterfaces;
	}
	public void setParametrosLogger(String archivoLog, String nivelLog){
		new ConfiguracionTrazas(logger, archivoLog, nivelLog);
	}

}
