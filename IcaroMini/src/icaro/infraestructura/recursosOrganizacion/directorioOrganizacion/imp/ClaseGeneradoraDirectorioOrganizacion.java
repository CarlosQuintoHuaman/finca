package icaro.infraestructura.recursosOrganizacion.directorioOrganizacion.imp;

import icaro.infraestructura.recursosOrganizacion.directorioOrganizacion.*;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;





/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:43
 */
public abstract class ClaseGeneradoraDirectorioOrganizacion extends ImplRecursoSimple implements ItfUsoDirectorioOrganizacion {
	
	
	public ClaseGeneradoraDirectorioOrganizacion(String idRecurso) {
		super(idRecurso);
	}

	private static ClaseGeneradoraDirectorioOrganizacion instance;

	public static ClaseGeneradoraDirectorioOrganizacion instance(){
		if (instance == null)
			instance = new DirectorioOrganizacionImp();
		return instance;
	}
	
	

	
}