package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.util.ArrayList;

public class DatosMedico {
private String nombre;
private int intervalo;
private ArrayList <DatosCitaSinValidar> datos;

public DatosMedico(String nombre, int intervalo) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = new ArrayList<DatosCitaSinValidar>();
}
public DatosMedico(String nombre){
	this.datos=new ArrayList<DatosCitaSinValidar>();
	this.nombre=nombre;
}

public DatosMedico(String nombre, int intervalo, ArrayList <DatosCitaSinValidar> datos) {
	this.nombre = nombre;
	this.intervalo = intervalo;
	this.datos = datos;
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

}
