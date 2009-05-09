package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.io.Serializable;

public class DatosCitaSinValidar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String nombre;
	private String apell1;
	private String apell2;
	private String telf;
	private String fecha;
	private String hora;
	private Boolean nuevo;
	private int periodo;
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2,String telf, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		this.hora= hora;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar(String nombre, String apell1, String apell2,String telf, String hora, boolean n,String usuario) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.hora= hora;
		this.usuario=usuario;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf,String fecha, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.fecha=fecha;
		this.hora= hora;
		periodo=1;
		
	}
	
	/**
	 * Crea un objeto nuevo donde almacenar los datos de una cita de la ventana Cita
	 * @param nombre
	 * @param apell1
	 * @param telf
	 * @param hora
	 * @param periodo
	 */
public DatosCitaSinValidar (String nombre, String apell1,String apell2, String telf, String hora, int periodo) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.hora= hora;
		this.periodo=periodo;
	}
	
public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf, String hora, int periodo, String usuario) {
	
	this.nombre = nombre;
	this.apell1 = apell1;
	this.apell2 = apell2;
	this.telf = telf;
	this.hora= hora;
	this.periodo=periodo;
	this.usuario=usuario;
}
	public Boolean getNuevo() {
	return nuevo;
}

public void setNuevo(Boolean nuevo) {
	this.nuevo = nuevo;
}

	public String tomaNombre() {
		return nombre;
	}
	
	public String tomaApell1() {
		return apell1;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String tomaTelf() {
		return telf;
	}
	
	public String getApell2() {
		return apell2;
	}

	public void setApell2(String apell2) {
		this.apell2 = apell2;
	}

	public String tomaHora() {
		return hora;
	}
	
	public int tomaPeriodo() {
		return periodo;
	}
	
	public void setNombre(String n){
		nombre=n;
	}
	
	public void setApell1(String a){
		apell1=a;
	}
	public void setTelf(String t){
		telf=t;
	}
	public void setHora(String h){
		hora=h;
	}
	public void setPeriodo(int p){
		periodo=p;;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	

}

