package icaro.aplicaciones.recursos.recTestPersonalidad.imp;

import icaro.aplicaciones.recursos.recInformacionUsuario.ExcepcionMensajes;
import icaro.aplicaciones.recursos.recTestPersonalidad.ExcepcionPreguntas;
import icaro.aplicaciones.recursos.recTestPersonalidad.ItfUsoRecTestPersonalidad;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

import java.rmi.RemoteException;





public class ClaseGeneradoraRecTestPersonalidad extends ImplRecursoSimple implements ItfUsoRecTestPersonalidad {
	private PreguntasTest preguntasTest;
	private static final long serialVersionUID = 1L;
	private static final int NUMERO_PREGUNTAS = 30;
	
	/**
	 * Constructor
	 * @throws RemoteException
	 */
	public ClaseGeneradoraRecTestPersonalidad(String id) {
		super(id);
		preguntasTest = new PreguntasTest(NUMERO_PREGUNTAS);
	}
	/**
	 * Carga las preguntas del test en el sistema y devuelve las respuestas
	 * que pudiese haber anteriores
	 * @param preguntas cadena de String con las preguntas
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public int[] cargaPreguntas(String[] preguntas) throws ExcepcionPreguntas {
		return preguntasTest.cargaPreguntas(preguntas);
	}
	/**
	 * Carga las preguntas del test en el sistema
	 * @param preguntas cadena de String con las preguntas
	 * @throws ExcepcionMensajes
	 * @throws RemoteException
	 */
	public void anotaRespuestas(String[] preguntas, int[] respuestas) throws ExcepcionPreguntas {
		preguntasTest.anotaRespuestas(preguntas, respuestas);
	}
	/**
	 * Almacena las respuestas en un fichero
	 * @throws ExcepcionPreguntas
	 * @throws RemoteException
	 */
	public void guardarProgresoFichero() throws ExcepcionPreguntas {
		preguntasTest.guardarProgresoFichero();
	}
	/**
	 * Indica si se han respondido todas las respuestas del formulario
	 * @throws RemoteException
	 */
	public boolean todasRespondidas() throws ExcepcionPreguntas {
		return preguntasTest.todasRespondidas();
	}
	/**
	 * Devuelve el texto resultado del test
	 * @throws RemoteException
	 */
	public String textoResultado(String[] textos) throws ExcepcionPreguntas {
		return preguntasTest.textoResultado(textos);
	}
	/**
	 * Devuelve los resultados numéricos del test
	 * @throws RemoteException
	 */
	public int[] getResultados() throws ExcepcionPreguntas {
		return preguntasTest.getResultados();
	}
	/**
	 * Reinicia la estructura de las preguntas
	 * @throws RemoteException
	 */
	public void reinicia() throws ExcepcionPreguntas {
		preguntasTest.reinicia();
	}
	
	
}
