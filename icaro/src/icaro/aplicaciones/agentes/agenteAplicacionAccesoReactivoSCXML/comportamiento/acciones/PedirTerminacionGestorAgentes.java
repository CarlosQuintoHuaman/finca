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
public class PedirTerminacionGestorAgentes extends AccionAbstracta {

    private ItfUsoVisualizadorAcceso visualizacion;
    private ItfUsoPersistencia persistencia;
    private ItfUsoAgenteReactivo agenteAcceso;
    ItfUsoRecursoTrazas trazas;
    RepositorioInterfaces itfUsoRepositorio;
    ItfUsoAgenteReactivo itfUsoGestorAReportar;

    
    @Override
    public void ejecutar(ContextoAccion contexto) throws ModelException, SCXMLExpressionException {
        itfUsoRepositorio = RepositorioInterfaces.instance();
        itfUsoGestorAReportar = contexto.getItfGestorAReportar();
        try {
            trazas = (ItfUsoRecursoTrazas) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        try {
             this.itfUsoGestorAReportar.aceptaEvento(new EventoInput("peticion_terminar_todo", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso2", NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Error al mandar el evento de terminar_todo",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                contexto.getPercepcion().aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso2", NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
                //agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso");
                //agenteAcceso.aceptaEvento(new EventoInput("error", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso"));
            } catch (Exception exc) {
                try {
                    trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                            "Fallo al enviar un evento error.",
                            InfoTraza.NivelTraza.error));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

    }
}

