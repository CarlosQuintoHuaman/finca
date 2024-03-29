package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.io.Serializable;

public class DatosCitaSinValidar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String usuario;
	private String nombre;
	private String medico;
	private String apell1;
	private String apell2;
	private String telf;
	private String fecha;
	private String hora;
	private String seguro;
	private Boolean nuevo;
	private int periodo;
	private int estado;
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2,String telf, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		this.hora= hora;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar (int estado, String nombre, String apell1, String apell2,String telf, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.telf = telf;
		this.hora= hora;
		periodo=1;
		this.estado=estado;
		
	}
	
	public DatosCitaSinValidar(String nombre, String apell1, String apell2,String telf, String hora, boolean n,String usuario) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.hora= hora;
		this.usuario=usuario;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf,String fecha, String hora) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.fecha=fecha;
		this.hora= hora;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf,String fecha, String hora,int n, String usuario) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.fecha=fecha;
		this.hora= hora;
		this.usuario=usuario;
		periodo=1;
		
	}
	
	public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf,String fecha, String hora, String medico) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.fecha=fecha;
		this.hora= hora;
		this.medico=medico;
		periodo=1;
		
	}
	
	/**
	 * Crea un objeto nuevo donde almacenar los datos de una cita de la ventana Cita
	 * @param nombre
	 * @param apell1
	 * @param telf
	 * @param hora
	 * @param periodo
	 */
public DatosCitaSinValidar (String nombre, String apell1,String apell2, String telf, String hora, int periodo) {
		
		this.nombre = nombre;
		this.apell1 = apell1;
		this.apell2 = apell2;
		this.telf = telf;
		this.hora= hora;
		this.periodo=periodo;
	}
	
public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf, String hora, int periodo, String usuario) {
	
	this.nombre = nombre;
	this.apell1 = apell1;
	this.apell2 = apell2;
	this.telf = telf;
	this.hora= hora;
	this.periodo=periodo;
	this.usuario=usuario;
}

public DatosCitaSinValidar (String nombre, String apell1, String apell2, String telf, String hora, int periodo, String usuario,int estado) {
	
	this.nombre = nombre;
	this.apell1 = apell1;
	this.apell2 = apell2;
	this.telf = telf;
	this.hora= hora;
	this.periodo=periodo;
	this.usuario=usuario;
	this.estado=estado;
}
public DatosCitaSinValidar (String nombre, String telf, boolean nuevo, String usuario){
	this.nombre = nombre;
	this.telf = telf;
	this.nuevo=nuevo;
	this.usuario=usuario;
}

public DatosCitaSinValidar (String nombre, String telf, String medico, String fecha, String hora, boolean n) {
	
	this.nombre = nombre;
	this.telf = telf;
	this.hora= hora;
	this.medico=medico;
	this.fecha=fecha;
}

public DatosCitaSinValidar (boolean nuevo) {
	
	this.nuevo=nuevo;
}

public DatosCitaSinValidar (String nombre, String telf, String fecha, String hora, boolean nuevo) {
	
	this.nombre = nombre;
	this.telf = telf;
	this.hora= hora;
	this.fecha=fecha;
	this.nuevo=nuevo;
}

public DatosCitaSinValidar (String nombre, String telf, String hora, String fecha) {
	
	this.nombre = nombre;
	this.telf = telf;
	this.hora= hora;
	this.fecha=fecha;

}

public DatosCitaSinValidar (String nombre, String telf, boolean nuevo) {
	
	this.nombre = nombre;
	this.telf = telf;
	this.nuevo=nuevo;
}


	public Boolean getNuevo() {
	return nuevo;
}

public void setNuevo(Boolean nuevo) {
	this.nuevo = nuevo;
}

	public String getSeguro() {
	return seguro;
}

public void setSeguro(String seguro) {
	this.seguro = seguro;
}

	public String getMedico() {
	return medico;
}

public void setMedico(String medico) {
	this.medico = medico;
}

	public String tomaNombre() {
		return nombre;
	}
	
	public String tomaApell1() {
		return apell1;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String tomaTelf() {
		return telf;
	}
	
	public String getApell2() {
		return apell2;
	}

	public void setApell2(String apell2) {
		this.apell2 = apell2;
	}

	public String tomaHora() {
		return hora;
	}
	
	public int tomaPeriodo() {
		return periodo;
	}
	
	public void setNombre(String n){
		nombre=n;
	}
	
	public void setApell1(String a){
		apell1=a;
	}
	public void setTelf(String t){
		telf=t;
	}
	public void setHora(String h){
		hora=h;
	}
	public void setPeriodo(int p){
		periodo=p;;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	

}

