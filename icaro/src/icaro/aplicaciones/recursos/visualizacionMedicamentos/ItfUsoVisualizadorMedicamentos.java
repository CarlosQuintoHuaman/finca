package icaro.aplicaciones.recursos.visualizacionMedicamentos;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorMedicamentos extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorBusqueda(String nombreAgente,String tipo, String paciente) throws Exception;
	public void mostrarVisualizadorNuevo(String nombreAgente,String tipo) throws Exception;
	
	public void mostrarDatosMedicamentos(ArrayList<InfoMedicamento> m) throws Exception;

    public void cerrarVisualizadorBusqueda() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}