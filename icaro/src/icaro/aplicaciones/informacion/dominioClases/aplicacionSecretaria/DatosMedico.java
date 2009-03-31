package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class DatosMedico {
private String nombre;
private int intervalo;
public DatosMedico(String nombre, int intervalo) {
	this.nombre = nombre;
	this.intervalo = intervalo;
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
