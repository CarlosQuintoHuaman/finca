package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;


import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;


/**
* Clase interna que se encarga de generar eventos de monitorizaci�n cada cierto tiempo
*
* @author Carlos Delgado
*/
public class HebraMonitorizacion extends Thread {

    /**
     * Milisegundos que esperar� antes de lanzar otra monitorizaci�n
     */
    protected long milis;

    /**
     * Indica cu�ndo debe dejar de monitorizar
     */
    protected boolean finalizar;

    /**
     * Agente reactivo al que se pasan los eventos de monitorizaci�n
     */
     protected ItfUsoAgenteReactivo agente;

     /**
      * Evento a producir
      */
     protected String evento="";

    /**
     * Constructor
     *
     * @param milis Milisegundos a esperar entre cada monitorizaci�n
     * @param eventoAProducir Elemento que se produce en la interfaz cada cierto tiempo
     */
    public HebraMonitorizacion(long milis, ItfUsoAgenteReactivo agente, String eventoAProducir) {
      super("HebraMonitorizaci�n del agente reactivo "+agente.toString());
      this.milis= milis;
      this.finalizar= false;
      this.agente= agente;
      this.setDaemon(true);
      this.evento = eventoAProducir;
    }


    /**
     * Termina la monitorizaci�n
     */
    public void finalizar() {
	this.finalizar= true;
    }

    public void run() {

      this.finalizar= false;

      while (!this.finalizar) {

	  // Duerme lo especificado
	  try {
	    Thread.sleep(this.milis);
	  } catch (InterruptedException ex) {}

	  // Genera un nuevo evento de input
	  if (!this.finalizar)
		try {
			this.agente.aceptaEvento(new EventoInput(this.evento,null,null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
    }

}
