package icaro.aplicaciones.recursos.visualizacionAccesoAlta.imp;

import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
/**
 * 
 *@author    Francisco J Garijo
 *@created    Febrero 2009
 */

public class NotificacionesAgenteAccesoAlta {
	
	protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
	protected ClaseGeneradoraVisualizacionAccesoAlta generadoraVisualizador;
    protected ItfUsoAgenteReactivo itfUsoAgenteaReportar;
    protected String identificadorAgenteaReportar;
    protected String identificadordeEsteRecurso;
	
	public NotificacionesAgenteAccesoAlta (ClaseGeneradoraVisualizacionAccesoAlta generadoraVis)throws Exception {
// Obtenemos las informaciones que necesitamos de la clase generadora del recurso
        generadoraVisualizador = generadoraVis;
        identificadordeEsteRecurso = generadoraVis.getNombredeEsteRecurso();
        identificadorAgenteaReportar = generadoraVis.getNombreAgenteAReportar();
        itfUsoAgenteaReportar = null;
  // se busca la interfaz de uso del agente a reportar en el repositorio de interfaces
   
   }

	
//	public ClaseGeneradoraVisualizacionAccesoAlta getVisualizador(){
		
//		throw new Exception("hola");
//	}
 public ClaseGeneradoraVisualizacionAccesoAlta getVisualizador() {
        return generadoraVisualizador;

    }
	public void notificacionCierreSistema() throws Exception{
	//cierre de ventanas que genera cierre del sistema
	
		if (itfUsoAgenteaReportar == null)
              getInformacionAgenteaReportar();

        try {
			//me aseguro de crear las interfaces si han sido registradas ya
	//		if (itfUsoRepositorioInterfaces == null){
	//			itfUsoRepositorioInterfaces = visualizador.getItfUsoRepositorioInterfaces();
	//		}
	//		ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
	//		.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta1");

            itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_terminacion_usuario",identificadordeEsteRecurso,NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"Alta1"));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento termina al agente");
			e.printStackTrace();
		}
	}

	
	public void PeticionDarAlta (String username, String password) throws Exception {
		// Lo dejamos de esta forma para que se vea otra manera diferente de hacerlo aunque la forma
        // correcta seria crear un objeto con la info 
        // InfoAccesoSinValidar InfoAutenticacion = new  InfoAccesoSinValidar(username,password);

        Object[] datosEnvio = new Object[]{username, password};
        if (itfUsoAgenteaReportar == null)
              getInformacionAgenteaReportar();
		try {

			itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_alta",datosEnvio,identificadordeEsteRecurso,identificadorAgenteaReportar));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
			e.printStackTrace();
              generadoraVisualizador.informeError("error al enviar peticion de alta  al agente de acceso ");
		}
	}
 public void peticionAutentificacion(String username, String password) throws Exception {
    
     Object[] datosEnvio = new Object[]{username, password};
    if (itfUsoAgenteaReportar == null)
              getInformacionAgenteaReportar();
     try {
			itfUsoAgenteaReportar.aceptaEvento(new EventoRecAgte("peticion_alta",datosEnvio,identificadordeEsteRecurso,identificadorAgenteaReportar));
			
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el usuario y el password al agente de acceso ");
			e.printStackTrace();
              generadoraVisualizador.informeError("error al enviar el usuario y el password al agente de acceso ");
		}
     }
  private void getInformacionAgenteaReportar()throws Exception {
        if (identificadorAgenteaReportar != "" ){
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
            }
            itfUsoAgenteaReportar = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO+ identificadorAgenteaReportar);
        System.out.println("verificando la interfaz de uso");
        }
            catch (Exception e) {
			System.out.println("No se ha podido obtener la interfaz de uso del agente a reportar");
			e.printStackTrace();
            }
                        }
    else {
    throw   new  Exception ("los parametros de entradas son incorrectos. La interfaz del repositorio es nula o el identificador del agente es vacion. Con ellos no se pueden enviar los eventos");
     }
 }
  }