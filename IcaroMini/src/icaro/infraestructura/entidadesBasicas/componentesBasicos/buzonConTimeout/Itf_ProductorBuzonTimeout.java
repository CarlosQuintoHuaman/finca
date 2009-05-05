/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;

/**
 *  Métodos que necesita un productor de un buzón
 *
 *@author     Jorge González
 *@created    2 de octubre de 2001
 */
public interface Itf_ProductorBuzonTimeout {

	/**
	 *  Añade un objeto al buzón, el objeto se encola al final de los ya existentes
	 *
	 *@param  evento  Objeto que añadimos
	 */
	public void produce(Object evento);


	/**
	 *  Añade un objeto al buzón, el objeto será consumido inmediatamente, se coloca como el primero a consumir
	 *
	 *@param  evento  Objeto que añadimos
	 */
	public void produceParaConsumirInmediatamente(Object evento);
}
