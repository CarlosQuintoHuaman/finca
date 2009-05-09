package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario;


import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.*;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
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
    /**
     * Generamos un evento para el automataFicha con input: 'mostrarVentanaFicha', con 'datos' como parametro para la accion semantica 
     * que le corresponde 'pintaVentanaFicha'. Este es un evento con origen VisualizacionSecretaria y destino AgenteFicha.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la ficha (nombre, telefono, hora,fecha, crear)
     */
    public void mostrarVentanaFicha(DatosCita datos){
    	getInformacionAgente();
    	
        try {
        if (itfUsoRepositorioInterfaces == null) {
            itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
        }
    	ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
        
        itfUsoFicha.aceptaEvento(new EventoRecAgte("mostrarVentanaFicha", datos, "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));
    } catch (Exception e) {
        System.out.println("Ha habido un error al activar el agente Ficha desde la visualizacion Secretaria");
        e.printStackTrace();
    }
    }
    
    /**
     * Generamos un evento para el automataFicha con input: 'mostrarVentanaFicha1', con 'datos' como parametro para la accion semantica 
     * que le corresponde 'pintaVentanaFicha1'. Este es un evento con origen VisualizacionSecretaria y destino AgenteFicha.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro buscando la informacion de la ficha en la bbdd
     * @param datos		:: Datos con los que rellenar la ficha (nombre, telefono, hora,fecha, crear)
     */
    public void mostrarVentanaFicha1(DatosCita datos){
    	getInformacionAgente();
    	
        try {
        if (itfUsoRepositorioInterfaces == null) {
            itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
        }
    	ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
        
        itfUsoFicha.aceptaEvento(new EventoRecAgte("mostrarVentanaFichaBD", datos, "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));
    } catch (Exception e) {
        System.out.println("Ha habido un error al activar el agente Ficha desde la visualizacion Secretaria");
        e.printStackTrace();
    }
    }
    
    public void mostrarVentanaFicha(){
    	getInformacionAgente();
    	
        try {
        if (itfUsoRepositorioInterfaces == null) {
            itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
        }
    	ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
        
        itfUsoFicha.aceptaEvento(new EventoRecAgte("mostrarVentanaFicha", "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));
    } catch (Exception e) {
        System.out.println("Ha habido un error al activar el agente Ficha desde la visualizacion Secretaria");
        e.printStackTrace();
    }
    }
    public void notificacionCierreSistema() {
         try {
             nombreAgenteSecretaria = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteSecretaria);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteSecretaria));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        
    }
    /**
     * Generamos un evento para el automataSecretaria con input: 'darCita', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'pintaVentanaCita'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la cita(nombre, apellido, telefono, hora)
     */
    public void mostrarVentanaCita(String nombre, String apellido1,String apellido2, String Telf, String hora, String fecha){
    	getInformacionAgente();
    	DatosCitaSinValidar datos= new DatosCitaSinValidar(nombre, apellido1,apellido2, Telf,fecha, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("darCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar cita ");
            e.printStackTrace();
        }
    }
    /**
     * Generamos un evento para el automataSecretaria con input: 'consultarPCitas', para la accion semantica 
     * que le corresponde: 'pintaVentanaPCitas'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana 
     */
    public void mostrarVentanaProximasCitas(){
    	getInformacionAgente();
    	//DatosCitaSinValidar datos= new DatosCitaSinValidar(nombre, apellido, Telf, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("consultarPCitas", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al consultarPCitas ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'darCita', para la accion semantica 
     * que le corresponde: 'pintaVentanaCita'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana 
     */
    public void mostrarVentanaCita(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("darCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar ventana cita ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'anadirLlamada', para la accion semantica 
     * que le corresponde: 'pintaVentanaLlamada'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana 
     */
    public void mostrarVentanaLlamadas(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("anadirLlamada", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrarventanaLlamadas ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'anadirLlamada', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'pintaVentanaLlamada'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
     */
    public void mostrarVentanaLlamada(String nombre, String mensaje, String Telf, Boolean paciente, String hora){
    	getInformacionAgente();
    	DatosLlamada datos= new DatosLlamada(nombre, mensaje, Telf, paciente, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("anadirLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al añadir llamada ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'anadirExtra', para la accion semantica 
     * que le corresponde: 'pintaVentanaExtra'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana 
     */
       public void mostrarVentanaExtra(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("anadirExtra", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el añadir extra ");
            e.printStackTrace();
        }
    }

   /**
    * Generamos un evento para el automataSecretaria con input: 'anadirExtra', con 'datos' como parametro para la accion semantica 
    * que le corresponde: 'pintaVentanaExtra'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
    * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
    * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
    */
    public void mostrarVentanaExtra(String nombre, String mensaje, String Telf, Boolean paciente, String hora){
    	getInformacionAgente();
    	DatosLlamada datos= new DatosLlamada(nombre, mensaje, Telf, paciente, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("anadirExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el extra ");
            e.printStackTrace();
        }
    }

    /**
    * Generamos un evento para el automataSecretaria con input: 'cancelarCita', para la accion semantica 
    * que le corresponde: 'nula'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
    * Su proposito es cerrar la ventana
     */
    public void cerrarVentanaCita(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorCita();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("cancelarCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarcita al agente secretaria");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'cancelarLlamada', para la accion semantica 
     * que le corresponde: 'nula'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es cerrar la ventana
      */
    public void cerrarVentanaLlamada(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorLlamada();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("cancelarLlamada", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarLlamada al agente secretaria");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'cancelarExtra', para la accion semantica 
     * que le corresponde: 'nula'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es cerrar la ventana
      */
    public void cerrarVentanaExtra(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorExtra();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("cancelarExtra", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }
            

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarExtra al agente secretaria");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'cancelarPCitas', para la accion semantica 
     * que le corresponde: 'nula'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es cerrar la ventana
      */
    public void cerrarVentanaProximasCitas(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorProximasCita();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("cancelarPCitas", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }
            

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarPCitas al agente secretaria");
            e.printStackTrace();
        }
    }
    
    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
    
    public void mostrarMensajeAviso(String mensaje, String titulo){
    	visualizador.mostrarMensajeAviso(titulo, mensaje);
    }
    
    public boolean mostrarMensajeAvisoC(String mensaje, String titulo){
    	return visualizador.mostrarMensajeAvisoC(titulo, mensaje);
    }
    /**
     * Generamos un evento para el automataSecretaria con input: 'infoCita', para la accion semantica 
     * que le corresponde: 'inserta'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es Almacenar los datos que se le pasan por parametro en la agenda en el lugar que indique datos.hora
     * @param datos 	:: Datos a insertar en la agenda (nombre, apellido1, telefono,fecha, hora, periodo)
      */
    public void validaCita(DatosCitaSinValidar datos) {
      
	   getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	
         

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("infoCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
        
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'agenda', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'rellenaAgendaSecretaria'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
     */
    public void mostrarAgendaSecretaria(String fecha,String usuEste){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	String[] datos={fecha,usuEste};
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("agenda", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar agenda ");
            e.printStackTrace();
        }
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'agenda', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'rellenaAgendaSecretaria'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es pintar la ventana con los datos que se le pasan por parametro 
     * @param datos		:: Datos con los que rellenar la llamada(nombre, mensaje, telefono, Espaciente, hora)
     */
    public void cerrarVentanaSecretaria(){

    
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoLogin = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionLogin1");
            
            itfUsoLogin.aceptaEvento(new EventoRecAgte("cerrarSesion", "VisualizacionSecretaria1", "AgenteAplicacionLogin1"));
            
            visualizador.cerrarVisualizadorSecretaria();
            
            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("cerrarAgenda","VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al cerrar la agenda ");
            e.printStackTrace();
        }
    }
    /**
     * Generamos un evento para el automataSecretaria con input: 'guardarAgenda', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'guardaAgenda'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es guardar los datos que se le pasan por parametro enviandoselos a la persistencia 
     * @param datos		:: Datos que incluyen todas las citas de todos los medicos para los que trabaja una secretaria concreta en un dia preestablecido
     */
    public void guardarAgenda(DatosSecretaria datos){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
  
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("guardarAgenda", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar agenda ");
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo en pruebas relacionado con las acciones correspondientes al evento del boton '+' del panelCita
     */
    public boolean estaLibre(HorasCita hora) {
        
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
     	
          

         try {
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
             }
           return visualizador.estaLibre(hora);

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos de la hora de la cita bMas ");
             e.printStackTrace();
             return false;
         }
         
     }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'buscarPacientes'.Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es Buscar todos los pacientes en la bbdd. Posteriormente se le enviara la peticion a la persistencia 
     */
    public void buscarPacientes(){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	try {
       	 
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("buscarPacientes", "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de buscarPaciente ");
            e.printStackTrace();
        }
       
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'buscarPaciente', con 'datos' como parametro para la accion semantica 
     * que le corresponde: 'buscaPaciente'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es Buscar un determinado paciente en la bbdd. Posteriormente se le enviara la peticion a la persistencia 
     * @param datos		:: Datos necesarios para buscar un paciente (nombre, telefono)
     */
    public void BuscarCitas(String nombre,String telf){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	String[] datos={nombre,telf};
    	try {
       	 
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("buscarPaciente", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de buscarPaciente ");
            e.printStackTrace();
        }
       
    }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'infoLlamada', para la accion semantica 
     * que le corresponde: 'insertaLlamada'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es Almacenar los datos que se le pasan por parametro en la tabla de llamadas de la agenda.
     * @param datos 	:: Datos a insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
    public void anadeLlamada(DatosLlamada datos) {
        
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
 	   try {
        	 
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
             }

             if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                 //AgenteAplicacionSecretaria
                 ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                 if (itfUsoAgente != null) {
                     itfUsoAgente.aceptaEvento(new EventoRecAgte("infoLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                 }
             }

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos de la llamada ");
             e.printStackTrace();
         }
         
     }
    /**
     * Generamos un evento para el automataSecretaria con input: 'modLlamada', para la accion semantica 
     * que le corresponde: 'modificaLlamada'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es modificar los datos que se le pasan por parametro (datAnt) por datPost en la tabla de llamadas de la agenda.
     * @param datAnt	:: Datos a modificar en la agenda (nombre,mensaje, telefono, paciente, hora)
     * @param datPost	:: Datos a modificados que se deben insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
    public void modificaLlamada(DatosLlamada datAnt, DatosLlamada datPost) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
           DatosLlamada[] datos={datAnt,datPost};
  	   //DatosLlamada datos=datAnt;

          try {
         	 
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoRecAgte("modLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al modificar los datos de la llamada ");
              e.printStackTrace();
          }
          
      }
    
    /**
     * Generamos un evento para el automataSecretaria con input: 'borrarLlamada', para la accion semantica 
     * que le corresponde: 'borrarLlamada'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es borrar la entrada de la en tabla de llamadas de la agenda que se corresponda con la que se pasa por parametro.
     * @param datos	:: Datos a borrar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
    public void borraLlamada(DatosLlamada datos) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
          try {
        	  visualizador.cerrarVisualizadorLlamada();
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoRecAgte("borrarLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al borrar datos llamada ");
              e.printStackTrace();
          }
          
    }
      
    /**
     * Generamos un evento para el automataSecretaria con input: 'infoExtra', para la accion semantica 
     * que le corresponde: 'insertaExtra'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es Almacenar los datos que se le pasan por parametro en la tabla de Extras de la agenda.
     * @param datos 	:: Datos a insertar en la agenda (nombre, mensaje, telefono, paciente, hora)
      */
	public void anadeExtra(DatosLlamada datos) {
    
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
         try {
        	 
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
             }

             if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                 //AgenteAplicacionSecretaria
                 ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                 if (itfUsoAgente != null) {
                     itfUsoAgente.aceptaEvento(new EventoRecAgte("infoExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                 }
             }

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos del extra ");
             e.printStackTrace();
         }
         
     }
        
    /**
     * Generamos un evento para el automataSecretaria con input: 'modExtra', para la accion semantica 
     * que le corresponde: 'modificaExtra'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
     * Su proposito es modificar los datos que se le pasan por parametro (datAnt) por datPost en la tabla de extras de la agenda.
     * @param datAnt	:: Datos a modificar en la agenda (nombre,mensaje, telefono, paciente, hora)
     * @param datPost	:: Datos a modificados que se deben insertar en la agenda (nombre,mensaje, telefono, paciente, hora)
      */
      public void modificaExtra(DatosLlamada datAnt, DatosLlamada datPost) {
          
     	   getInformacionAgente();
             //provoca la peticiï¿½n de autentificaciï¿½n
         	
              DatosLlamada[] datos={datAnt,datPost};
     	   

             try {
            	 
                 if (itfUsoRepositorioInterfaces == null) {
                     itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
                 }

                 if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                     //AgenteAplicacionSecretaria
                     ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                     if (itfUsoAgente != null) {
                         itfUsoAgente.aceptaEvento(new EventoRecAgte("modExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                     }
                 }

             } catch (Exception e) {
                 System.out.println("Ha habido un error al modificar los datos del extra ");
                 e.printStackTrace();
             }
             
         }
      
      /**
       * Generamos un evento para el automataSecretaria con input: 'borrarExtra', para la accion semantica 
       * que le corresponde: 'borrarExtra'. Este es un evento con origen VisualizacionSecretaria y destino AgenteSecretaria.
       * Su proposito es borrar la entrada de la en tabla de Extras de la agenda que se corresponda con la que se pasa por parametro.
       * @param datos	:: Datos a borrar en la agenda (nombre,mensaje, telefono, paciente, hora)
        */
    public void borraExtra(DatosLlamada datos) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
      	
           

          try {
        	  visualizador.cerrarVisualizadorExtra();
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoRecAgte("borrarExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al borrar los datos del extra ");
              e.printStackTrace();
          }
          
      }
     
     
    /**
     * Evento inicial que permite generar todos los agentes al principio
     */
    public void crearAgenteFicha() {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoRecAgte("comenzar", "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));



        } catch (Exception e) {
            System.out.println("Ha habido un error al crear agente de Ficha ");
            e.printStackTrace();
        }
    }
}

