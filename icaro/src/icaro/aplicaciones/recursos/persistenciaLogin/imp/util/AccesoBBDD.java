package icaro.aplicaciones.recursos.persistenciaLogin.imp.util;

import icaro.aplicaciones.recursos.persistenciaLogin.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class AccesoBBDD {
	
	// Los siguientes datos se van a leer del XML de configuracion

	// Nombre de la BBDD con la que se trabaja
	static public final String nombreBD= "doctoris";
	
	// Nombre de usuario de acceso (con privilegios de root) a la BBDD
	static private String login;

	// Clave de acceso correspondiente con el usuario indicado
	static private String password;

	// Url
	static private String urlConexion;

	// Objeto resultante de la comunicacion establecida con la bbdd
	private static Connection conn=null;
	
	// Script de creacion de tablas de la bbdd
	static private String ejecutable;
	
	/**
	 * Constructor por defecto
	 */
	public AccesoBBDD(){
	}

	/**
	 * Realiza una conexion a la BBDD
	 * @throws ErrorEnRecursoException
	 */
	
	 public static Connection conectar(String id) throws ErrorEnRecursoException {
		
		try	{
			//System.out.println("IIIIIIIIIDDDDDDD: "+id);
			//id = "PersistenciaLogin1";
			
			// MUY IMPORTANTE: El id que se pasa como parametro deberia ser algo del estilo "PersistenciaAlgo1"
			// Si este nombre esta mal va a petar
			
			
			// Para acceder al XML
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(id);
			
			// Se leen los datos del XML
			login  = descRecurso.getValorPropiedad("MYSQL_USER");
			password  = descRecurso.getValorPropiedad("MYSQL_PASSWORD") ;
			urlConexion = descRecurso.getValorPropiedad("MYSQL_URL");
			ejecutable = descRecurso.getValorPropiedad("MYSQL_SCRIPT_ITEMS");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema al obtener la configuracion del recurso de persistencia");
		}
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema con la conexion con la base de datos (instanciando el driver connector)");
			
		}
		
		try{
			conn = DriverManager.getConnection(urlConexion,login,password);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema con la conexion con la base de datos "+urlConexion+"\nusuario "+login+
					"\npassword "+password);
			
		}
		
		return conn;
	 
	}
	 
	 public static Connection getConexion() {
		 return conn;
	 }
	 

	/**
	 * Realiza la desconexion de la BBDD
	 * @throws ErrorEnRecursoException
	 */	 
	public static void desconectar() {
		
		try	{
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("\nNo se ha podido cerrar la conexion con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}