package icaro.infraestructura.entidadesBasicas;

/**
 * Definiciones de los nombres de las interfaces. Se puede heredar de esta clase en cada aplicaci�n
 * @author Francisco J Garijo
 * @version 1.0
 */

public class NombresPredefinidos {
  
  public static final String TIPO_COGNITIVO = "Cognitivo";
  public static final String TIPO_REACTIVO = "Reactivo";

	
  public static String NOMBRE_GESTOR_AGENTES = "GestorAgentes";
  public static String NOMBRE_GESTOR_RECURSOS = "GestorRecursos" ;
  public static String NOMBRE_GESTOR_ORGANIZACION ="GestorOrganizacion" ;
  public static String NOMBRE_INICIADOR ="Iniciador" ;
  public static String NOMBRE_AGENTE_APLICACION = "AgenteAplicacion";
  public static String ACTIVACION_PANEL_TRAZAS_DEBUG = "true";
  public static final String CONFIGURACION = "Configuracion";
  public static final String INTERVALO_MONITORIZACION_PROPERTY = "gestores.comun.intervaloMonitorizacion";
  
  public static final String RECURSO_TRAZAS = "RecursoTrazas";
  
  public static final String EXPR_REG_NOMBRE_AGENTE = "AgenteAplicacion([0-9a-zA-Z])*";
  public static final String EXPR_REG_NOMBRE_GESTOR = "(GestorOrganizacion|GestorAgentes|GestorRecursos)([0-9a-zA-Z])*";
  
  public static final String ITF_GESTION = "Itf_Ges_";
  public static final String ITF_USO = "Itf_Uso_";
  
  public static final String FACTORIA_AGENTE_REACTIVO = "FactoriaAgenteReactivo";
  public static final String FACTORIA_RECURSO_SIMPLE = "FactoriaRecursoSimple";
  
  public static final String FACTORIA_AGENTE_COGNITIVO = "FactoriaAgenteCognitivo";
  
  public static final String PAQUETE_GESTORES = "icaro.gestores";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_ORGANIZACION = "icaro.gestores.gestorOrganizacion.comportamiento";
  public static final String PAQUETE_INICIADOR = "icaro.gestores.iniciador";
  public static final String PAQUETE_GESTOR_ORGANIZACION = "icaro.gestores.gestorOrganizacion.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_AGENTES = "icaro.gestores.gestorAgentes.comportamiento";
  public static final String COMPORTAMIENTO_PORDEFECTO_GESTOR_RECURSOS = "icaro.gestores.gestorRecursos.comportamiento";
  public static final String PAQUETE_AGENTES_APLICACION = "icaro.aplicaciones.agentes";  
  public static final String PAQUETE_RECURSOS_APLICACION = "icaro.aplicaciones.recursos";

  public static final String PAQUETE_CLASES_GENERADORAS_ORGANIZACION = "icaro.infraestructura.clasesGeneradorasOrganizacion";
  public static  String CLASES_GENERADORA_ORGANIZACION_PORDEFECTO = "icaro.infraestructura.clasesGeneradorasOrganizacion.ArranqueSistemaSinAsistente";
  public static final String PREFIJO_CLASE_ACCIONES_SEMANTICAS = "AccionesSemanticas";
  public static final String PREFIJO_CLASE_GENERADORA_RECURSO = "ClaseGeneradora";
  public static final String PREFIJO_AUTOMATA = "automata";
  
  public static final String DESCRIPCION_SCHEMA = "./schemas/DescripcionOrganizacionSchema.xsd";
  public static  String DESCRIPCION_XML_POR_DEFECTO = "descripcionAcceso.xml";
  public static final String RUTA_DESCRIPCIONES = "./config/icaro/aplicaciones/descripcionOrganizaciones/";
  public static final String PAQUETE_JAXB = "icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb";
  public static final String RUTA_LOGS = "./log/";
  
  
  
  


  // a�adir los nombres de cada aplicaci�n en concreto

}
