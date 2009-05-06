package icaro.aplicaciones.recursos.visualizacionMensajeria.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionMensajeria.imp.ClaseGeneradoraVisualizacionMensajeria;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *@author     
 *@created    
 */
public class UsoAgenteMensajeria {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionMensajeria visualizador;
    private String nombreAgenteMensajeria;
    private String tipoAgenteMensajeria;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteMensajeria(ClaseGeneradoraVisualizacionMensajeria vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteMensajeria = visualizador.getNombreAgenteControlador();
        tipoAgenteMensajeria = visualizador.getTipoAgenteControlador();

        if (nombreAgenteMensajeria.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteMensajeria = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionMensajeria getVisualizador() {
        return visualizador;

    }


    // Al ser un metodo de EJEMPLO hay muchos nombres que cambiar (parametros, evento, etc).
    public void mostrarVentanaMensajeria(String paciente, String fecha) {
    	//visualizador.mostrarVisualizadorHistorial(nombreAgenteHistorial, tipoAgenteHistorial);
    	getInformacionAgente();
    	
    	try {
    		
    		Object datos[] = {paciente,fecha};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("mostrarVentanaHistorial", datos, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar el agente Historial");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaMensajeNuevo() {
    	visualizador.cerrarVisualizadorMensajeNuevo();
    }


    public void notificacionCierreSistema() {
         try {
             nombreAgenteMensajeria = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteMensajeria);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteMensajeria.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            if (tipoAgenteMensajeria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMensajeria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteMensajeria));
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
    

    // Este metodo esta aqui SOLO COMO EJEMPLO
    public void validaCita(String nombre, String apellido, String telf) {
      
	   getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
        String[] datosEnvio = new String[]{nombre, apellido,telf};

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteMensajeria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionMensajeria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMensajeria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("infoCita", datosEnvio, "VisualizacionMensajeria1", nombreAgenteMensajeria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
        
    }
    
    public void crearAgenteFicha() {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoRecAgte("comenzar", "VisualizacionMensajeria1", "AgenteAplicacionFicha1"));

/*            if (tipoAgenteMensajeria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionMensajeria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMensajeria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("darCita", "VisualizacionMensajeria1", nombreAgenteMensajeria));
                }
            }*/

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
}

