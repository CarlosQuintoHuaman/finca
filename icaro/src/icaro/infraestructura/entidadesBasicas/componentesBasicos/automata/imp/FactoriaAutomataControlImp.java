package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;


import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.AutomataControlAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.FactoriaAutomataControl;



public class FactoriaAutomataControlImp extends FactoriaAutomataControl{  
	public AutomataControlAbstracto crearAutomataControl (String NombreFicheroDescriptor, 
														  AccionesSemanticasAbstracto accionesSem, int nivelTraza, String nombreAgente) throws Exception {
        return new AutomataControlImp(NombreFicheroDescriptor,accionesSem,nivelTraza, nombreAgente);
        //elijo la implementación requerida (aunque podría haber más)
    }
}