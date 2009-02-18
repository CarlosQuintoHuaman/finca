package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones;




/**
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */

public interface ItfAccionesSemanticas {
	
	public void setAccion(String accion);
	public void setParametros(Object[] parametros);
	public AccionesSemanticasAgenteReactivo getAccionesSemanticas();
	public void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas);
	public void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante);
	public void run();

}
