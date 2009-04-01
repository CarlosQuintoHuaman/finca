package icaro.aplicaciones.informacion.dominioClases.aplicacionMedico;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class InfoCita implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String usuario;
	private Date fecha;
	private Time hora;

	public InfoCita (String p, Date f, Time t) {
		setUsuario(p);
		setFecha(f);
		setHora(t);
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

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Time getHora() {
		return hora;
	}
	
	
}