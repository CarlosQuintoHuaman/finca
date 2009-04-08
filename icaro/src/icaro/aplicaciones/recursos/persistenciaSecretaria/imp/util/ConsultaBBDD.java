package icaro.aplicaciones.recursos.persistenciaSecretaria.imp.util;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
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
	 * Url en d�nde se situa la bbdd
	 */
	static private   String urlConexion;//= Configuracion.obtenerParametro("MYSQL_URL");
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
	 * Consulta para obtener los pacientes con todos sus datos
	 * @return ArrayList de infoPaciente (nomUsuario, password, nombre, telefono, apellido1, apellido2, direccion, telefono, seguro)
	 */
	public ArrayList<InfoPaciente> getPacientes() {
		ArrayList<InfoPaciente> pacientes = new ArrayList<InfoPaciente>();
		
		try {
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM usuario U, paciente P WHERE P.NombreUsuario = U.NombreUsuario");
			
			while (resultado.next()) {
				InfoPaciente p = new InfoPaciente(resultado.getString("NombreUsuario"),
												resultado.getString("Password"),
												resultado.getString("Nombre"),
												resultado.getString("Apellido1"),
												resultado.getString("Apellido2"),
												resultado.getString("Direccion"),
												resultado.getString("Telefono"),
												resultado.getString("Seguro")
				);
				
				pacientes.add(p);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pacientes;
	}
	
	/**
	 * Consulta con la que obtenemos la lista de pacientes para cada medico de la lista que se le pasa por parametro en una fecha determinada
	 *  que se le pasa por parametro
	 * @param fecha		:: fecha concreta de las citas que queremos consultar
	 * @param lnombres	:: Lista de nombres de todos los medicos cuyas citas queremos obtener
	 * @return medicos	:: devuelve una arrayList de datosmedicos (nomMedico,Citas para ese medico especificando la fecha) 
	 */
	public ArrayList<DatosMedico> getCitas(String fecha, ArrayList<String> lnombres) {
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
	}
	
	/**
	 * Consulta con la que se obtiene la lista de medicos que tienen como secretaria la que se pasa por parametro
	 * @param s			:: Nombre de la secretaria de la que se quiere consultar sus medicos asociados
	 * @return medicos	:: ArrayList nombresMedicos
	 */
	public ArrayList<String> getMedicos(String s) {
		ArrayList<String> medicos = new ArrayList<String>();
		
		try {	
			
			crearQuery();
			//resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Fecha < '" + fecha2 + "'");
			resultado = query.executeQuery("SELECT * FROM tieneagenda WHERE Secretaria = '" + s + "'");
			while (resultado.next()) {
				String m=resultado.getString("Medico");			
				medicos.add(m);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return medicos;
	}
	
	/**
	 * Esta consulta nos sirve para obtener las citas posteriores al dia de hoy de un determinado paciente que se le pasa por parametro
	 * @param n			:: nombre del paciente
	 * @param t			:: telefono => pasara a ser de la persistencia mas adelante
	 * @param fecha		:: fecha concreta sobre la q deseamos hacer la consulta que suele ser la fecha actual
	 * @return p 		:: ArrayList DatosCita (nombre Paciente, telefono, medico, fecha, hora)
	 */
	public ArrayList<DatosCita> getPaciente(String n ,String t, String fecha) {
		ArrayList<DatosCita> p = new ArrayList<DatosCita>();
		
		try {	
			
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Paciente = '" + n + "'");
			//resultado = query.executeQuery("SELECT * FROM tieneagenda WHERE Secretaria = '" + s + "'");
			while (resultado.next()) {
				String m=resultado.getString("Medico");	
				String f=resultado.getTimestamp("Fecha").toString();
				String h=resultado.getTimestamp("Hora").toString();
				DatosCita c=new DatosCita(n,t,m,f,h);
				p.add(c);
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	/**
	 *  Con esta consulta pretendemos guardar los cambios que hayamos hecho en la agenda para el dia actual 
	 * @param s			:: parametro que contiene toda la informacion de una una agenda para una secretaria y un dia concreto(fecha actual).
	 * OJO: hay que a�adir parametro fecha!!!
	 * @return boolean	:: devuelve cierto si ha ido bien y falso en caso contrario
	 */
	public boolean meteAgenda(DatosSecretaria s) {
		try {	
			//borramos tabla medicoPaciente para los medicos de esa secretaria
			for(int i=0; i<s.getNumM();i++){
				String medico=s.getMedicos().get(i).getNombre();
				Date f1=util.StrToDateSQL(s.getFecha());
				Date f2 = new Date();
				
				f2.setTime(f1.getTime()+86400000);
				String f11=util.getStrDateSQL(f1).substring(0, 10);
				String f22=util.getStrDateSQL(f2).substring(0, 10);

				crearQuery();
				query.executeUpdate("DELETE FROM medicopaciente WHERE Medico = '" + medico + "' AND Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
			}
			//miramos si tenemos que insertar el paciente o si ya esta dado de alta en la tabla pacientes
			for(int i=0; i<s.getNumM();i++){
				for(int j=0;j<s.getMedicos().get(i).getDatos().size();j++){
					String nom=s.getMedicos().get(i).getDatos().get(j).tomaNombre();
					String telf=s.getMedicos().get(i).getDatos().get(j).tomaTelf();
					crearQuery();
					//resultado = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + nom + "' AND Telefono = '" + telf + "'");
					resultado = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + nom + "'");
					//si no existe debe darse de alta el paciente como usuario y como paciente
					if (!resultado.next()){
						crearQuery();
						query.executeUpdate("INSERT INTO usuario (NombreUsuario, Nombre, Telefono) VALUES " +"('"+nom+"', '"+nom+"', '"+telf+"')");
						crearQuery();
						query.executeUpdate("INSERT INTO paciente (Nombre, Telefono) VALUES " +"('"+nom+"', '"+telf+"')");
					}
				}
			}
			for(int i=0; i<s.getNumM();i++){
				for(int j=0;j<s.getMedicos().get(i).getDatos().size();j++){
					String nom=s.getMedicos().get(i).getDatos().get(j).tomaNombre();
					String f=s.getFecha().substring(0, 10);
					String h=s.getMedicos().get(i).getDatos().get(j).tomaHora();
					String medico=s.getMedicos().get(i).getNombre();
				crearQuery();
				query.executeUpdate("INSERT INTO medicopaciente (Medico, Paciente, Fecha, Hora) VALUES " +"('"+medico+"', '"+nom+"', '"+f+"', '"+h+"')");
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
		}
		
	}
	
	// OJO: De aqui hacia abajo se uso como ejemplo para empezar; no es codigo fiable
	/**
	 * EJEMPLO de como usar la BD
	 */
	public boolean compruebaUsuario(String usuario, String password) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
			// TODO Comprobar que la conexion este activa
      		
      		crearQuery();
      		resultado = query.executeQuery("SELECT * FROM usuario where NombreUsuario = '"
      					+ usuario + "' and password = '" + password + "'");	
			if (resultado.next()) estado = true;
			else estado = false;
			
			
			// Esto es solo una prueba que he hecho para verificar que funciona
			if (estado)
				System.out.println("Resultado: " + resultado.getString("Direccion"));

			resultado.close();
			
			return estado;	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	
	// OJO: De aqui hacia abajo se uso como ejemplo inicial; no es codigo fiable
	
	
	public boolean compruebaNombreUsuario(String usuario) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
      		//conectar();
      		crearQuery();
      		resultado = query.executeQuery("SELECT * FROM "+ this.nombreBD +".tb_acceso U where U.user = '"
      					+ usuario + "'");	
			if (resultado.next()) estado = true;
			else estado = false;
			resultado.close();
			//desconectar();
			return estado;	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public void insertaUsuario (String usuario, String password) throws ErrorEnRecursoException {

		boolean estado = false;
		
		try {
      		//conectar();
      		crearQuery();
      		query.executeUpdate("INSERT INTO "+ this.nombreBD +".tb_acceso VALUES ('"+
      				usuario+"','"+password+"')");
			//desconectar();
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public boolean insertaCita (DatosCitaSinValidar cita) throws ErrorEnRecursoException {
		return true;

	}
}