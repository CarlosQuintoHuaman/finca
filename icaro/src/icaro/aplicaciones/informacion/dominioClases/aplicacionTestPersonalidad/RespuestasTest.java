package icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad;
/**
 * Clase que representa las respuestas del Test.
 */
public class RespuestasTest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Contiene las respuestas de las preguntas
	 * 
	 */
	private int[] respuestas;
	
	public RespuestasTest(int numPreguntas){
		respuestas = new int[numPreguntas];
	}
	
	public void marcarRespuesta(int pregunta, int respuesta){
		respuestas[(pregunta-1)] = respuesta;
	}
	
	public int obtenerRespuesta(int pregunta){
		return respuestas[(pregunta-1)];
	}
	
	public int numeroRespuestas(){
		return respuestas.length;
	}
}