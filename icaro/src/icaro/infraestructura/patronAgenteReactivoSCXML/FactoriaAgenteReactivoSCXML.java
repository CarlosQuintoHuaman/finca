/*
 * FactoriaAgenteReactivo.java
 *
 * Creado en 20 de noviembre de 2007, 11:58
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public abstract class FactoriaAgenteReactivoSCXML {
    private static FactoriaAgenteReactivoSCXML instancia;
    /**
     * Devuelve una instancia de esta factoria.
     * @return
     */
    public static FactoriaAgenteReactivoSCXML instancia(){
        if(instancia==null){
            String c = System.getProperty("icaro.infraestructura.patronAgenteReactivoSCXML.FactoriaAgenteReactivo",
                    "icaro.infraestructura.patronAgenteReactivoSCXML.imp.FactoriaAgenteReactivoImp");
            try{
                instancia = (FactoriaAgenteReactivoSCXML) Class.forName(c).newInstance();
            }catch(Exception ex){
                throw new RuntimeException("Implementation not found for: icaro.infraestructura.patronAgenteReactivoSCXML.FactoriaAgenteReactivo");
            }
        }
        return instancia;
    }
    /**
     * Crea un agente reactivo.
     * @param automata - Fichero de maquina de estados en SCXML que describe el comportamiento del agente.
     * @param acciones - Fichero con la lista de acciones usadas en la maquina de estados.
     * @param daemon - Indica si el hilo de ejecuci&oacute;n que procesa los eventos debe ser un Daemon.
     * @return - Instancia de un Agente Reactivo.
     * @throws java.lang.Exception
     */
    public abstract void crearAgenteReactivo(DescInstanciaAgente descAgente) throws Exception;
}
