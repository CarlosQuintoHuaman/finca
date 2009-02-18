package icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL;

import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import organizacion.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.FactoriaRecursoSimpleIDL;

public class AdaptadorFactoriaRecursoSimpleIDL extends FactoriaRecursoSimple {

	private FactoriaRecursoSimpleIDL stub;
	
	public AdaptadorFactoriaRecursoSimpleIDL(FactoriaRecursoSimpleIDL stub) {
		this.stub = stub;
	}
	

	@Override
	public void crearRecursoSimple(String nombreRecurso) {
		stub.crearRecursoSimple(nombreRecurso);
		
	}

}
