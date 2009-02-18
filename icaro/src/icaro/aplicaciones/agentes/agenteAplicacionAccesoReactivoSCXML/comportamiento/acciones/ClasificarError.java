/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivoSCXML.comportamiento.acciones;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.AccionAbstracta;
import icaro.infraestructura.patronAgenteReactivoSCXML.ContextoAccion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import org.apache.commons.scxml.SCXMLExpressionException;
import org.apache.commons.scxml.model.ModelException;

/**
 *
 * @author Carlos Rodríguez Fernández
 */
public class ClasificarError extends AccionAbstracta {

    private ItfUsoVisualizadorAcceso visualizacion;
    private ItfUsoPersistencia persistencia;
    private ItfUsoAgenteReactivo agenteAcceso;
    ItfUsoRecursoTrazas trazas;
    RepositorioInterfaces itfUsoRepositorio;

    @Override
    public void ejecutar(ContextoAccion contexto) throws ModelException, SCXMLExpressionException {

        itfUsoRepositorio = RepositorioInterfaces.instance();
        try {
            trazas = (ItfUsoRecursoTrazas) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            
            contexto.getPercepcion().aceptaEvento(new EventoInput("errorIrrecuperable",null,null));
            //agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso");
            //agenteAcceso.aceptaEvento(new EventoInput("errorIrrecuperable", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso"));

        } catch (Exception e) {
            try {
                 trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Ha habido un problema enviar el evento usuario Valido/NoValido al agente Acceso",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
