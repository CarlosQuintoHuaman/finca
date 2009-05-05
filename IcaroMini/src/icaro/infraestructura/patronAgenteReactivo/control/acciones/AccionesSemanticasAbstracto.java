package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.GeneracioneInterfaces.ItfAccionesSemanticas;



/**
 *  
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */
public abstract class AccionesSemanticasAbstracto extends java.lang.Thread implements ItfAccionesSemanticas {

	public AccionesSemanticasAbstracto(String string) {
		super(string);
	}

	public AccionesSemanticasAbstracto() {}
        ;
	
}
