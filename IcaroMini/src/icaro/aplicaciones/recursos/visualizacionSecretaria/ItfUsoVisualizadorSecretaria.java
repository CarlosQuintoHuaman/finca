package icaro.aplicaciones.recursos.visualizacionSecretaria;


import java.util.ArrayList;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     F Garijo
 *@created    20 de noviembre de 2008
 */

public interface ItfUsoVisualizadorSecretaria extends ItfUsoRecursoSimple{
	
	 /**
     * Su proposito es pintar la ventana vacia
     */
	public void mostrarVisualizadorSecretaria(String nombreAgente,String tipo) throws Exception;

    /**
     * Su proposito es cerrar la ventana 
     */
    public void cerrarVisualizadorSecretaria() throws Exception;
    
    /**
	 * Funcion que una vez mostrada la ventana agenda (vacia) nos rellena la ventana con los datos sacados de la persistencia
	 * @param fecha :: fecha de la agenda que se muestra
	 * @param m	:: Lista de citas de todos los medicos a los que les corresponde esta secretaria
	 * @param num  :: Numero de medicos para los que tiene agenda este usuario (secretaria)
	 * @param s		:: Nombre del usuario actual
	 */
    public void meteDatos(String fecha, ArrayList<DatosMedico> m, int num, String s) throws Exception;
	/**
	 * Despues de mostrar la ventana de proximasCitas (sin datos) llamamos a esta funcion desde fuera de panelProximasCitas para
	 * que cargue los datos que se deben mostrar en esta ventana
	 * @param l
	 */
    public void meteDatos(ArrayList<DatosCitaSinValidar> l)throws Exception;
 
	/**
	 * Despues de mostrar la ventana de Citas (sin datos) llamamos a esta funcion desde fuera de panelCita para
	 * que cargue los datos que se deben mostrar en esta ventana
	 * @param l
	 */
    public void meteDatosPacientes(ArrayList<InfoPaciente> l)throws Exception;
    
    /**
	 * Despues de mostrar la ventana de extras (sin datos) llamamos a esta funcion desde fuera de panelExtra para
	 * que cargue los datos que se deben mostrar en esta ventana
	 * @param l
	 */
    public void meteDatosPacientesE(ArrayList<InfoPaciente> l)throws Exception;
    
    /**
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la cita(nombre, apellido, telefono, hora)
     */
    public void mostrarVisualizadorCita(String nombreAgente,String tipo, DatosCitaSinValidar datos) throws Exception;
    /**
     * Su proposito es pintar la ventana vacia
     */
    public void mostrarVisualizadorCita(String nombreAgente,String tipo) throws Exception;
    /**
     * Su proposito es pintar la ventana vacia
     */
    public void mostrarVisualizadorLlamada(String nombreAgente,String tipo) throws Exception;
    /**
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
     */
    public void mostrarVisualizadorLlamada(String nombreAgente,String tipo, DatosLlamada datos) throws Exception;
    /**
     * Su proposito es pintar la ventana vacia
     */
    public void mostrarVisualizadorExtra(String nombreAgente,String tipo) throws Exception;
    /**
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar del extra(nombre, mensaje, telefono, Espaciente, hora)
     */
    public void mostrarVisualizadorExtra(String nombreAgente,String tipo, DatosLlamada datos) throws Exception;
    
    /**
     * Su proposito es pintar la ventana 
     */
    public void mostrarVisualizadorPCitas(String nombreAgente, String tipo) throws Exception;
    
    /**
     * Su proposito es Almacenar los datos que se le pasan por parametro en la agenda en el lugar que indique datos.hora
     * @param datos 	:: Datos a insertar en la agenda (nombre, apellido1, telefono,fecha, hora, periodo)
     */
    public boolean comprobarInfoCita(String nombreAgente,String tipo, DatosCitaSinValidar datos) throws Exception;
    /**
     * Su proposito es borrar la entrada de la en tabla de citas de la agenda que se corresponda con la que se pasa por parametro.
     * @param datos	:: Datos a borrar en la agenda
     */
    public void borrarCita(String nombreAgente,String tipo, DatosCitaSinValidar datos)throws Exception;
    /**
    * Su proposito es Almacenar los datos que se le pasan por parametro en la tabla de llamadas de la agenda.
    * @param datos 	:: Datos a insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
     */
    public void insertaLlamada(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    
    /** 
     * @param datAnt	:: Datos a modificar en la agenda (nombre,mensaje, telefono, paciente, hora)
     * @param datPost	:: Datos a modificados que se deben insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
    */
    public void modificaLlamada(String nombreAgente,String tipo, DatosLlamada datAnt, DatosLlamada datPost)throws Exception;
    /**
     * Su proposito es borrar la entrada de la en tabla de llamadas de la agenda que se corresponda con la que se pasa por parametro.
     * @param datos	:: Datos a borrar en la agenda (nombre,mensaje, telefono, paciente, hora)
     */
    public void borrarLlamada(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    
    /**
     * Su proposito es Almacenar los datos que se le pasan por parametro en la tabla de extras de la agenda.
     * @param datos 	:: Datos a insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
    public void insertaExtra (String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    /**
     * Su proposito es borrar la entrada de la en tabla de extras de la agenda que se corresponda con la que se pasa por parametro.
     * @param datos	:: Datos a borrar en la agenda (nombre,mensaje, telefono, paciente, hora)
     */
    public void modificaExtra(String nombreAgente,String tipo, DatosLlamada datAnt, DatosLlamada datPost)throws Exception;
    /**
     * Su proposito es Almacenar los datos que se le pasan por parametro en la tabla de extras de la agenda.
     * @param datos 	:: Datos a insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
    public void borrarExtra(String nombreAgente,String tipo, DatosLlamada datos)throws Exception;
    /**
     * Su proposito es cerrar la ventana 
     */
    public void cerrarVisualizadorCita() throws Exception;
    /**
     * Su proposito es cerrar la ventana 
     */
    public void cerrarVisualizadorLlamada() throws Exception;
    /**
     * Su proposito es cerrar la ventana 
     */
    public void cerrarVisualizadorExtra() throws Exception;
    /**
     * Su proposito es cerrar la ventana 
     */
    public void cerrarVisualizadorProximasCita() throws Exception;
  	
    //Funcion en pruebas relacionada con la accion del evento del boton '+' de panelCita
    public boolean estaLibre(HorasCita hora)throws Exception;
    
    // Las funciones que siguen nos sirven para mostrar mensajes de error / aviso o informacion
    public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}