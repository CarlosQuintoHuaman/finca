package icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import organizacion.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDL;


/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoPersistenciaIDL implements ItfUsoPersistencia {

	private ItfUsoPersistenciaIDL stub;
	
	public AdaptadorItfUsoPersistenciaIDL(ItfUsoPersistenciaIDL stub){
		this.stub = stub;
	}


	/**
	 * 
	 * @param usuario
	 * @exception Exception
	 */
	public boolean compruebaNombreUsuario(String usuario)
	  throws Exception{
		return stub.compruebaNombreUsuario(usuario);
	}

	/**
	 * 
	 * @param usuario
	 * @param password
	 * @exception Exception
	 */
	public boolean compruebaUsuario(String usuario, String password)
	  throws Exception{
		return stub.compruebaUsuario(usuario, password);
	}

	/**
	 * 
	 * @param usuario
	 * @param password
	 * @exception Exception
	 */
	public void insertaUsuario(String usuario, String password)
	  throws Exception{
		stub.insertaUsuario(usuario, password);
	}

}