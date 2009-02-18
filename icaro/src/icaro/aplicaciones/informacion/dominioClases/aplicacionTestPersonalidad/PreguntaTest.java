package icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad;
/* Clase que representa cada una de las preguntas que componen
 * el test
 */
public class PreguntaTest implements java.io.Serializable {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * String que contiene la pregunta;
	 */
	public String pregunta;
	/*
	 * int que representa la respuesta del usuario;
	 */
	public int respuesta;
	/*
	 * bool que indica si la pregunta ha sido respondida o no
	 */
	public boolean respondida;
	/**
     * Constructor
     *
     * @param pregunta El texto de la pregunta
     */
	public PreguntaTest(String pregunta){
		this.pregunta = new String(pregunta);
		this.respuesta = -1;
		this.respondida = false;
	}
	public void responderPregunta(int respuesta){
		this.respuesta = respuesta;
		this.respondida = false;
	}
}