package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosLlamada {
	private String nombre;
	private String mensaje;
	private String telefono;
	private boolean paciente;
	private String hora;
	private String usuario;
	private String medico;
	private String tipo;
	private String fecha;
	
	/**
	  * Crea un objeto nuevo donde almacenar los datos de una Llamada de la agenda
	 * @param n
	 * @param m
	 * @param t
	 * @param p
	 * @param i
	 */
	public DatosLlamada(String n, String m, String t, boolean p, String i){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
		hora =i;
	}
	
	public DatosLlamada(String n, String m, String t, boolean p, String i, String me){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
		hora =i;
		medico=me;
	}
	
	public DatosLlamada(String n, String m, String t, boolean p, String i, String me, String fecha){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
		hora =i;
		medico=me;
		this.fecha=fecha;
	}
	
	public DatosLlamada(String n, String m, String t, boolean p, String i, String me, String fecha,String usuario){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
		hora =i;
		medico=me;
		this.fecha=fecha;
		this.usuario=usuario;
	}
	
	public DatosLlamada(String m,String fecha){
		medico=m;
		this.fecha=fecha;

	}
	
	public DatosLlamada(String n, String m, String t, boolean p){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
	}
	public DatosLlamada(String n, String m, String t){
		nombre=n;
		mensaje=m;
		telefono=t;
	}
	
	public DatosLlamada(String n, String m, String t, String f){
		nombre=n;
		mensaje=m;
		telefono=t;
		hora=f;
	}
	
	public DatosLlamada(String n, String m, String t, String f,String usuario, String fecha){
		nombre=n;
		mensaje=m;
		telefono=t;
		hora=f;
		this.usuario=usuario;
		this.fecha=fecha;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public String getNombre(){
		return  nombre;
	}
	public String getMensaje(){
		return  mensaje;
	}
	public String getTelf(){
		return  telefono;
	}
	public boolean getPaciente(){
		return  paciente;
	}
	
	public String getHora(){
		return  hora;
	}
	
	public void setNombre(String n){
		nombre=n;
	}
	public void setTelf(String n){
		telefono=n;
	}
	public void setMensaje(String n){
		mensaje=n;
	}
	public void setPaciente(boolean n){
		paciente=n;
	}
	
	public void setHora(String n){
		hora=n;
	}
}
