package icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.imp;



import icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.ItfUsoVisualizadorTestPersonalidad;
import icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.imp.gui.VentanaTestPersonalidad;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;

import javax.swing.JOptionPane;


public class ClaseGeneradoraVisualizacionTestPersonalidad extends ImplRecursoSimple 
		implements  ItfUsoVisualizadorTestPersonalidad{

	public ClaseGeneradoraVisualizacionTestPersonalidad(String idRecurso) {
		super(idRecurso);
	}

	private static final long serialVersionUID = 1L;
	/**
	 * Ventana principal del Test de Personalidad
	 */
	private VentanaTestPersonalidad ventanaPrincipal;
	/**
	 * Los textos que se deben mostrar en el test
	 */
	private String[] textosDelTest = null;
	/**
	 * Constructor
	 * @throws RemoteException
	 */
	
	/**
	 * Realiza las operaciones para salir del recurso de visualizacion
	 *
	 */
	public void accionSalir(){
		if(ventanaPrincipal.panelMostrado() == 1){ //si esta mostrando el panel de las preguntas
			int salida= JOptionPane.showConfirmDialog(null, this.textosDelTest[19]);
			try {
				ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
				.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
				switch(salida){
				case JOptionPane.YES_OPTION:{
					ventanaPrincipal.cerrar();
					itfUsoAgente.aceptaEvento(new EventoInput("guardarFin",null,null)); //por ahora hay que implementar el guardado
					break;
				}
				case JOptionPane.NO_OPTION:{
					ventanaPrincipal.cerrar();
					itfUsoAgente.aceptaEvento(new EventoInput("terminar",null,null));
					break;
				}
				}		
			} catch (Exception e) {
				System.out.println("Ha habido un error al enviar el evento de cierre al agente del test de personalidad");
				this.estadoAutomata.transita("error");
				e.printStackTrace();
			}
		}
		else{
			try {
				ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
				.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
				ventanaPrincipal.cerrar();
				itfUsoAgente.aceptaEvento(new EventoInput("terminar",null,null));
				
			} catch (Exception e) {
				System.out.println("Ha habido un error al enviar el evento de cierre al agente del test de personalidad");
				this.estadoAutomata.transita("error");
				e.printStackTrace();
			}
		}
	}
	
	public void mostrarConfirmacion(String[] textosTest){
		this.textosDelTest = textosTest;
		int res = JOptionPane.showConfirmDialog(null, this.textosDelTest[20], "Test de personalidad", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			switch(res){
			case JOptionPane.YES_OPTION:{
				ventanaPrincipal = new VentanaTestPersonalidad(this, this.textosDelTest);
				ventanaPrincipal.abrir();
				itfUsoAgente.aceptaEvento(new EventoInput("confirmacion_ok",null,null));
				break;
			}
			case JOptionPane.NO_OPTION:{
				itfUsoAgente.aceptaEvento(new EventoInput("confirmacion_no",null,null)); //por ahora hay que implementar el guardado
				break;
			}
			}		
		} catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de cierre al agente del test de personalidad");
			e.printStackTrace();
			this.estadoAutomata.transita("error");
		}
	}
	/**
	 * Abre la ventana principal y muestra las instrucciones
	 * 
	 */
	public void mostrarVentanaPrincipal(String[] listaAyudas) {
		ventanaPrincipal.muestraInstrucciones(listaAyudas);
	}
	
	/**
	 * Lanza el input para que comience el test
	 *
	 */
	public void comenzar(){
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput(
					"comenzar",new Integer(0),null,null));
				
		} catch (Exception e) {

			e.printStackTrace();
			this.estadoAutomata.transita("error");
		}
	}
	
	/*
	 * Obtiene el resultado de las preguntas respondidas en el panel y genera 
	 * el evento que indica que se ha terminado de responder un panel.
	 */
	public void aceptaResultado(){
		int[] respuestas = null;
		respuestas = ventanaPrincipal.darRespuestas();
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput(
					"infoRespuestas",respuestas,null,null));
			
			
		} catch (Exception e) {
			e.printStackTrace();
			this.estadoAutomata.transita("error");
		}
	}
	
	/**
	 * Carga un nuevo panel en pantalla con las preguntas siguientes
	 */
	public void variaVentanaPrincipal(int panel, String[] listaPreguntas, int[] resultadosPrevios) {
		if(panel==1) //es el primer panel
			ventanaPrincipal.muestraPanelPreguntas();
		ventanaPrincipal.cambiarPanel(panel, listaPreguntas, resultadosPrevios);
	}
	
	/**
	 * Muestra la ventana de Resultado con la descripcion pasada por parametro
	 */
	public void mostrarResultado(String textoResultado, int[] resultados, String[] textos){
		if(ventanaPrincipal == null){
			this.textosDelTest = textos;
			ventanaPrincipal = new VentanaTestPersonalidad(this, this.textosDelTest);
			ventanaPrincipal.abrir();
		}	
		ventanaPrincipal.mostrarResultado(textoResultado, resultados);
	}
	
	/**
	 * Muestra la ventana de Error con la descripcion pasada por parametro
	 */
	public void mostrarMensajeError(String descripcion){
		JOptionPane.showMessageDialog(null,descripcion,"Error",JOptionPane.ERROR_MESSAGE);
	}
	public void mostrarIncompletitud(){
		int res = JOptionPane.showConfirmDialog(null, this.textosDelTest[21], "Test de personalidad", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		switch(res){
			case JOptionPane.YES_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
					ventanaPrincipal.cerrar();
					itfUsoAgente.aceptaEvento(new EventoInput("guardaFin",null,null));
					
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento guardaFin al agente del test de personalidad");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
			case JOptionPane.NO_OPTION:{
				try {
					ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
					.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
					ventanaPrincipal.cerrar();
					itfUsoAgente.aceptaEvento(new EventoInput("terminar",null,null));
					
				} catch (Exception e) {
					System.out.println("Ha habido un error al enviar el evento de cierre al agente del test de personalidad");
					e.printStackTrace();
					this.estadoAutomata.transita("error");
				}
			}
		}
	}
	public void reiniciarTest(){
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput(
					"reinicio",null,null));
				
		} catch (Exception e) {
			this.estadoAutomata.transita("error");
			e.printStackTrace();
		}
	}
	
	public void termina() {
		this.ventanaPrincipal.cerrar();
		super.termina();
	}
}