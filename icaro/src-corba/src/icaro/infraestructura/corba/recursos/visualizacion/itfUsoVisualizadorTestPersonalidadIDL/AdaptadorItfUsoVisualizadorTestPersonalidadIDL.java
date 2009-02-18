package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL;

import icaro.aplicaciones.recursos.visualizacion.recTestPersonalidad.ItfUsoVisualizadorTestPersonalidad;
import organizacion.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.ItfUsoVisualizadorTestPersonalidadIDL;




/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfUsoVisualizadorTestPersonalidadIDL implements ItfUsoVisualizadorTestPersonalidad {

	private ItfUsoVisualizadorTestPersonalidadIDL stub;
	

	public AdaptadorItfUsoVisualizadorTestPersonalidadIDL(ItfUsoVisualizadorTestPersonalidadIDL stub){
		this.stub = stub;
	}


	public void accionSalir() {
		stub.accionSalir();
	}


	public void mostrarConfirmacion(String[] textosTest) {
		stub.mostrarConfirmacion(textosTest);
	}


	public void mostrarIncompletitud() {
		stub.mostrarIncompletitud();
	}


	public void mostrarMensajeError(String descripcion) {
		stub.mostrarMensajeError(descripcion);		
	}


	public void mostrarResultado(String descripcion, int[] resultados,
			String[] textosTest) {
		stub.mostrarResultado(descripcion, resultados, textosTest);
	}


	public void mostrarVentanaPrincipal(String[] listaAyudas) {
		stub.mostrarVentanaPrincipal(listaAyudas);
	}


	public void variaVentanaPrincipal(int panel, String[] listaPreguntas,
			int[] resultadosPrevios) {
		stub.variaVentanaPrincipal(panel, listaPreguntas, resultadosPrevios);	
	}


}