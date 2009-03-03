package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosLlamada {
	private String nombre;
	private String mensaje;
	private String telefono;
	private boolean paciente;
	private String hora;
	
	public DatosLlamada(String n, String m, String t, boolean p, String i){
		nombre=n;
		mensaje=m;
		telefono=t;
		paciente=p;
		hora =i;
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
