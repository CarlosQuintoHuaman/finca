package icaro.aplicaciones.recursos.persistenciaBaseDeDatos;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoPersistenciaBaseDeDatos extends ItfUsoRecursoSimple{

	public boolean abrirConexion(String usuario, String password) throws Exception;
    public boolean cerrarConexion() throws Exception;
    
    // Esta sera la funcion mas general posible. Sirve para enviar una consulta "a saco" metiendo
    // directamente el codigo SQL
    public Object[] enviarConsulta(String consulta);
    
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}
