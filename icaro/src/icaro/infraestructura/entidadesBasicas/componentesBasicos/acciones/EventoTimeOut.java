package icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class EventoTimeOut extends Thread {
	
	private long milis;
	private String nombre;
	private String origen;
	private String destino;
	
	private ItfUsoRepositorioInterfaces repositorio;
	
	public EventoTimeOut(long milis,String nombre,String origen,String destino, ItfUsoRepositorioInterfaces repositorio) {
		super();
		this.milis = milis;
		this.nombre = nombre;
		this.origen = origen;
		this.destino = destino;
		this.repositorio = repositorio;
		this.setDaemon(true);
	}

	@Override
	public void run(){
		try {
			sleep(milis);
			ItfUsoAgenteReactivo destinatario = (ItfUsoAgenteReactivo) repositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO+destino);
			destinatario.aceptaEvento(new EventoInput(nombre,origen,destino));
		} catch (Exception e) {
			System.err.println("Error al enviar evento de timeout");
			e.printStackTrace();
		}
		
	}


	
}