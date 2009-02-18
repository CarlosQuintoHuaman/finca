package icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso;

import java.io.Serializable;

/**
 * @author DavidA
 *
 */
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idPerfil;
	private String nombre;
	
	
	public Perfil() {
		this.idPerfil = 0;
		this.nombre = null;
	}
	
	public Perfil(int idPerfil,String nombre) {
		this.idPerfil = idPerfil;
		this.nombre = nombre;
	}
	
	public Perfil(String nombre) {
		this.idPerfil = 0;
		this.nombre = nombre;
	}
	

	/**
	 * @return Returns the idPerfil.
	 */
	public int getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param idPerfil The idPerfil to set.
	 */
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String toString() {
		return nombre;
	}
}
