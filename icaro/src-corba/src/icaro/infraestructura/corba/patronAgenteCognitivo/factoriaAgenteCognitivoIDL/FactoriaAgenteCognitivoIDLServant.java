package icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL;

import icaro.infraestructura.patronAgenteCognitivoSimple.FactoriaAgenteCognitivo;
import organizacion.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL._FactoriaAgenteCognitivoIDLImplBase;

public class FactoriaAgenteCognitivoIDLServant extends _FactoriaAgenteCognitivoIDLImplBase {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void crearAgenteCognitivo(String nombreAgente) {
		
		try {
			FactoriaAgenteCognitivo.instancia().crearAgenteCognitivo(nombreAgente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
