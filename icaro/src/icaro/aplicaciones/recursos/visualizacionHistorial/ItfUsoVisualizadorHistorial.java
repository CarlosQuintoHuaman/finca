package icaro.aplicaciones.recursos.visualizacionHistorial;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */

public interface ItfUsoVisualizadorHistorial extends ItfUsoRecursoSimple{

	/**
	 * Muestra la ventana de una visita del historial
	 * @param nombreAgente
	 * @param tipo
	 * @throws Exception
	 */
	public void mostrarVisualizadorHistorial(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorHistorial() throws Exception;
    /**
     * Rellena la ventana con los datos a mostrar
     * @param d Datos
     * @throws Exception
     */
    public void mostrarDatosHistorial(InfoVisita d) throws Exception;
    
    /**
     * Muestra la ventana con la lista de visitas
     * @param nombreAgente
     * @param tipo
     * @throws Exception
     */
    public void mostrarVisualizadorLista(String nombreAgente,String tipo) throws Exception;
    public void cerrarVisualizadorLista() throws Exception;
    /**
     * Rellena la ventana con los datos a mostrar
     * @param d
     * @throws Exception
     */
    public void mostrarDatosLista(ArrayList<InfoVisita> d) throws Exception;
    
    /**
     * Muestra la ventana de añadir pruebas
     * @param nombreAgente
     * @param tipo
     * @param v
     * @throws Exception
     */
    public void mostrarVisualizadorPrueba(String nombreAgente,String tipo, InfoVisita v) throws Exception;
    public void cerrarVisualizadorPrueba() throws Exception;
    /**
     * Rellena una ventana con los datos de las pruebas
     * @param p
     */
	public void mostrarDatosPrueba(ArrayList<InfoPrueba> p);
	
	/**
	 * Rellena una ventana con los datos de los medicamentos
	 * @param m
	 */
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m);
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
  	public boolean mostrarMensajePregunta(String titulo,String mensaje) throws Exception;
}