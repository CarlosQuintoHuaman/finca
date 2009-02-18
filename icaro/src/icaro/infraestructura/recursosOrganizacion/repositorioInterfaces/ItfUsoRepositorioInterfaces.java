package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces;

/**
 * Repositorio para acceder de forma centralizada a las interfaces de uso de los componentes
 *
 * @author Sonia Bravo, Jorge M. González Martín
 * @version 1.0
 */



import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

import java.rmi.RemoteException;

public interface ItfUsoRepositorioInterfaces extends ItfUsoRecursoSimple {

  /**
   * Almacena una interfaz en el repositorio
   * En caso de existir el nombre previamente, se actualiza la referencia
   * @param nombre
   * @param interfaz
   */
  public void registrarInterfaz(String nombre, Object interfaz);

  /**
   * Recupera una interfaz del repositorio
   * @param nombre  Nombre de la interfaz a recuperar
   * @return  Interfaz asociada a ese nombre o null si no se ha encontrado ese nombre
 * @throws Exception 
   */
  public Object obtenerInterfaz(String nombre) throws Exception;

  /**
   * Cancela el registro de una interfaz en el repositorio
   * @param nombre Nombre de la interfaz a eliminar del repositorio
   * @throws RemoteException
   */
  public void eliminarRegistroInterfaz(String nombre) throws Exception;

  /**
   * Devuelve los nombres de todos los interfaces registrados
   * @return
   * @throws RemoteException
   */
  public String listarNombresInterfacesRegistradas() throws Exception;
}