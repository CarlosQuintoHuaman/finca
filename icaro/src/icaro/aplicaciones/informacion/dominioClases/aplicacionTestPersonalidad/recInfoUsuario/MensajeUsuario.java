package icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad.recInfoUsuario;

import java.util.Vector;

/**
 * Clase que representa cada tipo de mensaje que se puede presentar al usuario, junto con todas
 *  las alternativas para presentarlo
 *
 * @author Carlos Delgado
 */
public class MensajeUsuario implements java.io.Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Indica que se presentar� cualquier alternativa del mensaje, de forma aleatoria, a no ser que
     *  se especifique lo contrario
     */
    public final static int PRESENTACION_ALEATORIA= 0;

    /**
     * Indica que se presentar�n las alternativas del mensaje de forma secuencial, a no ser que
     *  se especifique lo contrario
     */
    public final static int PRESENTACION_SECUENCIAL= 1;

    /**
     * Indica que se presentar� �nicamente la primera alternativa del mensaje, a no ser que
     *  se especifique lo contrario
     */
    public final static int PRESENTACION_PRIMERO= 2;

    /**
     * Tipo de presentaci�n del mensaje
     */
    public int tipoPresentacion;

    /**
     * Array de strings que contienen todas las alternativas del mensaje
     */
    public String[] alternativas;

    /**
     * String que sirve de identificador del mensaje
     */
    public String id;

    /**
     * Lleva cuenta del mensaje anterior que sac�
     */
    public int mensajeAnterior;

  
    /**
     * Constructor
     *
     * @param idMensaje String que identifica al mensaje
     * @param tipoPresentacion   tipo de presentaci�n del mensaje
     * @param alternativas  Vector con las alternativas del mensaje
     */
    public MensajeUsuario(String idMensaje, int tipoPresentacion, Vector alternativas) {
  
	this.id= new String(idMensaje);
	this.tipoPresentacion= tipoPresentacion;

	this.alternativas= new String[alternativas.size()];
	for (int i= 0; i < alternativas.size(); i++)
	    this.alternativas[i]= new String((String)alternativas.elementAt(i));

	// Pone como �ltimo mensaje que se obtuvo el �ltimo
	this.mensajeAnterior= this.alternativas.length-1;
	
    }
    public void addAlternativa(String alternativa){
    	String [] nuevasAlternativas=new String[this.alternativas.length+1];
    	System.arraycopy(this.alternativas, 0, nuevasAlternativas, 0, this.alternativas.length);
    	nuevasAlternativas[nuevasAlternativas.length-1]=alternativa;
    	this.alternativas=nuevasAlternativas;
    }


}