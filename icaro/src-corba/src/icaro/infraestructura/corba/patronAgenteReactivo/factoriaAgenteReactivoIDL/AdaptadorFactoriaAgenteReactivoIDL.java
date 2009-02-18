package icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import organizacion.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:04
 */
public class AdaptadorFactoriaAgenteReactivoIDL extends FactoriaAgenteReactivo {

	private FactoriaAgenteReactivoIDL stub;
	

	public AdaptadorFactoriaAgenteReactivoIDL(FactoriaAgenteReactivoIDL stub){
		this.stub = stub;
	}


	@Override
	public void crearAgenteReactivo(DescInstanciaAgente agente) {
		stub.crearAgenteReactivo(null);
		
	}

}