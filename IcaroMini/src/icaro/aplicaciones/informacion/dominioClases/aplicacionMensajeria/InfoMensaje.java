package icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria;

import java.io.Serializable;
import java.sql.Timestamp;

public class InfoMensaje implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String remitente;
	private String destinatario;
	private Timestamp fecha;
	private String asunto;
	private String contenido;
		
	
	public InfoMensaje (String remitente, String destinatario, Timestamp fecha, String asunto, String contenido) {
		setRemitente(remitente);
		setDestinatario(destinatario);
		setFecha(fecha);
		setAsunto(asunto);
		setContenido(contenido);
	}


	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}


	public String getRemitente() {
		return remitente;
	}


	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}


	public String getDestinatario() {
		return destinatario;
	}


	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}


	public Timestamp getFecha() {
		return fecha;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	public String getContenido() {
		return contenido;
	}
	
	
}