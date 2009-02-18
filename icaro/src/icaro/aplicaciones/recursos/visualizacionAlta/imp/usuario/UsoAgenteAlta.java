package icaro.aplicaciones.recursos.visualizacionAlta.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionAlta.imp.ClaseGeneradoraVisualizacionAlta;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class UsoAgenteAlta {
	
	protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	protected ClaseGeneradoraVisualizacionAlta visualizador;
	
	public UsoAgenteAlta (ClaseGeneradoraVisualizacionAlta vis){
		
		visualizador = vis;
	}
	
	public ClaseGeneradoraVisualizacionAlta getVisualizador(){
		return visualizador;
		
	}
	
	public void notificacionCierreSistema(){
	//cierre de ventanas que genera cierre del sistema
	
		try {
			//me aseguro de crear las interfaces si han sido registradas ya
			if (itfUsoRepositorioInterfaces == null){
				itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
			}
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario","VisualizacionAlta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
	}

	
	public void darAlta (String username, String password) {
		Object[] datosEnvio = new Object[]{username, password};
		try {
			if (itfUsoRepositorioInterfaces == null){
				itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
			}
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");;
			itfUsoAgente.aceptaEvento(new EventoInput("alta",datosEnvio,"VisualizacionAlta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
			e.printStackTrace();
		}
	}
}
