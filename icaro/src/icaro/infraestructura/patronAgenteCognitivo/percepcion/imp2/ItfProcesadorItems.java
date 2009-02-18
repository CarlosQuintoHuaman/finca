package icaro.infraestructura.patronAgenteCognitivo.percepcion.imp2;

public interface ItfProcesadorItems {
	public boolean procesarItem(Object item);

	public void termina();

	void arranca();
}
