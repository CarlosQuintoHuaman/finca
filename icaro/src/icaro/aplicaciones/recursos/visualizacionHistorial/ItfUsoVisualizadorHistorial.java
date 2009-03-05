package icaro.aplicaciones.recursos.visualizacionHistorial;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorHistorial extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorHistorial(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorHistorial() throws Exception;
    public void mostrarDatosHistorial(ArrayList<InfoVisita> d) throws Exception;
    
    public void mostrarVisualizadorLista(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorLista() throws Exception;
    public void mostrarDatosLista(ArrayList<InfoVisita> d) throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
  	public boolean mostrarMensajePregunta(String titulo,String mensaje) throws Exception;
}