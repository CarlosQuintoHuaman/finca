package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL;

import icaro.aplicaciones.recursos.visualizacion.recTestPersonalidad.ItfUsoVisualizadorTestPersonalidad;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL._ItfUsoVisualizadorTestPersonalidadIDLImplBase;



/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:13
 */
public class ItfUsoVisualizadorTestPersonalidadIDLServant extends _ItfUsoVisualizadorTestPersonalidadIDLImplBase {

	private ItfUsoVisualizadorTestPersonalidad delegate;
	

	public ItfUsoVisualizadorTestPersonalidadIDLServant(ItfUsoVisualizadorTestPersonalidad delegate){
		this.delegate = delegate;
	}


	public void accionSalir() {
		delegate.accionSalir();
		
	}


	public void mostrarConfirmacion(String[] textosTest) {
		delegate.mostrarConfirmacion(textosTest);
	}


	public void mostrarIncompletitud() {
		delegate.mostrarIncompletitud();		
	}


	public void mostrarMensajeError(String descripcion) {
		delegate.mostrarMensajeError(descripcion);
	}


	public void mostrarResultado(String descripcion, int[] resultados,
			String[] textosTest) {
		delegate.mostrarResultado(descripcion, resultados, textosTest);
	}


	public void mostrarVentanaPrincipal(String[] listaAyudas) {
		delegate.mostrarVentanaPrincipal(listaAyudas);
	}


	public void variaVentanaPrincipal(int panel, String[] listaPreguntas,
			int[] resultadosPrevios) {
		delegate.variaVentanaPrincipal(panel, listaPreguntas, resultadosPrevios);	
	}

	


}