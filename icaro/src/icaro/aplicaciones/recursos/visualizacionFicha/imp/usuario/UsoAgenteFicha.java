package icaro.aplicaciones.recursos.visualizacionFicha.imp.usuario;


import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.*;
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
public class UsoAgenteFicha {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionFicha visualizador;
    private String nombreAgenteFicha;
    private String tipoAgenteFicha;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteFicha(ClaseGeneradoraVisualizacionFicha vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteFicha = visualizador.getNombreAgenteControlador();
        tipoAgenteFicha = visualizador.getTipoAgenteControlador();

        if (nombreAgenteFicha.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteFicha = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionFicha getVisualizador() {
        return visualizador;

    }

    public void notificacionCierreSistema() {
         try {
             nombreAgenteFicha = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteFicha);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteFicha.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteFicha.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteFicha);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteFicha));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        
    }

    /**
     * METODO ANTIGUO  DE CUANDO NO TENIAMOS LA SECRETARIA
     * Generamos un evento para el automataFicha con input: 'darCita', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'pintaVentanaCita'. Este es un evento con origen VisualizacionFicha1 y destino AgenteFicha.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la cita(nombre, apellido, telefono, hora)
     */
    public void mostrarVentanaCita(String nombre, String apellido, String telf){
    	getInformacionAgente();
    	String[] datosEnvio = new String[]{nombre, apellido,telf};
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteFicha.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionFicha
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteFicha);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", datosEnvio, "VisualizacionFicha1", nombreAgenteFicha));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar Ficha ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataFicha con input: 'guardarFicha', para la accion semantica 
     * que le corresponde: 'guardaFicha'. Este es un evento con origen VisualizacionFicha y destino AgenteFicha.
     * Su proposito guardar los datos de la ficha que se le pasan por parametro
     * @param ficha
      */
    public void guardarFicha(DatosFicha original, DatosFicha fichaN){
    	getInformacionAgente();
    	DatosFicha[] fichas={original,fichaN};
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteFicha.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionFicha
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteFicha);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("guardarFicha", fichas, "VisualizacionFicha1", nombreAgenteFicha));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar Ficha ");
            e.printStackTrace();
        }
    }
    /**
     * Generamos un evento para el automataFicha con input: 'cerrarVentanaFicha', para la accion semantica 
     * que le corresponde: 'nula'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es cerrar la ventana
      */
    public void cerrarVentanaFicha(){
    	getInformacionAgente();
    	try {
    		visualizador.cerrarVisualizadorFicha();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteFicha.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionFicha
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteFicha);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("cerrarVentanaFicha", "VisualizacionFicha1", nombreAgenteFicha));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al cerrar la Ficha ");
            e.printStackTrace();
        }
    }
    
    
    //metodos para mostrar mensajes de aviso/error
    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
    
    public void mostrarMensajeAviso(String mensaje, String titulo){
    	visualizador.mostrarMensajeAviso(titulo, mensaje);
    }
    
    
    public void mostrarMensajeAvisoConfirmacion(String mensaje, String titulo){
    	visualizador.mostrarMensajeAvisoConfirmacion(titulo, mensaje);
    }
    
    /** METODO ANTIGUO DE CUANDO NO TENIAMOS A LA SECRETARIA
     * Generamos un evento para el automataFicha con input: 'infoCita', para la accion semantica 
     * que le corresponde: 'inserta'. Este es un evento con origen VisualizacionFicha y destino AgenteFicha.
     * Su proposito es Almacenar los datos que se le pasan por parametro en la agenda en el lugar que indique datos.hora
     * @param datos 	:: Datos a insertar en la agenda (nombre, apellido1, telefono,fecha, hora, periodo)
      */
    public void validaCita(String nombre, String apellido, String telf) {
      
	   getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
        String[] datosEnvio = new String[]{nombre, apellido,telf};

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteFicha.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionFicha
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteFicha);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("infoCita", datosEnvio, "VisualizacionFicha1", nombreAgenteFicha));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
        
    }
    /**
     * Generamos un evento para el automataFicha con input: 'comenzar', para la accion semantica 
     * que le corresponde: 'nula'. Este es un evento con origen VisualizacionFicha y destino AgenteFicha.
     * Su proposito es crear el agente ficha al arrancar la aplicacion
     */
    public void crearAgenteFicha() {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoInput("comenzar", "VisualizacionFicha1", "AgenteAplicacionFicha1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al crear agente de Ficha ");
            e.printStackTrace();
        }
    }
}

