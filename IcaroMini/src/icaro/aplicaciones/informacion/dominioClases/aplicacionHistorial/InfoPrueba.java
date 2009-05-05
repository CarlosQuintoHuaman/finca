package icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class InfoPrueba implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String paciente;
	private String nombre;
	private Timestamp fecha;
	private String tipo;
	private String archivo;
	private String descripcion;

	/**
	 * Crea un objetivo nuevo donde almacenar los datos de una Prueba
	 * @param p Nombre de usuario
	 * @param n Nombre de la prueba
	 * @param f Fecha de la visita en donde esta la prueba
	 * @param t Tipo de prueba
	 * @param a Archivo del documento
	 * @param d Descripcion
	 */
	public InfoPrueba (String p, String n, Date f, String t, String a, String d) {
		paciente = p;
		nombre = n;
		fecha = new Timestamp(f.getTime());
		tipo = t;
		archivo = a;
		descripcion = d;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setFecha(Date fecha) {
		this.fecha.setTime(fecha.getTime());
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	
}