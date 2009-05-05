/*
 *  Copyright 2001 Telef�nica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfProductorPercepcion {
	/**
	 *  A�ade un nuevo evento en la percepci�n
	 *
	 *@param  evento  Evento que vamos a a�adir
	 */
	public void produce(Object evento);


	/**
	 *  A�ade un nuevo evento de forma prioritaria en la percepci�n
	 *
	 *@param  evento  Evento que se consumir� el primero
	 */
	public void produceParaConsumirInmediatamente(Object evento);

}
