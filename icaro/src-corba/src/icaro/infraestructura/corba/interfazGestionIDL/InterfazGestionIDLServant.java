package icaro.infraestructura.corba.interfazGestionIDL;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import organizacion.infraestructura.corba.interfazGestionIDL._InterfazGestionIDLImplBase;

public class InterfazGestionIDLServant extends _InterfazGestionIDLImplBase{

	
	private InterfazGestion delegate;
	
	public InterfazGestionIDLServant(InterfazGestion delegate) {
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
