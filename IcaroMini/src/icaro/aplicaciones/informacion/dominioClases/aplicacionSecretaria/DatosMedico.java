package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.sql.Time;
import java.util.ArrayList;

public class DatosMedico {
private String nombre;
private String usuario;
private int intervalo;
private ArrayList <DatosCitaSinValidar> datos;
//Pendiente de añadir
private ArrayList<DatosLlamada> extras;
private ArrayList<DatosLlamada> llamadas;
private HorasCita mañana;
private HorasCita tarde;


public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}
public ArrayList<DatosLlamada> getExtras() {
	return extras;
}
public void setExtras(ArrayList<DatosLlamada> extras) {
	this.extras = extras;
}
public ArrayList<DatosLlamada> getLlamadas() {
	return llamadas;
}
public void setLlamadas(ArrayList<DatosLlamada> llamadas) {
	this.llamadas = llamadas;
}
public DatosMedico(String nombre, int intervalo) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = new ArrayList<DatosCitaSinValidar>();
	this.extras = new ArrayList<DatosLlamada>();
	this.llamadas = new ArrayList<DatosLlamada>();
}
public DatosMedico(String nombre){
	this.datos=new ArrayList<DatosCitaSinValidar>();
	this.nombre=nombre;
}

public DatosMedico(String nombre, String usuario){
	this.datos=new ArrayList<DatosCitaSinValidar>();
	this.nombre=nombre;
	this.usuario=usuario;
}

public DatosMedico(String nombre, String usuario, Time Iman, Time Itar, Time Fman, Time Ftar, int intervalo){
	this.datos=new ArrayList<DatosCitaSinValidar>();
	this.nombre=nombre;
	this.usuario=usuario;
	mañana=new HorasCita(Iman,Fman);
	tarde=new HorasCita(Itar,Ftar);
	this.intervalo=intervalo;
}
/**
 * Crea un objeto nuevo donde almacenar los datos de las citas de un de la agenda
 * @param nombre		:: nombre medico
 * @param intervalo 	:: intervalo interconsulta
 * @param datos			::Lista de citas de ese medico
 */
public DatosMedico(String nombre, int intervalo, ArrayList <DatosCitaSinValidar> datos) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = datos;
}

public DatosMedico(String nombre, int intervalo, ArrayList <DatosCitaSinValidar> datos, ArrayList<DatosLlamada> llamadas, ArrayList<DatosLlamada> extras) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = datos;
	this.llamadas=llamadas;
	this.extras=extras;
}

public DatosMedico(String nombre, int intervalo, ArrayList <DatosCitaSinValidar> datos, ArrayList<DatosLlamada> llamadas, ArrayList<DatosLlamada> extras, String usuario) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = datos;
	this.llamadas=llamadas;
	this.extras=extras;
	this.usuario=usuario;
}

public DatosMedico(String nombre, int intervalo, ArrayList <DatosCitaSinValidar> datos, ArrayList<DatosLlamada> llamadas, ArrayList<DatosLlamada> extras, 
				String usuario,Time iniMan, Time iniTar, Time FinMan, Time FinTar) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = datos;
	this.llamadas=llamadas;
	this.extras=extras;
	this.usuario=usuario;
	mañana=new HorasCita(iniMan,FinMan);
	tarde=new HorasCita(iniTar,FinTar);
}
public ArrayList<DatosCitaSinValidar> getDatos() {
	return datos;
}

public void setDatos(ArrayList<DatosCitaSinValidar> datos) {
	this.datos = datos;
}

public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public int getIntervalo() {
	return intervalo;
}
public void setIntervalo(int intervalo) {
	this.intervalo = intervalo;
}
public HorasCita getMañana() {
	return mañana;
}
public void setMañana(HorasCita mañana) {
	this.mañana = mañana;
}
public HorasCita getTarde() {
	return tarde;
}
public void setTarde(HorasCita tarde) {
	this.tarde = tarde;
}

}
