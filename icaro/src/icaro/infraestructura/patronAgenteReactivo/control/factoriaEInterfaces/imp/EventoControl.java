/*
 *  Copyright 2001 Telefónica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;


/**
 * Clase de los eventos de control *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class EventoControl {
	/**
	 *  Tipo de evento de control
	 */
	public int operacion;
	/**
	 *  Ha ocurrido un evento de timeout de alguna clase
	 */
	public final static int OPERACION_TIMEOUT = 0;
	/**
	 *  Se ha ordenado terminar
	 */
	public final static int OPERACION_TERMINAR = 1;


	/**
	 *  Constructor
	 *
	 *@param  op  Operación que simboliza este evento
	 */
	public EventoControl(int op)
	{
		operacion = op;
	}
}
