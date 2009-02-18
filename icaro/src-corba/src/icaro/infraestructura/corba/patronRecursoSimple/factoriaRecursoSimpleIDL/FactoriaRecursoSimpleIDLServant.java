package icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL;

import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import organizacion.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL._FactoriaRecursoSimpleIDLImplBase;

public class FactoriaRecursoSimpleIDLServant extends
		_FactoriaRecursoSimpleIDLImplBase {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	public FactoriaRecursoSimpleIDLServant() {
		
	}
	
	public void crearRecursoSimple(String nombreRecurso) {
		FactoriaRecursoSimple.instance().crearRecursoSimple(nombreRecurso);
	}

}
