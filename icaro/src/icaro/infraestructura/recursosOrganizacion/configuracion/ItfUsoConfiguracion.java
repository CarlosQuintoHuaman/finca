package icaro.infraestructura.recursosOrganizacion.configuracion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescRecursoAplicacion;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.UsoRecursoException;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:44
 */
public interface ItfUsoConfiguracion extends ItfUsoRecursoSimple {
	/**
	 * 
	 * @param atributo
	 */
	public String getValorPropiedadGlobal(String atributo) throws UsoRecursoException;

	/**
	 * 
	 * @param id
	 */
	public DescInstanciaGestor getDescInstanciaGestor(String id) throws UsoRecursoException;

	/**
	 * 
	 * @param id
	 */
	public DescInstanciaAgenteAplicacion getDescInstanciaAgenteAplicacion(String id) throws UsoRecursoException;

	/**
	 * 
	 * @param id
	 */
	public DescInstanciaRecursoAplicacion getDescInstanciaRecursoAplicacion(String id) throws UsoRecursoException;

	/**
	 * 
	 * @param nombre
	 */
	public DescRecursoAplicacion getDescRecursoAplicacion(String nombre) throws UsoRecursoException;

	/**
	 * 
	 * @param nombre
	 */
	public DescComportamientoAgente getDescComportamientoAgente(String nombre) throws UsoRecursoException;


}