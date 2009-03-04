package icaro.aplicaciones.informacion.dominioClases.aplicacionMedico;

import java.io.Serializable;
import java.util.Date;

public class InfoCita implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String usuario;
	private Date fecha;

	public InfoCita (String p, Date f) {
		setUsuario(p);
		setFecha(f);
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
	
	
}