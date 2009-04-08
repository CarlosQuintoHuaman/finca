package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosAgenda {
	private String nombre;
	private String telf;
	private String hora;
	private String fecha;
	private boolean crear;

	public DatosAgenda (String nombre, String telf, Boolean crear){
		this.nombre = nombre;
		this.telf = telf;
		this.crear = crear;
	}
	/**
	 * Crea un objeto nuevo donde almacenar la entrada de una cita de la agenda
	 * @param nombre
	 * @param telf
	 * @param hora
	 * @param fecha
	 */
	public DatosAgenda (String nombre, String telf, String hora, String fecha){
		this.nombre = nombre;
		this.telf = telf;
		this.hora= hora;
		this.fecha=fecha;
		this.crear = true;
	}
	
	public String tomaNombre() {
		return nombre;
	}
	
	public String tomatelf() {
		return telf;
	}
	
	public String tomaHora() {
		return hora;
	}
	
	public String tomaFecha() {
		return fecha;
	}
	public void setNombre(String n){
		nombre=n;
	}
	
	public void setHora(String h){
		hora=h;
	}
	public void setFecha(String n){
		fecha=n;
	}
	public void settelf(String a){
		telf=a;
	}
	
	public void setCrear(boolean a){
		crear=a;
	}

	public boolean tomaCrear(){
		return crear;
	}

}
