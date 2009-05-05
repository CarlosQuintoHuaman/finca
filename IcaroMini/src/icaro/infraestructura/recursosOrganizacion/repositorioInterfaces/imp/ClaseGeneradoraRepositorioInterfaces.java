package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;

/**
 * Implementaci�n de un repositorio de interfaces mediante una tabla hash
 * @author Jorge M. Gonz�lez Mart�n
 * @version 1.0
 */





import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.*;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;



public abstract class ClaseGeneradoraRepositorioInterfaces extends ImplRecursoSimple implements
		ItfUsoRepositorioInterfaces {

	public ClaseGeneradoraRepositorioInterfaces(String idRecurso) {
		super(idRecurso);
	}

	private static final long serialVersionUID = 1L;

	private static ClaseGeneradoraRepositorioInterfaces instance;
	
	public static final String IMP_LOCAL = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.RepositorioInterfacesImpLocal";
	public static final String IMP_CORBA = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.impCORBA.RepositorioInterfacesCORBA";
	
	public static ClaseGeneradoraRepositorioInterfaces instance() {
		if (instance == null)
			try {
	//			Class imp = Class.forName(IMP_CORBA);
				instance = new RepositorioInterfacesImpLocal ();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return instance;
	}
	
	public static ClaseGeneradoraRepositorioInterfaces instance(String implementacion) {
		if (instance == null)
			try {
				Class imp = Class.forName(implementacion);
				instance = (ClaseGeneradoraRepositorioInterfaces)imp.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return instance;
	}
	
	/**
	 * Almacena una interfaz en el repositorio En caso de existir el nombre
	 * previamente, se actualiza la referencia
	 * 
	 * @param nombre
	 * @param interfaz
	 */
	public abstract void registrarInterfaz(String nombre, Object interfaz);

	/**
	 * Recupera una interfaz del repositorio
	 * 
	 * @param nombre
	 *            Nombre de la interfaz a recuperar
	 * @return Interfaz asociada a ese nombre o null si no se ha encontrado ese
	 *         nombre
	 * @throws Exception 
	 */
	public abstract Object obtenerInterfaz(String nombre) throws Exception;

	/**
	 * Cancela el registro de una interfaz en el repositorio
	 * 
	 * @param nombre
	 *            Nombre de la interfaz a eliminar del repositorio
	 * @throws Exception
	 */

	public abstract void eliminarRegistroInterfaz(String nombre);

	/**
	 * Devuelve una lista con los nombres de todos los interfaces registrados
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String listarNombresInterfacesRegistradas();
}