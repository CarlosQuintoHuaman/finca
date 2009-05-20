package icaro.aplicaciones.informacion.dominioClases.aplicacionFicha;

import icaro.util.util;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class DatosFicha implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String usuario;
	private String apellidos;
	private String NIF;
	private Date fNacimiento;
	private int edad;
	private String direccion;
	private String CP;
	private String provincia;
	private String localidad;
	private String telf1;
	private String telf2;
	private String mail;
	private String profesion;
	private String aseguradora;
	private String otros;
	private String PestOtros;
	private boolean esta;
	
	public DatosFicha (String nombre, String apell1, String NIF, Date fNacimiento, int edad, String direccion, String CP, String prov, 
						String loc, String telf1, String telf2, String mail, String prof, String seguro, String otros, String pOtros) {
		
		this.nombre = nombre;
		this.apellidos = apell1;
		this.NIF=NIF;
		this.fNacimiento=fNacimiento;
		this.edad=edad;
		this.direccion=direccion;
		this.CP=CP;
		this.provincia=prov;
		this.localidad=loc;
		this.telf1=telf1;
		this.telf2=telf2;
		this.mail=mail;
		this.profesion=prof;
		this.aseguradora=seguro;
		this.otros=otros;
		this.PestOtros=pOtros;
	}
	
	
	public DatosFicha (String nombre, String usuario, String apell1, String NIF, Date fNacimiento, int edad, String direccion, String CP, String prov, 
			String loc, String telf1, String telf2, String mail, String prof, String seguro, String otros, String pOtros) {

this.nombre = nombre;
this.usuario= usuario;
this.apellidos = apell1;
this.NIF=NIF;
this.fNacimiento=fNacimiento;
this.edad=edad;
this.direccion=direccion;
this.CP=CP;
this.provincia=prov;
this.localidad=loc;
this.telf1=telf1;
this.telf2=telf2;
this.mail=mail;
this.profesion=prof;
this.aseguradora=seguro;
this.otros=otros;
this.PestOtros=pOtros;
}
	
	public DatosFicha (String nombre, String apell1, String NIF, Date fNacimiento, int edad, String direccion, String CP, String prov, 
			String loc, String telf1, String telf2, String mail, String prof, String seguro, String otros, String pOtros, boolean esta) {

		this.nombre = nombre;
		this.apellidos = apell1;
		this.NIF=NIF;
		this.fNacimiento=fNacimiento;
		this.edad=edad;
		this.direccion=direccion;
		this.CP=CP;
		this.provincia=prov;
		this.localidad=loc;
		this.telf1=telf1;
		this.telf2=telf2;
		this.mail=mail;
		this.profesion=prof;
		this.aseguradora=seguro;
		this.otros=otros;
		this.PestOtros=pOtros;
		this.esta=esta;
	}
	public DatosFicha () throws ParseException{
		util d=new util();
		String f=d.getStrDateSQL2();
		this.fNacimiento=d.StrToDate(f);
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isEsta() {
		return esta;
	}

	public void setEsta(boolean esta) {
		this.esta = esta;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nif) {
		NIF = nif;
	}

	public Date getFNacimiento() {
		return fNacimiento;
	}

	public void setFNacimiento(Date nacimiento) {
		fNacimiento = nacimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCP() {
		return CP;
	}

	public void setCP(String cp) {
		CP = cp;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getTelf1() {
		return telf1;
	}

	public void setTelf1(String telf1) {
		this.telf1 = telf1;
	}

	public String getTelf2() {
		return telf2;
	}

	public void setTelf2(String telf2) {
		this.telf2 = telf2;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(String aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getPestOtros() {
		return PestOtros;
	}

	public void setPestOtros(String pestOtros) {
		PestOtros = pestOtros;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	

	
	
}