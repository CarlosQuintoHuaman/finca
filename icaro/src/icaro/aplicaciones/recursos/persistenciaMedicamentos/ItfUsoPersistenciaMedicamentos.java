package icaro.aplicaciones.recursos.persistenciaMedicamentos;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaMedicamentos extends ItfUsoRecursoSimple {
	public ArrayList<InfoMedicamento> getMedicamentos() throws Exception;
	
	public void insertaMedicamento(InfoMedicamento m) throws Exception;
	public void borrarMedicamento(InfoMedicamento m) throws Exception;
}