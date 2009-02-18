package icaro.aplicaciones.recursos.visualizacionAlta.imp.swing;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitadorCampoTexto extends PlainDocument {
	
	private static final long serialVersionUID = 1L;
    private JTextField componenteTexto;
    private int numeroMaximoCaracteres;

    public LimitadorCampoTexto(){
    	super();
    }
    
    /**
     * Crea una instancia de LimitadorCampoTexto.
     * 
     * @param componenteTexto Componente de Texto en el que se quieren limitar los caracteres.
     * @param numeroMaximoCaracteres Número máximo de caracteres que queremos en el editor.
     */
    public LimitadorCampoTexto(JTextField componenteTexto, int numeroMaximoCaracteres) {
        this.componenteTexto = componenteTexto;
        this.numeroMaximoCaracteres = numeroMaximoCaracteres;
    }
    
    /**
     * Método al que llama el editor cada vez que se intenta insertar caracteres.
     * El método comprueba que no se sobrepasa el límite. Si es así, llama al
     * método de la clase padre para que se inserten los caracteres. Si se 
     * sobrepasa el límite, retorna sin hacer nada.
     */
    public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
        
    	if ((componenteTexto.getText().length() + arg1.length()) > this.numeroMaximoCaracteres) return;
        super.insertString(arg0,arg1,arg2);
    } 		
}