package icaro.aplicaciones.recursos.visualizacionAcceso.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.DatosAccesoSinValidar;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.ClaseGeneradoraVisualizacionAcceso;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */
public class UsoAgenteAcceso {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionAcceso visualizador;
    private String nombreAgenteAcceso;
    private String tipoAgenteAcceso;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteAcceso(ClaseGeneradoraVisualizacionAcceso vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
        tipoAgenteAcceso = visualizador.getTipoAgenteAcceso();

        if (nombreAgenteAcceso.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "AccesoEsclavo")) {
            nombreAgenteAcceso = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "AccesoMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionAcceso getVisualizador() {
        return visualizador;

    }

    public void notificacionCierreSistema() {
         try {
             nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteAcceso);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionAcceso", nombreAgenteAcceso));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }

    public void autentifica(String username, String password) {
    /*
         try {
            nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
            System.out.println("Nombre de agente en visualizador:"+nombreAgenteAcceso);
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteAcceso);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
         * 
         */
    	getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
        String[] datosEnvio = new String[]{username, password};
     /*   usoAgenteControlador.aceptaEvento(new Evento("autenticacion",datosEnvio));
        */
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionAcceso
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("autenticacion", datosEnvio, "VisualizacionAcceso1", nombreAgenteAcceso));
                }
            } else if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_COGNITIVO)) {

                ItfUsoAgenteCognitivo cognitivo = (ItfUsoAgenteCognitivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
                DatosAccesoSinValidar sinValidar = new DatosAccesoSinValidar(username, password);
                EventoInput evento = new EventoInput("", sinValidar, "Recurso:VisualizacionAcceso", nombreAgenteAcceso);
                if (cognitivo != null) {
                    cognitivo.aceptaEvento(evento);
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
            e.printStackTrace();
        }
         
    }
}
