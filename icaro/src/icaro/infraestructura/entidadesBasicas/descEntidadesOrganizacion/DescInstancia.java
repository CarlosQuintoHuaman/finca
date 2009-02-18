package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Nodo;

import java.util.Properties;


public class DescInstancia {
	private String id;
	private Nodo nodo;
	private Properties propiedades;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Nodo getNodo() {
		return nodo;
	}
	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}
	public void setPropiedades(Properties propiedades) {
		this.propiedades = propiedades;
	}
	public String getValorPropiedad(String atributo) {
		return propiedades.getProperty(atributo);
	}
}
