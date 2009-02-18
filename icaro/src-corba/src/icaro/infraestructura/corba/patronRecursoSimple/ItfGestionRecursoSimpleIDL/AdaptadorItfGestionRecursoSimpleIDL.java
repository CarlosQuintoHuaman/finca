package icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import organizacion.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.ItfGestionRecursoSimpleIDL;

/**
 * @author Damiano Spina
 * @version 1.0
 * @created 01-feb-2008 11:55:05
 */
public class AdaptadorItfGestionRecursoSimpleIDL implements ItfGestionRecursoSimple {

	private ItfGestionRecursoSimpleIDL stub;
	

	public AdaptadorItfGestionRecursoSimpleIDL(ItfGestionRecursoSimpleIDL stub){
		this.stub = stub;
	}



	/**
	 * Inicializa y prepara el elemento para recibir órdenes o información
	 * 
	 */
	public void arranca() {
	  stub.arranca();
	}

	

	/**
	 * Reanuda la disponibilidad del elemento si estaba parado, si no lo estaba no
	 * hace nada. Esta operacion es OPCIONAL
	 *
	 */
	public void continua() {
		stub.continua();
	}

	/**
	 * Comprueba el estado actual del elemento
	 * @return                      Estado en el que se encuentra el elemento
	 * 
	 */
	public int obtenerEstado() {
		return stub.obtenerEstado();
	}

	/**
	 * Detiene el servicio del elemento momentáneamente Esta operacion es OPCIONAL
	 * 
	 */
	public void para() {
		stub.para();
	}

	/**
	 * Acaba con el uso del elemento y destruye los recursos que estuviesen ligados a
	 * él
	 * 
	 */
	public void termina() {
	  stub.termina();
	}

}