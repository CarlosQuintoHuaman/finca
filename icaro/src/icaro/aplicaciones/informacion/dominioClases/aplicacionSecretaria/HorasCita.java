package icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria;

public class HorasCita {

	private String hInicio;
	private String hFin;
	public HorasCita(String inicio, String fin) {
		hInicio = inicio;
		hFin = fin;
	}
	public String getHInicio() {
		return hInicio;
	}
	public void setHInicio(String inicio) {
		hInicio = inicio;
	}
	public String getHFin() {
		return hFin;
	}
	public void setHFin(String fin) {
		hFin = fin;
	}
	
}
