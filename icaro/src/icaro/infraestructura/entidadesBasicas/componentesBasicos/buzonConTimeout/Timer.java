/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.buzonConTimeout;


/**
 *  Temporizador implementado usando una hebra
 *
 *@author     Jorge González
 *@created    11 de septiembre de 2001
 */
public class Timer extends java.lang.Thread {
	/**
	 *  Descipción del campo
	 */
	public boolean activo = true;
	private Itf_ProductorBuzonTimeout itfp;

	private int tiempo;


	/**
	 *  Constructor for the Timer object
	 *
	 *@param  milis  Description of Parameter
	 *@param  prod   Description of Parameter
	 */
	public Timer(int milis, Itf_ProductorBuzonTimeout prod)
	{
		super("Timer");
		tiempo = milis;
		itfp = prod;
		this.setDaemon(true);
	}


	/**
	 *  La hebra espera un tiempo y luego produce un evento de TimeOut
	 */
	public void run()
	{
		try
		{
			Thread.sleep(tiempo);
			if (activo)
				itfp.produce(new EventoTimeout());
		}
		catch (InterruptedException e)
		{
		}
	}

}
