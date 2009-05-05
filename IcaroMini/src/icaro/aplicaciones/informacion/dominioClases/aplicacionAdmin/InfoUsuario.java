package icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin;

import java.io.Serializable;

public class InfoUsuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String usuario;
	private String password;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String telefono;

	/**
	 * Crea un objetivo nuevo donde almacenar los datos de un usuario
	 * @param u Nombre de Usuario
	 * @param p Password
	 * @param n Nombre del paciente
	 * @param ape1 Apellido1
	 * @param ape2 Apellido2
	 * @param dir Direccion
	 * @param tel Telefono
	 */
	public InfoUsuario (String u, String p, String n, String ape1, String ape2, String dir, String tel) {
		setUsuario(u);
		setPassword(p);
		setNombre(n);
		setApellido1(ape1);
		setApellido2(ape2);
		setDireccion(dir);
		setTelefono(tel);
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono() {
		return telefono;
	}	
	
}