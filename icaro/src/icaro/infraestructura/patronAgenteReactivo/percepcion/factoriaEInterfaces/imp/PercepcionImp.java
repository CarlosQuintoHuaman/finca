/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout.Buzon;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout.ExcepcionTimeOutSuperado;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.EventoControl;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ExcepcionSuperadoTiempoLimite;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */
public class PercepcionImp extends PercepcionAbstracto {

	private Buzon buzon = new Buzon();

	private final static boolean DEBUG = false;


	/**
	 *  Consume un objeto, se queda esperando hasta que haya un objeto que consumir
	 *
	 *@return    Objeto que se ha consumido
	 */
	public Object consumeBloqueante()
	{
		Object obj = buzon.consumeBloqueante();
		if ( !(obj instanceof EventoInput)&& !(obj instanceof EventoControl) )
			if (DEBUG)
			{
				System.out.println("ERROR: Se esperaba un objeto de tipo EventoControl o EventoInput");
			}
      return obj;
	}


	/**
	 *  Consume un objeto, pero no se bloquea más de un tiempo especificado por
	 *  parámetro en caso de consumir ese tiempo se produce una excepción
	 *
	 *@param  tiempoEnMilisegundos               Tiempo que se esperará como máximo
	 *@return                                    Objeto que se ha consumido
	 *@exception  ExcepcionSuperadoTiempoLimite  Description of Exception
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionSuperadoTiempoLimite
	{
		try
		{
			Object obj = buzon.consumeConTimeout(tiempoEnMilisegundos);
			if (obj instanceof EventoInput)
			{
				return (EventoInput) obj;
			}
			else if (obj instanceof EventoControl)
			{
				return (EventoControl) obj;
			}
			else
			{
				if (DEBUG)
				{
					System.out.println("ERROR: Se esperaba un objeto de tipo EventoControl o EventoInput");
				}
				throw new ExcepcionSuperadoTiempoLimite("ERROR: Se esperaba un objeto de tipo Evento");
			}
		}
		catch (ExcepcionTimeOutSuperado ets)
		{
			throw new ExcepcionSuperadoTiempoLimite("Percepción: Se superó el tiempo predefinido al consumir");
		}
	}


	/**
	 *  Añade un nuevo evento en la percepción
	 *
	 *@param  evento  Evento que vamos a añadir
	 */
	public void produce(Object evento)
	{
		buzon.produce(evento);
	}


	/**
	 *  Añade un nuevo evento de forma prioritaria en la percepción
	 *
	 *@param  evento  Evento que se consumirá el primero
	 */
	public void produceParaConsumirInmediatamente(Object evento)
	{
		buzon.produceParaConsumirInmediatamente(evento);
	}
}
