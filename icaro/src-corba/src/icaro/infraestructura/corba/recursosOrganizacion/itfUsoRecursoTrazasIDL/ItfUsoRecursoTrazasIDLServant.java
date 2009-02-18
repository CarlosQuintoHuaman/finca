package icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL;

import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.InfoTrazaStruct;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDL;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import organizacion.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL._ItfUsoRecursoTrazasIDLImplBase;

public class ItfUsoRecursoTrazasIDLServant extends _ItfUsoRecursoTrazasIDLImplBase{

	ItfUsoRecursoTrazas delegate;
	
	public ItfUsoRecursoTrazasIDLServant(ItfUsoRecursoTrazas delegate) {
		this.delegate = delegate;
	}
	
	
	private NivelTraza getNivel(NivelTrazaIDL nivel) {
		if (nivel.equals(NivelTrazaIDL.error))
			return NivelTraza.error;
		else if (nivel.equals(NivelTrazaIDL.info))
			return NivelTraza.info;
		else if (nivel.equals(NivelTrazaIDL.debug))
			return NivelTraza.debug;
		else if (nivel.equals(NivelTrazaIDL.fatal))
			return NivelTraza.fatal;
		return NivelTraza.info;
	}
	
	private InfoTraza infoTrazaStruct2InfoTraza(InfoTrazaStruct traza) {
		InfoTraza infoTraza = new InfoTraza();
		infoTraza.setMensaje(traza.mensaje);
		infoTraza.setNombre(traza.entidadEmisora);
		infoTraza.setNivel(getNivel(traza.nivel));
		return infoTraza;
	}
	
	public void aceptaNuevaTraza(InfoTrazaStruct traza) {
		    InfoTraza infoTraza = infoTrazaStruct2InfoTraza(traza);
			delegate.aceptaNuevaTraza(infoTraza);		
	}

	
	public void visualizaNuevaTraza(InfoTrazaStruct traza) {
		InfoTraza infoTraza = infoTrazaStruct2InfoTraza(traza);
		delegate.visualizaNuevaTraza(infoTraza);	
	}


	public void pedirConfirmacionTerminacionAlUsuario() {
		delegate.pedirConfirmacionTerminacionAlUsuario();
	}


	

}
