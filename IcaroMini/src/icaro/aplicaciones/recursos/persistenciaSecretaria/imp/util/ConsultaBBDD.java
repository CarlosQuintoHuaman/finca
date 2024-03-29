package icaro.aplicaciones.recursos.persistenciaSecretaria.imp.util;

import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionMedico.InfoPaciente;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosMedico;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosSecretaria;
import icaro.aplicaciones.recursos.persistenciaMedico.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.util.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
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
	public ArrayList<DatosMedico> getCitas(String fecha, ArrayList<DatosMedico> lnombres) {
		ArrayList<DatosMedico> medicos = new ArrayList<DatosMedico>();
		
		try {		
			
			Date f1=util.StrToDateSQL(fecha);
			Date f2 = new Date();
			DatosMedico[]  datos=new DatosMedico[lnombres.size()];
			f2.setTime(f1.getTime()+86400000);
			String f11=util.getStrDateSQL(f1).substring(0, 10);
			String f22=util.getStrDateSQL(f2).substring(0, 10);
			ArrayList[] arrayLists1 = new ArrayList[lnombres.size()];
			ArrayList<DatosCitaSinValidar>[] citas = arrayLists1;
			ArrayList[] arrayLists2 = new ArrayList[lnombres.size()];
			ArrayList<DatosLlamada>[] llamadas =arrayLists2;
			ArrayList[] arrayLists3 = new ArrayList[lnombres.size()];
			ArrayList<DatosLlamada>[] extras =arrayLists3;
			
			for(int i=0;i<lnombres.size();i++){
				datos[i]=new DatosMedico(lnombres.get(i).getNombre(),lnombres.get(i).getUsuario());
				datos[i].setIntervalo(lnombres.get(i).getIntervalo());
				datos[i].setMa�ana(lnombres.get(i).getMa�ana());
				datos[i].setTarde(lnombres.get(i).getTarde());
				citas[i]=new ArrayList<DatosCitaSinValidar>();
				llamadas[i]=new ArrayList<DatosLlamada>();
				extras[i]=new ArrayList<DatosLlamada>();

			}
			
			// Consulta que nos devuelve los datos de la citas de todos los medicos que posteriormente filtramos
			crearQuery();
			//resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Fecha < '" + fecha2 + "'");
			resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
			while (resultado.next()) {
				
				String usuario=resultado.getString("Paciente");
				String medico=resultado.getString("Medico");
				 resultado.getTimestamp("Fecha").toString();
				 String h=resultado.getTimestamp("Hora").toString().substring(11, 16);
				 int estado=resultado.getInt("Estado");
				crearQuery();
				ResultSet resultado1 = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + usuario +"'");
				while (resultado1.next()) {
				String paciente=resultado1.getString("Nombre");
				String apellido1=resultado1.getString("Apellido1");
				String apellido2=resultado1.getString("Apellido2");
				String telf =resultado1.getString("Telefono");
				 
				//filtramos las citas que nos interesan segun los medicos que tiene asiganada esta secretaria 
				for(int i=0;i<lnombres.size();i++){
					if (medico.equals(lnombres.get(i).getUsuario())){
						
						DatosCitaSinValidar p = new DatosCitaSinValidar(paciente,apellido1,apellido2,telf,h,1,usuario,estado);
						
						citas[i].add(p);
						datos[i].setDatos(citas[i]);
					}
				}
			}
			}
			
			// Consulta que nos devuelve los datos de los extras y llamadas de todos los medicos que posteriormente filtramos
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM extras WHERE Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
			while (resultado.next()) {
				
				String usuario =resultado.getString("Nombre");
				String medico=resultado.getString("Medico");
				 String f=resultado.getTimestamp("Fecha").toString();
				 String mensaje =resultado.getString("Mensaje");
				 String telf=String.valueOf(resultado.getInt("Telefono"));
				 String tipo=resultado.getString("Tipo");
			
				crearQuery();
				ResultSet resultado1 = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + usuario +"'");
				if (resultado1.next()) {
					
					String nombre =resultado1.getString("Nombre")+" "+resultado1.getString("Apellido1")+" "+resultado1.getString("Apellido2");;
				//filtramos las citas que nos interesan segun los medicos que tiene asiganada esta secretaria 
				for(int i=0;i<lnombres.size();i++){
					if (medico.equals(lnombres.get(i).getUsuario())){
						if(tipo.equals("llamada")){
						DatosLlamada p = new DatosLlamada(nombre,mensaje,telf,f.substring(11, 19),usuario,f11);
						
						llamadas[i].add(p);
						datos[i].setLlamadas(llamadas[i]);
						}
						if(tipo.equals("extra")){
							DatosLlamada p = new DatosLlamada(nombre,mensaje,telf,f.substring(11, 19),usuario,f11);
							
							extras[i].add(p);
							datos[i].setExtras(extras[i]);
						}
					}
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
	 * @return medicos	:: ArrayList datosMedicos
	 */
	public ArrayList<DatosMedico> getMedicos(String s) {
		ArrayList<DatosMedico> medicos = new ArrayList<DatosMedico>();
		
		try {	
			String n="";
			Time IManana = null;
			Time ITarde= null;;
			Time FManana= null;;
			Time FTarde= null;;
			int intervalo=15;
			
			crearQuery();
			
			//resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Fecha < '" + fecha2 + "'");
			resultado = query.executeQuery("SELECT * FROM tieneagenda WHERE Secretaria = '" + s + "'");
			while (resultado.next()) {
				String m=resultado.getString("Medico");
				crearQuery();
				ResultSet resultado1 = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + m + "'");
				while (resultado1.next()) {
					n=resultado1.getString("Nombre");
				}
				
				crearQuery();
				resultado1 = query.executeQuery("SELECT * FROM medico WHERE NombreUsuario = '" + m + "'");
				while (resultado1.next()) {
					IManana=resultado1.getTime("InicioMan");
					ITarde=resultado1.getTime("InicioTar");
					FManana=resultado1.getTime("FinMan");
					FTarde=resultado1.getTime("FinTar");
					intervalo=resultado1.getInt("Intervalo");
				}
				DatosMedico med=new DatosMedico(n,m,IManana,ITarde,FManana,FTarde,intervalo);
				medicos.add(med);
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
	public ArrayList<DatosCitaSinValidar> getPaciente(String n ,String t, String fecha) {
		ArrayList<DatosCitaSinValidar> p = new ArrayList<DatosCitaSinValidar>();
		
		try {	
			
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM medicopaciente WHERE Fecha >= '" + fecha + "' AND Paciente = '" + n + "'");
			//resultado = query.executeQuery("SELECT * FROM tieneagenda WHERE Secretaria = '" + s + "'");
			while (resultado.next()) {
				String m=resultado.getString("Medico");	
				String f=resultado.getTimestamp("Fecha").toString();
				String h=resultado.getTimestamp("Hora").toString();
				int estado=resultado.getInt("Estado");
				DatosCitaSinValidar c=new DatosCitaSinValidar(estado,n,t,m,f,h);
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
			//borramos tabla medicoPaciente y extras para los medicos de esa secretaria
			for(int i=0; i<s.getNumM();i++){
				String medico=s.getMedicos().get(i).getNombre();
				Date f1=util.StrToDateSQL(s.getFecha());
				Date f2 = new Date();
				
				f2.setTime(f1.getTime()+86400000);
				String f11=util.getStrDateSQL(f1).substring(0, 10);
				String f22=util.getStrDateSQL(f2).substring(0, 10);

				crearQuery();
				query.executeUpdate("DELETE FROM medicopaciente WHERE Medico = '" + medico + "' AND Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
				crearQuery();
				query.executeUpdate("DELETE FROM extras WHERE Medico = '" + medico + "' AND Fecha >= '" + f11 +"' AND Fecha <= '" + f22 + "'");
			}
			//miramos si tenemos que insertar el paciente o si ya esta dado de alta en la tabla pacientes
			for(int i=0; i<s.getNumM();i++){
				for(int j=0;j<s.getMedicos().get(i).getDatos().size();j++){
					String nom=s.getMedicos().get(i).getDatos().get(j).getUsuario();
					String telf=s.getMedicos().get(i).getDatos().get(j).tomaTelf();
					crearQuery();
					//resultado = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + nom + "' AND Telefono = '" + telf + "'");
					resultado = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + nom + "'");
					//si no existe debe darse de alta el paciente como usuario y como paciente
					if (!resultado.next()){
						nom=s.getMedicos().get(i).getDatos().get(j).tomaNombre();
						crearQuery();
						query.executeUpdate("INSERT INTO usuario (NombreUsuario, Nombre, Telefono) VALUES " +"('"+nom+"', '"+nom+"', '"+telf+"')");
						crearQuery();
						query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+nom+"', '"+telf+"')");
					}
				}
			}
			
			String medico="";
			
			for(int i=0; i<s.getNumM();i++){
				String f=s.getFecha().substring(0, 10);
				for(int j=0;j<s.getMedicos().get(i).getDatos().size();j++){
					String nom=s.getMedicos().get(i).getDatos().get(j).getUsuario();
					String h=s.getMedicos().get(i).getDatos().get(j).tomaHora();
					medico=s.getMedicos().get(i).getNombre();
				crearQuery();
				query.executeUpdate("INSERT INTO medicopaciente (Medico, Paciente, Fecha, Hora) VALUES " +"('"+medico+"', '"+nom+"', '"+f+"', '"+h+"')");
				}
				f=s.getFecha();
				String tipo="llamada";
				if(!(s.getMedicos().get(i).getLlamadas()==null)){
					for(int j=0;j<s.getMedicos().get(i).getLlamadas().size();j++){
						String nom=s.getMedicos().get(i).getLlamadas().get(j).getNombre();
						String telf=s.getMedicos().get(i).getLlamadas().get(j).getTelf();
						String men=s.getMedicos().get(i).getLlamadas().get(j).getMensaje();
						medico=s.getMedicos().get(i).getNombre();
						f=f.substring(0, 10)+" "+s.getMedicos().get(i).getLlamadas().get(j).getHora();
						
						String[]aux=nom.split(" ");
						nom=aux[0];
						String mm="";
						String ape1="";
						if (aux.length>1){
							ape1=aux[1];
						}
						for(int k=2;k<aux.length;k++){
							mm=mm+aux[k];
						}
						crearQuery();
						resultado = query.executeQuery("SELECT * FROM usuario WHERE Nombre = '" + nom + "' AND Apellido1 = '" +ape1+ "' AND Apellido2 = '"+mm+"'");
						//si no existe debe darse de alta el paciente como usuario y como paciente
						if (!resultado.next()){
							crearQuery();
							query.executeUpdate("INSERT INTO usuario (NombreUsuario, Nombre, Telefono) VALUES " +"('"+nom+"', '"+nom+"', '"+telf+"')");
							crearQuery();
							query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+nom+"', '"+telf+"')");
						}else
							nom=resultado.getString("NombreUsuario");
						
						crearQuery();
						query.executeUpdate("INSERT INTO extras (Nombre, Medico, Fecha, Mensaje, Telefono, Tipo) VALUES " +"('"+nom+"', '"+medico+"', '"+f+"', '"+men+"'," +
															" '"+telf+"', '"+tipo+"')");
					}
				}
				tipo="extra";
				if(!(s.getMedicos().get(i).getExtras()==null)){
					for(int j=0;j<s.getMedicos().get(i).getExtras().size();j++){
						String nom=s.getMedicos().get(i).getExtras().get(j).getNombre();
						String telf=s.getMedicos().get(i).getExtras().get(j).getTelf();
						String men=s.getMedicos().get(i).getExtras().get(j).getMensaje();
						medico=s.getMedicos().get(i).getNombre();
						f=f.substring(0, 10)+" "+s.getMedicos().get(i).getExtras().get(j).getHora();
						
						String[]aux=nom.split(" ");
						nom=aux[0];
						String mm="";
						String ape1="";
						if (aux.length>1){
							ape1=aux[1];
						}
						for(int k=2;k<aux.length;k++){
							mm=mm+aux[k];
						}
						crearQuery();
						resultado = query.executeQuery("SELECT * FROM usuario WHERE Nombre = '" + nom + "' AND Apellido1 = '" +ape1+ "' AND Apellido2 = '"+mm+"'");
						//si no existe debe darse de alta el paciente como usuario y como paciente
						if (!resultado.next()){
							crearQuery();
							query.executeUpdate("INSERT INTO usuario (NombreUsuario, Nombre, Telefono) VALUES " +"('"+nom+"', '"+nom+"', '"+telf+"')");
							crearQuery();
							query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+nom+"', '"+telf+"')");
						}else
							nom=resultado.getString("NombreUsuario");
						
						crearQuery();
						query.executeUpdate("INSERT INTO extras (Nombre, Medico, Fecha, Mensaje, Telefono, Tipo) VALUES " +"('"+nom+"', '"+medico+"', '"+f+"', '"+men+"'," +
															" '"+telf+"', '"+tipo+"')");
					}
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
	
	public void insertaCita (DatosCitaSinValidar cita) throws ErrorEnRecursoException {
		try {
			if (cita.getNuevo()){
				crearQuery();
				query.executeUpdate("INSERT INTO usuario (NombreUsuario, Password, Nombre,  Apellido1, Apellido2, Telefono) VALUES " +"('"+cita.getUsuario()+
						"', '"+cita.getUsuario()+"', '"+cita.tomaNombre()+"', '"+cita.tomaApell1()+"', '"+cita.getApell2()+"', '"+cita.tomaTelf()+"')");
				crearQuery();
				query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+cita.getUsuario()+"', '"+cita.getSeguro()+"')");
				
			}
				crearQuery();
	      		resultado = query.executeQuery("SELECT * FROM medicopaciente where Medico = '"
	      					+ cita.getMedico() + "' and Fecha = '" + cita.getFecha().substring(0, 10) + "' and Hora = '" + cita.tomaHora() + "'");	
	      		int e=0;
	      		if (resultado.next()){
					crearQuery();
					query.executeUpdate("UPDATE medicopaciente SET Paciente = '" + cita.getUsuario() +"', Estado = '" +e+ "' WHERE Medico = '"+cita.getMedico() +
							"' and Fecha = '" + cita.getFecha().substring(0, 10) + "' and Hora = '" + cita.tomaHora() + "'");
				}
	      		else{
	      			crearQuery();
	      			
					query.executeUpdate("INSERT INTO medicopaciente (Medico, Paciente, Fecha, Hora, Estado) VALUES " +"('"+cita.getMedico()+"', '"+cita.getUsuario()+"', '"+
							cita.getFecha().substring(0, 10)+"', '"+cita.tomaHora()+"', '"+e+"')");
	      		}
		
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}

	}
	
	public void borraCita (DatosCitaSinValidar cita) throws ErrorEnRecursoException {
		try {

				crearQuery();
				String hora=cita.tomaHora()+":00";
				query.executeUpdate("DELETE FROM medicopaciente where Medico = '"
	      					+ cita.getMedico() + "' and Fecha = '" + cita.getFecha().substring(0, 10) + "' and Hora = '" + hora + "'");	
		
		}catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}

	}
	
	public void modificaEstado (DatosCitaSinValidar cita) throws ErrorEnRecursoException {
		try {

				crearQuery();
				query.executeUpdate("UPDATE medicopaciente SET Estado = '" + cita.getEstado() + "' WHERE Medico = '"+cita.getMedico() +"' and Fecha = '" + cita.getFecha().substring(0, 10) + "' and Hora = '" + cita.tomaHora() + "'");	
		
		}catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}

	}
	
	public void modificaExtra (DatosLlamada la,DatosLlamada lp) throws ErrorEnRecursoException {
		try {
			String fechaA=la.getFecha().substring(0, 10)+" "+la.getHora();
			String fechaB=lp.getFecha().substring(0, 10)+" "+lp.getHora();
			if (lp.getPaciente()){
				String n=lp.getNombre();
				String[]aux=n.split(" ");
				String mm="";
				String ape1="";
				String s="Ninguno";
				if (aux.length>1){
					ape1=aux[1];
				}
				for(int k=2;k<aux.length;k++){
					mm=mm+aux[k];
				}
				crearQuery();
				query.executeUpdate("INSERT INTO usuario (NombreUsuario, Password, Nombre,  Apellido1, Apellido2, Telefono) VALUES " +"('"+lp.getUsuario()+
						"', '"+lp.getUsuario()+"', '"+aux[0]+"', '"+ape1+"', '"+mm+"', '"+lp.getTelf()+"')");
				crearQuery();
				query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+lp.getUsuario()+"', '"+s+"')");
				
			}
				crearQuery();
	      		resultado = query.executeQuery("SELECT * FROM extras where Medico = '"
	      					+ la.getMedico() + "' and Fecha = '" + fechaA + "' and Nombre = '" + la.getUsuario() + "'");	
				
	      		if (resultado.next()){
					crearQuery();
					query.executeUpdate("UPDATE extras SET Nombre = '" + lp.getUsuario() +"', Fecha = '" + fechaB + "', Mensaje = '" + lp.getMensaje()+ "', Telefono = '" + lp.getTelf()
							+ "' WHERE Nombre = '"+la.getUsuario() +"' and Fecha = '" + fechaA + "' and Mensaje = '" + la.getMensaje() + "'");
				}
	      		else{
	      			crearQuery();
					query.executeUpdate("INSERT INTO extras (Nombre, Medico, Fecha, Mensaje, Telefono, Tipo) VALUES " +"('"+lp.getUsuario()+"', '"+lp.getMedico()+"', '"+
							fechaB+"', '"+lp.getMensaje()+"', '"+lp.getTelf()+"', '"+lp.getTipo()+"')");
	      		}
		
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}

	}
	
	public void borraExtra (DatosLlamada d) throws ErrorEnRecursoException {
		try {
			String fechaA=d.getFecha().substring(0, 10)+" "+d.getHora();
			query.executeUpdate("DELETE FROM extras where Medico = '"
  					+ d.getMedico() + "' and Fecha = '" + fechaA + "' and Nombre = '" + d.getUsuario() + "' and Mensaje = '" + d.getMensaje() +"'");	
		}
		
		catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}

	}
}