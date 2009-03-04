package icaro.aplicaciones.recursos.persistenciaMedico;

import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaMedico extends ItfUsoRecursoSimple {
	public ArrayList<InfoPaciente> getPacientes();
	public ArrayList<InfoCita> getCitas();
	
	
	public boolean compruebaUsuario(String usuario, String password) throws Exception;
	public boolean compruebaNombreUsuario(String usuario) throws Exception;
	public void insertaUsuario (String usuario,String password) throws Exception;
	
}