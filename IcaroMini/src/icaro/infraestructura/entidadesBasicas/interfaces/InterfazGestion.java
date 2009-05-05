/*
 *  Copyright 2001 Telefónica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.interfaces;


/**
 *  Título: Interfaz de Gestión Completo Copyright: Copyright (c) 2001 Empresa:
 *  Telefónica I+D
 *@author     Felipe Polo
 *@created    3 de diciembre de 2007
 */

public interface InterfazGestion  {
	/**
	 *  Se ha detectado un error irrecuperable en el recurso
	 */
	public final static int ESTADO_ERRONEO_IRRECUPERABLE = -1;
	/**
	 *  Se ha detectado un error que puede subsanarse en el recurso
	 */
	public final static int ESTADO_ERRONEO_RECUPERABLE = -2;
	/**
	 *  El recurso se ha instanciado, pero no se ha arrancado todavía
	 */
	public final static int ESTADO_CREADO = 0;
	/**
	 *  El recurso está arrancando pero no ha terminado todavía
	 */
	public final static int ESTADO_ARRANCANDO = 1;
	/**
	 *  El recurso ha arrancado correctamente y está en espera de peticiones
	 */
	public final static int ESTADO_ACTIVO = 2;
	/**
	 *  El recurso está detenido temporalmente
	 */
	public final static int ESTADO_PARADO = 3;
	/**
	 *  El recurso está terminando su estructura
	 */
	public final static int ESTADO_TERMINANDO = 4;
	/**
	 *  El recurso ha terminado completamente
	 */
	public final static int ESTADO_TERMINADO = 5;
	/**
	 *  El recurso está en un estado indeterminado o especial
	 */
	public final static int ESTADO_OTRO = 10;


	/**
	 *  Inicializa y prepara el elemento para recibir órdenes o información
	 *
	 *@exception  Exception  
	 */
	public void arranca();


	//public void arrancaConEvento();
	
	//public void arrancaConInput(String nombreInput);
	/**
	 *  Detiene el servicio del elemento momentáneamente Esta operacion es OPCIONAL
	 *
	 *@exception  Exception  
	 */
	public void para();


	/**
	 *  Acaba con el uso del elemento y destruye los recursos que estuviesen
	 *  ligados a él
	 *
	 *@exception  Exception
	 */
	public void termina();


	/**
	 *  Reanuda la disponibilidad del elemento si estaba parado, si no lo estaba no
	 *  hace nada. Esta operacion es OPCIONAL
	 *
	 *@exception  Exception
	 */
	public void continua();


	/**
	 *  Comprueba el estado actual del elemento
	 *
	 *@return                      Estado en el que se encuentra el elemento
	 *@exception  Exception
	 */
	public int obtenerEstado();
	
	
	
}
