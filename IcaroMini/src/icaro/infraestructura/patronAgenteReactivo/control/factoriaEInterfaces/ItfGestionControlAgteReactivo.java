/*
 *  Copyright 2001 Telef�nica I+D
 *
 *
 *  All rights reserved
 */
package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;

 import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

/**
 * 
 *@author     F Garijo
 *@created    10 de enero  de 2009
 */

public interface ItfGestionControlAgteReactivo extends InterfazGestion {

public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar);
}
