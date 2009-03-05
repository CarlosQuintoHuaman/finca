package icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial;

import java.io.Serializable;
import java.util.Date;

public class InfoVisita implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private Date fecha;
	private String motivo;
	private String descripcion;
	private String exploracion;
	private String diagnostico;

	public InfoVisita (String p, Date f, String m, String d, String e, String diag) {
		setUsuario(p);
		setFecha(f);
		setMotivo(m);
		setDescripcion(d);
		setExploracion(e);
		setDiagnostico(diag);
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
	
	
}