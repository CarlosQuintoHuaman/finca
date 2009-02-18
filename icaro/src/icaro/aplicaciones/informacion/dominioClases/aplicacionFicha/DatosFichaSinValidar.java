package icaro.aplicaciones.informacion.dominioClases.aplicacionFicha;

import java.io.Serializable;

public class DatosFichaSinValidar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String apell1;
	private String apell2;
		
	
	public DatosFichaSinValidar (String nombre, String apell1, String apell2) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		
	}
	
	public String tomaNombre() {
		return nombre;
	}
	
	public String tomaApell1() {
		return apell1;
	}
	
	public String tomaApell2() {
		return apell2;
	}
	
	
}