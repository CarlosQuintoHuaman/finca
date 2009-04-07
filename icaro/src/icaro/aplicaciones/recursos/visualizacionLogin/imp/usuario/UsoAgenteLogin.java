package icaro.aplicaciones.recursos.visualizacionLogin.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionLogin.imp.ClaseGeneradoraVisualizacionLogin;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
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
 * @author Camilo Andres Benito Rojas
 *
 */
public class UsoAgenteLogin {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionLogin visualizador;
    private String nombreAgenteLogin;
    private String tipoAgenteLogin;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteLogin(ClaseGeneradoraVisualizacionLogin vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteLogin = visualizador.getNombreAgenteControlador();
        tipoAgenteLogin = visualizador.getTipoAgenteControlador();

        if (nombreAgenteLogin.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteLogin = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionLogin getVisualizador() {
        return visualizador;

    }

    public void notificacionCierreSistema() {
         try {
             nombreAgenteLogin = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteLogin);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteLogin.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteLogin.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteLogin);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteLogin));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }

    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
    
    public void validaUsuario(String u, String p) {
    	getInformacionAgente();
    	
        String[] datosEnvio = new String[]{u,p};

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteLogin.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionLogin
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteLogin);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("validaUsuario", datosEnvio, "VisualizacionLogin1", nombreAgenteLogin));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
    }
}

