package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;


import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfGestionControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
/**
 * @author      Felipe Polo
 * @created     30 de noviembre de 2007
 */

public abstract class AgenteReactivoAbstracto implements ItfGestionAgenteReactivo,ItfUsoAgenteReactivo {

	
	public abstract void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog);
	
	public abstract void setDebug (boolean d);
	public abstract boolean getDebug();
	/**
	 * @uml.property  name="patron"
	 * @uml.associationEnd  readOnly="true"
	 */
	public abstract AgenteReactivoAbstracto getPatron();
	public abstract int getEstado();
	public abstract void setEstado(int e);
	/**
	 * @uml.property  name="control"
	 * @uml.associationEnd  readOnly="true"
	 */
//	public abstract ControlReactivoImp getControl();
	public abstract AgenteReactivoAbstracto AgenteReactivoImplementacion(ItfGestionControlAgteReactivo itfGestionControlAgteReactivo, ItfProductorPercepcion itfProductorPercepcion,ItfConsumidorPercepcion itfConsumidorPercepcion) ;
}