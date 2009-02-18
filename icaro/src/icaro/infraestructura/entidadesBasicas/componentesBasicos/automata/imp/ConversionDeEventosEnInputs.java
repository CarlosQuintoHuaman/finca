/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;



/**
 *  Clase que traduce objetos de la clase EventoInput en los inputs que tengamos
 *  definidos en el autómata
 *
 *@author     Jorge González
 *@created    6 de septiembre de 2001
 */
public class ConversionDeEventosEnInputs {

	/**
	 *  Constructor for the ConversionDeEventosEnInputs object
	 */
	public ConversionDeEventosEnInputs() { }


	/**
	 *@param  ev  Evento a traducir
	 *@return     Input procesable por el autómata
	 */
	public static String procesarEventoParaProducirInput(EventoInput ev)
	{
		return (ev.getInput()).trim();
	}
}
