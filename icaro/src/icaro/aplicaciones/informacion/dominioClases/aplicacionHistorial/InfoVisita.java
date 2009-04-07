package icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class InfoVisita implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private Date fecha;
	private String motivo;
	private String descripcion;
	private String exploracion;
	private String diagnostico;
	private String tratamiento;

	/**
	 * Crea un objetivo nuevo donde almacenar los datos de una visita
	 * @param p Nombre de usuario
	 * @param f Fecha de la visita
	 * @param m Motivo
	 * @param d Descripcion
	 * @param e Exploracion
	 * @param diag Diagnostico
	 * @param t Tratamiento
	 */
	public InfoVisita (String p, Date f, String m, String d, String e, String diag, String t) {
		setUsuario(p);
		setFecha(f);
		setMotivo(m);
		setDescripcion(d);
		setExploracion(e);
		setDiagnostico(diag);
		setTratamiento(t);
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setExploracion(String exploracion) {
		this.exploracion = exploracion;
	}

	public String getExploracion() {
		return exploracion;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getTratamiento() {
		return tratamiento;
	}
	
	
}