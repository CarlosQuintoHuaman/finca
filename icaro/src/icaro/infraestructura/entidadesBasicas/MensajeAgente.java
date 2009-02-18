package icaro.infraestructura.entidadesBasicas;

import java.io.Serializable;

public class MensajeAgente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object contenido;
	private Object emisor;
	private Object receptor;
	
	public Object getEmisor() {
		return emisor;
	}
	public void setEmisor(Object emisor) {
		this.emisor = emisor;
	}
	public Object getReceptor() {
		return receptor;
	}
	public void setReceptor(Object receptor) {
		this.receptor = receptor;
	}
	public Object getContenido() {
		return contenido;
	}
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
        public String toString(){
            String str = "Emisor:"+emisor+", Receptor:"+receptor;
            return str;
        }
	
}