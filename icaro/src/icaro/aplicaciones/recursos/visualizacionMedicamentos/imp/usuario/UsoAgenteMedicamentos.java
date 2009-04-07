package icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionMedicamentos.imp.ClaseGeneradoraVisualizacionMedicamentos;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class UsoAgenteMedicamentos {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionMedicamentos visualizador;
    private String nombreAgenteMedicamentos;
    private String tipoAgenteMedicamentos;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteMedicamentos(ClaseGeneradoraVisualizacionMedicamentos vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteMedicamentos = visualizador.getNombreAgenteControlador();
        tipoAgenteMedicamentos = visualizador.getTipoAgenteControlador();

        if (nombreAgenteMedicamentos.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteMedicamentos = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionMedicamentos getVisualizador() {
        return visualizador;

    }
    
    /**
     * - En fase experimental - Metodo para indicar cuando se termina de cargar una
     * ventana para saber cuando se debe obtener una interfaz de otro agente 
     * @param c Composite indicando el que va a ser el "container" de la interfaz externa
     */
    public void cargaFinalizada(Composite c) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedico = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedico1");
            
            itfUsoMedico.aceptaEvento(new EventoInput("mostrarTabMed", c, "VisualizacionMedicamentos1", "AgenteAplicacionMedico1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Asigna un medicamento a un determinado paciente
     * @param p Paciente
     * @param m Medicamento que se le receta
     */
    public void asignarMed(String p, InfoMedicamento m) {
    	getInformacionAgente();
    	
    	try {
    		
    		// Agrupo los datos para enviarlos todos juntos en el evento
    		Object d[] = {p,m};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoInput("asignarMedicamento", d, "VisualizacionMedicamentos1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al asignar el medicamento");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaBusqueda() {
    	visualizador.cerrarVisualizadorBusqueda();
    }
    
    
    /**
     * Inserta un medicamento en la BD
     * @param m InfoMedicamento
     */
    public void insertarMed(InfoMedicamento m) {
    	getInformacionAgente();
    	
    	try {
    		
    		// Agrupo los datos para enviarlos todos juntos en el evento
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoInput("insertarMedicamento", m, "VisualizacionMedicamentos1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaNuevo() {
    	visualizador.cerrarVisualizadorNuevo();
    }
    
    
    

    public void notificacionCierreSistema() {
         try {
             nombreAgenteMedicamentos = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteMedicamentos);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteMedicamentos.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteMedicamentos.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteMedicamentos);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteMedicamentos));
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
}

