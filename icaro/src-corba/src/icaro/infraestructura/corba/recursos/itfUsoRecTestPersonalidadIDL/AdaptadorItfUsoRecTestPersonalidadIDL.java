package icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL;

import icaro.aplicaciones.recursos.recTestPersonalidad.ExcepcionPreguntas;
import icaro.aplicaciones.recursos.recTestPersonalidad.ItfUsoRecTestPersonalidad;
import organizacion.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL.ItfUsoRecTestPersonalidadIDL;

public class AdaptadorItfUsoRecTestPersonalidadIDL implements
		ItfUsoRecTestPersonalidad {
	
	private ItfUsoRecTestPersonalidadIDL stub;
	
	public AdaptadorItfUsoRecTestPersonalidadIDL(ItfUsoRecTestPersonalidadIDL stub) {
		this.stub = stub;
	}

	public void anotaRespuestas(String[] preguntas, int[] respuestas)
			throws ExcepcionPreguntas {
		stub.anotaRespuestas(preguntas, respuestas);
	}

	public int[] cargaPreguntas(String[] preguntas) throws ExcepcionPreguntas {
		return stub.cargaPreguntas(preguntas);
	}

	public int[] getResultados() throws ExcepcionPreguntas {
		return stub.getResultados();
	}

	public void guardarProgresoFichero() throws ExcepcionPreguntas {
		stub.guardarProgresoFichero();
	}

	public void reinicia() throws ExcepcionPreguntas {
		stub.reinicia();
	}

	public String textoResultado(String[] textos) throws ExcepcionPreguntas {
		return stub.textoResultado(textos);
	}

	public boolean todasRespondidas() throws ExcepcionPreguntas {
		return stub.todasRespondidas();
	}


}
