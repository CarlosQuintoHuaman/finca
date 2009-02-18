/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;



/**
 *  Operaciones que necesita el consumidor de este buzón
 *
 *@author     Jorge González
 *@created    11 de septiembre de 2001
 */
public interface Itf_ConsumidorBuzonTimeout {

	/**
	 *  Consumir un objeto del buzón, manteniéndose el consumidor bloqueado no más
	 *  de 'tiempoEnMilisegundos' milisegundos
	 *
	 *@param  tiempoEnMilisegundos          Milis que se esperará como máximo
	 *@return                               El Objeto que se ha sacado
	 *@exception  ExcepcionTimeOutSuperado  Generada si pasa el tiempo especificado y no se ha extraído ningun objeto
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionTimeOutSuperado;


	/**
	 *  Consume un objeto del buzón. Espera hasta que haya un objeto disponible.
	 *
	 *@return    Objeto que se ha extraído del buzón
	 */
	public Object consumeBloqueante();
}
