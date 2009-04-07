package icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos;

import java.io.Serializable;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class InfoMedicamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private int codigo;
	private String nombre;
	private String pa;
	private String descripcion;
	private String indicaciones;
	
	/**
	 * Crea un objetivo nuevo donde almacenar los datos de un medicamento
	 * @param cod Codigo
	 * @param nombre Nombre
	 * @param pa Principio Activo
	 * @param desc Descripcion
	 * @param ind Indicaciones
	 */
	public InfoMedicamento (int cod, String nombre, String pa, String desc, String ind) {
		setCodigo(cod);
		this.setNombre(nombre);
		this.setPa(pa);
		this.setDescripcion(desc);
		this.setIndicaciones(ind);
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getNombre() {
		return nombre;
	}


	public void setPa(String pa) {
		this.pa = pa;
	}


	public String getPa() {
		return pa;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setIndicaciones(String indicaciones) {
		this.indicaciones = indicaciones;
	}


	public String getIndicaciones() {
		return indicaciones;
	}
	

	
	
}