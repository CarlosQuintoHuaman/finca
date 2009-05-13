package icaro.aplicaciones.recursos.persistenciaAdmin.imp.util;


import icaro.aplicaciones.informacion.dominioClases.aplicacionAdmin.InfoUsuario;
import icaro.aplicaciones.recursos.persistenciaAdmin.imp.ErrorEnRecursoException;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

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
			String q = "OPTIMIZE TABLE administrador, antecedentes, comunica, consentimientos, documentos, extras, medicamentos, medico, medicopaciente, medicportrat, medporreceta, paciente, recetas, secretaria, tieneagenda, tienecons, tienedoc, tienehistorial, tienetratamientos, tratamientos, usuario, visita";
      		crearQuery();
      		resultado = query.executeQuery(q);	
			
      	} catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public void crear() throws ErrorEnRecursoException {
		try {
			crearQuery();
			
			query.addBatch("DROP TABLE IF EXISTS TieneAntecedentes;");
					query.addBatch("DROP TABLE IF EXISTS TieneCons;");
					query.addBatch("DROP TABLE IF EXISTS Recetas;");
					query.addBatch("DROP TABLE IF EXISTS TieneDoc;");
					query.addBatch("DROP TABLE IF EXISTS TieneTratamientos;");
					query.addBatch("DROP TABLE IF EXISTS Tratamientos;");
					query.addBatch("DROP TABLE IF EXISTS TieneHistorial;");
					query.addBatch("DROP TABLE IF EXISTS Comunica;");
					query.addBatch("DROP TABLE IF EXISTS TieneAgenda;");
					query.addBatch("DROP TABLE IF EXISTS MedicoPaciente;");
					query.addBatch("DROP TABLE IF EXISTS Antecedentes;");
					query.addBatch("DROP TABLE IF EXISTS Medicamentos;");
					query.addBatch("DROP TABLE IF EXISTS Consentimientos;");
					query.addBatch("DROP TABLE IF EXISTS Documentos;");
					query.addBatch("DROP TABLE IF EXISTS Visita;");
					query.addBatch("DROP TABLE IF EXISTS Extras;");
					query.addBatch("DROP TABLE IF EXISTS Administrador;");
					query.addBatch("DROP TABLE IF EXISTS Secretaria;");
					query.addBatch("DROP TABLE IF EXISTS Paciente;");
					query.addBatch("DROP TABLE IF EXISTS Medico;");
					query.addBatch("DROP TABLE IF EXISTS Usuario;");
					
					query.executeBatch();
		      		query.close();
		      		crearQuery();
		      		
					query.addBatch("CREATE TABLE Extras(Nombre VARCHAR(20), Medico VARCHAR(20), Fecha TIMESTAMP, Mensaje VARCHAR(100), Telefono INTEGER, Tipo VARCHAR(10));");
					
					query.addBatch("CREATE TABLE Antecedentes(Paciente VARCHAR(20), Descripcion TEXT(500));");
					
					query.addBatch("CREATE TABLE Tratamientos(Nombre VARCHAR(50), Descripcion TEXT(1000), Tipo VARCHAR(20));");
					query.addBatch("CREATE TABLE MedicPorTrat(Tratamiento VARCHAR(50), CodigoMed INTEGER);");
					query.addBatch("CREATE TABLE Medicamentos(Codigo INTEGER, Nombre VARCHAR(20), PrincipioActivo VARCHAR(50), Descripcion TEXT(500), Indicaciones TEXT(100));");
					query.addBatch("CREATE TABLE Consentimientos(Nombre VARCHAR(50), Titulo VARCHAR(50), Contenido TEXT(1000));");
					query.addBatch("CREATE TABLE Documentos(Paciente VARCHAR(20), Nombre VARCHAR(50), FechaVisita TIMESTAMP, Tipo VARCHAR(20), Archivo VARCHAR(100), DESCRIPCION TEXT(1000));");
					query.addBatch("CREATE TABLE Visita(Paciente VARCHAR(20), Fecha TIMESTAMP, Motivo TEXT(500), Descripcion TEXT(1000), Exploracion TEXT(500), Diagnostico TEXT(1000), Tratamiento TEXT(1000));");
					query.addBatch("CREATE TABLE Administrador(NombreUsuario VARCHAR(20));");
					query.addBatch("CREATE TABLE Secretaria(NombreUsuario VARCHAR(20), InicioMan TINYINT(4), FinMan TINYINT(4), InicioTar TINYINT(4), FinTar TINYINT(4));");
					query.addBatch("CREATE TABLE Usuario(NombreUsuario VARCHAR(20), Password VARCHAR(15), Nombre VARCHAR(15), Apellido1 VARCHAR(15), Apellido2 VARCHAR(15), Direccion VARCHAR(20), Telefono INTEGER);");
					query.addBatch("CREATE TABLE Paciente(NombreUsuario VARCHAR(20), Seguro VARCHAR(20));");
					query.addBatch("CREATE TABLE Medico(NombreUsuario VARCHAR(20), Especialidad VARCHAR(30), Intervalo TINYINT(4), InicioMan TINYINT(4), FinMan TINYINT(4), InicioTar TINYINT(4), FinTar TINYINT(4));");
					query.addBatch("CREATE TABLE TieneCons(NombreTratamiento VARCHAR(50), NombreConsentimiento VARCHAR(50));");
					query.addBatch("CREATE TABLE Recetas(Codigo INTEGER, Paciente VARCHAR(50), FechaVisita TIMESTAMP, Notas VARCHAR(500));");
					query.addBatch("CREATE TABLE MedPorReceta(CodigoRec INTEGER, CodigoMed INTEGER);");
					query.addBatch("CREATE TABLE TieneDoc(NombreDoc VARCHAR(50), FechaVisita TIMESTAMP, Fecha TIMESTAMP);");
					query.addBatch("CREATE TABLE TieneTratamientos(Nombre VARCHAR(50), Paciente VARCHAR(50), FechaVisita TIMESTAMP);");
					query.addBatch("CREATE TABLE TieneHistorial(NombreUsuario VARCHAR(20), FechaVisita TIMESTAMP);");
					query.addBatch("CREATE TABLE Comunica(Remitente VARCHAR(20), Destinatario VARCHAR(20), Fecha TIMESTAMP, Asunto VARCHAR(30), Contenido TEXT(5000));");
					query.addBatch("CREATE TABLE TieneAgenda(Medico VARCHAR(20), Secretaria VARCHAR(20));");
					query.addBatch("CREATE TABLE MedicoPaciente(Medico VARCHAR(20), Paciente VARCHAR(20), Fecha DATE, Hora TIME, Estado TINYINT(4));");
										
					query.executeBatch();
		      		query.close();
		      		crearQuery();
		      		
					query.addBatch("ALTER TABLE Usuario ADD PRIMARY KEY (NombreUsuario);");
					query.addBatch("ALTER TABLE Antecedentes ADD PRIMARY KEY (Paciente);");
					query.addBatch("ALTER TABLE Tratamientos ADD PRIMARY KEY (Nombre);");
					query.addBatch("ALTER TABLE Medicamentos ADD PRIMARY KEY (Codigo);");
					query.addBatch("ALTER TABLE Medicamentos CHANGE Codigo Codigo INT( 11 ) NOT NULL AUTO_INCREMENT;");
					query.addBatch("ALTER TABLE MedicPorTrat ADD PRIMARY KEY(Tratamiento, CodigoMed);");
					query.addBatch("ALTER TABLE MedicPorTrat ADD FOREIGN KEY (Tratamiento) REFERENCES Tratamientos (Nombre);");
					query.addBatch("ALTER TABLE MedicPorTrat ADD FOREIGN KEY (CodigoMed) REFERENCES Medicamentos (Codigo);");
					query.addBatch("ALTER TABLE Consentimientos ADD PRIMARY KEY (Nombre);");
					query.addBatch("ALTER TABLE Documentos ADD PRIMARY KEY (Paciente, Nombre, FechaVisita);");
					query.addBatch("ALTER TABLE Administrador ADD PRIMARY KEY (NombreUsuario);");
					query.addBatch("ALTER TABLE Administrador ADD FOREIGN KEY (NombreUsuario) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Secretaria ADD PRIMARY KEY (NombreUsuario);");
					query.addBatch("ALTER TABLE Secretaria ADD FOREIGN KEY (NombreUsuario) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Paciente ADD PRIMARY KEY (NombreUsuario);");
					query.addBatch("ALTER TABLE Paciente ADD FOREIGN KEY (NombreUsuario) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Medico ADD PRIMARY KEY (NombreUsuario);");
					query.addBatch("ALTER TABLE Medico ADD FOREIGN KEY (NombreUsuario) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Visita ADD FOREIGN KEY (Paciente) REFERENCES Paciente (NombreUsuario);");
					query.addBatch("ALTER TABLE Visita ADD PRIMARY KEY (Paciente, Fecha);");
					query.addBatch("ALTER TABLE TieneCons ADD FOREIGN KEY (NombreConsentimiento) REFERENCES Consentimientos (Nombre);");
					query.addBatch("ALTER TABLE TieneCons ADD FOREIGN KEY (NombreTratamiento) REFERENCES Tratamientos (Nombre);");
					query.addBatch("ALTER TABLE Recetas ADD PRIMARY KEY (Codigo);");
					query.addBatch("ALTER TABLE Recetas CHANGE Codigo Codigo INT( 11 ) NOT NULL AUTO_INCREMENT;");
					query.addBatch("ALTER TABLE Recetas ADD FOREIGN KEY (Paciente) REFERENCES Paciente (NombreUsuario);");
					query.addBatch("-- ALTER TABLE Recetas ADD FOREIGN KEY (FechaVisita) REFERENCES Visita (Fecha);");
					query.addBatch("ALTER TABLE MedPorReceta ADD PRIMARY KEY (CodigoRec, CodigoMed);");
					query.addBatch("ALTER TABLE MedPorReceta ADD FOREIGN KEY (CodigoRec) REFERENCES Recetas (Codigo);");
					query.addBatch("ALTER TABLE MedPorReceta ADD FOREIGN KEY (CodigoMed) REFERENCES Medicamentos (Codigo);");
					query.addBatch("-- ALTER TABLE TieneDoc ADD FOREIGN KEY (FechaVisita) REFERENCES Visita (Fecha);");
					query.addBatch("-- ALTER TABLE TieneDoc ADD FOREIGN KEY (NombreDoc) REFERENCES Documentos (Nombre);");
					query.addBatch("-- ALTER TABLE TieneTratamientos ADD FOREIGN KEY (FechaVisita) REFERENCES Visita (Fecha);");
					query.addBatch("ALTER TABLE TieneTratamientos ADD PRIMARY KEY (Nombre, Paciente, FechaVisita);");
					query.addBatch("ALTER TABLE TieneTratamientos ADD FOREIGN KEY (Nombre) REFERENCES Tratamientos (Nombre);");
					query.addBatch("-- ALTER TABLE TieneHistorial ADD FOREIGN KEY (FechaVisita) REFERENCES Visita (Fecha);");
					query.addBatch("ALTER TABLE Comunica ADD FOREIGN KEY (Remitente) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Comunica ADD FOREIGN KEY (Destinatario) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE Comunica ADD PRIMARY KEY (Remitente, Destinatario, Fecha);");
					query.addBatch("ALTER TABLE TieneAgenda ADD FOREIGN KEY (Medico) REFERENCES Medico (NombreUsuario);");
					query.addBatch("ALTER TABLE TieneAgenda ADD FOREIGN KEY (Secretaria) REFERENCES Secretaria (NombreUsuario);");
					query.addBatch("ALTER TABLE Extras ADD PRIMARY KEY (Fecha, Mensaje, Nombre);");
					query.addBatch("ALTER TABLE Extras ADD FOREIGN KEY (Medico) REFERENCES Medico (NombreUsuario);");
					query.addBatch("ALTER TABLE Extras ADD FOREIGN KEY (Nombre) REFERENCES Usuario (NombreUsuario);");
					query.addBatch("ALTER TABLE MedicoPaciente ADD PRIMARY KEY (Medico, Paciente, Fecha, Hora);");
					query.addBatch("ALTER TABLE MedicoPaciente ADD FOREIGN KEY (Medico) REFERENCES Medico (NombreUsuario);");
					query.addBatch("ALTER TABLE MedicoPaciente ADD FOREIGN KEY (Paciente) REFERENCES Paciente (NombreUsuario);");
			
      		query.executeBatch();
      		query.close();
			
      	} catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
	
	public void resetear() throws ErrorEnRecursoException {
		try {
			crearQuery();
			query.addBatch("TRUNCATE administrador;");
					query.addBatch("TRUNCATE antecedentes;");
					query.addBatch("TRUNCATE comunica;");
					query.addBatch("TRUNCATE consentimientos;");
					query.addBatch("TRUNCATE documentos;");
					query.addBatch("TRUNCATE extras;");
					query.addBatch("TRUNCATE medicopaciente;");
					query.addBatch("TRUNCATE medicportrat;");
					query.addBatch("TRUNCATE medporreceta;");
					query.addBatch("TRUNCATE medicamentos;");
					query.addBatch("TRUNCATE recetas;");
					query.addBatch("TRUNCATE tieneagenda;");
					query.addBatch("TRUNCATE tienecons;");
					query.addBatch("TRUNCATE tienedoc;");
					query.addBatch("TRUNCATE tienehistorial;");
					query.addBatch("TRUNCATE tienetratamientos;");
					query.addBatch("TRUNCATE tratamientos;");
					query.addBatch("TRUNCATE visita;");
					query.addBatch("TRUNCATE paciente;");
					query.addBatch("TRUNCATE medico;");
					query.addBatch("TRUNCATE secretaria;");
					query.addBatch("TRUNCATE usuario;");
			
      		query.executeBatch();
      		query.close();
      		
      		crearQuery();
      		
      		query.addBatch("INSERT INTO Usuario (NombreUsuario, Password, Nombre, Apellido1, Apellido2, Direccion, Telefono) VALUES" +
      				"('admin', 'admin', 'Administrador', 'Sin', 'Apellido', 'Calle', 123)," +
      				"('med1', 'med1', 'medico1', 'ape1m', 'ape2m', 'calle', 124)," +
      				"('med2', 'med2', 'medico2', 'ape1m', 'ape2m', 'calle', 124)," +
      				"('pac1', 'pac1', 'Paciente1', 'Ape1p1', 'Ape2p1', 'calle p1', 2)," +
      				"('pac2', 'pac2', 'Paciente2', 'ape1p2', 'ape2p2', 'Calle p2', '353')," +
      				"('sec1', 'sec1', 'Secretaria1', 'ape1s', 'ape2s', 'Calle', 456)," +
      				"('q', 'q', 'Secretaria1', 'ape1s', 'ape2s', 'Calle', 456);");
      				
      				query.executeBatch();
      				query.close();
      				crearQuery();
				
      				query.addBatch("INSERT INTO Paciente (NombreUsuario, Seguro) VALUES" +
      				"('pac1', 'privado')," +
      				"('pac2', 'adeslas');");
      				
      				query.addBatch("INSERT INTO Medico (NombreUsuario, Especialidad, Intervalo, InicioMan, FinMan, InicioTar, FinTar) VALUES ('med2', 'Dermatologia', '15', '9', '14', '17', '21'),('med1', 'Dermatologia', '15', '9', '14', '17', '21');");
      				
      				query.addBatch("INSERT INTO Secretaria (NombreUsuario, InicioMan, FinMan, InicioTar, FinTar) VALUES" +
      				"('sec1', '9', '14', '17', '21')," +
      				"('q', '9', '14', '17', '21');");
      				
      				query.addBatch("INSERT INTO Administrador (NombreUsuario) VALUES ('admin');");
      				
      				query.executeBatch();
      				crearQuery();
      				
      				query.addBatch("INSERT INTO TieneAgenda (Medico, Secretaria) VALUES" +
      				"('med2', 'sec1')," +
      				"('med1', 'sec1')," +
      				"('med2', 'q')," +
      				"('med1', 'q');");
      				
      				query.addBatch("DELETE FROM MedicoPaciente;");
      				query.addBatch("INSERT INTO MedicoPaciente (Medico, Paciente, Fecha, Hora, Estado) VALUES" +
      				"('med1', 'pac1', '2009-03-31', '10:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '11:30:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '12:30:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '13:30:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '17:30:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '18:30:00', 0)," +
      				"('med1', 'pac1', '2009-04-02', '19:30:00', 0)," +
      				"('med2', 'pac1', '2009-04-02', '11:00:00', 0)," +
      				"('med2', 'pac1', '2009-04-02', '12:00:00', 0)," +
      				"('med2', 'pac1', '2009-04-02', '13:00:00', 0)," +
      				"('med2', 'pac2', '2009-04-02', '17:00:00', 0)," +
      				"('med2', 'pac2', '2009-04-02', '18:00:00', 0)," +
      				"('med2', 'pac2', '2009-04-02', '19:00:00', 0)," +
      				"('med2', 'pac2', '2009-04-02', '20:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '10:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '11:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '12:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '13:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '17:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '18:00:00', 0)," +
      				"('med1', 'pac1', '2009-04-16', '19:00:00', 0)," +
      				"('med2', 'pac1', '2009-04-16', '11:30:00', 0)," +
      				"('med2', 'pac1', '2009-04-16', '12:30:00', 0)," +
      				"('med2', 'pac1', '2009-04-16', '13:30:00', 0)," +
      				"('med2', 'pac2', '2009-04-16', '17:30:00', 0)," +
      				"('med2', 'pac2', '2009-04-16', '18:30:00', 0)," +
      				"('med2', 'pac2', '2009-04-16', '19:30:00', 0)," +
      				"('med2', 'pac2', '2009-04-16', '20:30:00', 0);");
      				
      				query.executeBatch();
      				crearQuery();
      				
      				query.addBatch("DELETE FROM Extras;");
      				query.addBatch("INSERT INTO Extras (Nombre, Medico, Fecha, Mensaje, Telefono, Tipo) VALUES" +
      				"('pac1','med1','2009-04-12 20:55:49','Asunto personal','928345674','llamada')," +
      				"('pac2','med1','2009-04-12 20:55:41','Asunto personal2','495684340','llamada')," +
      				"('pac1','med1','2009-04-12 20:55:42','Reaccion al medicamento','978657654','extra')," +
      				"('pac2','med1','2009-04-12 20:55:43','Reaccion al medicamento1','987654324','extra')," +
      				"('pac1','med1','2009-04-16 20:55:44','Amigo de la familia','987687687','llamada')," +
      				"('pac2','med1','2009-04-16 20:55:45','Amigo de la familia1','998764865','llamada')," +
      				"('pac1','med1','2009-04-16 20:55:46','Famoseo','457386479','extra')," +
      				"('pac2','med1','2009-04-16 20:55:47','Famoseo1','938367953','extra')," +
      				"('pac1','med1','2009-04-12 20:55:48','Asunto personal2','935794245','llamada')," +
      				"('pac2','med1','2009-04-12 20:55:19','Asunto personal1','928567654','llamada')," +
      				"('pac1','med1','2009-04-12 20:55:29','Reaccion al medicamento1','998765456','extra')," +
      				"('pac2','med1','2009-04-12 20:55:39','Reaccion al medicamento','998765678','extra')," +
      				"('pac1','med1','2009-04-16 20:55:49','Amigo de la familia1','937465789','llamada')," +
      				"('pac2','med1','2009-04-16 20:55:59','Amigo de la familia','967853456','llamada')," +
      				"('pac1','med1','2009-04-16 20:55:09','Famoseo1','9868547463','extra')," +
      				"('pac2','med1','2009-04-16 20:55:07','Famoseo','918273645','extra');");
      				
      				query.addBatch("INSERT INTO Antecedentes (Paciente, Descripcion) VALUES" +
      				"('pac1','78345678K/pac2//12-12-1980////Madrid/918765412/657678987/peod@slkdf.es/Estudiante/Mutua/Sordomuda/Es un poco pesada')," +
      				"('pac2','78345678K/pac2//12-12-1980////Madrid/918765412/657678987/peod@slkdf.es/Estudiante/Mutua/Sordomuda/Es un poco pesada');");
      				
      				query.addBatch("UPDATE doctoris.antecedentes SET Descripcion = '78345678K/pac1//04-09-1981//calle p1//Madrid/Madrid/918765412/657678987/peod@slkdf.es/Estudiante/Mutua/Sordomuda/Es un poco pesada' WHERE antecedentes.Paciente = 'pac1' LIMIT 1 ;");
      				
      				query.addBatch("INSERT INTO Visita (Paciente, Fecha, Motivo, Descripcion, Exploracion, Diagnostico, Tratamiento) VALUES" +
      				"('pac1', '2009-03-20 20:55:49', 'Sindrome post dia del padre', 'Esta raro', 'No tiene nada visible', 'Hijitis', 'Tener mas hijos')," +
      				"('pac2', '2009-03-18 20:56:41', 'Dolor de cabeza', 'Dolor intenso en la cabeza', 'Bultos en la parte trasera', 'Sin solucion', 'Tomar aspirinas')," +
      				"('pac1', '2009-04-06 18:59:25', 'Catarro', 'Frio en primavera', 'Temblores', 'Hasta el 40 de mayo, no te quites el sayo.', 'Abrigarse bien');");
      				
      				query.addBatch("INSERT INTO Medicamentos (Codigo, Nombre, PrincipioActivo, Descripcion, Indicaciones) VALUES" +
      				"(1, 'Aspirina', 'Acido', 'Quita todo', 'No tomar para cualquier cosa')," +
      				"(2, 'Ibuprofeno 500', 'Ibuprofeno', 'Arregla todo', 'No abusar de el')," +
      				"(3, 'Frenadol', 'Paracetamol', 'Quita el resfriado', 'Sabe mal');");
      				
      				query.addBatch("INSERT INTO Documentos (Paciente, Nombre, FechaVisita, Tipo, Archivo, Descripcion) VALUES" +
      				"('pac1', 'nueva', '2009-03-18 00:00:00', 'prueba', 'no hay', 'dewsospjfsjo')," +
      				"('pac1', 'otro', '2009-03-25 00:00:00', 'prueba', 'otro', 'iohoih');");
      				
      				query.addBatch("-- INSERT INTO Tratamientos (Nombre, Descripcion, Tipo) VALUES" +
      				"-- ('Dolor de cabeza', 'Para quitar el dolor de cabeza', 'Uno')," +
      				"-- ('Apendicectomia', 'Una operacion', 'Dos');");
      				
      				query.executeBatch();
      				crearQuery();
      				
      				query.addBatch("-- INSERT INTO TieneTratamientos (Nombre, Paciente, FechaVisita) VALUES" +
      				"-- ('Dolor de cabeza', 'pac1', '2009-03-20 20:55:49')," +
      				"-- ('Apendicectomia', 'pac2', '2009-03-18 20:56:41');");
      				
      				query.addBatch("INSERT INTO Recetas (Codigo, Paciente, FechaVisita, Notas) VALUES" +
      				"(1, 'pac1', '2009-03-20 20:55:49', 'Ninguna por ahora');");
      				
      				query.addBatch("INSERT INTO MedPorReceta (CodigoRec, CodigoMed) VALUES" +
      				"(1, 1)," +
      				"(1, 2); ");
      		
      		query.executeBatch();
      		query.close();
			
      	} catch (Exception e) {
			throw new ErrorEnRecursoException(e.getMessage());
		}
	}
}