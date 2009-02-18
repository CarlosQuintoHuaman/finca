package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class ControlAbstracto extends java.lang.Thread implements ItfGestionControlGestor,ItfUsoControlGestor {

	public ControlAbstracto(String string) {
		super(string);
	}

}