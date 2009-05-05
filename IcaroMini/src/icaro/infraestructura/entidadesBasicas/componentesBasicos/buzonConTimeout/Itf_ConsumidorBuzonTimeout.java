/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;



/**
 *  Operaciones que necesita el consumidor de este buz�n
 *
 *@author     Jorge Gonz�lez
 *@created    11 de septiembre de 2001
 */
public interface Itf_ConsumidorBuzonTimeout {

	/**
	 *  Consumir un objeto del buz�n, manteni�ndose el consumidor bloqueado no m�s
	 *  de 'tiempoEnMilisegundos' milisegundos
	 *
	 *@param  tiempoEnMilisegundos          Milis que se esperar� como m�ximo
	 *@return                               El Objeto que se ha sacado
	 *@exception  ExcepcionTimeOutSuperado  Generada si pasa el tiempo especificado y no se ha extra�do ningun objeto
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionTimeOutSuperado;


	/**
	 *  Consume un objeto del buz�n. Espera hasta que haya un objeto disponible.
	 *
	 *@return    Objeto que se ha extra�do del buz�n
	 */
	public Object consumeBloqueante();
}
