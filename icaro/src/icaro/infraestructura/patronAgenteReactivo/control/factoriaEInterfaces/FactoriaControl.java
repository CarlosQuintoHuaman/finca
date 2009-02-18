package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class FactoriaControl {
    
    private static FactoriaControl instancia;
    
    public static FactoriaControl instancia(){
        Log log = LogFactory.getLog(FactoriaControl.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp",
            		"icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.FactoriaControlImp");
            try{
                instancia = (FactoriaControl)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion del Control no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract ControlAbstracto crearControl(ItfConsumidorPercepcion percConsumidor, AutomataControlAbstracto automata,
												  ItfProductorPercepcion percProductor, String nombreDelControl);
    
}
