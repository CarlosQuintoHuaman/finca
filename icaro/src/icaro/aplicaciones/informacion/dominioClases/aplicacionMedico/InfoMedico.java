package icaro.aplicaciones.informacion.dominioClases.aplicacionMedico;

import java.io.Serializable;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class InfoMedico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String apell1;
	private String telf;
		
	/**
	 * Crea un objetivo nuevo donde almacenar los datos de un medico
	 * @param nombre
	 * @param apell1
	 * @param telf
	 */
	public InfoMedico (String nombre, String apell1, String telf) {
		
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