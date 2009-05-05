package icaro.infraestructura.recursosOrganizacion.configuracion.imp;

import icaro.infraestructura.recursosOrganizacion.configuracion.*;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ConfiguracionImp;





/**
 * @author Damiano Spina
 * @version 1.0
 * @created 19-feb-2008 13:20:43
 */
public abstract class ClaseGeneradoraConfiguracion extends ImplRecursoSimple implements ItfUsoConfiguracion {
	
	
	public ClaseGeneradoraConfiguracion(String idRecurso) {
		super(idRecurso);
	}

	private static ClaseGeneradoraConfiguracion instance;

	public static ClaseGeneradoraConfiguracion instance(){
		if (instance == null)
			instance = new ConfiguracionImp();
		return instance;
	}
	
	public static ClaseGeneradoraConfiguracion instance(String descripcionXMLAlternativo) {
		if (instance == null)
			instance = new ConfiguracionImp(descripcionXMLAlternativo);
		return instance;
	}

	
}