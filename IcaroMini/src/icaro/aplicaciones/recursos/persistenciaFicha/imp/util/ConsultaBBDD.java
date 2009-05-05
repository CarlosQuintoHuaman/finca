package icaro.aplicaciones.recursos.persistenciaFicha.imp.util;


import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFicha;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCita;
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
	
	public boolean borraFicha(DatosFicha ficha){
		try {
			String[] aux=ficha.getApellidos().split(" ");
			String ape1=aux[0];
			
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM usuario WHERE Nombre = '" + ficha.getNombre() +"' AND Apellido1 = '" + ape1 +
					"' AND Telefono = '" + ficha.getTelf1() + "'");
			
			if (resultado.next()){
				String usuario=resultado.getString("NombreUsuario");
				crearQuery();
				query.executeUpdate("DELETE FROM Antecedentes WHERE Paciente = '" + usuario +"'");
			}
			else
				return false;
		return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean meteFicha(DatosFicha original, DatosFicha fichaN){
		try {
			
			String[] aux=fichaN.getApellidos().split(" ");
			String ape1=aux[0];
			String ape2="";
			for(int i=1;i<aux.length;i++)
				ape2=ape2+aux[i];
			
			String[] aux2=original.getApellidos().split(" ");
			String ape1o=aux2[0];

			
			
			String desc=fichaN.getNIF()+'/'+fichaN.getNombre()+'/'+fichaN.getApellidos()+'/'+util.getStrDateSQL2(fichaN.getFNacimiento())+'/'+Integer.valueOf(fichaN.getEdad())+'/'
			+fichaN.getDireccion()+'/'+fichaN.getCP()+'/'+fichaN.getProvincia()+'/'+fichaN.getLocalidad()+'/'+fichaN.getTelf1()+'/'+fichaN.getTelf2()+'/'+fichaN.getMail()+'/'+fichaN.getProfesion()
			+'/'+fichaN.getAseguradora()+'/'+fichaN.getOtros()+'/'+fichaN.getPestOtros()+"/c";
			
			int id=1;
			//Compruebo si esta dado de alta en la bbdd
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM usuario WHERE Nombre = '" + original.getNombre() +"' AND Apellido1 = '" + ape1o +
					"' AND Telefono = '" + original.getTelf1() + "'");
			
			
			if (resultado.next()){
				String usuario=resultado.getString("NombreUsuario");
				crearQuery();
				query.executeUpdate("DELETE FROM Antecedentes WHERE Paciente = '" + usuario +"'");
//				crearQuery();
//				query.executeUpdate("DELETE FROM paciente WHERE NombreUsuario = '" + usuario +"'");
//				crearQuery();
//				query.executeUpdate("DELETE FROM usuario WHERE NombreUsuario = '" + usuario +"'");
				
				crearQuery();
//				query.executeUpdate("INSERT INTO usuario (NombreUsuario, Password, Nombre, Apellido1, Apellido2, Direccion, Telefono) " +
//						"VALUES " +"('"+usuario+"', '"+pass+"', '"+ficha.getNombre()+"', '"+ape1+"', '"+ape2+"', '"+ficha.getDireccion()+"'" +
//								", '"+ficha.getTelf1()+"')");
				//UPDATE `doctoris`.`usuario` SET `Nombre` = 'Secreetaria1' WHERE `usuario`.`NombreUsuario` = 'sec1' LIMIT 1 ;
				query.executeUpdate("UPDATE usuario SET Nombre = '" + fichaN.getNombre() +"', Apellido1 = '" + ape1 +"', Apellido2 = '" + ape2 +
						"', Direccion = '" + fichaN.getDireccion() +"', Telefono = '" + fichaN.getTelf1() + "' WHERE NombreUsuario = '"+usuario+"'");
//				crearQuery();
//				query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+usuario+"', '"+ficha.getAseguradora()+"')");
				
				crearQuery();
				query.executeUpdate("INSERT INTO Antecedentes (Paciente, Descripcion) VALUES " +"('"+usuario+"', '"+desc+"')");
				
			}
			else{
			//Si no esta dado de alta se inserta en la bbdd
				id=4;
				crearQuery();
				query.executeUpdate("INSERT INTO usuario (NombreUsuario, Password, Nombre, Apellido1, Apellido2, Direccion, Telefono) " +
						"VALUES " +"('"+fichaN.getNombre()+"', '"+fichaN.getNombre()+"', '"+fichaN.getNombre()+"', '"+ape1+"', '"+ape2+"', '"+fichaN.getDireccion()+"'" +
								", '"+fichaN.getTelf1()+"')");
				crearQuery();
				query.executeUpdate("INSERT INTO paciente (NombreUsuario, Seguro) VALUES " +"('"+fichaN.getNombre()+"', '"+fichaN.getAseguradora()+"')");
				
				crearQuery();
				query.executeUpdate("INSERT INTO Antecedentes (Paciente, Descripcion) VALUES " +"('"+fichaN.getNombre()+"', '"+desc+"')");
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public DatosFicha getFicha(DatosCita datos){
		try {
			DatosFicha ficha= new DatosFicha();
			String[] aux=datos.getNombre().split(" ");
			String nom=aux[0];
			String ape1="";
			String desc="";
			if (aux.length>1)
				ape1=aux[1];
			
			crearQuery();
			resultado = query.executeQuery("SELECT * FROM usuario WHERE NombreUsuario = '" + nom +"'");
			
			
			if (resultado.next()){
				String usuario=resultado.getString("NombreUsuario");
				crearQuery();
				resultado = query.executeQuery("SELECT * FROM Antecedentes WHERE Paciente = '" + usuario +"'");
				if (resultado.next()){
					desc=resultado.getString("Descripcion");
				}
			}
			if (!desc.equals("")){
			String[] aux2=desc.split("/");
			ficha.setNIF(aux2[0]);
			ficha.setNombre(aux2[1]);
			ficha.setApellidos(aux2[2]);
			ficha.setFNacimiento(util.StrToDate(aux2[3]));

			ficha.setDireccion(aux2[5]);
			ficha.setCP(aux2[6]);
			ficha.setProvincia(aux2[7]);
			ficha.setLocalidad(aux2[8]);
			ficha.setTelf1(aux2[9]);
			ficha.setTelf2(aux2[10]);
			ficha.setMail(aux2[11]);
			ficha.setProfesion(aux2[12]);
			ficha.setAseguradora(aux2[13]);
			ficha.setOtros(aux2[14]);
			ficha.setEsta(true);
			if (aux2.length>15)
				ficha.setPestOtros(aux2[15]);
			else
				ficha.setPestOtros("");
			}
			else
				ficha.setEsta(false);
			return ficha;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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