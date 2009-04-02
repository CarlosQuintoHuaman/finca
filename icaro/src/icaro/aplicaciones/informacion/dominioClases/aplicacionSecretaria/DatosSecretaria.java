package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.util.ArrayList;

public class DatosSecretaria {

private ArrayList<DatosMedico> medicos;
private int numM;
private String fecha;

public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public DatosSecretaria(ArrayList<DatosMedico> medicos, int numM) {
	this.medicos = medicos;
	this.numM = numM;
}
public ArrayList<DatosMedico> getMedicos() {
	return medicos;
}
public void setMedicos(ArrayList<DatosMedico> medicos) {
	this.medicos = medicos;
}
public int getNumM() {
	return numM;
}
public void setNumM(int numM) {
	this.numM = numM;
}

}
