package icaro.aplicaciones.recursos.persistenciaHistorial;

import java.sql.Timestamp;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaHistorial extends ItfUsoRecursoSimple {
	public ArrayList<InfoVisita> getHistorial(String paciente);
	public InfoVisita getHistorial(String paciente, Timestamp fecha);
	public ArrayList<InfoPrueba> getPruebas(String paciente, Timestamp fecha);
	
	public void setVisita(InfoVisita v);
	public void setPrueba(InfoPrueba p);
	
	public void borrarPrueba(InfoPrueba p);
	public void borrarMedicamento(InfoMedicamento p) throws Exception;
}