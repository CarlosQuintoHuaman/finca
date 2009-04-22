package icaro.aplicaciones.recursos.persistenciaHistorial.imp.util;

import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoPrueba;
import icaro.aplicaciones.informacion.dominioClases.aplicacionHistorial.InfoVisita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedicamentos.InfoMedicamento;
import icaro.aplicaciones.recursos.persistenciaHistorial.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
	
	/**
	 * Constructor
	 * @param id Nombre del recurso de persistencia. Por ejemplo, PersistenciaAlgo1
	 * @throws Exception
	 */
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

	/**
	 * Se inicializa la variable query
	 * @throws ErrorEnRecursoException
	 */
	public void crearQuery() throws ErrorEnRecursoException {

		try	{
			query = conn.createStatement();	
		}
		catch (SQLException e) {
			throw new ErrorEnRecursoException("No se ha podido crear la sentencia SQL para acceder a la base de datos: " + e.getMessage());
		}			
	}
	
	/**
	 * Obtiene el historial completo de un paciente
	 * @param usuario Nombre de usuario del paciente
	 * @return ArrayList con las visitas que componen el historial
	 */
	public ArrayList<InfoVisita> getHistorial(String usuario) {
		ArrayList<InfoVisita> citas = new ArrayList<InfoVisita>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM visita WHERE Paciente = '"+usuario+"'");
			
			while (resultado.next()) {
				InfoVisita p = new InfoVisita(resultado.getString("Paciente"),
										resultado.getTimestamp("Fecha"),
										resultado.getString("Motivo"),
										resultado.getString("Descripcion"),
										resultado.getString("Exploracion"),
										resultado.getString("Diagnostico"),
										resultado.getString("Tratamiento")
				);
				
				citas.add(p);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return citas;
	}
	
	/**
	 * Obtiene una visita en concreto
	 * @param usuario Nombre de usuario del paciente
	 * @param fecha Fecha de la visita
	 * @return Un objeto InfoVisita con los datos
	 * 		   Si no esta la visita en la BD el objeto visita tendra todos los campos vacios menos
	 * 		   el nombre de usuario y la fecha
	 */
	public InfoVisita getHistorial(String usuario, Timestamp fecha) {
		InfoVisita p = new InfoVisita(usuario,fecha,"","","","","");
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM visita WHERE Paciente = '"+usuario+"' AND Fecha='"+fecha+"'");
			
			while (resultado.next()) {
				p = new InfoVisita(resultado.getString("Paciente"),
										resultado.getTimestamp("Fecha"),
										resultado.getString("Motivo"),
										resultado.getString("Descripcion"),
										resultado.getString("Exploracion"),
										resultado.getString("Diagnostico"),
										resultado.getString("Tratamiento")
				);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return p;
	}
	
	/**
	 * Guarda una visita en la BD. Si la visita ya existe en la BD la actualiza con los nuevos datos.
	 * Si no existe la crea nueva.
	 * @param v Objeto InfoVisita con los datos
	 */
	public void setVisita(InfoVisita v) {
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM Visita WHERE Paciente='"+ v.getUsuario() + 
								"' AND Fecha='" + v.getFecha() + "'");
			
			if (resultado.next()) {
				crearQuery();
				query.executeUpdate("UPDATE visita SET Motivo = '" + v.getMotivo() + 
								"' , Descripcion = '" + v.getDescripcion() +
								"' , Exploracion = '" + v.getExploracion() +
								"' , Diagnostico = '" + v.getDiagnostico() + 
								"' , Tratamiento = '" + v.getTratamiento() +
								"' WHERE Paciente = '" + v.getUsuario() + "' AND Fecha='" + v.getFecha() + "'");
			} else {
				crearQuery();
				query.executeUpdate("INSERT INTO Visita (Paciente, Fecha, Motivo, Descripcion, Exploracion, Diagnostico, Tratamiento) VALUES " +
									"('" + v.getUsuario() + "', '" + v.getFecha() + "', '" + v.getMotivo() + "'," +
											"'" + v.getDescripcion() + "','" + v.getExploracion() + "','" + v.getDiagnostico() + "', '" + v.getTratamiento() + "')");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtiene todas las pruebas de un paciente para una visita concreta
	 * @param usuario Nombre de usuario
	 * @param fecha Fecha de la visita
	 * @return ArrayList con todas las pruebas
	 */
	public ArrayList<InfoPrueba> getPruebas(String usuario, Timestamp fecha) {
		ArrayList<InfoPrueba> pruebas = new ArrayList<InfoPrueba>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM documentos WHERE Paciente = '"+usuario+"' AND FechaVisita='"+fecha+"'");
			
			while (resultado.next()) {
				InfoPrueba p = new InfoPrueba(usuario,
											resultado.getString("Nombre"),
											new Date(resultado.getTimestamp("FechaVisita").getTime()),
											resultado.getString("tipo"),
											resultado.getString("archivo"),
											resultado.getString("descripcion")
				);
				
				pruebas.add(p);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pruebas;
	}
	
	/**
	 * Guarda una prueba en la BD
	 * @param p Objeto InfoPrueba con los datos
	 */
	public void setPrueba(InfoPrueba p) {
		try {
			crearQuery();			
			query.executeUpdate("INSERT INTO documentos (paciente,nombre,fechavisita,tipo,archivo,descripcion) VALUES ('" + p.getPaciente() + 
								"', '" + p.getNombre() +
								"', '" + p.getFecha() +
								"', '" + p.getTipo() +
								"', '" + p.getArchivo() + 
								"', '" + p.getDescripcion() + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra una prueba de la BD
	 * @param p Objeto InfoPrueba de la prueba a borrar
	 */
	public void borrarPrueba(InfoPrueba p) {
		try {
			crearQuery();
			query.executeUpdate("DELETE FROM documentos WHERE Paciente='"+p.getPaciente()+"' AND Nombre='"+p.getNombre()+"' AND FechaVisita='"+p.getFecha()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Borra un medicamento de la BD
	 * @param m Objeto InfoMedicamento del medicamento a borrar
	 */
	public void borrarMedicamento(InfoMedicamento m) {
		try {
			crearQuery();
			query.executeUpdate("DELETE FROM MedPorReceta WHERE CodigoRec='"+m.getCodigo()+"' AND CodigoMed='"+m.getNombre()+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}