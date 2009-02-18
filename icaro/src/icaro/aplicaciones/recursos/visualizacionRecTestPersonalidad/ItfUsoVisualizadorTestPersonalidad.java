package icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad;



import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;


public interface ItfUsoVisualizadorTestPersonalidad extends ItfUsoRecursoSimple {

	public void accionSalir();
	public void mostrarMensajeError(String descripcion);
	public void mostrarConfirmacion(String[] textosTest);
	public void mostrarVentanaPrincipal(String[] listaAyudas);
	public void variaVentanaPrincipal(int panel, String[] listaPreguntas, int[] resultadosPrevios);
	public void mostrarResultado(String descripcion, int[]resultados, String[] textosTest);
	public void mostrarIncompletitud();
}