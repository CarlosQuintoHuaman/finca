package icaro.aplicaciones.recursos.visualizacionMedicamentos;


import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public interface ItfUsoVisualizadorMedicamentos extends ItfUsoRecursoSimple{

	/**
	 * - En fase de pruebas - Aun no funciona
	 * @param nombreAgente
	 * @param tipo
	 * @param c
	 * @param estilo
	 */
	public void mostrarTabMed(String nombreAgente, String tipo, Composite c, int estilo);
	
	/**
	 * Busqueda de medicamentos
	 * @param nombreAgente
	 * @param tipo
	 * @param paciente
	 * @throws Exception
	 */
	public void mostrarVisualizadorBusqueda(String nombreAgente,String tipo, String paciente) throws Exception;
	
	/**
	 * Nuevo medicamento
	 * @param nombreAgente
	 * @param tipo
	 * @throws Exception
	 */
	public void mostrarVisualizadorNuevo(String nombreAgente,String tipo) throws Exception;
	
	/**
	 * Enviar los datos de los medicamentos asincronamente a otro agente
	 * @param m
	 * @throws Exception
	 */
	public void mostrarDatosMedicamentos(ArrayList<InfoMedicamento> m) throws Exception;

    public void cerrarVisualizadorBusqueda() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}