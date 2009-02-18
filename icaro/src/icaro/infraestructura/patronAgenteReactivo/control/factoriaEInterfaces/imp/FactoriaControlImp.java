package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ControlAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControl;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;



/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class FactoriaControlImp extends FactoriaControl{  
	public ControlAbstracto crearControl(ItfConsumidorPercepcion percConsumidor, AutomataControlAbstracto automata,
			  ItfProductorPercepcion percProductor, String nombreDelControl){
        return new ControlImp(percConsumidor,automata,percProductor,nombreDelControl);
        //elijo la implementación adecuada (aunque podría haber más)
    }
}
