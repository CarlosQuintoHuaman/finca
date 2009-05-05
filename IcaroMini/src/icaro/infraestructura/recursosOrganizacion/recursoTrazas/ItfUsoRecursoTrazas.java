package icaro.infraestructura.recursosOrganizacion.recursoTrazas;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.util.List;
/**
 * @author Francisco J Garijo
 * @version 1.0
 * @created 27-marzo-2008 10:25:43
 */
public interface ItfUsoRecursoTrazas {
	public void visualizacionDeTrazas(Boolean opcionTraza);
	public void aceptaNuevaTraza(InfoTraza traza);
	public void visualizaNuevaTraza(InfoTraza traza);
	public void visualizarComponentesTrazables(List<String> listaElementosaTrazar);
	public void pedirConfirmacionTerminacionAlUsuario();
    public void mostrarMensajeError(String mensaje);
}