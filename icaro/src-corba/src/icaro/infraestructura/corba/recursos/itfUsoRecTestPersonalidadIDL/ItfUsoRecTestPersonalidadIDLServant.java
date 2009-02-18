package icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL;

import icaro.aplicaciones.recursos.recTestPersonalidad.ExcepcionPreguntas;
import icaro.aplicaciones.recursos.recTestPersonalidad.ItfUsoRecTestPersonalidad;
import organizacion.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL._ItfUsoRecTestPersonalidadIDLImplBase;

public class ItfUsoRecTestPersonalidadIDLServant extends
		_ItfUsoRecTestPersonalidadIDLImplBase {
	
	private ItfUsoRecTestPersonalidad delegate;

	public ItfUsoRecTestPersonalidadIDLServant(ItfUsoRecTestPersonalidad delegate) {
		this.delegate = delegate;
	}
	public void anotaRespuestas(String[] preguntas, int[] respuestas) {
		try {
			delegate.anotaRespuestas(preguntas, respuestas);
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] cargaPreguntas(String[] preguntas) {
		try {
			return delegate.cargaPreguntas(preguntas);
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int[] getResultados() {
		try {
			return delegate.getResultados();
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void guardarProgresoFichero() {
		try {
			delegate.guardarProgresoFichero();
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reinicia() {
		try {
			delegate.reinicia();
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String textoResultado(String[] textos) {
		try {
			return delegate.textoResultado(textos);
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean todasRespondidas() {
		try {
			return delegate.todasRespondidas();
		} catch (ExcepcionPreguntas e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
