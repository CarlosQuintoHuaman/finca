/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;



/**
 *  Clase que traduce objetos de la clase EventoInput en los inputs que tengamos
 *  definidos en el aut�mata
 *
 *@author     Jorge Gonz�lez
 *@created    6 de septiembre de 2001
 */
public class ConversionDeEventosEnInputs {

	/**
	 *  Constructor for the ConversionDeEventosEnInputs object
	 */
	public ConversionDeEventosEnInputs() { }


	/**
	 *@param  ev  Evento a traducir
	 *@return     Input procesable por el aut�mata
	 */
	public static String procesarEventoParaProducirInput(EventoInput ev)
	{
		return (ev.getInput()).trim();
	}
}
