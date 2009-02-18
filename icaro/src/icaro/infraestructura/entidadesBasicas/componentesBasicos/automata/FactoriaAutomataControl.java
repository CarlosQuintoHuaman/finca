package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */

public abstract class FactoriaAutomataControl {
    
    private static FactoriaAutomataControl instancia;
    
    public static FactoriaAutomataControl instancia(){
        Log log = LogFactory.getLog(FactoriaAutomataControl.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp",
                    "icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp.FactoriaAutomataControlImp");
            try{
                instancia = (FactoriaAutomataControl)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion del Control no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract AutomataControlAbstracto crearAutomataControl (String NombreFicheroDescriptor, AccionesSemanticasAbstracto accionesSem, int nivelTraza, String nombreAgente) throws Exception;
    
}