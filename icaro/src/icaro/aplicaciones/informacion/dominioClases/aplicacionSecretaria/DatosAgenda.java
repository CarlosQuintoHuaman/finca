package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosAgenda {
	private String nombre;
	private String telf;
	private boolean crear;
	public DatosAgenda (String nombre, String telf, Boolean crear){
		this.nombre = nombre;
		this.telf = telf;
		this.crear = crear;
	}
	
	public String tomaNombre() {
		return nombre;
	}
	
	public String tomatelf() {
		return telf;
	}
	
	public void setNombre(String n){
		nombre=n;
	}
	
	public void settelf(String a){
		telf=a;
	}

	public boolean tomaCrear(){
		return crear;
	}

}
