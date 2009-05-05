/*
 *  Copyright 2009 ISSIS
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.entidadesBasicas;

import java.io.Serializable;

/**
 *  
 *
 *@author
 *@created    11 de septiembre de 2001
 *@modified	  6 de enero de 2009
 *@version    2.0
 */

public class EventoRecAgte extends EventoSimple {

	private String destino;

	public EventoRecAgte(String msg, String origen, String destino) {
		super( msg,  origen);
		
		this.destino = destino;
        
	}
	
	public EventoRecAgte(String msg, Object msgParametro, String origen, String destino) {
		super( msg,  msgParametro, origen );
		
		this.destino = destino;
	}
	
	public EventoRecAgte(String msg, Object[] msgParametros, String origen, String destino) {
		super( msg,  msgParametros, origen );
		
		this.destino = destino;
	}


    public String getDestino(){
		if (destino!=null)

			return destino;
		else
			return "destino no especificado";
	}

	/**
	 * @param origen
	 * @uml.property  name="origen"
	 */
	public void setDestino(String destino){
		this.destino = destino;
	}
	
}
