package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFichaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosAgenda;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.*;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
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
public class UsoAgenteSecretaria {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionSecretaria visualizador;
    private String nombreAgenteSecretaria;
    private String tipoAgenteSecretaria;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteSecretaria(ClaseGeneradoraVisualizacionSecretaria vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteSecretaria = visualizador.getNombreAgenteControlador();
        tipoAgenteSecretaria = visualizador.getTipoAgenteControlador();

        if (nombreAgenteSecretaria.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteSecretaria = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionSecretaria getVisualizador() {
        return visualizador;

    }

    public void mostrarVentanaFicha(DatosAgenda datos){
    	getInformacionAgente();
    	
        try {
        if (itfUsoRepositorioInterfaces == null) {
            itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
        }
    	ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
        
        itfUsoFicha.aceptaEvento(new EventoInput("mostrarVentanaFicha", datos, "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));
    } catch (Exception e) {
        System.out.println("Ha habido un error al activar el agente Ficha desde el agente Secretaria");
        e.printStackTrace();
    }
    }
    public void notificacionCierreSistema() {
         try {
             nombreAgenteSecretaria = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteSecretaria);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteSecretaria));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }

    public void mostrarVentanaCita(String nombre, String apellido, String Telf, String hora){
    	getInformacionAgente();
    	DatosCitaSinValidar datos= new DatosCitaSinValidar(nombre, apellido, Telf, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
    
    
    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
    
    public void validaCita(DatosCitaSinValidar datos) {
      
	   getInformacionAgente();
        //provoca la petici�n de autentificaci�n
    	
         

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("infoCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
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
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoInput("comenzar", "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));

/*            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }*/

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
}

