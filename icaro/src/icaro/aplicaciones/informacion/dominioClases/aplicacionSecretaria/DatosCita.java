package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosCita {
	private String nombre;
	private String telf;
	private String medico;
	private String fecha;
	private String hora;
	
	/**
	 * Crea un objeto nuevo donde almacenar los datos de una cita de la agenda con medico
	 * @param nombre
	 * @param telf
	 * @param medico
	 * @param fecha
	 * @param hora
	 */
	public DatosCita(String nombre, String telf, String medico, String fecha,
			String hora) {
		this.nombre = nombre;
		this.telf = telf;
		this.medico = medico;
		this.fecha = fecha;
		this.hora = hora;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelf() {
		return telf;
	}
	public void setTelf(String telf) {
		this.telf = telf;
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = medico;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}	
}
