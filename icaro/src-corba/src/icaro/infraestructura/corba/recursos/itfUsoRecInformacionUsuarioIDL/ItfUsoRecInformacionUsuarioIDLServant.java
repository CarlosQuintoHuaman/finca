package icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL;

import icaro.aplicaciones.informacion.recInfoUsuario.MensajeUsuario;
import icaro.aplicaciones.recursos.recInformacionUsuario.ExcepcionMensajes;
import icaro.aplicaciones.recursos.recInformacionUsuario.ItfUsoRecInformacionUsuario;
import icaro.infraestructura.corba.Serializer;

import java.util.Vector;

import organizacion.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLImplBase;

public class ItfUsoRecInformacionUsuarioIDLServant extends
		_ItfUsoRecInformacionUsuarioIDLImplBase {
	
	private ItfUsoRecInformacionUsuario delegate;
	
	public ItfUsoRecInformacionUsuarioIDLServant(ItfUsoRecInformacionUsuario delegate) {
		this.delegate = delegate;
	}

	public void anyadirAlternativaAlMensaje(String id, String alternativa) {
		try {
			delegate.anyadirAlternativaAlMensaje(id, alternativa);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String dameTablaDatosMostrar() {
		Object[][] tabla = delegate.dameTablaDatosMostrar();
		return Serializer.serialize(tabla);
	}

	public void eliminarAlternativaMensaje(String id, int alternativa) {
		try {
			delegate.eliminarAlternativaMensaje(id, alternativa);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void eliminarMensaje(String id) {
		try {
			delegate.eliminarMensaje(id);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void guardarMensaje(String mensaje) {
		try {
			MensajeUsuario mensajeDeserializado = (MensajeUsuario) Serializer.deserialize(mensaje);
			delegate.guardarMensaje(mensajeDeserializado);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String obtenerMensaje(String idMensaje) {
		try {
			return delegate.obtenerMensaje(idMensaje);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String obtenerMensajeAlternativo(String idMensaje, int numAlternativa) {
		try {
			return delegate.obtenerMensajeAlternativo(idMensaje, numAlternativa);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}

	public String obtenerMensajeAlternativoParametrizado(String idMensaje,
			String parametros, int numAlternativa) {
		try {
			Vector parametrosDeserializados = (Vector)Serializer.deserialize(parametros);
			return delegate.obtenerMensajeAlternativoParametrizado(idMensaje, parametrosDeserializados, numAlternativa);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String obtenerMensajeParametrizado(String idMensaje,
			String parametros) {
		
		try {
			Vector parametrosDeserializados = (Vector)Serializer.deserialize(parametros);
			return delegate.obtenerMensajeParametrizado(idMensaje, parametrosDeserializados);
		} catch (ExcepcionMensajes e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public String obtenerTodosLosMensajes() {
		MensajeUsuario[] mensajes = delegate.obtenerTodosLosMensajes();
		return Serializer.serialize(mensajes);
	}

	
	
	
}
