/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.recursosOrganizacion.asistenteDescOrganizacion.imp;

import icaro.herramientas.descripcionorganizacion.asistentecreacion.control.Controlador;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.NombreEvento;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.recursosOrganizacion.asistenteDescOrganizacion.AsistenteDescOrganizacion;

/**
 *
 * @author 
 */
public class AsistenteDescOrganizacionImpl extends AsistenteDescOrganizacion{
    /**
	 * @uml.property  name="estado"
	 */
    private int estado = InterfazGestion.ESTADO_CREADO;

    public void arranca() {
        Evento evento = new Evento();
        evento.setNombreEvento(NombreEvento.INICIO);
        Controlador.getInstancia().evento(evento);
        estado = InterfazGestion.ESTADO_ACTIVO;
    }

    public void para() {
        Evento evento = new Evento();
        evento.setNombreEvento(NombreEvento.CERRAR_TODO);
        Controlador.getInstancia().evento(evento);
        estado = InterfazGestion.ESTADO_PARADO;
    }

    public void termina() {
        Evento evento = new Evento();
        evento.setNombreEvento(NombreEvento.CERRAR_TODO);
        Controlador.getInstancia().evento(evento);
        estado = InterfazGestion.ESTADO_TERMINADO;
    }

    public void continua() {
        estado = InterfazGestion.ESTADO_ACTIVO;
    }

    public int obtenerEstado() {
        return estado;
    }

}
