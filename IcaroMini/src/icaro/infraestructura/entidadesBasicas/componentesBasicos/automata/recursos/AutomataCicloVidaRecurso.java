/*
    Copyright 2001 Telef�nica I+D. All rights reserved
  */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.recursos;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;

import org.apache.log4j.Logger;


/**
 *  Clase que define aut�matas de estados finitos, sin acciones semanticas a ejecutar en las transiciones, 
 *  escritos en XML
 *
 *@author     �lvaro Rodr�guez
 *@created    5 de septiembre de 2001
 *@modified	  02 de Marzo de 2007
 */

public class AutomataCicloVidaRecurso implements ItfUsoAutomataCicloVidaRecurso {
	
	
	public static final String ESTADO_CREADO = "creado";
	public static final String ESTADO_ARRANCADO = "arrancado";
	public static final String ESTADO_FALLO_ARRANQUE = "falloArranque";
	public static final String ESTADO_ERROR = "error";
	public static final String ESTADO_ACTIVO = "activo";
	public static final String ESTADO_FALLO_TEMPORAL = "falloTemporal";
	public static final String ESTADO_PARADO = "parado";
	public static final String ESTADO_TERMINANDO = "terminando";
	public static final String ESTADO_TERMINADO = "terminado";

	/**
	 * Indica si se deben mostrar mensajes de depuraci�n o no
	 * @uml.property  name="dEBUG"
	 */
	public boolean DEBUG = false;

	/**
	 * Controla la profundidad de las trazas
	 * @uml.property  name="traza"
	 */
	protected int traza = 0;

	/**
	 * @uml.property  name="estadoActual"
	 */
	private String estadoActual;

	/**
	 * Tabla que representa los estados del aut�mata
	 * @uml.property  name="theTablaEstadosControl"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private TablaEstadosCicloVidaRecurso theTablaEstadosControl;

	/**
	 *  No se muestra traza
	 */
	public final static int NIVEL_TRAZA_DESACTIVADO = 0;
	/**
	 *  S�lo se muestra una indicaci�n cuando existe una transici�n de estados
	 */
	public final static int NIVEL_TRAZA_SOLO_TRANSICIONES = 1;
	/**
	 *  Se muestra todo lo posible en la traza
	 */
	public final static int NIVEL_TRAZA_TODO = 2;

	/**
	 * @uml.property  name="logger"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private Logger logger = Logger.getRootLogger();
	
	/**
	 * @uml.property  name="trazas"
	 * @uml.associationEnd  readOnly="true"
	 */
	protected ItfUsoRecursoTrazas trazas;
	

	/**
	 *  Crea un aut�mata XML
	 *
	 *@param  NombreFicheroDescriptor  Nombre del fichero que contiene el aut�mata
	 *@param  nivelTraza               Profundidad de las trazas (usar constantes
	 *      definidas est�ticas en esta clase)
	 */
	public AutomataCicloVidaRecurso(String NombreFicheroDescriptor, int nivelTraza)
	{
		XMLParserTablaCicloVidaRecurso parser = new XMLParserTablaCicloVidaRecurso();
		//String ruta = tid.tecHabla.agentes.componentes.infraestructura.configuracion.Configuracion.obtenerParametro("RUTA_FICHEROS_DEFINICION_AUTOMATAS");

		theTablaEstadosControl = parser.extraeTablaEstadosDesdeFicheroXML(NombreFicheroDescriptor);

		// colocamos el aut�mata en el estado inicial
		cambiaEstado(theTablaEstadosControl.dameEstadoInicial());

		// actualizamos el DEBUG para las trazas
		if (nivelTraza == 2)
			DEBUG = true;
		else
			DEBUG = false;

		traza = nivelTraza;
		logger.debug("Usando el aut�mata de ciclo de vida del fichero: " + NombreFicheroDescriptor);
		logger.debug(this.toString());
		/*try{
	    	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	    			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	    }catch(Exception e){
	    	System.out.println("No se pudo usar el recurso de trazas");
	    }*/
	}


	/**
	 *  Dice si el automata se encuentra en un estado final o no
	 *
	 *@return    est� en estado final o no
	 */
	public boolean esEstadoFinal()
	{
		return (theTablaEstadosControl.esEstadoFinal(estadoActual));
	}


	/**
	 *  Admite un input y lo procesa segun la tabla de estados
	 *
	 *@param  input  Input a procesar
	 */
	public void transita(String input)
	{
		String siguiente;
		// comprobar que es un input reconocido por el estado actual
		if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual, input))
		{
			siguiente = theTablaEstadosControl.dameEstadoSiguiente(estadoActual, input);
			// cambiar al siguiente estado
			logger.info("Transici�n en el ciclo de vida usando input:" + input + "  :" + estadoActual + " -> " + siguiente);
			cambiaEstado(siguiente);
		}
		else
		{
			logger.info("AVISO: Input de ciclo de vida ignorado.El input: " + input + " no pertenece a los inputs v�lidos para el estado actual: " + estadoActual);
			/*trazas.aceptaNuevaTraza(new InfoTraza("AutomataCicloVidaRecurso",
					"AVISO: Input de ciclo de vida ignorado.El input: " + input + " no pertenece a los inputs v�lidos para el estado actual: " + estadoActual,
					InfoTraza.NivelTraza.info));*/
		}

	}



	/**
	 *  Imprime la tabla de estados y el estado actual del aut�mata
	 *
	 *@return    Cadena con la informaci�n
	 */
	public String toString()
	{
		String dev = theTablaEstadosControl.toString();
		dev += "\nEstado actual= " + estadoActual;
		return dev;
	}


	/**
	 *  Devuelve el aut�mata a su estado inicial
	 */
	public void volverAEstadoInicial()
	{
		this.cambiaEstado(this.theTablaEstadosControl.dameEstadoInicial());
	}


	/**
	 *  Cambia el estado interno del aut�mata
	 *
	 *@param  estado  Estado del automata al que cambiamos
	 */
	private void cambiaEstado(String estado)
	{
		estadoActual = estado;
	}

	/**
	 * 	Dice si el recurso est� en estado activo, es decir, que puede
	 *  ejecutar m�todos
	 * 
	 * @return est� en estado activo o no
	 */
	public boolean estadoActivo(){
		return this.estadoActual.equals("activo");
	}

	/**
	 * Dice el estado del aut�mata en el que se encuentra el recurso
	 * @return el estado en que se encuentra
	 */
	public String monitorizar(){
		return this.estadoActual;
	}
	/**
	 *  Programa de pruebas del componente
	 *
	 *@param  args  The command line arguments
	 */
	/*
	public static void main(String[] args)
	{
		// ejemplo de uso del automata de control

		// 1. Creo las acciones sem�nticas
		tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones fun = new tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones();
		// 1.5 Creo objeto de acciones sem�nticas
		AccionesSemanticas as = new AccionesSemanticas(fun);

		// 2. Creo el aut�mata
		AutomataControl automata = new AutomataControl("TablaEstadosPruebaDeTablaEstados.xml", as, tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.NIVEL_TRAZA_TODO);

		// 3. Tengo el aut�mata disponible para usar

		automata.toString();
		automata.procesaInput("inicia");
		automata.procesaInput("inputU");

		automata.toString();
	}
	*/

	/**
	 *  Clase de pruebas para el componente, s�lo para pruebas de funcionamiento
	 *  b�sicas
	 *
	 *@author     Jorge Gonz�lez
	 *@created    26 de septiembre de 2001
	 */
	public static class funciones {
		/**
		 */
		public void accionU()
		{
			System.out.println(">> Comienzo a ejecutar m�todo accionU()");
		}


		/**
		 *  M�todo del usuario del componente
		 */
		public void inicial()
		{
			System.out.println(">> Comienzo a ejecutar m�todo inicial()");
			boolean repite = true;
			while (repite)
			{
			}
		}

	}
	/**
	 * @param logger
	 * @uml.property  name="logger"
	 */
	public void setLogger(Logger logger){
		this.logger = logger;
	}
	/**
	 * @return
	 * @uml.property  name="logger"
	 */
	public Logger getLogger(){
		return logger;
	}

}
