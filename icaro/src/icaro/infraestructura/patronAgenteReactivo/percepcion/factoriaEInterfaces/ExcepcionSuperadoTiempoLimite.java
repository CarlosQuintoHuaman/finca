/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

/**
 *  Excepción que simboliza un timeout al consumir de la percepción
 *
 *@author     Jorge González
 *@created    2 de octubre de 2001
 */
public class ExcepcionSuperadoTiempoLimite extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	public ExcepcionSuperadoTiempoLimite(String cad) {
      super(cad);
	}
}
