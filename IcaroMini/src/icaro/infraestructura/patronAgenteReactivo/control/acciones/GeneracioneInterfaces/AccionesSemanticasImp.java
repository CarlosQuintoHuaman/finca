package icaro.infraestructura.patronAgenteReactivo.control.acciones.GeneracioneInterfaces;

import icaro.infraestructura.patronAgenteReactivo.control.acciones.*;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.ExcepcionEjecucionAcciones;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
        
        
        /**
         *  Define la clase de objetos que pueden contener acciones sem�nticas, que se
         *  ejecutar�n din�micamente por el aut�mata
         *
         *	@author		Jorge Gonz�lez, David �lvarez Mart�nez
         *	@created	7 de septiembre de 2001
         *	@modified	22 de junio de 2006
         *	@version	2.0
         */

import java.util.logging.Level;
public class AccionesSemanticasImp extends AccionesSemanticasAbstracto {

    /**
     * Objeto que almacena las funciones
     * @uml.property  name="accionesSemanticas"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    protected AccionesSemanticasAgenteReactivo accionesSemanticas;
    /**
     * Pr�xima acci�n que ejecutamos
     * @uml.property  name="accionSemanticaAEjecutar"
     */
    protected String accionSemanticaAEjecutar;
    /**
     * Lista de par�metros de la acci�n sem�ntica a ejecutar
     * @uml.property  name="parametros" multiplicity="(0 -1)" dimension="1"
     */
    protected Object[] parametros;

    /**
     *  Constructor
     *	@param accionesSemanticas Clase que contiene las acciones ejecutables java
     */
    public AccionesSemanticasImp(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        super("Acciones semanticas");
        this.accionesSemanticas = accionesSemanticas;
    }

    /**
     *  Asigna la acci�n a ejecutar
     *	@param  accion  Nombre del m�todo
     */
    public void setAccion(String accion) {
        accionSemanticaAEjecutar = accion;
    }

    /**
     * Asigna los par�metros de la acci�n a ejecutar
     * @param parametros  Lista de par�metros de la acci�n
     * @uml.property  name="parametros"
     */
    public void setParametros(Object[] parametros) {
        this.parametros = parametros;
    }

    /**
     * @return  Returns the accionesSemanticas.
     * @uml.property  name="accionesSemanticas"
     */
    public AccionesSemanticasAgenteReactivo getAccionesSemanticas() {
        return accionesSemanticas;
    }

    /**
     * @param accionesSemanticas  The accionesSemanticas to set.
     * @uml.property  name="accionesSemanticas"
     */
    public void setAccionesSemanticas(AccionesSemanticasAgenteReactivo accionesSemanticas) {
        this.accionesSemanticas = accionesSemanticas;
    }
    /**
     *  Decide el modo de ejecuci�n dependiendo de la acci�n sem�ntica a realizar.
     *  Funciona como dispatcher de funciones. Debe evolucionar hacia una tabla
     *  donde re recoja el tipo de acci�n sem�ntica de la que se trata => modificar
     *  el aut�mata para especificar transiciones bloqueantes o no bloqueantes.
     *
     *	@param  accion Nombre del m�todo a ejecutar
     *	@param  modoBloqueante Marca la espera o no por el resultado, en caso de ser
     *      no bloqueante se ejecuta en una nueva hebra
     */
   	public void ejecutarAccion(String accion, Object[] parametros, boolean modoBloqueante) throws ExcepcionEjecucionAcciones {
		      /**/
		if (accion != null)	{
			if (modoBloqueante){
                    ejecutarAccionBloqueante(accion, parametros); 
           }                
			else this.ejecutarAccionEnNuevaHebra(accion,parametros);
		}
		else System.out.println("AVISO: Accion Ignorada.La accion a ejecutar era null");
	}

	/**
	 *  M�todo que ejecuta la acci�n en una nueva hebra
	 */
	public void run() {
        try {
            ejecutarAccionBloqueante(accionSemanticaAEjecutar, parametros);
        } catch (ExcepcionEjecucionAcciones ex) {
            Logger.getLogger(AccionesSemanticasImp.class.getName()).log(Level.SEVERE, null, ex);
        }
	}


	/**
	 *  M�todo que ejecuta la acci�n din�micamente esperando su vuelta
	 *
	 *	@param nombre Nombre del m�todo a ejecutar
	 */
	protected void ejecutarAccionBloqueante(String nombre, Object[] parametros) throws ExcepcionEjecucionAcciones {
		
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
			System.out.println("ERROR en los privilegios de acceso (no es publico) para en metodo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "se ha producido un IlegalAccessIAE");
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el m�todo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
            throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando metodo con parametros de sus superclases correspondientes");
			throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion NoSuchMethodError");
            //ejecutarAccionBloqueantePolimorfica(nombre, parametros, 1);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecucion del metodo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepcion producida en el metodo: ");
			ite.getTargetException().printStackTrace();
		throw new ExcepcionEjecucionAcciones( "AccionesSemanticasImp", "error al ejecutar un metodo"+ nombre + " de la clase: " + accionesSemanticas.getClass().getName(),
                                                          "El metodo a invocar no existe. Se ha producido una excepcion InvocationTargetException");
        }
	}

	/**
	 *  M�todo que ejecuta la acci�n din�micamente esperando su vuelta. 
	 *  Con el orden se va evaluando distintos niveles de herencia en los par�metros teniendo en
	 *  cuenta que pueden pasarse infinitos par�metros. El modo de operaci�n consiste en probar llamando
	 *  a m�todos con las clases finales en la jerarqu�a de herencia y en caso de no encontrar el
	 *  m�todo solicitado, subir un nivel de herencia para el primer par�metro manteniendo el resto
	 *  hasta llegar a la clase Object (primer nivel de la jeranqu�a). En ese momento se sube un nivel en
	 *  la jerarqu�a del segundo par�metro...
	 *
	 *	@param nombre Nombre del m�todo a ejecutar
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
			System.out.println("ERROR en los privilegios de acceso (no es p�blico) para en m�todo: " + nombre + " de la clase: " + accionesSemanticas.getClass().getName());
			iae.printStackTrace();
		}
		catch (NoSuchMethodError nsme) {
			System.out.println("ERROR (NO EXISTE) al invocar el m�todo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsme.printStackTrace();
		}
		catch (NoSuchMethodException nsmee) {
			System.out.println("ERROR (NO EXISTE) al invocar el m�todo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			nsmee.printStackTrace();
			System.out.println("Invocando m�todo con par�metros de sus superclases correspondientes");
			if(!salir) ejecutarAccionBloqueantePolimorfica(nombre, parametros, ++nivel);
		}
		catch (InvocationTargetException ite) {
			System.out.println("ERROR en la ejecuci�n del m�todo: " + nombre + " en la clase: " + accionesSemanticas.getClass().getName());
			ite.printStackTrace();
			System.out.println("Excepci�n producida en el m�todo: ");
			ite.getTargetException().printStackTrace();
		}
	}

	/**
	 *  Ejecutar la acci�n en una hebra nueva y volver
	 *
	 *@param nombre  Nombre del m�todo a ejecutar
	 *@param parametros Par�metros del m�todo a ejecutar
	 */
	protected void ejecutarAccionEnNuevaHebra(String nombre, Object[] parametros) {
		
		this.setAccion(nombre);
		this.setParametros(parametros);
		this.setDaemon(true);
		this.start();
	}
}
