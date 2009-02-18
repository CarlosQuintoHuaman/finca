package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Productora de instancias de acciones semánticas 
 *
 *@author     Felipe Polo
 *@created    3 de Diciembre de 2007
 */

public abstract class FactoriaAccionesSemanticas {
    
    private static FactoriaAccionesSemanticas instancia;
    
    public static FactoriaAccionesSemanticas instancia(){
        Log log = LogFactory.getLog(FactoriaAccionesSemanticas.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.imp",
                    "icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.imp.FactoriaAccionesSemanticasImp");
            try{
                instancia = (FactoriaAccionesSemanticas)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion del Control no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract AccionesSemanticasAbstracto crearAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas);
    
}
