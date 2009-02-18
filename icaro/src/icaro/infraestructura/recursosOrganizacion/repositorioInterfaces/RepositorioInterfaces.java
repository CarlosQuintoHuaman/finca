package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces;

/**
 * Implementación de un repositorio de interfaces mediante una tabla hash
 * @author Jorge M. González Martín
 * @version 1.0
 */





import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;



public abstract class RepositorioInterfaces extends ImplRecursoSimple implements
		ItfUsoRepositorioInterfaces {

	public RepositorioInterfaces(String idRecurso) {
		super(idRecurso);
	}

	private static final long serialVersionUID = 1L;

	private static RepositorioInterfaces instance;
	
	public static final String IMP_LOCAL = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.impLocal.RepositorioInterfacesImpLocal";
	public static final String IMP_CORBA = "icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.impCORBA.RepositorioInterfacesCORBA";
	
	public static RepositorioInterfaces instance() {
		if (instance == null)
			try {
				Class imp = Class.forName(IMP_CORBA);
				instance = (RepositorioInterfaces)imp.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return instance;
	}
	
	public static RepositorioInterfaces instance(String implementacion) {
		if (instance == null)
			try {
				Class imp = Class.forName(implementacion);
				instance = (RepositorioInterfaces)imp.newInstance();
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