/*
 * RepartidorEventos.java
 *
 * Creado en 26 de noviembre de 2007, 10:21
 *
 *Telef&oacute;nica I+D. &copy; 2007
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp.control;

import icaro.infraestructura.entidadesBasicas.EventoInput;

import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.SCXMLExecutor;
import org.apache.commons.scxml.TriggerEvent;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ProcesadorEventos extends Thread{
    private BlockingQueue<EventoInput> queue;
    private SCXMLExecutor executor;
    private Log log = LogFactory.getLog(ProcesadorEventos.class);
    
    private boolean pararConsumo;
    /** Creates a new instance of RepartidorEventos */
    public ProcesadorEventos(BlockingQueue<EventoInput> queue,SCXMLExecutor executor,boolean daemon) {
        this.queue = queue;
        this.executor = executor;
        this.setDaemon(daemon);

    }
    public void run(){
        pararConsumo = false;
        try{
            while(!pararConsumo){
                EventoInput ev = queue.take();
                log.debug("Evento nuevo tomado del buzon:"+ev.toString());
                if(pararConsumo) break;
                executor.getRootContext().set("Evento",ev);
                executor.triggerEvent( new TriggerEvent(ev.getInput(),TriggerEvent.SIGNAL_EVENT,ev.getParametros()));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void pararConsumo(){
        pararConsumo = true;
    }
}
