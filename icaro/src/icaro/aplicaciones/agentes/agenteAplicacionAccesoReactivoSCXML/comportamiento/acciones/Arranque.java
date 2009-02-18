/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivoSCXML.comportamiento.acciones;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.AccionAbstracta;
import icaro.infraestructura.patronAgenteReactivoSCXML.ContextoAccion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.scxml.SCXMLExpressionException;
import org.apache.commons.scxml.model.ModelException;

/**
 *
 * @author Carlos Rodríguez Fernández
 */
public class Arranque extends AccionAbstracta {

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
            Logger.getLogger(Arranque.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
            visualizacion.mostrarVisualizadorAcceso(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso2", NombresPredefinidos.TIPO_REACTIVO);
            trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2", "El nombre dado al visualizador fue:"+NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso2", InfoTraza.NivelTraza.debug));
            trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2", "Se acaba de mostrar el visualizador", InfoTraza.NivelTraza.debug));
        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Ha habido un problema al abrir el visualizador de Acceso en accion semantica 'arranque()'",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
