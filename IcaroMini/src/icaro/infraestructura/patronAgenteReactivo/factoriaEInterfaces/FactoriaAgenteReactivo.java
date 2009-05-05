package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;
import icaro.infraestructura.entidadesBasicas.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public abstract class FactoriaAgenteReactivo {
	
	   private static FactoriaAgenteReactivo instancia;
    
    public static FactoriaAgenteReactivo instancia(){
        Log log = LogFactory.getLog(FactoriaAgenteReactivo.class);
        if(instancia==null){
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp",
                    "icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoImp");
            try{
                instancia = (FactoriaAgenteReactivo)Class.forName(clase).newInstance();
            }catch(Exception ex){
                log.error("Implementacion del Control no encontrado",ex);
            }
            
        }
        return instancia;
    }
    
    public abstract void crearAgenteReactivo (DescInstanciaAgente descInstanciaAgente)throws ExcepcionEnComponente ;
    public abstract void crearAgenteReactivo(String nombreInstanciaAgente, String rutaComportamiento)throws ExcepcionEnComponente;
    //public abstract void crearAgenteReactivoDesdeFichero (String fichConfig);
    
}