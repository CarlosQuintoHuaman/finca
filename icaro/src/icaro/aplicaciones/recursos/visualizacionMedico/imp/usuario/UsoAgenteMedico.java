package icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFichaSinValidar;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.*;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.ClaseGeneradoraVisualizacionMedico;
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
public class UsoAgenteMedico {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionMedico visualizador;
    private String nombreAgenteMedico;
    private String tipoAgenteMedico;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteMedico(ClaseGeneradoraVisualizacionMedico vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteMedico = visualizador.getNombreAgenteControlador();
        tipoAgenteMedico = visualizador.getTipoAgenteControlador();

        if (nombreAgenteMedico.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteMedico = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionMedico getVisualizador() {
        return visualizador;

    }

    public void notificacionCierreSistema() {
         try {
             nombreAgenteMedico = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteMedico);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteMedico.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteMedico.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMedico);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteMedico));
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
    
    public void validaCita(String nombre, String apellido, String telf) {
      
	   getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
        String[] datosEnvio = new String[]{nombre, apellido,telf};

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteMedico.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionMedico
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMedico);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("infoCita", datosEnvio, "VisualizacionMedico1", nombreAgenteMedico));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
        
    }
    
    /**
     * Activa el agente Historial
     * @param paciente
     */
    public void abrirHistorial(String paciente) {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaHistorial", "VisualizacionMedico1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Historial desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    public void abrirVisita(String paciente) {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            //ItfUsoAgenteReactivo itfUsoMedico = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            //itfUsoFicha.aceptaEvento(new EventoInput("mostrarVentanaVisita", "VisualizacionMedico1", "AgenteAplicacionMedico1"));

/*            if (tipoAgenteMedico.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionMedico
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMedico);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", "VisualizacionMedico1", nombreAgenteMedico));
                }
            }*/

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Visita desde el agente Medico");
            e.printStackTrace();
        }
    }    
}

