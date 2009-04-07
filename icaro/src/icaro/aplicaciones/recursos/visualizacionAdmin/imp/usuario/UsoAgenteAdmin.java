package icaro.aplicaciones.recursos.visualizacionAdmin.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionAdmin.imp.ClaseGeneradoraVisualizacionAdmin;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class UsoAgenteAdmin {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionAdmin visualizador;
    private String nombreAgenteAdmin;
    private String tipoAgenteAdmin;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteAdmin(ClaseGeneradoraVisualizacionAdmin vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteAdmin = visualizador.getNombreAgenteControlador();
        tipoAgenteAdmin = visualizador.getTipoAgenteControlador();

        if (nombreAgenteAdmin.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteAdmin = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionAdmin getVisualizador() {
        return visualizador;

    }


    /**
     * Muestra la ventana de administrador
     */
    public void mostrarVentanaAdmin() {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaHistorial", "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar el agente Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Cierra la ventana de administrador
     */
    public void cerrarVentanaAdmin() {
    	visualizador.cerrarVisualizadorAdmin();
    }


    public void notificacionCierreSistema() {
         try {
             nombreAgenteAdmin = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteAdmin);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteAdmin.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAdmin);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteAdmin));
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
}

