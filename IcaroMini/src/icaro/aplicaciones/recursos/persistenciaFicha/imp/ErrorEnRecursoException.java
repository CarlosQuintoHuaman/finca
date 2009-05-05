package icaro.aplicaciones.recursos.persistenciaFicha.imp;


public class ErrorEnRecursoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ErrorEnRecursoException(){
		super();
	}
	public ErrorEnRecursoException(String mensaje){
		super("Error en recurso de persistencia \n"+mensaje);
	}
}
