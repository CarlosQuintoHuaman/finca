/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.ConversionDeEventosEnInputs;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ControlAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;



/**
 *  Clase que implementa un control mediante el uso del componente percepción y
 *  el componente automata. En concreto el control trata eventos que llegan a
 *  través de la percepción y los procesa siguiendo un autómata descrito en XML
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ControlImp extends ControlAbstracto {

	private boolean DEBUG = false;
	/**
	 *  autómata que describe el control
	 */
	private AutomataControlAbstracto automataControl;

	/**
	 *  estado interno del componente control
	 */
	private int estado = InterfazGestion.ESTADO_CREADO;


	/**
	 *  Nombre del componente a efectos de traza
	 */
	private String nombre;

	private ItfConsumidorPercepcion percepcionConsumidor;
	private ItfProductorPercepcion percepcionProductor;


	/**
	 *  Constructor
	 *
	 *@param  automata          Autómata con los estados que rigen el control
	 *@param  percConsumidor    Interfaz de consumo de la percepción
	 *@param  percProductor     Interfaz de producción de la percepción
	 *@param  nombreDelControl  Nombre que tomará en componente control
	 */
	public ControlImp(ItfConsumidorPercepcion percConsumidor, AutomataControlAbstracto automata,
			ItfProductorPercepcion percProductor, String nombreDelControl)
	{
		super("Agente reactivo "+nombreDelControl);
		percepcionConsumidor = percConsumidor;
		percepcionProductor = percProductor;
		automataControl = automata;
		nombre = nombreDelControl;
	}


	/**
	 *  Inicia los recursos internos
	 */
	public void arranca()
	{
		if (DEBUG)
			System.out.println(nombre + ": arranca()");
		estado = InterfazGestion.ESTADO_ARRANCANDO;
		try
		{
			this.start();
			//start llama a run()
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/*
	public void arrancaConEvento()
	{
		if (DEBUG)
			System.out.println(nombre + ": arranca()");
		estado = InterfazGestion.ESTADO_ARRANCANDO;
		try
		{
			this.start();
			//start llama a run()
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void arrancaConInput(String nombreInput)
	{
		if (DEBUG)
			System.out.println(nombre + ": arranca()");
		estado = InterfazGestion.ESTADO_ARRANCANDO;
		try
		{
			this.start();
			//start llama a run()
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

*/
	/**
	 *  Descripción del método
	 */
	public void continua()
	{
		throw new java.lang.UnsupportedOperationException("El método continua() aún no está implementado.");
	}



	/**
	 *  Consulta el estado interno del control
	 *
	 *@return    estado del control
	 */
	public int obtenerEstado()
	{
		if (DEBUG)
			System.out.println(nombre + ": obtenerEstado()");
		return estado;
	}


	/**
	 *  Description of the Method
	 */
	public void para()
	{
		throw new java.lang.UnsupportedOperationException("El método para() aún no está implementado.");
	}


	/**
	 *  Mira si el buzón tiene algun evento, lo trata y vuelve a dormir
	 */
	public void run()
	{
		estado = InterfazGestion.ESTADO_ACTIVO;
		while (!(estado == InterfazGestion.ESTADO_TERMINANDO))
		{
			// habría que procurar no consumir recursos de la máquina cuando se está parado
//			if (!(estado == this.ESTADO_PARADO))
//			{
			int milis = 3600000;
			// 1 hora
			Object obj = null;
			try
			{
				// sacar el evento
				// controlamos tiempo para informar de inanición del gestor
				obj = percepcionConsumidor.consumeConTimeout(milis);
			}
			catch (Exception e)
			{
				if (DEBUG)
					System.out.println(nombre + ": No ha llegado evento al control del Gestor en " + milis + " milisegundos");
			}

			if (obj != null)
			{
				// identificar el evento
				if (obj instanceof EventoControl)
				{
					// ejecutamos y cambiamos al siguiente estado
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de control");
					tratarEventoControl((EventoControl) obj);
				}
				else if (obj instanceof EventoInput)
				{
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de input");
					tratarEventoInput((EventoInput) obj);
				}
				else
				{
					if (DEBUG)
						System.out.println("ERROR: " + nombre + ": Ha llegado al 'Control' del 'Gestor' un 'Evento' no reconocido");
				}
			}
			// if esEstadoFinal {itfGestion.termina();}
			yield();
		}
	}


	/**
	 *  Elimina los recursos internos usados por el control
	 */
	public void termina()
	{
		if (DEBUG)
		{
			System.out.println(nombre + ":terminando ...");
		}
		estado = InterfazGestion.ESTADO_TERMINANDO;
		// vamos a terminar usando la percepción para salir de los posibles consume()
		percepcionProductor.produceParaConsumirInmediatamente(new EventoControl(EventoControl.OPERACION_TERMINAR));
	}


	/**
	 *  Método auxiliar para tratar eventos de control
	 *
	 *@param  ec  Evanto de control a tratar
	 */
	private void tratarEventoControl(EventoControl ec)
	{
		switch (ec.operacion)
		{
						case EventoControl.OPERACION_TERMINAR:
							estado = InterfazGestion.ESTADO_TERMINANDO;
							// paranoia
							break;
						case EventoControl.OPERACION_TIMEOUT:
							if (DEBUG)
								System.out.println(nombre + "Alerta: Ha llegado un timeout de inanición: ");
							break;
						default:
							if (DEBUG)
								System.out.println("ERROR: " + nombre + " :Evento de control desconocido");
							break;
		}
	}


	/**
	 *  Método auxiliar que trata los eventos de input
	 *
	 *@param  ei  Evento de input a tratar
	 */
	private void tratarEventoInput(EventoInput ei)
	{
		automataControl.procesaInput(ConversionDeEventosEnInputs.procesarEventoParaProducirInput(ei),
				ei.getParametros());

		if (automataControl.esEstadoFinal())
		{
			this.termina();
		}
	}
}
