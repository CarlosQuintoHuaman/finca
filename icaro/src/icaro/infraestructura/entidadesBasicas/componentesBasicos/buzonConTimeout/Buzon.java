/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;


/**
 *  Buz�n de objetos con funcionalidades de consumo no bloqueante
 *
 *@author     Jorge Gonz�lez
 *@created    11 de septiembre de 2001
 */
public class Buzon implements Itf_ProductorBuzonTimeout, Itf_ConsumidorBuzonTimeout {
	/**
	 *  Temporizador
	 */
	public Timer theTimer;
	/**
	 *  Tama�o m�ximo que tendr� la cola
	 */
	private int TAM_MAX_COLA_CIRCULAR = 5000;
	/**
	 *  Cola circular de contenidos
	 */
	private java.util.Vector<Object> contenido;

	private final static boolean DEBUG = false;


	/**
	 *  Constructor for the Buzon object
	 */
	public Buzon()
	{
		if (DEBUG)
		{
			System.out.println("BuzonConTimeout: Constructor");
		}
		contenido = new java.util.Vector<Object>();
	}


	/**
	 *  Consume un objeto del buz�n. Espera hasta que haya un objeto disponible.
	 *
	 *@return    Objeto que se ha extra�do del buz�n
	 */
	public synchronized Object consumeBloqueante()
	{
		Object obj;
		obj = saca();
		return obj;
	}


	/**
	 *  Consumir un objeto del buz�n, manteni�ndose el consumidor bloqueado no m�s
	 *  de 'tiempoEnMilisegundos' milisegundos
	 *
	 *@param  tiempoEnMilisegundos          Milis que se esperar� como m�ximo
	 *@return                               El Objeto que se ha sacado
	 *@exception  ExcepcionTimeOutSuperado  Excepcion producida tras el tiempo
	 *      especificado
	 */
	public synchronized Object consumeConTimeout(int tiempoEnMilisegundos)
		throws ExcepcionTimeOutSuperado
	{
		Object obj;
		// crear un timeout con el tiempo especificado
		Timer timeout = new Timer(tiempoEnMilisegundos, this);
		timeout.start();
		obj = saca();

		if (timeout.isAlive())
		{
		    timeout.activo = false;
		     // Correcci�n al problema: Si no interrumpimos el timeout se queda en el sleep hasta que acaba y luego,
		     // como ya no est� activo ni siquiera genera el evento, la soluci�n es interrumpir
		     // la hebra para que termine su m�todo run y se consiga reciclar, con ello
		     // no tenemos timers activos en espera, que luego ni siquiera van a
		     // producir nada
		     timeout.interrupt();

		     try {
			timeout.join();
		     } catch (InterruptedException ie) {
			System.out.println("Join de timeout interrumpido");
		     }

		}
		// comprobar que obj no sea un EventoTimeout

		if (obj instanceof EventoTimeout)
		{
			throw new ExcepcionTimeOutSuperado();
		}
      //timeout.destroy();
		return obj;
	}


	/**
	 *  Introduce un nuevo evento en el buz�n, se ignoran nuevos eventos si el
	 *  buz�n no est� activo
	 *
	 *@param  evento  Description of Parameter
	 */
	public synchronized void produce(Object evento)
	{
		mete(evento, false);
	}


	/**
	 *@param  obj  Description of Parameter
	 */
	public synchronized void produceParaConsumirInmediatamente(Object obj)
	{
		mete(obj, true);
	}



	/**
	 *@param  objeto          Description of Parameter
	 *@param  meterElPrimero  Description of Parameter
	 */
	private synchronized void mete(Object objeto, boolean meterElPrimero)
	{
		if (DEBUG)
		{
			System.out.println("BuzonConTimeout: mete");
		}
		// comprobar cola circular
		if (numElementos() >= this.TAM_MAX_COLA_CIRCULAR)
		{
			System.err.println("BuzonConTimeout: Se ha sobrepasado la capacidad de almacenamiento de la cola de eventos, sobreescribiendo ...");
			contenido.removeElementAt(0);
		}
		if (meterElPrimero)
		{
			contenido.insertElementAt(objeto, 0);
		}
		else
		{
			contenido.addElement(objeto);
		}
		notifyAll();
	}


	/**
	 *  Devuelve el n� de elementos
	 *
	 *@return    n� de elementos que contiene el buz�n
	 */
	private synchronized int numElementos()
	{
		return this.contenido.size();
	}


	/**
	 *  Elimina todos los elementos del buz�n
	 */
	public synchronized void reset()
	{
		if (DEBUG)
		{
			System.out.println("BuzonTimeout: reset");
		}
		contenido.removeAllElements();

	}


	/**
	 *@return     Description of the Returned Value
	 */
	private synchronized Object saca()
	{
		Object ret;
		if (DEBUG)
		{
			System.out.println("BuzonConTimeout: saca");
		}
		// llamada a saca es bloqueante
		while (contenido.size() == 0)
		{
			try
			{
				wait();
			}
			catch (java.lang.InterruptedException ie)
			{
			}
		}

		if (contenido.size() != 0)
		{
			ret = contenido.elementAt(0);
			contenido.removeElementAt(0);
		}
		else
		{
			ret = null;
		}
		return ret;
	}


	/**
	 *  The main program for the Buzon class
	 *
	 *@param  args  The command line arguments
	 */
	/*
	public static void main(String[] args)
	{
		Buzon bz = new Buzon();

		//bz.produce(new Object());

		try
		{
			bz.produce(new Object());
			Object o = bz.consumeConTimeout(1000);
			bz.produce(new Object());
			bz.produce(new Object());
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
			o = bz.consumeConTimeout(1000);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	*/

        /**
         * Interrumpe el buz�n introduciendo un evento de timeout
         */
        public void interrumpe()
        {
          this.theTimer.interrupt();
          this.mete(new ExcepcionTimeOutSuperado(),false);
        }
}
