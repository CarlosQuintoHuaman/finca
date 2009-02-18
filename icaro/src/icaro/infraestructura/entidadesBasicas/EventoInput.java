/*
 *  Copyright 2001 Telefónica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.entidadesBasicas;

import java.io.Serializable;

/**
 *  Título: Descripcion: Copyright: Copyright (c) 2001 Empresa:
 *
 *@author
 *@created    11 de septiembre de 2001
 *@modified	  20 de junio de 2006
 *@version    2.0
 */

public class EventoInput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String input;
	private Object[] parametros;
	private String justificacion;
	private String origen;
	private String destino;

	public EventoInput(String input, String origen, String destino) {
		
		this.input = input;
		this.parametros = new Object[0];
		this.justificacion = null;
		this.origen = origen;
		this.destino = destino;
	}
	
	public EventoInput(String input, Object parametro, String origen, String destino) {
		
		this.input = input;
		this.parametros = new Object[]{parametro};
		this.justificacion = null;
		this.origen = origen;
		this.destino = destino;
	}
	
	public EventoInput(String input, Object[] parametros, String origen, String destino) {
		
		this.input = input;
		this.parametros = parametros;
		this.justificacion = null;
		this.origen = origen;
		this.destino = destino;
	}


    /**
	 * @return Returns the input.
	 */
	public String getInput() {
		return input;
	}
	

	/**
	 * @param input The input to set.
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * @return Returns the justificacion.
	 */
	public String getJustificacion() {
		return justificacion;
	}

	/**
	 * @param justificacion The justificacion to set.
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	/**
	 * @return Returns the parametros.
	 */
	public Object[] getParametros() {
		return parametros;
	}

	/**
	 * @param parametros The parametros to set.
	 */
	public void setParametros(Object[] parametros) {
		this.parametros = parametros;
	}

	public String getOrigen(){
		if (origen!=null)
			
			return origen;
		else	
			return "no especificado";
	}
	
	public void setOrigen(String origen){
		this.origen = origen;
	}
	
	public String getDestino(){
		if (destino!=null)
			return destino;
		else
			return "no especificado";
	}
	
	public void setDestino(String destino){
		this.destino = destino;
	}
	
	public String toString() {
        return this.input;
    }
	
	public boolean tieneParametros() {
		return parametros != null && parametros.length != 0;
	}
}
