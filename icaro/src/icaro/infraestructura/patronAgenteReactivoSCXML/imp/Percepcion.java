/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.patronAgenteReactivoSCXML.imp.ItfPercepcionEventos;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author 
 */
public class Percepcion extends LinkedBlockingQueue<EventoInput> implements ItfPercepcionEventos {

    public Percepcion(int capacidad){
        super(capacidad);
    }
    public void aceptaEvento(EventoInput e) {
        super.offer(e);
    }

}
