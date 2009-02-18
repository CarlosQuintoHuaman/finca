package icaro.aplicaciones.recursos.visualizacionBaseDeDatos.imp.swt;

import org.eclipse.swt.widgets.*;

import icaro.aplicaciones.recursos.visualizacionBaseDeDatos.imp.ClaseGeneradoraVisualizacionBaseDeDatos;
import icaro.aplicaciones.recursos.visualizacionBaseDeDatos.imp.usuario.UsoAgenteBaseDeDatos;

public class PanelBaseDeDatos extends Thread {

	// Variables
	
	/**
	 * comunicacion con el agente (control)
	 * Hay que cambiar "Template" por el nombre del agente.
	 */
	final UsoAgenteBaseDeDatos usoAgente;
	
	// Variables de inicializacion de SWT
	private Display disp;
	private Shell shell;
	private PanelBaseDeDatos este;

	/**
	 * 
	 * @param visualizador
	 */
	public PanelBaseDeDatos(ClaseGeneradoraVisualizacionBaseDeDatos visualizador){
		super("Agenda");
		este = this;
		
		usoAgente = new UsoAgenteBaseDeDatos(visualizador);
	}

	public void run(){
		// Crear el display y generar la ventana pero SIN MOSTRARLA
		// Es decir, no debe haber un shell.open en initGUI()
		disp = new Display();
		initGUI();
	}

	public void mostrar(){
		// Al ser un Thread, SWT nos obliga a enviarle comandos
		// rodeando el codigo de esta manera
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.open();
	       }
         });
	}

	public void ocultar(){
		// NO CONFIRMADO
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.close();
	       }
         });
	}

	public void destruir(){
		disp.asyncExec(new Runnable() {
            public void run() {
         	   shell.dispose();
	       }
         });
	}

	public UsoAgenteBaseDeDatos getAgente(){
		return usoAgente;
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI(){
		// Lo primero es inicializar el Shell
		shell = new Shell(disp);
		
		// Ahora va el codigo de la ventana.
		// ¡Ojo! Las variables de SWT deberian ser globales
	}


	// Aqui iran los metodos especificos de cada ventana

}