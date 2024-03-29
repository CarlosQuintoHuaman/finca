/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.interfaces;


/**
 *  T�tulo: Interfaz de Gesti�n Completo Copyright: Copyright (c) 2001 Empresa:
 *  Telef�nica I+D
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
	 *  El recurso se ha instanciado, pero no se ha arrancado todav�a
	 */
	public final static int ESTADO_CREADO = 0;
	/**
	 *  El recurso est� arrancando pero no ha terminado todav�a
	 */
	public final static int ESTADO_ARRANCANDO = 1;
	/**
	 *  El recurso ha arrancado correctamente y est� en espera de peticiones
	 */
	public final static int ESTADO_ACTIVO = 2;
	/**
	 *  El recurso est� detenido temporalmente
	 */
	public final static int ESTADO_PARADO = 3;
	/**
	 *  El recurso est� terminando su estructura
	 */
	public final static int ESTADO_TERMINANDO = 4;
	/**
	 *  El recurso ha terminado completamente
	 */
	public final static int ESTADO_TERMINADO = 5;
	/**
	 *  El recurso est� en un estado indeterminado o especial
	 */
	public final static int ESTADO_OTRO = 10;


	/**
	 *  Inicializa y prepara el elemento para recibir �rdenes o informaci�n
	 *
	 *@exception  Exception  
	 */
	public void arranca();


	//public void arrancaConEvento();
	
	//public void arrancaConInput(String nombreInput);
	/**
	 *  Detiene el servicio del elemento moment�neamente Esta operacion es OPCIONAL
	 *
	 *@exception  Exception  
	 */
	public void para();


	/**
	 *  Acaba con el uso del elemento y destruye los recursos que estuviesen
	 *  ligados a �l
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
