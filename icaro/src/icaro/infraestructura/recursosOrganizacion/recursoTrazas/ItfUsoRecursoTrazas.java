package icaro.infraestructura.recursosOrganizacion.recursoTrazas;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 * @author Felipe Polo
 * @version 1.0
 * @created 27-marzo-2008 10:25:43
 */
public interface ItfUsoRecursoTrazas {
	
	public void aceptaNuevaTraza(InfoTraza traza);
	public void visualizaNuevaTraza(InfoTraza traza);
	
	public void pedirConfirmacionTerminacionAlUsuario();
        public void mostrarMensajeError(String mensaje);
}