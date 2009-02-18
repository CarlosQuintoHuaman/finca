package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.imp;


import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.FactoriaAccionesSemanticas;


public class FactoriaAccionesSemanticasImp extends FactoriaAccionesSemanticas{  
	public AccionesSemanticasAbstracto crearAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas){
        return new AccionesSemanticasImp(accionesSemanticas);
        //elijo la �nica implementaci�n posible (aunque podr�a haber m�s)
    }
}