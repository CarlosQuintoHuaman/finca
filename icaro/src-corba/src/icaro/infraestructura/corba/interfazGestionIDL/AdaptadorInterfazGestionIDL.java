package icaro.infraestructura.corba.interfazGestionIDL;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import organizacion.infraestructura.corba.interfazGestionIDL.InterfazGestionIDL;

public class AdaptadorInterfazGestionIDL implements InterfazGestion{

	private InterfazGestionIDL stub;
	
	public AdaptadorInterfazGestionIDL(InterfazGestionIDL stub) {
		this.stub = stub;
	}

	
	public void arranca() {
		stub.arranca();		
	}

	 
	public void continua() {
		stub.continua();
	}

	 
	public int obtenerEstado() {
		return stub.obtenerEstado();
	}

	 
	public void para() {
		stub.para();	
	}

	 
	public void termina() {
		stub.termina();
	}
	
	
}
