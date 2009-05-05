package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp;


import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;



public class RepositorioInterfacesImpLocal extends ClaseGeneradoraRepositorioInterfaces {

	  // Tabla con todos los interfaces
	  private Hashtable<String,Object> repositorio;

	  // Depuracion del componente
	  public final static boolean DEBUG=true;
	  /**
	   * Constructor. Registra el repositorio y lo deja accesible de forma remota.
	 */ 
	  public RepositorioInterfacesImpLocal() {
		  super("RepositorioInterfaces");
		  repositorio = new Hashtable<String, Object>();
	  }
	  


	  /**
	   * Almacena una interfaz en el repositorio
	   * En caso de existir el nombre previamente, se actualiza la referencia
	   * @param nombre
	   * @param interfaz
	   */
	  public synchronized void registrarInterfaz(String nombre, Object interfaz) 
	  {
	    this.repositorio.put(nombre,interfaz);
	    if (DEBUG) System.out.println("Registrado en repositorio nombre="+nombre+", interfaz="+interfaz);
	  }

	  /**
	   * Recupera una interfaz del repositorio
	   * @param nombre  Nombre de la interfaz a recuperar
	   * @return  Interfaz asociada a ese nombre o null si no se ha encontrado ese nombre
	   */
	  public Object obtenerInterfaz(String nombre) 
	  {
	    if ( !this.repositorio.containsKey(nombre) ){
	      System.err.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ning�n objeto con ese nombre");
	      System.err.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
	      System.out.println("RepositorioInterfaces: No se pudo recuperar "+nombre+" porque no existe ning�n objeto con ese nombre");
	      System.out.println("RepositorioInterfaces: Los objetos que hay registrados hasta ahora son -> "+this.listarNombresInterfacesRegistradas());
	    }
	    return this.repositorio.get(nombre);
	  }

	
	  /**
	   * Cancela el registro de una interfaz en el repositorio
	   * @param nombre Nombre de la interfaz a eliminar del repositorio
	   * @throws RemoteException
	   */
	  public synchronized void eliminarRegistroInterfaz(String nombre)
	  {

	    if (this.repositorio.containsKey(nombre))
	    {
	      this.repositorio.remove(nombre);
	      if(DEBUG) System.out.println("Se elimino la referencia a "+nombre+" del repositorio de interfaces.");
	    }
	    else
	      if(DEBUG) System.out.println("Se intento eliminar la referencia "+nombre+" del repositorio, pero no estaba definida.");
	  }
	  
	  
	  /**
	   * Devuelve una lista con los nombres de todos los interfaces registrados
	   * @return
	   * @throws RemoteException
	   */
	  public String listarNombresInterfacesRegistradas()
	  {
	    String ret="";
	    Enumeration enume = this.repositorio.keys();
	    while (enume.hasMoreElements()) {
	      Object item = enume.nextElement();
	      ret += item+" ";
	    }
	    return ret;
	  }
	

	  public String toString()
	  {
	    StringBuffer str= new StringBuffer("Listado de interfaces registrados Nombre/Interfaz");
	    Enumeration enume = this.repositorio.keys();
	    while (enume.hasMoreElements()) {
	      Object key = enume.nextElement();
	      str.append("\n");
	      str.append((String)key);
	      str.append("->");
	      str.append(this.repositorio.get(key));
	    }
	    return str.toString();
	  }

	  
	  /**
	   *  Pruebas
	   
	  public static void main(String[] args) {
	    Object obj1 = new Object();
	    Object obj2 = new Object();
	    Object obj3 = new Object();


	    try {
	      ItfUsoRepositorioInterfaces rep = RepositorioInterfaces.instance();
	    	
	      rep.registrarInterfaz("Objeto UNO",obj1);
	      rep.registrarInterfaz("Objeto DOS",obj2);
	      rep.registrarInterfaz("Objeto tRES",obj3);

	      System.out.println("El objeto uno es "+rep.obtenerInterfaz("Objeto UNO"));
	      System.out.println(""+rep.toString());
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }

	  }
		*/
}
