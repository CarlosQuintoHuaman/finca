package icaro.aplicaciones.recursos.visualizacionSecretaria;


import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorSecretaria extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorSecretaria(String nombreAgente,String tipo) throws Exception;

    public void cerrarVisualizadorSecretaria() throws Exception;
    
    public void mostrarVisualizadorCita(String nombreAgente,String tipo, DatosCitaSinValidar datos) throws Exception;
    public void mostrarVisualizadorCita(String nombreAgente,String tipo) throws Exception;
    public void mostrarVisualizadorLlamada(String nombreAgente,String tipo) throws Exception;
    public void mostrarVisualizadorLlamada(String nombreAgente,String tipo, DatosLlamada datos) throws Exception;
    public void mostrarVisualizadorExtra(String nombreAgente,String tipo) throws Exception;
    public void mostrarVisualizadorExtra(String nombreAgente,String tipo, DatosLlamada datos) throws Exception;
    public void comprobarInfoCita(String nombreAgente,String tipo, DatosCitaSinValidar datos) throws Exception;
    public void insertaLlamada(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    public void borrarLlamada(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    public void insertaExtra (String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    public void borrarExtra(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    public void cerrarVisualizadorCita() throws Exception;
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}