package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.util.ArrayList;

public class DatosSecretaria {

private ArrayList<DatosMedico> medicos;
private int numM;
// Fecha de la agenda a los que se refieren todos los datos almacenados en este tipo
private String fecha;

public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
/**
 * Crea un objeto nuevo donde almacenar los datos de la agenda de una determinada secretaria
 * @param medicos		:: Lista de medicos a los que esta asociada la secretaria con sus citas
 * @param numM			:: Numero de medicos a los que esta asociada.
 */
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
