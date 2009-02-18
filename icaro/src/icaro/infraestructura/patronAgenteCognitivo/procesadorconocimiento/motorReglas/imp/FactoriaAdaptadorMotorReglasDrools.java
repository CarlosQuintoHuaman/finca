/*
 * FactoriaAdaptadorMotorReglasDrools.java
 *
 * Creado 18 de abril de 2007, 11:56
 *
 * Telefonica I+D Copyright 2006-2007
 */

package icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.imp;

import icaro.infraestructura.patronAgenteCognitivo.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.FactoriaAdaptadorMotorReglas;
import icaro.infraestructura.patronAgenteCognitivo.procesadorconocimiento.motorReglas.ItfMotorReglas;



/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class FactoriaAdaptadorMotorReglasDrools extends FactoriaAdaptadorMotorReglas{
    
    public ItfMotorReglas crearAdaptadorMotorReglas(AgenteCognitivo agente){
        return new MotorReglasDrools(agente);
    }
    
}
