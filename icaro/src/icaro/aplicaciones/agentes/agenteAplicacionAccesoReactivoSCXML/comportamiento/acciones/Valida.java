/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.agenteAplicacionAccesoReactivoSCXML.comportamiento.acciones;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
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
public class Valida extends AccionAbstracta {

    private ItfUsoVisualizadorAcceso visualizacion;
    private ItfUsoPersistencia persistencia;
    private ItfUsoAgenteReactivo agenteAcceso;
    ItfUsoRecursoTrazas trazas;
    RepositorioInterfaces itfUsoRepositorio;

    @Override
    public void ejecutar(ContextoAccion contexto) throws ModelException, SCXMLExpressionException {
        boolean ok = false;
        itfUsoRepositorio = RepositorioInterfaces.instance();
        try {
            trazas = (ItfUsoRecursoTrazas) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        String[] datosEv = (String[]) contexto.getEvento().getParametros();

        DatosAccesoSinValidar datos = new DatosAccesoSinValidar(datosEv[0], datosEv[1]);
        try {
            persistencia = (ItfUsoPersistencia) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + "Persistencia");
            ok = persistencia.compruebaUsuario(datos.tomaUsuario(), datos.tomaPassword());
            try {
                trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Comprobando usuario...",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            try {
                trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Ha habido un problema en la persistencia al comprobar el usuario y el password",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            //agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso");
            
            if (ok) {
                contexto.getPercepcion().aceptaEvento(new EventoInput("usuarioValido",datosEv,null,null));
                //agenteAcceso.aceptaEvento(new EventoInput("usuarioValido", datosEnvio, NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso"));
            } else {
                contexto.getPercepcion().aceptaEvento(new EventoInput("usuarioNoValido",datosEv,null,null));
                //agenteAcceso.aceptaEvento(new EventoInput("usuarioNoValido", datosEnvio, NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso", NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "Acceso"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso2",
                        "Ha habido un problema enviar el evento usuario Valido/NoValido al agente",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

