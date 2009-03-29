package icaro.aplicaciones.recursos.persistenciaHistorial;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaHistorial extends ItfUsoRecursoSimple {
	public ArrayList<InfoVisita> getHistorial(String paciente);
	public ArrayList<InfoPrueba> getPruebas(String paciente);
	
	public void setVisita(InfoVisita v);
	public void setPrueba(InfoPrueba p);
	
	public void borrarPrueba(InfoPrueba p);
}