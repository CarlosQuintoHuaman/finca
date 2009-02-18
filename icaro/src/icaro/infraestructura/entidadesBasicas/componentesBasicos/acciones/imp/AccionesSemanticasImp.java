package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.imp;

import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 *  Define la clase de objetos que pueden contener acciones semánticas, que se
 *  ejecutarán dinámicamente por el autómata
 *
 *	@author		Jorge González, David Álvarez Martínez
 *	@created	7 de septiembre de 2001
 *	@modified	22 de junio de 2006
 *	@version	2.0
 */

public class AccionesSemanticasImp extends  AccionesSemanticasAbstracto{

	/** Objeto que almacena las funciones */
	protected AccionesSemanticasAgenteReactivo accionesSemanticas;
	
	/** Próxima acción que ejecutamos */
	protected String accionSemanticaAEjecutar;
	
	/** Lista de parámetros de la acción semántica a ejecutar */
	protected Object[] parametros;


	/**
	 *  Constructor
	 *	@param accionesSemanticas Clase que contiene las acciones ejecutables java
	 */
	public AccionesSemanticasImp(AccionesSemanticasAgenteReactivo accionesSemanticas) {
		super("Acciones semánticas");
		this.accionesSemanticas = accionesSemanticas;
	}


	/**
	 *  Asigna la acción a ejecutar
	 *	@param  accion  Nombre del método
	 */
	public void setAccion(String accion) {
		accionSemanticaAEjecutar = accion;
	}
	
	/**
	 *  Asigna los parámetros de la acción a ejecutar
	 *	@param  parametros Lista de parámetros de la acción
	 */
	public void setParametros(Object[] parametros) {
		this.parametros = parametros;
	}

	/**
	 * @return Returns the accionesSemanticas.
	 */
	public AccionesSemanticasAgenteReactivo getAccionesSemanticas() {
		return accionesSemanticas;
	}

	/**
	 * @param accionesSemanticas The accionesSemanticas to set.
	 */
	public void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas) {
		this.accionesSemanticas = accionesSemanticas;
	}


	/**
	 *  Decide el modo de ejecución dependiendo de la acción semántica a realizar.
	 *  Funciona como dispatcher de funciones. Debe evolucionar hacia una tabla
	 *  donde re recoja el tipo de acción semántica de la que se trata => modificar
	 *  el autómata para especificar transiciones bloqueantes o no bloqueantes.
	 *
	 *	@param  accion Nombre del método a ejecutar
	 *	@param  modoBloqueante Marca la espera o no por el resultado, en caso de ser
	 *      no bloqueante se ejecuta en una nueva hebra
	 */
	public void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) {
		
		if (accion != null)	{
			if (modoBloqueante) ejecutarAccionBloqueante(accion,parametros);
			else this.ejecutarAccionEnNuevaHebra(accion,parametros);
		}
		else System.out.println("AVISO: Acción Ignorada.La acción a ejecutar era null");
	}

	/**
	 *  Método que ejecuta la acción en una nueva hebra
	 */
	public void run() {
		ejecutarAccionBloqueante(accionSemanticaAEjecutar,parametros);
	}


	/**
	 *  Método que ejecuta la acción dinámicamente esperando su vuelta
	 *
	 *	@param nombre Nombre del método a ejecutar
	 */
	protected void ejecutarAccionBloqueante(String nombre, Object[] parametros) {
		
		Class params[] = {};
		Object paramsObj[] = {};
		
		if (parametros != null && parametros.length > 0) {
			params = new Class[parametros.length];
			paramsObj = new Object[parametros.length];
			for (int i=0; i<parametros.length; i++) {
				params[i] = parametros[i].getClass();
				paramsObj[i] = parametros[i];
			}
		}
		else {
			params = new Class[0];
			paramsObj = new Object[0];
		}

		try	{
			Class thisClass = accionesSemanticas.getClass();
			Object iClass = accionesSemanticas;
			Method thisMethod = thisClass.getMethod(nombre, params);
			thisMethod.invoke(iClass, paramsObj);
		}
		catch (IllegalAccessException iae) {
			System.out.println("ERROR en los privilegios de acceso (no es público) para en método: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando método con parámetros de sus superclases correspondientes");
			//ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecución del método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepción producida en el método: ");
			ite.getTargetException().printStackTrace();
		}
	}

	/**
	 *  Método que ejecuta la acción dinámicamente esperando su vuelta. 
	 *  Con el orden se va evaluando distintos niveles de herencia en los parámetros teniendo en
	 *  cuenta que pueden pasarse infinitos parámetros. El modo de operación consiste en probar llamando
	 *  a métodos con las clases finales en la jerarquía de herencia y en caso de no encontrar el
	 *  método solicitado, subir un nivel de herencia para el primer parámetro manteniendo el resto
	 *  hasta llegar a la clase Object (primer nivel de la jeranquía). En ese momento se sube un nivel en
	 *  la jerarquía del segundo parámetro...
	 *
	 *	@param nombre Nombre del método a ejecutar
	 */
	protected void ejecutarAccionBloqueantePolimorfica(String nombre, Object[] parametros, int nivel) {
		
		Class params[] = {};
		Object paramsObj[] = {};
		boolean salir = false;
		
		if (parametros != null && parametros.length > 0) {
			params = new Class[parametros.length];
			paramsObj = new Object[parametros.length];
			for (int i=0; i<parametros.length; i++) {
				params[i] = parametros[i].getClass();
				for (int j=0; j<nivel; j++) {
					if(params[i].getSuperclass() != null) {
						params[i] = params[i].getSuperclass();
						System.out.println("\n\nCLASS: "+params[i].toString()+"\n\n");
						salir = false;
					} else {salir = true;}
				}
				paramsObj[i] = parametros[i];
			}
		}
		else {
			params = new Class[0];
			paramsObj = new Object[0];
		}

		try	{
			Class thisClass = accionesSemanticas.getClass();
			Object iClass = accionesSemanticas;
			Method thisMethod = thisClass.getMethod(nombre, params);
			thisMethod.invoke(iClass, paramsObj);
		}
		catch (IllegalAccessException iae) {
			System.out.println("ERROR en los privilegios de acceso (no es público) para en método: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando método con parámetros de sus superclases correspondientes");
			if(!salir) ejecutarAccionBloqueantePolimorfica(nombre, parametros, ++nivel);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecución del método: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepción producida en el método: ");
			ite.getTargetException().printStackTrace();
		}
	}

	/**
	 *  Ejecutar la acción en una hebra nueva y volver
	 *
	 *@param nombre  Nombre del método a ejecutar
	 *@param parametros Parámetros del método a ejecutar
	 */
	protected void ejecutarAccionEnNuevaHebra(String nombre, Object[] parametros) {
		
		this.setAccion(nombre);
		this.setParametros(parametros);
		this.setDaemon(true);
		this.start();
	}
}
