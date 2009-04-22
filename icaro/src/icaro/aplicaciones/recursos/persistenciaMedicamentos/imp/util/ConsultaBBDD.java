package icaro.aplicaciones.recursos.persistenciaMedicamentos.imp.util;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.persistenciaMedicamentos.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

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
	
	/**
	 * Obtiene todos los medicamentos de la BD
	 * @return ArrayList con todos los medicamentos
	 * @throws Exception
	 */
	public ArrayList<InfoMedicamento> getMedicamentos() {
		ArrayList<InfoMedicamento> med = new ArrayList<InfoMedicamento>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM medicamentos");
			
			while (resultado.next()) {
				InfoMedicamento p = new InfoMedicamento(resultado.getInt("Codigo"),
										resultado.getString("Nombre"),
										resultado.getString("PrincipioActivo"),
										resultado.getString("Descripcion"),
										resultado.getString("Indicaciones")
				);
				
				med.add(p);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return med;
	}
	
	/**
	 * Lee de la BD todos los medicamentos para un paciente en concreto
	 * @param p
	 * @return
	 */
	public ArrayList<InfoMedicamento> getMedicamentos(String p, Timestamp fecha) {
		ArrayList<InfoMedicamento> med = new ArrayList<InfoMedicamento>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM Recetas WHERE Paciente='"+p+"' AND FechaVisita='"+fecha+"'");
			
			int r = 0;
			if (resultado.next()) {
				r = resultado.getInt("Codigo");
				
				crearQuery();
				resultado = query.executeQuery("SELECT * FROM MedPorReceta MR, Medicamentos M " +
												"WHERE MR.CodigoRec = " + r + " AND M.Codigo = MR.CodigoMed");
				
				while (resultado.next()) {
					InfoMedicamento m = new InfoMedicamento(resultado.getInt("CodigoMed"),
											resultado.getString("Nombre"),
											resultado.getString("PrincipioActivo"),
											resultado.getString("Descripcion"),
											resultado.getString("Indicaciones")
					);
					
					med.add(m);
				}
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return med;
	}
	
	/**
	 * Inserta un medicamento en la BD
	 * @param m InfoMedicamento con los datos del medicamento
	 * @throws Exception
	 */
	public void insertaMedicamento(InfoMedicamento m) {
		try {
			crearQuery();
			query.executeUpdate("INSERT INTO medicamentos (Nombre,PrincipioActivo,Descripcion,Indicaciones) VALUES ('" + m.getNombre() + 
								"', '" + m.getPa() +
								"', '" + m.getDescripcion() +
								"', '" + m.getIndicaciones() + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Asigna un medicamento a un paciente en una visita. Es decir, añade un medicamento
	 * a la receta de una visita
	 * @param p Nombre de ususario
	 * @param m Medicamento
	 * @param f Fecha de la visita
	 * @throws Exception
	 */
	public void asignaMedicamento(String p, InfoMedicamento m, Timestamp fecha) {
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM Recetas WHERE Paciente='"+p+"' AND FechaVisita='"+fecha+"'");
			
			int r = 0;
			if (resultado.next()) {
				r = resultado.getInt("Codigo");
			} else {
				crearQuery();
				query.executeUpdate("INSERT INTO Recetas (Paciente, FechaVisita, Notas) VALUES " +
									"('"+p+"', '"+fecha+"', 'Receta para "+p+"')");
				
				resultado = query.getGeneratedKeys(); 
				if (resultado.next())
					r = resultado.getInt(1);
			}
			
			if (r > 0) {
				crearQuery();
				query.executeUpdate("INSERT INTO MedPorReceta (CodigoRec,CodigoMed) VALUES ('" + r +
					"', '"+m.getCodigo() +"')");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra un medicamento de la receta de un usuario
	 * @param p Nombre de usaurio
	 * @param t Fecha de la visita
	 * @param m Datos Medicamento
	 * @throws Exception
	 */
	public void borrarMedicamento(String p, Timestamp t, InfoMedicamento m) {
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT Codigo FROM Recetas WHERE Paciente='" + p + "' AND FechaVisita='" + t + "'");
			
			if (resultado.next()) {
				crearQuery();
				query.executeUpdate("DELETE FROM MedPorReceta WHERE CodigoRec='"+resultado.getString("Codigo")+"' AND CodigoMed='"+m.getCodigo()+"'");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra un medicamento completamente de la BD
	 * @param m
	 */
	public void eliminarMedicamento(InfoMedicamento m) {
		try {
			crearQuery();
			query.executeUpdate("DELETE FROM Medicamentos WHERE Codigo='"+m.getCodigo()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}