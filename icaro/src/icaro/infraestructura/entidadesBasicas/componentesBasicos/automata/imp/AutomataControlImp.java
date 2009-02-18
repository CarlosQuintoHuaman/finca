/*
    Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Set;

import org.apache.log4j.Logger;


/**
 * Clase que define autómatas de estados finitos sin transiciones vacías
 * escritos en XML
 * 
 * @author Jorge González
 * @created 5 de septiembre de 2001
 * @modified 26 de Junio de 2006
 */

public class AutomataControlImp extends AutomataControlAbstracto {

	/**
	 * Indica si se deben mostrar mensajes de depuración o no
	 */
	public boolean DEBUG = false;

	/**
	 * Controla la profundidad de las trazas
	 */
	protected int traza = 0;

	private AccionesSemanticasAbstracto acciones;

	private String estadoActual;

	/**
	 * Tabla que representa los estados del autómata
	 */
	private TablaEstadosControl theTablaEstadosControl;
	
	/**
	 * Nombre del agente al que pertenece el control
	 */
	private String nombreAgente;

	/**
	 * No se muestra traza
	 */
	public final static int NIVEL_TRAZA_DESACTIVADO = 0;
	/**
	 * Sólo se muestra una indicación cuando existe una transición de estados
	 */
	public final static int NIVEL_TRAZA_SOLO_TRANSICIONES = 1;
	/**
	 * Se muestra todo lo posible en la traza
	 */
	public final static int NIVEL_TRAZA_TODO = 2;

	private Logger logger = Logger.getRootLogger();
	
	protected ItfUsoRecursoTrazas trazas;
	
	private Set<String> conjuntoInputs;
	
	private String descripcionAutomata;

	/**
	 * Crea un autómata XML
	 * 
	 * @param NombreFicheroDescriptor
	 *            Nombre del fichero que contiene el autómata
	 * @param accionesSem
	 *            Objeto que contiene la implementación de los métodos referidos
	 *            en el autómata
	 * @param nivelTraza
	 *            Profundidad de las trazas (usar constantes definidas estáticas
	 *            en esta clase)
	 */
	public AutomataControlImp(String NombreFicheroDescriptor,
			AccionesSemanticasAbstracto accionesSem, int nivelTraza, String nombreAgente) throws Exception {
		XMLParserTablaEstados parser = new XMLParserTablaEstados(NombreFicheroDescriptor);
		// String ruta =
		// tid.tecHabla.agentes.componentes.infraestructura.configuracion.Configuracion.obtenerParametro("RUTA_FICHEROS_DEFINICION_AUTOMATAS");

		theTablaEstadosControl = parser.extraeTablaEstados();
		conjuntoInputs = parser.extraeConjuntoInputs();
		descripcionAutomata = parser.extraerDescripcionTablaEstados();
		acciones = accionesSem;
		this.nombreAgente = nombreAgente;
		try{
	    	trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
	    			NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
	    }catch(Exception e){
	    	System.out.println("No se pudo usar el recurso de trazas");
	    }
		// colocamos el autómata en el estado inicial
		cambiaEstado(theTablaEstadosControl.dameEstadoInicial());

		// actualizamos el DEBUG para las trazas
		if (nivelTraza == 2)
			DEBUG = true;
		else
			DEBUG = false;

		traza = nivelTraza;
		logger.debug("Usando el autómata del fichero: "	+ NombreFicheroDescriptor);
		trazas.visualizaNuevaTraza(new InfoTraza(nombreAgente,
				"Usando el autómata del fichero: "	+ NombreFicheroDescriptor,
				InfoTraza.NivelTraza.debug));
		logger.debug(this.toString());
	}

	/**
	 * Dice si el automata se encuentra en un estado final o no
	 * 
	 * @return está en estado final o no
	 */
	public boolean esEstadoFinal() {
		return (theTablaEstadosControl.esEstadoFinal(estadoActual));
	}

	/**
	 * Admite un input y lo procesa segul ta tabla de estados, ejecutando la
	 * transición correspondiente
	 * 
	 * @param input
	 *            Input a procesar
	 */
	public void procesaInput(String input, Object[] parametros) {
		// String accion;
		// String siguiente;
		Operacion op;
		// comprobar que es un input reconocido por el estado actual

		if (conjuntoInputs.contains(input)) {
			if (theTablaEstadosControl.esInputValidoDeEstado(estadoActual,
					input)) {
				op = theTablaEstadosControl.dameOperacion(estadoActual, input);
				// ejecutar la accion semantica (posible TO)
				logger.debug("Ejecutando accion: " + op.accionSemantica);
				trazas.visualizaNuevaTraza(new InfoTraza(nombreAgente,
						"Ejecutando accion: " + op.accionSemantica,
						InfoTraza.NivelTraza.debug));
				acciones.ejecutarAccion(op.accionSemantica, parametros,
						op.modoTransicionBloqueante);

				// cambiar al siguiente estado
				logger.info("Transición usando input:" + input + "  :"+ estadoActual + " -> " + op.estadoSiguiente);
				trazas.visualizaNuevaTraza(new InfoTraza(nombreAgente,
						"Transición usando input '" + input + "'. ESTADO ACTUAL: "
						+ estadoActual + " -> " + "ESTADO SIGUIENTE: "+op.estadoSiguiente,
						InfoTraza.NivelTraza.info));
				cambiaEstado(op.estadoSiguiente);
			} else {
				trazas.visualizaNuevaTraza(new InfoTraza(nombreAgente,
						"AVISO: Input ignorado.El input: "
								+ input
								+ " no pertenece a los inputs válidos para el estado actual: "
								+ estadoActual,
						InfoTraza.NivelTraza.info));
				logger
						.info("AVISO: Input ignorado.El input: "
								+ input
								+ " no pertenece a los inputs válidos para el estado actual: "
								+ estadoActual);

			}
		} else {
			logger.error("ERROR: Input " + input + " no válido para " + descripcionAutomata );
			trazas.visualizaNuevaTraza(new InfoTraza(nombreAgente,
					"ERROR: Input " + input + " no válido para " + descripcionAutomata,
					InfoTraza.NivelTraza.error));
			logger.error(conjuntoInputs);
		}

	}

	/**
	 * Imprime la tabla de estados y el estado actual del autómata
	 * 
	 * @return Cadena con la información
	 */
	public String toString() {
		String dev = theTablaEstadosControl.toString();
		dev += "\nEstado actual= " + estadoActual;
		return dev;
	}

	/**
	 * Devuelve el autómata a su estado inicial
	 */
	public void volverAEstadoInicial() {
		this.cambiaEstado(this.theTablaEstadosControl.dameEstadoInicial());
	}

	/**
	 * Cambia el estado interno del autómata
	 * 
	 * @param estado
	 *            Estado del automata al que cambiamos
	 */
	public void cambiaEstado(String estado) {
		estadoActual = estado;
	}

	/**
	 * Programa de pruebas del componente
	 * 
	 * @param args
	 *            The command line arguments
	 */
	/*
	 * public static void main(String[] args) { // ejemplo de uso del automata
	 * de control
	 *  // 1. Creo las acciones semánticas
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones
	 * fun = new
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.funciones(); //
	 * 1.5 Creo objeto de acciones semánticas AccionesSemanticas as = new
	 * AccionesSemanticas(fun);
	 *  // 2. Creo el autómata AutomataControl automata = new
	 * AutomataControl("TablaEstadosPruebaDeTablaEstados.xml", as,
	 * tid.tecHabla.agentes.componentes.infraestructura.automata.AutomataControl.NIVEL_TRAZA_TODO);
	 *  // 3. Tengo el autómata disponible para usar
	 * 
	 * automata.toString(); automata.procesaInput("inicia");
	 * automata.procesaInput("inputU");
	 * 
	 * automata.toString(); }
	 */

	/**
	 * Clase de pruebas para el componente, sólo para pruebas de funcionamiento
	 * básicas
	 * 
	 * @author Jorge González
	 * @created 26 de septiembre de 2001
	 */
	public static class funciones {
		/**
		 */
		public void accionU() {
			System.out.println(">> Comienzo a ejecutar método accionU()");
		}

		/**
		 * Método del usuario del componente
		 */
		public void inicial() {
			System.out.println(">> Comienzo a ejecutar método inicial()");
			boolean repite = true;
			while (repite) {
			}
		}

	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

}
