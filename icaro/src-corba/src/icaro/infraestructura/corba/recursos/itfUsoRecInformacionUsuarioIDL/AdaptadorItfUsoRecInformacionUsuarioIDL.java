package icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL;

import icaro.aplicaciones.informacion.recInfoUsuario.MensajeUsuario;
import icaro.aplicaciones.recursos.recInformacionUsuario.ExcepcionMensajes;
import icaro.aplicaciones.recursos.recInformacionUsuario.ItfUsoRecInformacionUsuario;
import icaro.infraestructura.corba.Serializer;

import java.util.Vector;

import organizacion.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL;

public class AdaptadorItfUsoRecInformacionUsuarioIDL implements
		ItfUsoRecInformacionUsuario {
	
	private ItfUsoRecInformacionUsuarioIDL stub;
	
	public AdaptadorItfUsoRecInformacionUsuarioIDL(ItfUsoRecInformacionUsuarioIDL stub) {
		this.stub = stub;
	}

	public void anyadirAlternativaAlMensaje(String id, String alternativa)
			throws ExcepcionMensajes {
		stub.anyadirAlternativaAlMensaje(id, alternativa);

	}

	public Object[][] dameTablaDatosMostrar() {
		String tablaSerializada = stub.dameTablaDatosMostrar();
		Object tablaDeserializada = Serializer.deserialize(tablaSerializada);
		return (Object[][]) tablaDeserializada;
	}

	public void eliminarAlternativaMensaje(String id, int alternativa)
			throws ExcepcionMensajes {
		stub.eliminarAlternativaMensaje(id, alternativa);
	}

	public void eliminarMensaje(String id) throws ExcepcionMensajes {
		stub.eliminarMensaje(id);
	}

	public void guardarMensaje(MensajeUsuario mensaje) throws ExcepcionMensajes {
		String mensajeSerializado = Serializer.serialize(mensaje);
		stub.guardarMensaje(mensajeSerializado);
	}

	public String obtenerMensaje(String idMensaje) throws ExcepcionMensajes {
		String s = stub.obtenerMensaje(idMensaje);
		return s;
	}

	public String obtenerMensajeAlternativo(String idMensaje, int numAlternativa)
			throws ExcepcionMensajes {
		return stub.obtenerMensajeAlternativo(idMensaje, numAlternativa);
	}

	public String obtenerMensajeAlternativoParametrizado(String idMensaje,
			Vector parametros, int numAlternativa) throws ExcepcionMensajes {
		String parametrosSerializados = Serializer.serialize(parametros);
		return stub.obtenerMensajeAlternativoParametrizado(idMensaje, parametrosSerializados, numAlternativa);
	}

	public String obtenerMensajeParametrizado(String idMensaje,
			Vector parametros) throws ExcepcionMensajes {
		String parametrosSerializados = Serializer.serialize(parametros);
		return stub.obtenerMensajeParametrizado(idMensaje, parametrosSerializados);
	}

	public MensajeUsuario[] obtenerTodosLosMensajes() {
		String mensajesSerializados = stub.obtenerTodosLosMensajes();
		Object mensajesDeserializados = Serializer.deserialize(mensajesSerializados);
		return (MensajeUsuario[]) mensajesDeserializados;
	}

	
	
}
