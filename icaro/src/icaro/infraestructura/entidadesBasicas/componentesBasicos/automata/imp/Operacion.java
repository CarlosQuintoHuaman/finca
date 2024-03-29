/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

/**
 *  T�tulo: Contenedor de acciones y estados siguientes Descripcion:
 *
 *@author     Jorge Gonz�lez
 *@created    24 de septiembre de 2001
 *@version    1.0
 */

public class Operacion {

	/**
	 *  Nombre de la acci�n a ejecutar
	 */
	public String accionSemantica;
	/**
	 *  Estado que se alcanza tras la ejecuci�n de la acci�n
	 */
	public String estadoSiguiente;
	/**
	 *  determina si la acci�n se ejecutar� de forma bloqueante y se esperar� su
	 *  resultado antes de cambiar de estado o no
	 */
	public boolean modoTransicionBloqueante;


	/**
	 *  Constructor
	 *
	 *@param  accion     m�todo a ejecutar
	 *@param  estadoSig  estado siguiente del aut�mata
	 *@param  modo       modo de ejecuci�n (true=bloqueante, false=nobloqueante)
	 */
	public Operacion(String accion, String estadoSig, boolean modo)
	{
		accionSemantica = accion;
		estadoSiguiente = estadoSig;
		modoTransicionBloqueante = modo;
	}
}
