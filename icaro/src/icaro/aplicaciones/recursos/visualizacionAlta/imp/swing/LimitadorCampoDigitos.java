package icaro.aplicaciones.recursos.visualizacionAlta.imp.swing;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;



@SuppressWarnings("serial")
public class LimitadorCampoDigitos extends LimitadorCampoTexto {

	public LimitadorCampoDigitos(JTextField componenteTexto, int numeroMaximoCaracteres){
		super(componenteTexto, numeroMaximoCaracteres);
	}
	
	public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
		try{
			Integer.parseInt(arg1);
		} catch (NumberFormatException e){
			// El caracter no era un n�mero
			return;
		}
		super.insertString(arg0, arg1, arg2);
	}
}

