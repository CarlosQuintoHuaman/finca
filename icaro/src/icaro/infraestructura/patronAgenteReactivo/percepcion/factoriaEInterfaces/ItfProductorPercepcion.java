/*
 *  Copyright 2001 Telefónica I+D
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
	 *  Añade un nuevo evento en la percepción
	 *
	 *@param  evento  Evento que vamos a añadir
	 */
	public void produce(Object evento);


	/**
	 *  Añade un nuevo evento de forma prioritaria en la percepción
	 *
	 *@param  evento  Evento que se consumirá el primero
	 */
	public void produceParaConsumirInmediatamente(Object evento);

}
