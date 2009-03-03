package icaro.aplicaciones.informacion.dominioClases.aplicacionMedico;

import java.io.Serializable;

public class InfoPaciente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String apell1;
	private String telf;
		
	
	public InfoPaciente (String nombre, String apell1, String telf) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		
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
	
	
}