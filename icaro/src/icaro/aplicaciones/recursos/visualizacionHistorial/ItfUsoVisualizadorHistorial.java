package icaro.aplicaciones.recursos.visualizacionHistorial;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorHistorial extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorHistorial(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorHistorial() throws Exception;
    public void mostrarDatosHistorial(InfoVisita d) throws Exception;
    
    public void mostrarVisualizadorLista(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorLista() throws Exception;
    public void mostrarDatosLista(ArrayList<InfoVisita> d) throws Exception;
    
    public void mostrarVisualizadorPrueba(String nombreAgente,String tipo, String paciente) throws Exception;
    public void cerrarVisualizadorPrueba() throws Exception;
	public void mostrarDatosPrueba(ArrayList<InfoPrueba> p);
	
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m);
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
  	public boolean mostrarMensajePregunta(String titulo,String mensaje) throws Exception;
}