package icaro.aplicaciones.recursos.persistenciaFicha.imp.util;


import icaro.aplicaciones.recursos.persistenciaMedico.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import icaro.util.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


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
	 * Consulta con la que obtenemos la lista de pacientes para cada medico de la lista que se le pasa por parametro en una fecha determinada
	 *  que se le pasa por parametro
	 * @param fecha		:: fecha concreta de las citas que queremos consultar
	 * @param lnombres	:: Lista de nombres de todos los medicos cuyas citas queremos obtener
	 * @return medicos	:: devuelve una arrayList de datosmedicos (nomMedico,Citas para ese medico especificando la fecha) 
	 */
	/*public ArrayList<DatosMedico> getCitas(String fecha, ArrayList<String> lnombres) {
		ArrayList<DatosMedico> medicos = new ArrayList<DatosMedico>();
		
		try {		
			
			Date f1=util.StrToDateSQL(fecha);
			Date f2 = new Date();
			DatosMedico[]  datos=new DatosMedico[lnombres.size()];
			f2.setTime(f1.getTime()+86400000);
			String f11=util.getStrDateSQL(f1).substring(0, 10);
			String f22=util.getStrDateSQL(f2).substring(0, 10);
			ArrayList[] arrayLists = new ArrayList[lnombres.size()];
			ArrayList<DatosCitaSinValidar>[] citas = arrayLists;
			
			for(int i=0;i<lnombres.size();i++){
				datos[i]=new DatosMedico(lnombres.get(i));
				datos[i].setIntervalo(15);
				citas[i]=new ArrayList<DatosCitaSinValidar>();
			}
			crearQuery();
			//resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Fecha < '" + fecha2 + "'");
			resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
			while (resultado.next()) {
				
				String paciente=resultado.getString("Paciente");
				String aux[]= paciente.split(" ");
				String apellido = "";
				for (int i=1;i<aux.length;i++){
					apellido=apellido+aux[i]+" ";
				}
				String medico=resultado.getString("Medico");
				 resultado.getTimestamp("Fecha").toString();

				for(int i=0;i<lnombres.size();i++){
					if (medico.equals(lnombres.get(i))){
						
						DatosCitaSinValidar p = new DatosCitaSinValidar(aux[0],apellido,"918765412",resultado.getTimestamp("Hora").toString().substring(11, 16),1);
						
						citas[i].add(p);
						datos[i].setDatos(citas[i]);
					}
				}
			}
			for(int i=0;i<lnombres.size();i++){
				medicos.add(datos[i]);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return medicos;
	}*/
	

}