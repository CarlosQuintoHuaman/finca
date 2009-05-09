package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosCita {
	private String usuario;
	private String nombre;
	private String telf;
	private String medico;
	private String fecha;
	private String hora;
	private Boolean crear;

	

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
	public DatosCita(String nombre, String telf, Boolean crear, String usu){
		this.nombre = nombre;
		this.telf = telf;
		this.usuario = usu;
		this.crear=crear;
	}
	public DatosCita(String nombre, String telf, String fecha,
			String hora, Boolean crear) {
		this.nombre = nombre;
		this.telf = telf;
		this.fecha = fecha;
		this.hora = hora;
		this.crear = crear;
	}
	
	public DatosCita(String nombre, String telf, String hora,
			String fecha){
		this.nombre = nombre;
		this.telf = telf;
		this.fecha = fecha;
		this.hora = hora;
	}
	public DatosCita(String nombre, String telf, Boolean crear){
		this.nombre = nombre;
		this.telf = telf;
		this.crear = crear;
	}
	public String getNombre() {
		return nombre;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String nombreUsuario) {
		this.usuario = nombreUsuario;
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
	public Boolean getCrear() {
		return crear;
	}
	public void setCrear(Boolean crear) {
		this.crear = crear;
	}

	
}
