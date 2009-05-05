/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;

//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.EventoSimple;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout.Buzon;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout.ExcepcionTimeOutSuperado;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItemControl;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ExcepcionSuperadoTiempoLimite;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */
public class PercepcionImp extends PercepcionAbstracto {

	/**
	 * @uml.property  name="buzon"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
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
		if ( !(obj instanceof EventoSimple)&& !(obj instanceof ItemControl) )
			if (DEBUG)
			{
				System.out.println("ERROR: Se esperaba un objeto de tipo EventoControl o EventoInput");
			}
      return obj;
	}


	/**
	 *  Consume un objeto, pero no se bloquea m�s de un tiempo especificado por
	 *  par�metro en caso de consumir ese tiempo se produce una excepci�n
	 *
	 *@param  tiempoEnMilisegundos               Tiempo que se esperar� como m�ximo
	 *@return                                    Objeto que se ha consumido
	 *@exception  ExcepcionSuperadoTiempoLimite  Description of Exception
	 */
	public Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionSuperadoTiempoLimite
	{
		try
		{
			Object obj = buzon.consumeConTimeout(tiempoEnMilisegundos);
			if (obj instanceof EventoSimple)
			{
//				return (EventoRecAgte) obj;
                return  obj;
			}
			else if (obj instanceof ItemControl)
			{
				return (ItemControl) obj;
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
			throw new ExcepcionSuperadoTiempoLimite("Percepci�n: Se super� el tiempo predefinido al consumir");
		}
	}


	/**
	 *  A�ade un nuevo evento en la percepci�n
	 *
	 *@param  evento  Evento que vamos a a�adir
	 */
	public void produce(Object evento)
	{
		buzon.produce(evento);
	}


	/**
	 *  A�ade un nuevo evento de forma prioritaria en la percepci�n
	 *
	 *@param  evento  Evento que se consumir� el primero
	 */
	public void produceParaConsumirInmediatamente(Object evento)
	{
		buzon.produceParaConsumirInmediatamente(evento);
	}
}
