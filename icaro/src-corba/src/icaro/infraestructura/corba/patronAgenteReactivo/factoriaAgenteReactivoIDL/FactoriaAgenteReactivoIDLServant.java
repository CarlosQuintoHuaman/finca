package icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import organizacion.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLImplBase;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:07
 */
public class FactoriaAgenteReactivoIDLServant extends _FactoriaAgenteReactivoIDLImplBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public FactoriaAgenteReactivoIDLServant(){

	}


	
	public void crearAgenteReactivo(DescInstanciaAgente agente) {
		FactoriaAgenteReactivo.instancia().crearAgenteReactivo(null);
		
	}

}