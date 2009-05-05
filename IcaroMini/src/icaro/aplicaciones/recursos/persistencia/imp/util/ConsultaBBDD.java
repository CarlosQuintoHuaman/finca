package icaro.aplicaciones.recursos.persistencia.imp.util;

import icaro.aplicaciones.recursos.persistencia.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConsultaBBDD {
	/**
	 * Nombre de la BBDD con la que se trabaja
	 */
	static public  final String nombreBD= "bbddejemplo";
	/**
	 * Nombre de usuario de acceso (con privilegios de root) a la bbdd
	 */
	static private   String LOGIN;//= Configuracion.obtenerParametro("MYSQL_USER");
	//static private  final String LOGIN="root";
	/**
	 * Clave de acceso correspondiente con el usuario indicado
	 */
	static private   String PASSWORD;//= Configuracion.obtenerParametro("MYSQL_PASSWORD");
	//static private  final String PASSWORD= "adminwww";
	/**
	 * Url en d�nde se situa la bbdd
	 */
	static private   String URL_CONEXION;//= Configuracion.obtenerParametro("MYSQL_URL");
	//static private  final String URL_CONEXION="jdbc:mysql://localhost:3306/";
	/**
	 * Objeto resultante de la comunicaci�n establecida con la bbdd
	 */
	private static Connection conn=null;
	/**
	 * Objeto continente de toda la informaci�n sobre una orden dada a la bbdd
	 */
	private Statement query;
	/**
	 * Resultado de la consulta a la base de datos
	 */
	private ResultSet resultado;
	
	private String id;
	
	public ConsultaBBDD(String id) throws Exception {
		try {
		ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
		DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(id);
		LOGIN = descRecurso.getValorPropiedad("MYSQL_USER");
		PASSWORD = descRecurso.getValorPropiedad("MYSQL_PASSWORD") ;
		URL_CONEXION = descRecurso.getValorPropiedad("MYSQL_URL");
		conectar();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
	}
	
	/**
	 * Realiza una conexion (principio de la comunicaci�n) sobre la bbdd
	 * @throws ErrorEnRecursoException
	 */
	
	 private static void conectar() throws ErrorEnRecursoException {
		
		try	{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema con la conexion con la base de datos (instanciando el driver connector)");
			
		}
		
		try{
			conn = DriverManager.getConnection(URL_CONEXION,LOGIN,PASSWORD);	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema con la conexion con la base de datos "+URL_CONEXION+"\nusuario "+LOGIN+
					"\npassword "+PASSWORD);
			// TODO Bloque catch generado autom�ticamente
			
		}
	 
	}
	 
	/**
	 * Realiza la desconexion (fin de la comunicaci�n) sobre la bbdd
	 * @throws ErrorEnRecursoException
	 */
	 
	 
	public static void desconectar() {
		
		try	{
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("\nNo se ha podido cerrar la conexi�n con la base de datos: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	public void crearQuery() throws ErrorEnRecursoException {

		try	{
			query = conn.createStatement();	
		}
		catch (SQLException e) {
			throw new ErrorEnRecursoException("No se ha podido crear la sentencia SQL para acceder a la base de datos: " + e.getMessage());
		}			
	}
	
	/**
	 * Valida al usuario frente a la Base de Datos
	 * 
	 * 
	 * @param usuario El nombre de usuario
	 * @param password La contrase�a del usuario
	 * @return true si se encuentra en la base de datos, false si no aparece.
	 * @throws Exception Si ocurre un fallo de conexi�n
	 */
	public boolean compruebaUsuario(String usuario, String password) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
      		conectar();
      		crearQuery();
      		resultado = query.executeQuery("SELECT * FROM "+ this.nombreBD +".tb_acceso U where U.user = '"
      					+ usuario + "' and U.password = '" + password + "'");	
			if (resultado.next()) estado = true;
			else estado = false;
			resultado.close();
			desconectar();
			return estado;	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public boolean compruebaNombreUsuario(String usuario) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
      		conectar();
      		crearQuery();
      		resultado = query.executeQuery("SELECT * FROM "+ this.nombreBD +".tb_acceso U where U.user = '"
      					+ usuario + "'");	
			if (resultado.next()) estado = true;
			else estado = false;
			resultado.close();
			desconectar();
			return estado;	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public void insertaUsuario (String usuario, String password) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
      		conectar();
      		crearQuery();
      		query.executeUpdate("INSERT INTO "+ this.nombreBD +".tb_acceso VALUES ('"+
      				usuario+"','"+password+"')");
			desconectar();
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
}