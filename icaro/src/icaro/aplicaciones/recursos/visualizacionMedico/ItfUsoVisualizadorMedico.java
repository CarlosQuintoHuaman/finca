package icaro.aplicaciones.recursos.visualizacionMedico;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorMedico extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorMedico(String nombreAgente,String tipo) throws Exception;
	public void mostrarTabMed(Composite c) throws Exception;
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) throws Exception;

    public void cerrarVisualizadorMedico() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}