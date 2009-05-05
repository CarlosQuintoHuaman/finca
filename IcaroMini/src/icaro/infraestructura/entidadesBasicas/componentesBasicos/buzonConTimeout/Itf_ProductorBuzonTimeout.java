/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;

/**
 *  M�todos que necesita un productor de un buz�n
 *
 *@author     Jorge Gonz�lez
 *@created    2 de octubre de 2001
 */
public interface Itf_ProductorBuzonTimeout {

	/**
	 *  A�ade un objeto al buz�n, el objeto se encola al final de los ya existentes
	 *
	 *@param  evento  Objeto que a�adimos
	 */
	public void produce(Object evento);


	/**
	 *  A�ade un objeto al buz�n, el objeto ser� consumido inmediatamente, se coloca como el primero a consumir
	 *
	 *@param  evento  Objeto que a�adimos
	 */
	public void produceParaConsumirInmediatamente(Object evento);
}
