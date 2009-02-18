package icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import organizacion.infraestructura.corba.recursos.ItfUsoPersistenciaIDL._ItfUsoPersistenciaIDLImplBase;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:12
 */
public class ItfUsoPersistenciaIDLServant extends _ItfUsoPersistenciaIDLImplBase {

	private ItfUsoPersistencia delegate;
	

	public ItfUsoPersistenciaIDLServant(ItfUsoPersistencia delegate){
		this.delegate = delegate;

	}

	
	public boolean compruebaNombreUsuario(String usuario) {
		try {
			return delegate.compruebaNombreUsuario(usuario);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean compruebaUsuario(String usuario, String password) {
		try {
			return delegate.compruebaUsuario(usuario, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	
	public void insertaUsuario(String usuario, String password) {
		try {
			delegate.insertaUsuario(usuario, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}