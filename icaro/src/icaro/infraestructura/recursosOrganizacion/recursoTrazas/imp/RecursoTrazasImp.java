package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;




import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.RecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorTextual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasEspecificas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.List;

import javax.swing.JOptionPane;


/**
 * @author Felipe Polo
 * @version 1.0
 * @created 27-marzo-2008 10:20:43
 */
public class RecursoTrazasImp extends RecursoTrazas {

	//private Properties properties;
	private ClasificadorVisual visual;
	private ClasificadorTextual textual;

	public RecursoTrazasImp(String id) { 
		super(id);
		creacionRecursoTrazas();
	}
	

	public void creacionRecursoTrazas() {
		
		textual = new ClasificadorTextual();
		visual = new ClasificadorVisual();

	}

	
	public void aceptaNuevaTraza(InfoTraza traza) {
		//saca la informaci�n de manera visual
		
		visual.muestraTraza(traza);
		
		//y la persiste en ficheros de log
		
		textual.clasificaTraza(traza);
	}
	
	
	public void visualizaNuevaTraza(InfoTraza traza) {
		//saca la informaci�n s�lo de manera visual
		
		visual.muestraTraza(traza);
		
	}
	
	
	public void termina() {
		//elimino todas las ventanas
		visual.getPanelPrincipal().dispose();
		List<PanelTrazasEspecificas> listaPaneles = visual.getArrayPaneles();
		for (int i=0;i<listaPaneles.size();i++){
			listaPaneles.get(i).dispose();
		}
		super.termina();
	}
        public void mostrarMensajeError(String mensaje){
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        }

	public void pedirConfirmacionTerminacionAlUsuario() {
		int res = JOptionPane.showConfirmDialog(null, "Confirmar terminaci�n","Confirmar terminaci�n", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		switch(res){
			case JOptionPane.YES_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
					itfUsoAgente.aceptaEvento(new EventoInput("terminacion_confirmada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_confirmada al gestor de organizaci�n");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
			case JOptionPane.NO_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
					itfUsoAgente.aceptaEvento(new EventoInput("terminacion_anulada",NombresPredefinidos.RECURSO_TRAZAS,NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento terminacion_anulada al gestor de organizaci�n");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
		}
		
		
	}


	
	
	
	

	
}