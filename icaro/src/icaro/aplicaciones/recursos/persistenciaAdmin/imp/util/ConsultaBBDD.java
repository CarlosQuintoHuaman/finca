package icaro.aplicaciones.recursos.persistenciaAdmin.imp.util;


import icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin.InfoUsuario;
import icaro.aplicaciones.recursos.persistenciaAdmin.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author Camilo Andres Benito Rojas
 *
 */
public class ConsultaBBDD {
	/**
	 * Nombre de la BBDD con la que se trabaja
	 */
	static public  final String nombreBD= "doctoris";
	/**
	 * Nombre de usuario de acceso (con privilegios de root) a la bbdd
	 */
	static private   String login;//= Configuracion.obtenerParametro("MYSQL_USER");
	//static private  final String Admin="root";
	/**
	 * Clave de acceso correspondiente con el usuario indicado
	 */
	static private   String password;//= Configuracion.obtenerParametro("MYSQL_PASSWORD");
	//static private  final String PASSWORD= "adminwww";
	/**
	 * Url en dónde se situa la bbdd
	 */
	static private   String urlConexion;//= Configuracion.obtenerParametro("MYSQL_URL");
	//static private  final String URL_CONEXION="jdbc:mysql://localhost:3306/";
	/**
	 * Objeto resultante de la comunicación establecida con la bbdd
	 */
	private static Connection conn=null;
	/**
	 * Objeto continente de toda la información sobre una orden dada a la bbdd
	 */
	private Statement query;
	/**
	 * Resultado de la consulta a la base de datos
	 */
	private ResultSet resultado;
	
	private String id;
	
	public ConsultaBBDD(String id) throws Exception {
		try {
		ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
		DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(id);
		login = descRecurso.getValorPropiedad("MYSQL_USER");
		password = descRecurso.getValorPropiedad("MYSQL_PASSWORD") ;
		urlConexion = descRecurso.getValorPropiedad("MYSQL_URL");
		//conectar();
		
		
		while (conn == null)
			conn = AccesoBBDD.getConexion();
		System.out.println("Conexion: " + conn.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
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

	
	public ArrayList<InfoUsuario> getUsuarios() throws ErrorEnRecursoException {

		try {
			// TODO Comprobar que la conexion este activa
      		
      		crearQuery();
      		resultado = query.executeQuery("SELECT * FROM usuario");	
			
      		ArrayList<InfoUsuario> usuarios = new ArrayList<InfoUsuario>();
      		
      		while (resultado.next()) {
      			InfoUsuario u = new InfoUsuario(resultado.getString("NombreUsuario"),
      											resultado.getString("Password"),
      											resultado.getString("Nombre"),
      											resultado.getString("Apellido1"),
      											resultado.getString("Apellido2"),
      											resultado.getString("Direccion"),
      											resultado.getString("Telefono"));
      			usuarios.add(u);
      		}

			resultado.close();
			
			return usuarios;	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public void optimizar() throws ErrorEnRecursoException {
		try {
      		crearQuery();
      		resultado = query.executeQuery("OPTIMIZE TABLE usuario");	
			
      	} catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
}