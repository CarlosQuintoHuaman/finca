package icaro.aplicaciones.recursos.persistenciaMedicamentos;

import java.sql.Timestamp;
import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaMedicamentos extends ItfUsoRecursoSimple {
	public ArrayList<InfoMedicamento> getMedicamentos() throws Exception;
	public ArrayList<InfoMedicamento> getMedicamentos(String p, Timestamp f) throws Exception;
	
	public void insertaMedicamento(InfoMedicamento m) throws Exception;
	public void asignaMedicamento(String p, InfoMedicamento m, Timestamp f) throws Exception;
	public void borrarMedicamento(InfoMedicamento m) throws Exception;
}