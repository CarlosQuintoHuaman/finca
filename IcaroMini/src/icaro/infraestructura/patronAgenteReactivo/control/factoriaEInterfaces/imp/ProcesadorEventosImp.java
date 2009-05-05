/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItemControl;
//import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.EventoSimple;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.GeneracionEInterfaces.AutomataEFEImp;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ConversionDeEventosEnInputs;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorEventosAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;



/**
 *  Clase que implementa un control mediante el uso del componente percepci�n y
 *  el componente automata. En concreto el control trata eventos que llegan a
 *  trav�s de la percepci�n y los procesa siguiendo un aut�mata descrito en XML
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class ProcesadorEventosImp extends ProcesadorEventosAbstracto{

	/**
	 * @uml.property  name="dEBUG"
	 */
	private boolean DEBUG = false;
	/**
	 * aut�mata que describe el control
	 * @uml.property  name="automataControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private AutomataEFEImp automataControl;
        
    private AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
	/**
	 * estado interno del componente control
	 * @uml.property  name="estado"
	 */
	private int estado = InterfazGestion.ESTADO_CREADO;


	/**
	 * Nombre del componente a efectos de traza
	 * @uml.property  name="nombre"
	 */
	private String nombre;

	/**
	 * @uml.property  name="percepcionConsumidor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ItfConsumidorPercepcion percepcionConsumidor;
	/**
	 * @uml.property  name="percepcionProductor"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ItfProductorPercepcion percepcionProductor;


	/**
	 *  Constructor
	 *
	 *@param  automata          Aut�mata con los estados que rigen el control
	 *@param  percConsumidor    Interfaz de consumo de la percepci�n
	 *@param  percProductor     Interfaz de producci�n de la percepci�n
	 *@param  nombreDelControl  Nombre que tomar� en componente control
	 */
	public ProcesadorEventosImp( AutomataEFEImp automata,AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas,
			ItfProductorPercepcion percProductor, ItfConsumidorPercepcion percConsumidor, String nombreDelControl)
	{
		super("Agente reactivo "+nombreDelControl);
                automataControl = automata;
                accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;	
                percepcionConsumidor = percConsumidor;
		percepcionProductor = percProductor;
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
	 *  Descripci�n del m�todo
	 */
	public void continua()
	{
		throw new java.lang.UnsupportedOperationException("El metodo continua() a�n no est� implementado.");
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
		throw new java.lang.UnsupportedOperationException("El m�todo para() a�n no est� implementado.");
	}


	/**
	 *  Mira si el buzon tiene algun evento, lo trata y vuelve a dormir
	 */
	public void run()
	{
		estado = InterfazGestion.ESTADO_ACTIVO;
		while (!(estado == InterfazGestion.ESTADO_TERMINANDO))
		{
			// habr�a que procurar no consumir recursos de la m�quina cuando se est� parado
//			if (!(estado == this.ESTADO_PARADO))
//			{
			int milis = 3600000;
			// 1 hora
			Object obj = null;
			try
			{
				// sacar el evento
				// controlamos tiempo para informar de inanici�n del gestor
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
				if (obj instanceof ItemControl)
				{
					// ejecutamos y cambiamos al siguiente estado
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de control");
					tratarEventoControl((ItemControl) obj);
				}
				else if (obj instanceof EventoSimple)
				{
					if (DEBUG)
						System.out.println(nombre + ":Percibido Evento de input");
//					tratarEventoInput((EventoRecAgte) obj);
                    tratarEventoInput((EventoSimple) obj);
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
//	yield();
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
		// vamos a terminar usando la percepci�n para salir de los posibles consume()
		percepcionProductor.produceParaConsumirInmediatamente(new ItemControl(ItemControl.OPERACION_TERMINAR));
	}


	/**
	 *  M�todo auxiliar para tratar eventos de control
	 *
	 *@param  ec  Evanto de control a tratar
	 */
	private void tratarEventoControl(ItemControl ec)
	{
		switch (ec.operacion)
		{
						case ItemControl.OPERACION_TERMINAR:
							estado = InterfazGestion.ESTADO_TERMINANDO;
							// paranoia
							break;
						case ItemControl.OPERACION_TIMEOUT:
							if (DEBUG)
								System.out.println(nombre + "Alerta: Ha llegado un timeout de inanicion: ");
							break;
						default:
							if (DEBUG)
								System.out.println("ERROR: " + nombre + " :Evento de control desconocido");
							break;
		}
	}


	/**
	 *  M�todo auxiliar que trata los eventos de input
	 *
	 *@param  ei  Evento de input a tratar
	 */
	private void tratarEventoInput(EventoSimple ei)
	{
    // Se extrae del evento el mensaje (msg) y los objetos enviados como parametros (msgElements)
    // Se envian al automata para que transite y ejecute las acciones correspondientes
    // El control vuelve si la ejecucion de las acciones termina. Si la accion es no bloqueante siempre vuelve
		automataControl.procesaInput(ConversionDeEventosEnInputs.procesarEventoParaProducirInput(ei),
				ei.getMsgElements());

		if (automataControl.esEstadoFinal())
		{
			this.termina();
		}
            } 
        

    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    /**
	 * @return
	 * @uml.property  name="estado"
	 */
    public int getEstado() {
        return estado;
    }

    /**
	 * @param e
	 * @uml.property  name="estado"
	 */
    public void setEstado(int e) {
        this.estado = e;
    }

    /**
	 * @return
	 * @uml.property  name="control"
	 */
public  void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar) {
   
       accionesSemanticasAgenteCreado.setItfUsoGestorAReportar(itfUsoGestorAReportar);
        
        }   

    @Override
    public String toString() {
        return nombre;
    }

    //@Override
    /**
     *  Establece el gestor a reportar
     *  @param nombreGestor nombre del gestor a reportar
     *  @param listaEventos lista de posibles eventos que le puede enviar.
     *  
     *  El gestionador obtendr� las interfaces del gestor a partir del repositorio de interfaces y podr� validar la informaci�n.
     *
     */
    
}
