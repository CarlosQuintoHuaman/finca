package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

import java.sql.Time;

//Clase pendiente de añadir
public class HorasCita {

	private Time hInicio;
	private Time hFin;
	private String hinicio;
	private String hfin;
	/**
	  * Crea un objeto nuevo donde almacenar los horarios de los medicos
	 * @param inicio
	 * @param fin
	 */
	public HorasCita(Time inicio, Time fin) {
		hInicio = inicio;
		hFin = fin;
	}
	
	public HorasCita(String inicio, String fin) {
		hinicio = inicio;
		hfin = fin;
	}
	public Time getHInicio() {
		return hInicio;
	}
	public void setHInicio(Time inicio) {
		hInicio = inicio;
	}
	public Time getHFin() {
		return hFin;
	}
	public void setHFin(Time fin) {
		hFin = fin;
	}
	public String getHinicio() {
		return hinicio;
	}
	public void setHinicio(String hinicio) {
		this.hinicio = hinicio;
	}
	public String getHfin() {
		return hfin;
	}
	public void setHfin(String hfin) {
		this.hfin = hfin;
	}
	
}
