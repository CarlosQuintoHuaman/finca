package icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL;

import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.InfoTrazaStruct;
import icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDL;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import organizacion.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.ItfUsoRecursoTrazasIDL;

public class AdaptadorItfUsoRecursoTrazasIDL implements ItfUsoRecursoTrazas {

	private ItfUsoRecursoTrazasIDL stub;

	public AdaptadorItfUsoRecursoTrazasIDL(ItfUsoRecursoTrazasIDL stub) {
		this.stub = stub;
	}

	private InfoTrazaStruct infoTraza2InfoTrazaStruct(InfoTraza infoTraza) {
		InfoTrazaStruct trazaStruct = new InfoTrazaStruct();
		trazaStruct.entidadEmisora = infoTraza.getNombre();
		trazaStruct.mensaje = infoTraza.getMensaje();
		trazaStruct.nivel = getNivelTrazaIDL(infoTraza.getNivel());
		return trazaStruct;
	}

	private NivelTrazaIDL getNivelTrazaIDL(NivelTraza nivel) {
		switch (nivel) {
		case error:
			return NivelTrazaIDL.error;
		case debug:
			return NivelTrazaIDL.debug;
		case info:
			return NivelTrazaIDL.info;
		case fatal:
			return NivelTrazaIDL.fatal;
		default:
			return NivelTrazaIDL.info;
		}
	}

	
	public void aceptaNuevaTraza(InfoTraza infoTraza) {
		InfoTrazaStruct traza = infoTraza2InfoTrazaStruct(infoTraza);
		stub.aceptaNuevaTraza(traza);
	}

	
	public void visualizaNuevaTraza(InfoTraza infoTraza) {
		InfoTrazaStruct traza = infoTraza2InfoTrazaStruct(infoTraza);
		stub.visualizaNuevaTraza(traza);

	}

	public void pedirConfirmacionTerminacionAlUsuario() {
		stub.pedirConfirmacionTerminacionAlUsuario();
		
	}

}
