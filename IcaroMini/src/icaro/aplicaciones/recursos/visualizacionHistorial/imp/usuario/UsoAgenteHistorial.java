package icaro.aplicaciones.recursos.visualizacionHistorial.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.visualizacionHistorial.imp.ClaseGeneradoraVisualizacionHistorial;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Andres Benito Rojas
 *
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

    public void notificacionCierreSistema() {
    	try {
             nombreAgenteHistorial = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteHistorial);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteHistorial.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            if (tipoAgenteHistorial.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteHistorial);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteHistorial));
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
    
    public boolean mostrarMensajePregunta(String mensaje, String titulo){
    	return visualizador.mostrarMensajePregunta(titulo, mensaje);
    }

    /**
     * Muestra la ventana de una visita
     * @param paciente Paciente
     * @param fecha Fecha de la visita
     */
    public void mostrarVentanaHistorial(String paciente, Date fecha) {
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
    
    public void cerrarVentanaHistorial() {
    	visualizador.cerrarVisualizadorHistorial();
    }
    
    public void cerrarVentanaLista() {
    	visualizador.cerrarVisualizadorLista();
    }
    
    /**
     * Muestra la ventana de una prueba
     * @param v InfoVisita
     */
    public void mostrarVentanaPrueba(InfoVisita v) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("mostrarVentanaPrueba", v, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al mostrar el panel Prueba de Historial");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaPrueba() {
    	visualizador.cerrarVisualizadorPrueba();
    }
    
    /**
     * Guarda una visita en la BD
     * @param v Info Visita
     */
    public void guardarVisita(InfoVisita v) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("guardarVisita", v, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Guarda una prueba
     * @param p InfoPrueba
     */
    public void guardarPrueba(InfoPrueba p) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("guardarPrueba", p, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Borra una prueba
     * @param p InfoPrueba
     */
    public void borrarPrueba(InfoPrueba p) {
    	getInformacionAgente();
    	
    	try {
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoHistorial = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionHistorial1");
            
            itfUsoHistorial.aceptaEvento(new EventoRecAgte("borrarPrueba", p, "VisualizacionHistorial1", "AgenteAplicacionHistorial1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra la ventana de busqueda de medicamentos
     * @param paciente
     * @param fecha Fecha de la visita
     */
    public void mostrarVentanaBusquedaMed(String paciente, Timestamp fecha) {
    	getInformacionAgente();
    	
    	try {
    		
    		Object d[] = {paciente,fecha};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("mostrarVentanaBusqueda", d, "AgenteAplicacionHistorial1", "VisualizacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
    
    /**
     * Borra un medicamento de una visita
     * @param v InfoVisita
     * @param m InfoMedicamento
     */
    public void borrarMed(InfoVisita v, InfoMedicamento m) {
    	getInformacionAgente();
    	
    	try {
    		Object d[] = {v.getUsuario(), v.getFecha(), m};
    		
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoMedicamentos = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionMedicamentos1");
            
            itfUsoMedicamentos.aceptaEvento(new EventoRecAgte("borrarMedicamento", d, "VisualizacionHistorial1", "AgenteAplicacionMedicamentos1"));

        } catch (Exception e) {
            System.out.println("Ha habido un error al guardar el Historial");
            e.printStackTrace();
        }
    }
}

