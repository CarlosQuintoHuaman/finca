/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces;

/**
 *  Excepci�n que simboliza un timeout al consumir de la percepci�n
 *
 *@author     Jorge Gonz�lez
 *@created    2 de octubre de 2001
 */
public class ExcepcionSuperadoTiempoLimite extends java.lang.Exception {

	private static final long serialVersionUID = 1L;

	public ExcepcionSuperadoTiempoLimite(String cad) {
      super(cad);
	}
}
