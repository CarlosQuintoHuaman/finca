package icaro.infraestructura.recursosOrganizacion.recursoTrazas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.RecursoTrazasImp;





/**
 * @author Felipe Polo
 * @version 1.0
 * @created 27-marzo-2008 10:28:43
 */
public abstract class RecursoTrazas extends ImplRecursoSimple implements ItfUsoRecursoTrazas {
	
	
	public RecursoTrazas(String idRecurso) {
		super(idRecurso);
	}

	private static RecursoTrazas instance;

	public static RecursoTrazas instance(){
		if (instance == null)
			instance = new RecursoTrazasImp(NombresPredefinidos.RECURSO_TRAZAS);
		return instance;
	}	
}