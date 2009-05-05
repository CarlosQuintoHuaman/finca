package icaro.infraestructura.entidadesBasicas;

import java.io.Serializable;

public class MensajeAgente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @uml.property  name="contenido"
	 */
	private Object contenido;
	/**
	 * @uml.property  name="emisor"
	 */
	private Object emisor;
	/**
	 * @uml.property  name="receptor"
	 */
	private Object receptor;
	
	/**
	 * @return
	 * @uml.property  name="emisor"
	 */
	public Object getEmisor() {
		return emisor;
	}
	/**
	 * @param emisor
	 * @uml.property  name="emisor"
	 */
	public void setEmisor(Object emisor) {
		this.emisor = emisor;
	}
	/**
	 * @return
	 * @uml.property  name="receptor"
	 */
	public Object getReceptor() {
		return receptor;
	}
	/**
	 * @param receptor
	 * @uml.property  name="receptor"
	 */
	public void setReceptor(Object receptor) {
		this.receptor = receptor;
	}
	/**
	 * @return
	 * @uml.property  name="contenido"
	 */
	public Object getContenido() {
		return contenido;
	}
	/**
	 * @param contenido
	 * @uml.property  name="contenido"
	 */
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
        public String toString(){
            String str = "Emisor:"+emisor+", Receptor:"+receptor;
            return str;
        }
	
}