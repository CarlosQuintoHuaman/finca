package icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.ClaseGeneradoraVisualizacionMedico;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
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
            usoAgenteControlador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteMedico);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteMedico.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMedico);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteMedico));
                }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }
    
    public void mostrarMensajeAviso(String mensaje, String titulo){
    	visualizador.mostrarMensajeAviso(titulo, mensaje);
    }
    
    public void mostrarMensajeInformacion(String mensaje, String titulo){
    	visualizador.mostrarMensajeInformacion(titulo, mensaje);
    }
    
    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
   
    /**
     * Activa el agente Historial y abre la lista de visitas
     * @param paciente
     */
    public void abrirHistorial(String paciente) {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("mostrarVentanaLista", paciente, "VisualizacionMedico1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Historial desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Abre una visita en concreto del historial
     * @param c
     */
    public void abrirVisita(InfoCita c) {
    	getInformacionAgente();
    	
    	try {
            
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            Timestamp t = new Timestamp(0);
            
            t.setTime(c.getFecha().getTime() + c.getHora().getTime());
            
            Object d[] = {c.getUsuario(),t};
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("mostrarVentanaHistorial", d, "VisualizacionMedico1", "AgenteAplicacionHistorial1"));


        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Visita desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Llama al agente Medicamentos para mostrar la ventana de añadir medicamento
     */
    public void abrirNuevoMedicamento() {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("mostrarVentanaNuevo", "AgenteAplicacionMedico1", "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Historial desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Llama al agente Ficha para que muestre la ficha de un paciente
     * @param paciente
     */
    public void abrirFicha(String paciente) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoRecAgte("mostrarVentanaFicha", "VisualizacionMedico1", "AgenteAplicacionFicha1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Ficha desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Envia un evento a Medicamentos para que cargue y envie de vuelta los medicamentos (asincrono)
     */
    public void cargarMedicamentos() {
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            // Como dato le paso el nombre de este agente para que me devuelva los datos
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("cargarMedicamentos", "AgenteAplicacionMedico1", "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Medicamentos desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Borra un medicamento de una visita
     * @param v InfoVisita
     * @param m InfoMedicamento
     */
    public void borrarMed(InfoMedicamento m) {
    	getInformacionAgente();
    	
    	try {
    		Object d[] = {"AgenteAplicacionMedico1",m};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("eliminarMedicamento", d, "VisualizacionHistorial1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Envia un evento a Mensajes para que cargue y envie de vuelta los mensajes (asincrono)
     */
    public void cargarMensajes(String usuario) {
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            Object[] datos = {usuario,"AgenteAplicacionMedico1"};
            
            ItfUsoAgenteReactivo itfUsoMensajeria = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMensajeria1");
            
            // Como dato le paso el nombre de este agente para que me devuelva los datos
            itfUsoMensajeria.aceptaEvento(new EventoRecAgte("cargarMensajes", datos, "VisualizacionMedico1", "AgenteAplicacionMensajeria1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Mensajeria desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Envia un evento a Mensajes para que muestre la ventana de envio de mensaje
     */
    public void enviarMensaje(String usuario) {
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMensajeria = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMensajeria1");
            
            // Como dato le paso el nombre de este agente para que me devuelva los datos
            itfUsoMensajeria.aceptaEvento(new EventoRecAgte("enviarMensaje", usuario, "VisualizacionMedico1", "AgenteAplicacionMensajeria1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Mensajeria desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * -En fase experimental- Aun no funciona
     * @param c
     * @param estilo
     */
    public void cargarTabMed(Composite c, int estilo) {
    	//getInformacionAgente();
    	
    	try {
    	
    		Object d[] = {(Composite)c,estilo};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("mostrarTabMed", d, "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Medicamentos desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Desconecta al usuario
     */
    public void cerrarSesion() {
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoLogin = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionLogin1");
            
            itfUsoLogin.aceptaEvento(new EventoRecAgte("cerrarSesion", "VisualizacionMedico1", "AgenteAplicacionLogin1"));
            
            visualizador.cerrarVisualizadorMedico();
            
            ItfUsoAgenteReactivo itfUsoMedico = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedico1");
            
            itfUsoMedico.aceptaEvento(new EventoRecAgte("cerrarSesion", "VisualizacionMedico1", "AgenteAplicacionMedico1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al cerrar la sesion del Medico");
            e.printStackTrace();
        }
    }
}

