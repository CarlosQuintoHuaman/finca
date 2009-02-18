/*
 * FactoriaAdaptadorMotorReglas.java
 *
 * Creado 18 de abril de 2007, 11:52
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;



/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class FactoriaAdaptadorMotorReglas {
   
    private static FactoriaAdaptadorMotorReglas instancia;
    public static FactoriaAdaptadorMotorReglas instancia(){
        if(instancia==null){
            String c = System.getProperty("organizacion.infraestructura.procesadorconocimiento.agentecognitivo2.motorReglas.FactoriaAdaptadorMotorReglas",
                    "organizacion.infraestructura.agentecognitivo2.procesadorconocimiento.motorReglas.imp.FactoriaAdaptadorMotorReglasDrools");
            try{
                instancia = (FactoriaAdaptadorMotorReglas) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for:organizacion.infraestructura.agentecognitivo2.motorReglas.FactoriaAdaptadorMotorReglas");
            }
        }
        return instancia;

    }
    public abstract ItfMotorReglas crearAdaptadorMotorReglas(AgenteCognitivo agente);
}
