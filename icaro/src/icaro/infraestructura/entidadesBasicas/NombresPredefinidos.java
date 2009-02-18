package icaro.infraestructura.entidadesBasicas;

/**
 * Definiciones de los nombres de las interfaces. Se puede heredar de esta clase en cada aplicación
 * @author Jorge M. González Martín
 * @version 1.0
 */

public class NombresPredefinidos {
  
  public static final String TIPO_COGNITIVO = "Cognitivo";
  public static final String TIPO_REACTIVO = "Reactivo";

	
  public static String NOMBRE_GESTOR_AGENTES = "GestorAgentes";
  public static String NOMBRE_GESTOR_RECURSOS = "GestorRecursos" ;
  public static String NOMBRE_GESTOR_ORGANIZACION ="GestorOrganizacion" ;
  public static String NOMBRE_AGENTE_APLICACION = "AgenteAplicacion";
  
  public static final String CONFIGURACION = "Configuracion";
  public static final String RECURSO_TRAZAS = "RecursoTrazas";
  
  public static final String EXPR_REG_NOMBRE_AGENTE = "AgenteAplicacion([0-9a-zA-Z])*";
  public static final String EXPR_REG_NOMBRE_GESTOR = "(GestorOrganizacion|GestorAgentes|GestorRecursos)([0-9a-zA-Z])*";
  
  public static final String ITF_GESTION = "Itf_Ges_";
  public static final String ITF_USO = "Itf_Uso_";
  
  public static final String FACTORIA_AGENTE_REACTIVO = "FactoriaAgenteReactivo";
  public static final String FACTORIA_RECURSO_SIMPLE = "FactoriaRecursoSimple";
  
  public static final String FACTORIA_AGENTE_COGNITIVO = "FactoriaAgenteCognitivo";
  
  
  


  // añadir los nombres de cada aplicación en concreto

}