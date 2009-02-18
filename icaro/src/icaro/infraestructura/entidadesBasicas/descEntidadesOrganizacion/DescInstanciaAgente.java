package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;

public class DescInstanciaAgente extends DescInstancia {
	
	private DescComportamientoAgente  descComportamiento;
	
	
	public DescComportamientoAgente getDescComportamiento() {
		return descComportamiento;
	}
	public void setDescComportamiento(DescComportamientoAgente descComportamiento) {
		this.descComportamiento = descComportamiento;
	}

}
