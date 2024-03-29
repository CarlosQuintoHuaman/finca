package icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial;

import java.io.Serializable;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class InfoHistorial implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String motivo;
	private String descripcion;
	private String exploracion;
	private String diagnostico;
	private String tratamiento;
		
	/**
	 * Crea un objetivo nuevo donde almacenar los datos de una visita para el historial de un paciente
	 * @param motivo
	 * @param descripcion
	 * @param exploracion
	 * @param diagnostico
	 * @param tratamiento
	 */
	public InfoHistorial (String motivo, String descripcion, String exploracion, String diagnostico, String tratamiento) {
		this.motivo = motivo;
		this.descripcion = descripcion;
		this.exploracion = exploracion;
		this.diagnostico = diagnostico;
		this.tratamiento = tratamiento;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getExploracion() {
		return exploracion;
	}
	
	public String getDiagnostico() {
		return diagnostico;
	}
	
	public String getTratamiento() {
		return tratamiento;
	}	
	
	
}