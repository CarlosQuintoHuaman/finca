package icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.impCORBA;

import icaro.aplicaciones.recursos.persistencia.ItfUsoPersistencia;
import icaro.aplicaciones.recursos.recInformacionUsuario.ItfUsoRecInformacionUsuario;
import icaro.aplicaciones.recursos.recTestPersonalidad.ItfUsoRecTestPersonalidad;
import icaro.aplicaciones.recursos.visualizacion.acceso.ItfUsoVisualizadorAcceso;
import icaro.aplicaciones.recursos.visualizacion.accesoAlta.ItfUsoVisualizadorAccesoAlta;
import icaro.aplicaciones.recursos.visualizacion.alta.ItfUsoVisualizadorAlta;
import icaro.aplicaciones.recursos.visualizacion.recTestPersonalidad.ItfUsoVisualizadorTestPersonalidad;
import icaro.infraestructura.corba.interfazGestionIDL.AdaptadorInterfazGestionIDL;
import icaro.infraestructura.corba.interfazGestionIDL.InterfazGestionIDL;
import icaro.infraestructura.corba.interfazGestionIDL.InterfazGestionIDLHelper;
import icaro.infraestructura.corba.interfazGestionIDL.InterfazGestionIDLServant;
import icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.AdaptadorFactoriaAgenteCognitivoIDL;
import icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.FactoriaAgenteCognitivoIDL;
import icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.FactoriaAgenteCognitivoIDLHelper;
import icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.FactoriaAgenteCognitivoIDLServant;
import icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.AdaptadorIPercepcionAgenteCognitivoIDL;
import icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDL;
import icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDLHelper;
import icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDLServant;
import icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.AdaptadorFactoriaAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDLHelper;
import icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDLServant;
import icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.AdaptadorItfGestionAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDLHelper;
import icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDLServant;
import icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL.AdaptadorItfUsoAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL.ItfUsoAgenteReactivoIDL;
import icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL.ItfUsoAgenteReactivoIDLHelper;
import icaro.infraestructura.corba.patronAgenteReactivo.itfUsoAgenteReactivoIDL.ItfUsoAgenteReactivoIDLServant;
import icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.AdaptadorItfGestionRecursoSimpleIDL;
import icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.ItfGestionRecursoSimpleIDL;
import icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.ItfGestionRecursoSimpleIDLHelper;
import icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.ItfGestionRecursoSimpleIDLServant;
import icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.AdaptadorFactoriaRecursoSimpleIDL;
import icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.FactoriaRecursoSimpleIDL;
import icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.FactoriaRecursoSimpleIDLHelper;
import icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.FactoriaRecursoSimpleIDLServant;
import icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.AdaptadorItfUsoPersistenciaIDL;
import icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDL;
import icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDLHelper;
import icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDLServant;
import icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.AdaptadorItfUsoRecInformacionUsuarioIDL;
import icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL;
import icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLHelper;
import icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLServant;
import icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL.AdaptadorItfUsoRecTestPersonalidadIDL;
import icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL.ItfUsoRecTestPersonalidadIDL;
import icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL.ItfUsoRecTestPersonalidadIDLHelper;
import icaro.infraestructura.corba.recursos.itfUsoRecTestPersonalidadIDL.ItfUsoRecTestPersonalidadIDLServant;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL.AdaptadorItfUsoVisualizadorAccesoAltaIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL.ItfUsoVisualizadorAccesoAltaIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL.ItfUsoVisualizadorAccesoAltaIDLHelper;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoAltaIDL.ItfUsoVisualizadorAccesoAltaIDLServant;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.AdaptadorItfUsoVisualizadorAccesoIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDLHelper;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDLServant;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.AdaptadorItfUsoVisualizadorAltaIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDLHelper;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDLServant;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.AdaptadorItfUsoVisualizadorTestPersonalidadIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.ItfUsoVisualizadorTestPersonalidadIDL;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.ItfUsoVisualizadorTestPersonalidadIDLHelper;
import icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.ItfUsoVisualizadorTestPersonalidadIDLServant;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.AdaptadorItfUsoRecursoTrazasIDL;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.ItfUsoRecursoTrazasIDL;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.ItfUsoRecursoTrazasIDLHelper;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.ItfUsoRecursoTrazasIDLServant;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.entidadesBasicas.interfaces.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivoSimple.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivoSimple.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronRecursoSimple.FactoriaRecursoSimple;
import icaro.infraestructura.patronRecursoSimple.ItfGestionRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class RepositorioInterfacesCORBA extends RepositorioInterfaces {

	private ORB orb;
	private NamingContext repositorio;

	public RepositorioInterfacesCORBA() {
		super("RepositorioInterfaces");

		// Crear el ORB a partir del HOST leido desde la configuracion.
		InputStream corba;
		Properties props = null;
		String nombreHost=null,puerto=null;
		org.omg.CORBA.Object objRef = null;
		try {
			corba = new FileInputStream("./config/corba.properties");
			props = new Properties();

			props.load(corba);
			
			nombreHost = (String) props.get("org.omg.CORBA.ORBInitialHost");
			puerto= (String) props.get("org.omg.CORBA.ORBInitialPort");
					
			orb = ORB.init((String[]) null, props);
			
			objRef = orb.resolve_initial_references("NameService");
			
			repositorio = NamingContextHelper.narrow(objRef);
		} catch (FileNotFoundException e) {
			logger
					.fatal("El fichero de configuración de CORBA no se encuentra:"
							+ "./config/corba.properties");
		} catch (IOException e) {
			logger
					.fatal("No se puede leer el fichero de configuración de CORBA");
		}
		catch (InvalidName e) {

			logger.fatal("Error al conectar con el ORB. Compruebe que se esté ejecutando el demonio orbd en el host "+nombreHost+
					":\n orbd -ORBInitialHost "+nombreHost+" -ORBInitialPort "+puerto);
		}
		catch (Exception e) {
			logger.fatal("Error de conectividad en el ORB. Compruebe que el puerto " + puerto+" del nodo "+ nombreHost + " está abierto o no está siendo utilizado por otro proceso distinto a orbd.exe",e);
		}
		
	}

	public ORB getORB() {
		return orb;
	}

	@Override
	public void eliminarRegistroInterfaz(String nombre) {
		NameComponent nc = new NameComponent(nombre, "");
		NameComponent[] path = { nc };
		try {
			repositorio.unbind(path);
		} catch (NotFound e) {
			logger.fatal("Error al eliminar la interfaz: " + nombre+". Interfaz no encontrada. Motivo:"+ e.why);
		} catch (CannotProceed e) {
			logger.fatal("Error al eliminar la interfaz: " + nombre+". No se puede proceder.");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			logger.fatal("Error al eliminar la interfaz: " + nombre+". Nombre no válido.");
		}
	}

	@Override
	public java.lang.Object obtenerInterfaz(String nombre) throws Exception{
		NameComponent nc = new NameComponent(nombre, "");
		NameComponent[] path = { nc };
		try {
			Object obj = repositorio.resolve(path);
			return devolverAdaptador(obj);
		} catch (NotFound e) {
			logger.fatal("Error al obtener la interfaz: " + nombre+". Interfaz no encontrada. Motivo:"+ e.why);
		} catch (CannotProceed e) {
			logger.fatal("Error al obtener la interfaz: " + nombre+". No se puede proceder.");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			logger.fatal("Error al obtener la interfaz: " + nombre+". Nombre no válido.");
		}
		return null;
	}

	@Override
	public void registrarInterfaz(String nombre, java.lang.Object interfaz) {
		Object servant = crearServant(nombre,interfaz);
		NameComponent nc = new NameComponent(nombre, "");
		NameComponent[] path = { nc };
		try {
			repositorio.rebind(path, servant);
		} catch (NotFound e) {
			logger.fatal("Error al registrar la interfaz: " + nombre+". Interfaz no encontrada. Motivo:"+ e.why);
		} catch (CannotProceed e) {
			logger.fatal("Error al registrar la interfaz: " + nombre+". No se puede proceder.");
		} catch (org.omg.CosNaming.NamingContextPackage.InvalidName e) {
			logger.fatal("Error al registrar la interfaz: " + nombre+". Nombre no válido.");
		} catch (Exception e) {
			logger.fatal("",e);
		}
	}

	@Override
	public String listarNombresInterfacesRegistradas() {
		return repositorio.toString();
	}
	
	private java.lang.Object devolverAdaptador(Object obj)  {
		java.lang.Object adaptador = null;
		if (obj._is_a(ItfUsoAgenteReactivoIDLHelper.id())) {
			ItfUsoAgenteReactivoIDL itf = ItfUsoAgenteReactivoIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoAgenteReactivoIDL(itf);
		} else if (obj._is_a(ItfGestionAgenteReactivoIDLHelper.id())) {
			ItfGestionAgenteReactivoIDL itf = ItfGestionAgenteReactivoIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfGestionAgenteReactivoIDL(itf);
			
		} else if (obj._is_a(ItfGestionRecursoSimpleIDLHelper.id())) {
			ItfGestionRecursoSimpleIDL itf = ItfGestionRecursoSimpleIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfGestionRecursoSimpleIDL(itf);
			
		} else if (obj._is_a(ItfUsoPersistenciaIDLHelper.id())) {
			ItfUsoPersistenciaIDL itf = ItfUsoPersistenciaIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoPersistenciaIDL(itf);
			
		} else if (obj._is_a(ItfUsoVisualizadorAccesoIDLHelper.id())) {
			ItfUsoVisualizadorAccesoIDL itf = ItfUsoVisualizadorAccesoIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoVisualizadorAccesoIDL(itf);
		} else if (obj._is_a(ItfUsoVisualizadorAccesoAltaIDLHelper.id())) {
			ItfUsoVisualizadorAccesoAltaIDL itf = ItfUsoVisualizadorAccesoAltaIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoVisualizadorAccesoAltaIDL(itf);
		} else if (obj._is_a(ItfUsoVisualizadorAltaIDLHelper.id())) {
			ItfUsoVisualizadorAltaIDL itf = ItfUsoVisualizadorAltaIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoVisualizadorAltaIDL(itf);
		} else if (obj._is_a(ItfUsoVisualizadorTestPersonalidadIDLHelper.id())) {
			ItfUsoVisualizadorTestPersonalidadIDL itf = ItfUsoVisualizadorTestPersonalidadIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoVisualizadorTestPersonalidadIDL(itf);
			
		} else if (obj._is_a(ItfUsoRecTestPersonalidadIDLHelper.id())) {
			ItfUsoRecTestPersonalidadIDL itf = ItfUsoRecTestPersonalidadIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoRecTestPersonalidadIDL(itf);
			
		} else if (obj._is_a(ItfUsoRecInformacionUsuarioIDLHelper.id())) {
			ItfUsoRecInformacionUsuarioIDL itf = ItfUsoRecInformacionUsuarioIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorItfUsoRecInformacionUsuarioIDL(itf);
			
			
		} else if (obj._is_a(FactoriaAgenteReactivoIDLHelper.id())) {
			FactoriaAgenteReactivoIDL itf = FactoriaAgenteReactivoIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorFactoriaAgenteReactivoIDL(itf);
		} else if (obj._is_a(FactoriaRecursoSimpleIDLHelper.id())) {
			FactoriaRecursoSimpleIDL itf = FactoriaRecursoSimpleIDLHelper
					.narrow(obj);
			adaptador = new AdaptadorFactoriaRecursoSimpleIDL(itf);
			
		} else if (obj._is_a(ItfUsoConfiguracionIDLHelper.id())) {
			ItfUsoConfiguracionIDL itf = ItfUsoConfiguracionIDLHelper.narrow(obj);
			adaptador = new AdaptadorItfUsoConfiguracionIDL(itf);
		} else if (obj._is_a(ItfUsoRecursoTrazasIDLHelper.id())) {
			ItfUsoRecursoTrazasIDL itf = ItfUsoRecursoTrazasIDLHelper.narrow(obj);
			adaptador = new AdaptadorItfUsoRecursoTrazasIDL(itf);
			
		} else if (obj._is_a(IPercepcionAgenteCognitivoIDLHelper.id())) {
			IPercepcionAgenteCognitivoIDL itf = IPercepcionAgenteCognitivoIDLHelper.narrow(obj);
			adaptador = new AdaptadorIPercepcionAgenteCognitivoIDL(itf);
		} else if (obj._is_a(FactoriaAgenteCognitivoIDLHelper.id())) {
			FactoriaAgenteCognitivoIDL itf = FactoriaAgenteCognitivoIDLHelper.narrow(obj);
			adaptador = new AdaptadorFactoriaAgenteCognitivoIDL(itf);
		//Esta rama siempre tiene que ser la última (hay interfaces que heredan de InterfazGestion )
		} else if (obj._is_a(InterfazGestionIDLHelper.id())) {
		InterfazGestionIDL itf = InterfazGestionIDLHelper.narrow(obj);
		adaptador = new AdaptadorInterfazGestionIDL(itf);
		}
		return adaptador;

	}
	
	private Object crearServant(String nombre, java.lang.Object interfaz) {
		Object servant = null;
		if (interfaz instanceof ItfUsoAgenteReactivo
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoAgenteReactivo itf = (ItfUsoAgenteReactivo) interfaz;
			servant = new ItfUsoAgenteReactivoIDLServant(itf);
		} else if (interfaz instanceof ItfGestionAgenteReactivo
				&& nombre.contains(NombresPredefinidos.ITF_GESTION)) {
			ItfGestionAgenteReactivo itf = (ItfGestionAgenteReactivo) interfaz;
			servant = new ItfGestionAgenteReactivoIDLServant(itf);
		} else if (interfaz instanceof ItfGestionRecursoSimple
				&& nombre.contains(NombresPredefinidos.ITF_GESTION)) {
			ItfGestionRecursoSimple itf = (ItfGestionRecursoSimple) interfaz;
			servant = new ItfGestionRecursoSimpleIDLServant(itf);

		} else if (interfaz instanceof ItfUsoPersistencia
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoPersistencia itf = (ItfUsoPersistencia) interfaz;
			servant = new ItfUsoPersistenciaIDLServant(itf);

		} else if (interfaz instanceof ItfUsoVisualizadorAcceso
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoVisualizadorAcceso itf = (ItfUsoVisualizadorAcceso) interfaz;
			servant = new ItfUsoVisualizadorAccesoIDLServant(itf);
		} else if (interfaz instanceof ItfUsoVisualizadorAccesoAlta
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoVisualizadorAccesoAlta itf = (ItfUsoVisualizadorAccesoAlta) interfaz;
			servant = new ItfUsoVisualizadorAccesoAltaIDLServant(itf);
		} else if (interfaz instanceof ItfUsoVisualizadorAlta
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoVisualizadorAlta itf = (ItfUsoVisualizadorAlta) interfaz;
			servant = new ItfUsoVisualizadorAltaIDLServant(itf);
		} else if (interfaz instanceof ItfUsoVisualizadorTestPersonalidad
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoVisualizadorTestPersonalidad itf = (ItfUsoVisualizadorTestPersonalidad) interfaz;
			servant = new ItfUsoVisualizadorTestPersonalidadIDLServant(itf);
			
		} else if (interfaz instanceof ItfUsoRecTestPersonalidad
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoRecTestPersonalidad itf = (ItfUsoRecTestPersonalidad) interfaz;
			servant = new ItfUsoRecTestPersonalidadIDLServant(itf);
			
		} else if (interfaz instanceof ItfUsoRecInformacionUsuario 
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoRecInformacionUsuario itf = (ItfUsoRecInformacionUsuario) interfaz;
			servant = new ItfUsoRecInformacionUsuarioIDLServant(itf);
			
		} else if (interfaz instanceof ItfUsoConfiguracion
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoConfiguracion itf = (ItfUsoConfiguracion) interfaz;
			servant = new ItfUsoConfiguracionIDLServant(itf);
		} else if (interfaz instanceof ItfUsoRecursoTrazas
				&& nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoRecursoTrazas itf = (ItfUsoRecursoTrazas) interfaz;
			servant = new ItfUsoRecursoTrazasIDLServant(itf);
		} else if (interfaz instanceof FactoriaAgenteReactivo) {
			servant = new FactoriaAgenteReactivoIDLServant();

		} else if (interfaz instanceof FactoriaRecursoSimple) {
			servant = new FactoriaRecursoSimpleIDLServant();

		} else if (interfaz instanceof ItfUsoAgenteCognitivo && nombre.contains(NombresPredefinidos.ITF_USO)) {
			ItfUsoAgenteCognitivo itf = (ItfUsoAgenteCognitivo) interfaz;
			servant = new IPercepcionAgenteCognitivoIDLServant(itf);
		} else if (interfaz instanceof FactoriaAgenteCognitivo) {
			servant = new FactoriaAgenteCognitivoIDLServant();

		} 
		//Esta rama siempre tiene que ser la última (hay interfaces que heredan de InterfazGestion )
		else if (interfaz instanceof InterfazGestion && nombre.contains(NombresPredefinidos.ITF_GESTION)) {
			InterfazGestion itf = (InterfazGestion) interfaz;
			servant = new InterfazGestionIDLServant(itf);
		}
		return servant;
	}

}
