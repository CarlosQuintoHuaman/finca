package icaro.aplicaciones.recursos.visualizacionMedico.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.recursos.visualizacionMedico.imp.ClaseGeneradoraVisualizacionMedico;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

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
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaLista", paciente, "VisualizacionMedico1", "AgenteAplicacionHistorial1"));

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
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            Timestamp t = new Timestamp(0);
            
            t.setTime(c.getFecha().getTime() + c.getHora().getTime());
            
            Object d[] = {c.getUsuario(),t};
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoInput("mostrarVentanaHistorial", d, "VisualizacionMedico1", "AgenteAplicacionHistorial1"));


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
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoInput("mostrarVentanaNuevo", "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Historial desde el agente Medico");
            e.printStackTrace();
        }
    }
    
    /**
     * Envia un evento a Medicamentos para que cargue y envie de vuelta los medicamentos (asincrono)
     */
    public void cargarMedicamentos() {
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            // Como dato le paso el nombre de este agente para que me devuelva los datos
            itfUsoMedicamentos.aceptaEvento(new EventoInput("cargarMedicamentos", "AgenteAplicacionMedico1", "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Medicamentos desde el agente Medico");
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
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoInput("mostrarTabMed", d, "VisualizacionMedico1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al activar el agente Medicamentos desde el agente Medico");
            e.printStackTrace();
        }
    }
}

