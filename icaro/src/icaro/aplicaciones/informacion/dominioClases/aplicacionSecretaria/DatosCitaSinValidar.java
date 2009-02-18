package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.io.Serializable;

public class DatosCitaSinValidar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String apell1;
	private String telf;
	private String hora;
	private int periodo;
	
	public DatosCitaSinValidar (String nombre, String apell1, String telf, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		this.hora= hora;
		
	}
	
public DatosCitaSinValidar (String nombre, String apell1, String telf, String hora, int periodo) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		this.hora= hora;
		this.periodo=periodo;
	}
	
	public String tomaNombre() {
		return nombre;
	}
	
	public String tomaApell1() {
		return apell1;
	}
	
	public String tomaTelf() {
		return telf;
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

}

