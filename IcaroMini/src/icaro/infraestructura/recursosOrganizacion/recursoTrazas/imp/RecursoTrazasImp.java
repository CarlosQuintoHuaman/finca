package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;




import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorTextual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.NotificacionesRecTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * @author Felipe Polo
 * @version 1.0
 * @created 27-marzo-2008 10:20:43
 */
public class RecursoTrazasImp extends ClaseGeneradoraRecursoTrazas {

	//private Properties properties;
	/**
	 * @uml.property  name="visual"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ClasificadorVisual visual;
    private NotificacionesRecTrazas notificador;
	/**
	 * @uml.property  name="textual"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ClasificadorTextual textual;
    private Boolean ActivacionPanelTrazas = false;
 private Logger logger = Logger
			.getLogger(this.getClass().getCanonicalName());

	public RecursoTrazasImp(String id) { 
		super(id);
		creacionRecursoTrazas();
	}
	

	public void creacionRecursoTrazas() {
		  if (NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG.startsWith("t")){
           ActivacionPanelTrazas = true;

            textual = new ClasificadorTextual();
            notificador = new NotificacionesRecTrazas ();
            visual = new ClasificadorVisual(notificador);
         }
	}

	public void visualizacionDeTrazas(Boolean opcionTraza) {
      // Permite cambiar la opcion de visualizar o no las trazas
        ActivacionPanelTrazas = opcionTraza;
    }
	public void aceptaNuevaTraza(InfoTraza traza) {
		//saca la informacion de manera visual
	try {
      if ( ActivacionPanelTrazas ){
		visual.muestraTraza(traza);
		//y la persiste en ficheros de log
		textual.clasificaTraza(traza);
         }
        }
        catch (Exception e) {
				logger.fatal(
								"Error al mostrar la traza. Hay un problema con el recurso de trazas,",
								e);
            }
	}
	
	public  void visualizaNuevaTraza(InfoTraza traza) {
		//saca la informaci�n s�lo de manera visual
		
		visual.muestraTraza(traza);
		
	}
    public void visualizarComponentesTrazables(List<String> listaElementosaTrazar)
    {
       if ( ActivacionPanelTrazas && (listaElementosaTrazar != null )){
        visual.visualizarElementosTrazables(listaElementosaTrazar);
        }
	}
	public void termina() {
		//elimino todas las ventanas

        visual.cerrarVentanas();
	/*
        visual.getPanelPrincipal().dispose();
		HashMap tablaPaneles = visual.getArrayPaneles();
        if (tablaPaneles !=null ){
        String identPanel="";
	    Set conjIdentPanels = tablaPaneles.keySet();
        Iterator<String> iter = conjIdentPanels.iterator();
	    while (iter.hasNext()) {
	       identPanel = iter.next();
            PanelTrazasEspecificas panel = (PanelTrazasEspecificas) tablaPaneles.get(identPanel);
            panel.dispose();
                }
            }
	//	for (int i=0;i<listaPaneles.size();i++){
	//		listaPaneles.get(i).dispose();
	//	}
     */
		super.termina();
	}
        public void mostrarMensajeError(String mensaje){
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }

	public void pedirConfirmacionTerminacionAlUsuario() {
		int res = JOptionPane.showConfirmDialog(null, "Confirmar terminacion","Confirmar terminaci�n", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		switch(res){
			case JOptionPane.YES_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance()
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
					itfUsoAgente.aceptaEvento(new EventoRecAgte("terminacion_confirmada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_confirmada al gestor de organizaci�n");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
			case JOptionPane.NO_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance()
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
					itfUsoAgente.aceptaEvento(new EventoRecAgte("terminacion_anulada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_anulada al gestor de organizaci�n");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
		}
		
		
	}


	
	
	
	

	
}