package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;


import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.FactoriaPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class FactoriaPercepcionImp extends FactoriaPercepcion{  
	public PercepcionAbstracto crearPercepcion(){
        return new PercepcionImp();
        //elijo la única implementación posible (aunque podría haber más)
    }
}