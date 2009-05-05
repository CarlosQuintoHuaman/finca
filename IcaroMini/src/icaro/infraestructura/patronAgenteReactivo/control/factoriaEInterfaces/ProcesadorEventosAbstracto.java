package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class ProcesadorEventosAbstracto extends java.lang.Thread implements ItfGestionControlAgteReactivo {

	public ProcesadorEventosAbstracto(String string) {
		super(string);
	}

}