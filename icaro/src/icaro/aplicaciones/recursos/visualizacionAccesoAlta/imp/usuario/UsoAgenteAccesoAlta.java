package icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.usuario;

import icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp.ClaseGeneradoraVisualizacionAccesoAlta;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public class UsoAgenteAccesoAlta {
	
	protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	protected ClaseGeneradoraVisualizacionAccesoAlta visualizador;
	
	public UsoAgenteAccesoAlta (ClaseGeneradoraVisualizacionAccesoAlta vis){
		
		visualizador = vis;
	}
	
	public ClaseGeneradoraVisualizacionAccesoAlta getVisualizador(){
		return visualizador;
		
	}
	
	public void notificacionCierreSistema(){
		//cierre de ventanas que genera cierre del sistema
		
		try {
			//me aseguro de crear las interfaces si han sido registradas ya
			if (itfUsoRepositorioInterfaces == null){
				itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
			}
			/*ItfUsoAgenteAcceso itfUsoAgente = (ItfUsoAgenteAcceso) itfUsoRepositorioInterfaces
			.obtenerInterfaz("AgenteAccesoUso");*/
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
			
			itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario","VisualizacionAccesoAlta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
	}

		public void autentifica(String username, String password) {
		//provoca la petici�n de autentificaci�n
			Object[] datosEnvio = new Object[]{username, password};
			try {
				if (itfUsoRepositorioInterfaces == null){
					itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
				}
				/*ItfUsoAgenteAcceso itfUsoAgente = (ItfUsoAgenteAcceso) itfUsoRepositorioInterfaces
				.obtenerInterfaz("AgenteAccesoUso");*/
				ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
				.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta");
				itfUsoAgente.aceptaEvento(new EventoInput("autenticacion",datosEnvio,"VisualizacionAccesoAlta",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta"));
				
			} catch (Exception e) {
				System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
				e.printStackTrace();
			}
		}

}
