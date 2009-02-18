package icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionHistorial.imp.ClaseGeneradoraVisualizacionHistorial;
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
 *@author     
 *@created    
 */
public class UsoAgenteHistorial {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionHistorial visualizador;
    private String nombreAgenteHistorial;
    private String tipoAgenteHistorial;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteHistorial(ClaseGeneradoraVisualizacionHistorial vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteHistorial = visualizador.getNombreAgenteControlador();
        tipoAgenteHistorial = visualizador.getTipoAgenteControlador();

        if (nombreAgenteHistorial.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteHistorial = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionHistorial getVisualizador() {
        return visualizador;

    }
    
    public void insertaDatos(String motivo, String descripcion, String exploracion, String diagnostico, String tratamiento) {
        
 	   getInformacionAgente();
         //provoca la petici�n de autentificaci�n
     	
         String[] datosEnvio = new String[]{motivo, descripcion, exploracion, diagnostico, tratamiento};

         try {
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
             }

             if (tipoAgenteHistorial.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                 //AgenteAplicacionHistorial
                 ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteHistorial);
                 if (itfUsoAgente != null) {
                     itfUsoAgente.aceptaEvento(new EventoInput("guardarDatos", datosEnvio, "VisualizacionHistorial1", nombreAgenteHistorial));
                 }
             }

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos de la cita ");
             e.printStackTrace();
         }
         
    }

    public void notificacionCierreSistema() {
    	try {
             nombreAgenteHistorial = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteHistorial);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteHistorial.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteHistorial.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteHistorial);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteHistorial));
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
    
    public void mostrarMensajeAviso(String mensaje, String titulo){
    	visualizador.mostrarMensajeAviso(titulo, mensaje);
    }
    
    public void mostrarMensajeInformacion(String mensaje, String titulo){
    	visualizador.mostrarMensajeInformacion(titulo, mensaje);
    }
}

