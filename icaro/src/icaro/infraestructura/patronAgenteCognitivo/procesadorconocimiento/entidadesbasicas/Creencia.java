package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas;

public class Creencia {
	
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
            return "Emisor: "+emisor+ " Receptor: "+receptor+ " Contenido:"+contenido;
        }
}