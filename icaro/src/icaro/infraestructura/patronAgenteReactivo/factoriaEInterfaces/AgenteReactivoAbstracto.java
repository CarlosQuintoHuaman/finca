package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;


import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ControlAbstracto;


/**
 *  
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class AgenteReactivoAbstracto implements ItfGestionAgenteReactivo,ItfUsoAgenteReactivo {

	
	public abstract void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog);
	
	public abstract void setDebug (boolean d);
	public abstract boolean getDebug();
	public abstract AgenteReactivoAbstracto getPatron();
	public abstract int getEstado();
	public abstract void setEstado(int e);
	public abstract ControlAbstracto getControl();
	public abstract void setParametrosAgReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente);
}