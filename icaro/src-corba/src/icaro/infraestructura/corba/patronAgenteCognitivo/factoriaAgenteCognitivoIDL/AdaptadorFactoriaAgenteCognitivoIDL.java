package icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL;

import icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL;
import icaro.infraestructura.patronAgenteCognitivoSimple.FactoriaAgenteCognitivo;
import organizacion.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.FactoriaAgenteCognitivoIDL;

public class AdaptadorFactoriaAgenteCognitivoIDL extends FactoriaAgenteCognitivo {

private FactoriaAgenteCognitivoIDL stub;
	

	public AdaptadorFactoriaAgenteCognitivoIDL(FactoriaAgenteCognitivoIDL stub){
		this.stub = stub;
	}

	
	@Override
	public void crearAgenteCognitivo(String nombreAgente) throws Exception {
		stub.crearAgenteCognitivo(nombreAgente);
	}

}
