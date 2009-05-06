package icaro.aplicaciones.recursos.persistenciaMensajeria.imp.util;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMensajeria.InfoMensaje;
import icaro.aplicaciones.recursos.persistenciaMedicamentos.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	//static private  final String LOGIN="root";
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
		ItfUsoConfiguracion config = (ItfUsoConfiguracion) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
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
	
	/**
	 * Lee de la BD todos los mensajes para un usuario en concreto
	 * @param p
	 * @return
	 */
	public ArrayList<InfoMensaje> getMensajes(String usuario) {
		ArrayList<InfoMensaje> men = new ArrayList<InfoMensaje>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM comunica WHERE Destinatario='"+usuario+"' ORDER BY fecha DESC");
			
			while (resultado.next()) {
				InfoMensaje m = new InfoMensaje(resultado.getString("Remitente"),
										resultado.getString("Destinatario"),
										resultado.getTimestamp("Fecha"),
										resultado.getString("Asunto"),
										resultado.getString("Contenido")
				);
				
				men.add(m);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return men;
	}
	
	/**
	 * Lee de la BD todos los usuarios para un paciente en concreto
	 * @return
	 */
	public ArrayList<String> getUsuarios() {
		ArrayList<String> usuarios = new ArrayList<String>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM usuario");
			
			while (resultado.next()) {
//				InfoMensaje m = new InfoMensaje(resultado.getString("Remitente"),
//										resultado.getString("Destinatario"),
//										resultado.getTimestamp("Fecha"),
//										resultado.getString("Asunto"),
//										resultado.getString("Contenido")
//				);
				
				usuarios.add(resultado.getString("NombreUsuario"));
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarios;
	}	
	
	/**
	 * Inserta un medicamento en la BD
	 * @param m InfoMedicamento con los datos del medicamento
	 * @throws Exception
	 */
	public void insertaMensaje(InfoMensaje m) {
		try {
			crearQuery();
			query.executeUpdate("INSERT INTO comunica (Remitente, Destinatario, Fecha, Asunto, Contenido) VALUES ('" + m.getRemitente() + 
								"', '" + m.getDestinatario() +
								"', '" + m.getFecha() +
								"', '" + m.getAsunto() + 
								"', '" + m.getContenido() + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra un medicamento completamente de la BD
	 * @param m
	 */
	public void eliminarMensaje(InfoMensaje m) {
		try {
			crearQuery();
			query.executeUpdate("DELETE FROM comunica WHERE Remitente='"+m.getRemitente()+"' AND Destinatario='"+m.getDestinatario()
								+ "' AND Fecha='"+m.getFecha()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}