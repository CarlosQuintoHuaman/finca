/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;




/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfConsumidorPercepcion {
	/**
	 *  Consume un objeto, pero no se bloquea más de un tiempo especificado por
	 *  parámetro en caso de consumir ese tiempo se produce una excepción
	 *
	 *@param  tiempoEnMilisegundos               Tiempo que se esperará como máximo
	 *@return                                    Objeto que se ha consumido
	 *@exception  ExcepcionSuperadoTiempoLimite  No se consiguio consumir en el tiempo especificado
	 *@todo                                      producen
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionSuperadoTiempoLimite;


	/**
	 *  Consume un objeto, se queda esperando hasta que haya un objeto que consumir
	 *
	 *@return    Objeto que se ha consumido
	 */
	public Object consumeBloqueante();
}
