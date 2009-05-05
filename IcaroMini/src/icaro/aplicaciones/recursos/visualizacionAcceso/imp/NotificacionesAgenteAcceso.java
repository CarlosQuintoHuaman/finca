package icaro.aplicaciones.recursos.visualizacionAcceso.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.ClaseGeneradoraVisualizacionAcceso;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */
public class NotificacionesAgenteAcceso {

    /**
	 * @uml.property  name="itfUsoRepositorioInterfaces"
	 * @uml.associationEnd  
	 */
    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    /**
	 * @uml.property  name="visualizador"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    protected ClaseGeneradoraVisualizacionAcceso visualizador;
    /**
	 * @uml.property  name="nombreAgenteAcceso"
	 */
   // El identificador del recurso al que pertenece esta clase
    private String nombredeEsteRecurso;

    private String nombreAgenteAcceso;
    /**
	 * @uml.property  name="tipoAgenteAcceso"
	 */
    private String tipoAgenteAcceso;
    /**
	 * @uml.property  name="usoAgenteControlador"
	 * @uml.associationEnd  
	 */
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public NotificacionesAgenteAcceso(ClaseGeneradoraVisualizacionAcceso vis) {

        visualizador = vis;
        nombredeEsteRecurso = visualizador.getNombredeEsteRecurso();
        
    }

    private void getInformacionAgente() {
        nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
        tipoAgenteAcceso = visualizador.getTipoAgenteAcceso();

        if (nombreAgenteAcceso.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "AccesoEsclavo")) {
            nombreAgenteAcceso = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "AccesoMaestro";
        }
    }

    /**
	 * @return
	 * @uml.property  name="visualizador"
	 */
    public ClaseGeneradoraVisualizacionAcceso getVisualizador() {
        return visualizador;

    }

    public void notificacionCierreSistema() {
         try {
             nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
            usoAgenteControlador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteAcceso);
        } catch (Exception ex) {
            Logger.getLogger(NotificacionesAgenteAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario", nombredeEsteRecurso, nombreAgenteAcceso));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }

    public void peticionAutentificacion(String username, String password) {
    /*
         try {
            nombreAgenteAcceso = visualizador.getNombreAgenteAcceso();
            System.out.println("Nombre de agente en visualizador:"+nombreAgenteAcceso);
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteAcceso);
        } catch (Exception ex) {
            Logger.getLogger(NotificacionesAgenteAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
         * 
         */
    	getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
       //  String[] InfoAutenticacion = new String[]{username, password};
        InfoAccesoSinValidar InfoAutenticacion = new  InfoAccesoSinValidar(username,password);
     /*   usoAgenteControlador.aceptaEvento(new Evento("autenticacion",datosEnvio));
        */
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionAcceso
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
                if (itfUsoAgente != null) {
                    // Creación del evento para enviarlo al agente. La informacion del evento es la siguiente
                    // msg= "autenticacion"; msgElement= InfoAutenticacion ;
                    // origen = "VisualizacionAcceso1"  ( Nombre del recurso de visualizacion) y destino = nombreAgenteAcceso
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("autenticacion", InfoAutenticacion, nombredeEsteRecurso, nombreAgenteAcceso));
                }

    // Comentada esta  parte para la versión Mini que no tiene  patrón de agente cognitivo. El recurso no debería enterarse
    // del tipo de agente al que le envía el evento, con la interfaz le vale
            //} else if (tipoAgenteAcceso.equals(NombresPredefinidos.TIPO_COGNITIVO)) {

           //     ItfUsoAgenteCognitivo cognitivo = (ItfUsoAgenteCognitivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteAcceso);
            //    InfoAccesoSinValidar sinValidar = new InfoAccesoSinValidar(username, password);
           //     EventoRecAgte evento = new EventoRecAgte("", sinValidar, "Recurso:VisualizacionAcceso", nombreAgenteAcceso);
           //     if (cognitivo != null) {
           //         cognitivo.aceptaEvento(evento);
           //     }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
            e.printStackTrace();
        }
         
    }
}
