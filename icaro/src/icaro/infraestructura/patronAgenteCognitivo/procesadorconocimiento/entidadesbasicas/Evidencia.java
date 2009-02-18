package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.entidadesbasicas;

public class Evidencia {

	
	private Object origen;
	private Object contenido;
	
	public Evidencia(){}
	public Evidencia(Object origen, Object contenido) {
		this.origen = origen;
		this.contenido = contenido;
	}
	
	public Object getOrigen() {
		return origen;
	}
	public void setOrigen(Object object) {
		this.origen = object;
	}
	public Object getContenido() {
		return contenido;
	}
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
}
