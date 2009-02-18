/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivoSCXML.comportamiento.acciones;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
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
public class MostrarUsuarioNoAccede extends AccionAbstracta {

    private ItfUsoVisualizadorAcceso visualizacion;
    private ItfUsoPersistencia persistencia;
    private ItfUsoAgenteReactivo agenteAcceso;
    ItfUsoRecursoTrazas trazas;
    RepositorioInterfaces itfUsoRepositorio;

    @Override
    public void ejecutar(ContextoAccion contexto) throws ModelException, SCXMLExpressionException {
        System.out.println("Por aqui paso 2");
        itfUsoRepositorio = RepositorioInterfaces.instance();
        try {
            trazas = (ItfUsoRecursoTrazas) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String[] datos = (String[]) contexto.getEvento().getParametros();

        DatosAccesoSinValidar dav = new DatosAccesoSinValidar(datos[0], datos[1]);

        try {
            visualizacion = (ItfUsoVisualizadorAcceso) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "VisualizacionAcceso");
            visualizacion.mostrarMensajeError("Acceso Incorrecto", "El usuario " + dav.tomaUsuario() + " no ha accedido al sistema.");
        } catch (Exception ex) {
            try {
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Ha habido un problema al abrir el visualizador de Acceso",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
