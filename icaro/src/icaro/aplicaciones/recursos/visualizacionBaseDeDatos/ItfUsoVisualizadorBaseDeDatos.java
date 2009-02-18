package icaro.aplicaciones.recursos.visualizacionBaseDeDatos;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;


public interface ItfUsoVisualizadorBaseDeDatos extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorBaseDeDatos(String nombreAgente,String tipo) throws Exception;

    public void cerrarVisualizadorBaseDeDatos() throws Exception;
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}