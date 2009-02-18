package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.ListaPropiedades;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.Propiedad;

import java.util.Properties;


public class ConstructorProperties {

	public static Properties obtenerProperties(ListaPropiedades listaPropiedades) {
		if (listaPropiedades != null) {
			Properties properties = new Properties();
			for (Propiedad prop : listaPropiedades.getPropiedad())
				properties.put(prop.getAtributo(), prop.getValor());
			return properties;
		}
		return null;

	}
}
