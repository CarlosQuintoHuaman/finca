package icaro.aplicaciones.recursos.visualizacionMedico;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoVisualizadorMedico extends ItfUsoRecursoSimple{

	/**
	 * 
	 * @param nombreAgente
	 * @param tipo
	 * @throws Exception
	 */
	public void mostrarVisualizadorMedico(String nombreAgente,String tipo, String usuario) throws Exception;
	
	/**
	 * - En fase experimental - Aun no funciona
	 * @param c
	 * @throws Exception
	 */
	public void mostrarTabMed(Composite c) throws Exception;
	
	/**
	 * Se usa para avisar asincronamente de que los datos de medicamentos ya estan
	 * listos para ser mostrados por pantalla
	 * @param m
	 * @throws Exception
	 */
	public void mostrarDatosMed(ArrayList<InfoMedicamento> m) throws Exception;
	
	/**
	 * Se usa para avisar asincronamente de que los datos de los mensajes ya estan
	 * listos para ser mostrados por pantalla
	 * @param m
	 * @throws Exception
	 */
	public void mostrarMensajes(ArrayList<InfoMensaje> m) throws Exception;

	/**
	 * 
	 * @throws Exception
	 */
    public void cerrarVisualizadorMedico() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}