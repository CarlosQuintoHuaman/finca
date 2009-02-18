package icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import organizacion.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL._ItfGestionRecursoSimpleIDLImplBase;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:10
 */
public class ItfGestionRecursoSimpleIDLServant extends _ItfGestionRecursoSimpleIDLImplBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItfGestionRecursoSimple delegate;
	
	public ItfGestionRecursoSimpleIDLServant(ItfGestionRecursoSimple delegate){
		this.delegate = delegate;
	}


	
	public void arranca() {
		delegate.arranca();
		
	}



	
	public void continua() {
		delegate.continua();		
	}



	
	public int obtenerEstado() {
		return delegate.obtenerEstado();
	}



	
	public void para() {
		delegate.para();
		
	}



	
	public void termina() {
		delegate.termina();
		
	}

}