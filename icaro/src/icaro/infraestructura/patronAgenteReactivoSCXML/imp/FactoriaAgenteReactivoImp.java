/*
 * FactoriaAgenteReactivoImp.java
 *
 * Creado en 20 de noviembre de 2007, 12:04
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */
package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.RolAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.patronAgenteReactivoSCXML.AgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.Configuracion;
import icaro.infraestructura.patronAgenteReactivoSCXML.FactoriaAgenteReactivoSCXML;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.InputStream;


/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class FactoriaAgenteReactivoImp extends FactoriaAgenteReactivoSCXML {

    public void crearAgenteReactivo(DescInstanciaAgente descAgente) throws Exception {
        try {
            //se obtiene la descripci�n del agente a construir en la configuraci�n
            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

            DescComportamientoAgente descComportamiento = descAgente.getDescComportamiento();
            boolean esGestor = descComportamiento.getRol() == RolAgente.GESTOR;
         /*
            
            if (descAgente == null) { //si no es agente de aplicacion, se busca entre los gestores
                descAgente = config.getDescripcionGestor(nombreAgente);
                esGestor = true;
            }
            */
            if (descComportamiento.getTipo() == TipoAgente.REACTIVO_SCXML) {
                InputStream accionesSemanticas = listaAccionesSemanticas(descComportamiento.getNombreComportamiento(), esGestor);
                InputStream maquinaEstados = maquinaEstados(descComportamiento.getNombreComportamiento(), esGestor);
                Configuracion conf = new Configuracion();
                conf.setAcciones(accionesSemanticas);
                conf.setMaquinaEstados(maquinaEstados);
                conf.setDaemon(true);
                AgenteReactivo agente = new PatronHelper().crearAgenteReactivo(conf);
                
                RepositorioInterfaces.instance().registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + descAgente.getId(),agente);
                RepositorioInterfaces.instance().registrarInterfaz(
                        NombresPredefinidos.ITF_USO + descAgente.getId(),agente);
                //RepositorioInterfaces.instance().registrarInterfaz(descAgente.getId(),agente);

            } else {
                throw new Exception("Esta factoria solo crea agentes reactivos scxml");
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private InputStream listaAccionesSemanticas(String nombreComportamientoAgente, boolean esGestor) throws Exception {
        String ruta = "/icaro/";
        ruta+= esGestor ? "gestores/":"aplicaciones/agentes/";
        ruta+= nombreComportamientoAgente+"/comportamiento/accionesSemanticas/acciones.xml";
        return getClass().getResourceAsStream(ruta);
    }

    private InputStream maquinaEstados(String nombreComportamientoAgente, boolean esGestor) throws Exception {
        String ruta = "/icaro/";
        ruta+= esGestor ? "gestores/":"aplicaciones/agentes/";
        ruta+= nombreComportamientoAgente+"/comportamiento/automata.xml";
        return getClass().getResourceAsStream(ruta);
    }
}
