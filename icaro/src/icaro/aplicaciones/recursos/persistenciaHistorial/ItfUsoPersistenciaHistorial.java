package icaro.aplicaciones.recursos.persistenciaHistorial;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaHistorial extends ItfUsoRecursoSimple {
	public ArrayList<InfoVisita> getHistorial(String paciente);
	public void setVisita(InfoVisita v);
	
}