/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

/**
 *  Título: Contenedor de acciones y estados siguientes Descripcion:
 *
 *@author     Jorge González
 *@created    24 de septiembre de 2001
 *@version    1.0
 */

public class Operacion {

	/**
	 *  Nombre de la acción a ejecutar
	 */
	public String accionSemantica;
	/**
	 *  Estado que se alcanza tras la ejecución de la acción
	 */
	public String estadoSiguiente;
	/**
	 *  determina si la acción se ejecutará de forma bloqueante y se esperará su
	 *  resultado antes de cambiar de estado o no
	 */
	public boolean modoTransicionBloqueante;


	/**
	 *  Constructor
	 *
	 *@param  accion     método a ejecutar
	 *@param  estadoSig  estado siguiente del autómata
	 *@param  modo       modo de ejecución (true=bloqueante, false=nobloqueante)
	 */
	public Operacion(String accion, String estadoSig, boolean modo)
	{
		accionSemantica = accion;
		estadoSiguiente = estadoSig;
		modoTransicionBloqueante = modo;
	}
}
