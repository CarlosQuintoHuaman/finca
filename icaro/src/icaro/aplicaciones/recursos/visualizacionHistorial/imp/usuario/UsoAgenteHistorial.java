package icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
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

    public void mostrarVentanaHistorial(String paciente) {
    	//visualizador.mostrarVisualizadorHistorial(nombreAgenteHistorial, tipoAgenteHistorial);
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaHistorial", paciente, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar el agente Historial");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaHistorial() {
    	visualizador.cerrarVisualizadorHistorial();
    }
    
    public void cerrarVentanaLista() {
    	visualizador.cerrarVisualizadorLista();
    }
    
    public void mostrarVentanaPrueba(String paciente) {
    	//visualizador.mostrarVisualizadorHistorial(nombreAgenteHistorial, tipoAgenteHistorial);
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaPrueba", paciente, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar el panel Prueba de Historial");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaPrueba() {
    	visualizador.cerrarVisualizadorPrueba();
    }
    
    public void guardarVisita(InfoVisita v) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("guardarVisita", v, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    public void guardarPrueba(InfoPrueba p) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("guardarPrueba", p, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    public void borrarPrueba(InfoPrueba p) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("borrarPrueba", p, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
}

