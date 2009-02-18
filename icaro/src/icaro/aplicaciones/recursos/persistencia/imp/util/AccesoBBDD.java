package icaro.aplicaciones.recursos.persistencia.imp.util;

import icaro.aplicaciones.recursos.persistencia.imp.ErrorEnRecursoException;
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
 * Proporciona los servicios de acceso a la bbdd con mysql
 * 
 * @author �lvaro Rodr�guez P�rez
 *
 */
public class AccesoBBDD {
	
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
	 * Script de creacion de tablas de la bbdd
	 */
	static private String ejecutable;//= Configuracion.obtenerParametro("MYSQL_SCRIPT_ITEMS");
	//static private final String ejecutable="createTablasItems.bat";
	
	/**
	 * Constructor por defecto
	 *
	 */
	public AccesoBBDD(){
	}
	
	
	public static void crearEsquema(String id) throws Exception {
		try {			
			conectar(id);
			Statement stmt = null;
			ResultSet rs;
			String msg_text = "";
			System.out.println("--------------Creaci�n del esquema de bbdditems----------------");
			stmt = conn.createStatement();
			stmt.executeUpdate(
					"CREATE DATABASE IF NOT EXISTS "+AccesoBBDD.nombreBD);
			
			rs = stmt.executeQuery(
					"CHECK TABLE "+AccesoBBDD.nombreBD+".USERS");
			while(rs.next()){
		        msg_text= rs.getString("Msg_text").trim();
		      }
			
			//Solo se crean las tablas del script si no existen
			if (!msg_text.equals("OK")){
				System.out.println("--------------Ejecuta script de creaci�n de las tablas bbdditems----------------");
				crearTablas();
				//BDUtils BD=new BDUtils();
				//BD.precargaRuben2();
				
				
			}
			desconectar();
			
		}catch( Exception e ) {
			
			e.printStackTrace();
		}
		
	}
	
	private static boolean crearTablas() throws Exception {
		Runtime r = Runtime.getRuntime();
		Process p ;
		boolean esOK=false;
		try {
			String cmd="mysql --host="+AccesoBBDD.URL_CONEXION.substring(AccesoBBDD.URL_CONEXION.indexOf("://")+3,AccesoBBDD.URL_CONEXION.lastIndexOf(":"))
			+" --port="+AccesoBBDD.URL_CONEXION.substring(AccesoBBDD.URL_CONEXION.lastIndexOf(":")+1,AccesoBBDD.URL_CONEXION.lastIndexOf(":")+5)
			+" --password="+AccesoBBDD.PASSWORD+" --user="+AccesoBBDD.LOGIN+" "+AccesoBBDD.nombreBD+" < "+AccesoBBDD.ejecutable;
			System.out.print("\n"+cmd+"\n\n");
			p = r.exec(cmd);
			try {
				p.waitFor();
				esOK=(p.exitValue()==0);
				if (!esOK){
					System.out.println("Ha habido algun problema al crear las tablas ("+AccesoBBDD.ejecutable+") de la base de datos "+AccesoBBDD.nombreBD);
					
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				esOK=false;
			}
			
		} catch (IOException e) {
			esOK=false;
			System.err.println("Error al crear las tablas ("+AccesoBBDD.ejecutable+") de la base de datos "+AccesoBBDD.nombreBD);
			e.printStackTrace();
		}
		return esOK;
	}
	
	/**
	 * Realiza una conexion (principio de la comunicaci�n) sobre la bbdd
	 * @throws ErrorEnRecursoException
	 */
	
	 private static void conectar(String id) throws ErrorEnRecursoException {
		
		try	{
			ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(id);
			LOGIN = descRecurso.getValorPropiedad("MYSQL_USER");
			PASSWORD = descRecurso.getValorPropiedad("MYSQL_PASSWORD") ;
			URL_CONEXION = descRecurso.getValorPropiedad("MYSQL_URL");
			ejecutable = descRecurso.getValorPropiedad("MYSQL_SCRIPT_ITEMS");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorEnRecursoException("Ha habido un problema al obtener la configuraci�n del recurso de persistencia");
		}
		try{
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
}