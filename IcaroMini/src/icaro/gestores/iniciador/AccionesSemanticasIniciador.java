package icaro.gestores.iniciador;

import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorRutasEntidades;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;

import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import java.util.Enumeration;
import java.util.Vector;

/*
 * Implementaci�n por defecto de las acciones que se ejecutar�n por parte del gestor de agentes.
 * @author     Felipe Polo
 */
public class AccionesSemanticasIniciador extends AccionesSemanticasAgenteReactivo {

    /**
     * Almac�n de los nombres de los agentes que este gestor debe gestionar
     */
    protected Vector<String> nombresAgentesGestionados = new Vector<String>();
    /**
     * Depuraci�n para pruebas
     */
    protected static boolean DEBUG = true;
    private InterfazGestion ItfGestionRecTrazas;
    private ItfUsoRecursoTrazas ItfUsoRecTrazas;
    private ItfGestionAgenteReactivo ItfgestGestorOrg = null;
    /**
     * Hebra para la monitorizaci�n
     */
    private HebraMonitorizacion hebra;
    /**
     * Tiempo de monitorizaci�n
     */
    protected int tiempoParaNuevaMonitorizacion;
    protected final static String SUBTIPO_COGNITIVO = "Cognitivo";
    protected final static String SUBTIPO_REACTIVO = "Reactivo";

    /**
     * Constructor
     */
    public AccionesSemanticasIniciador() {
        super();
         try {
       itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();
        
       ItfUsoRecTrazas = (ItfUsoRecursoTrazas) this.itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
       ItfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
        }
          catch (Exception e2) {
                e2.printStackTrace();
                }
    /*
     * ItfUsoConfiguracion config =
     * (ItfUsoConfiguracion)RepositorioInterfaces.instance().obtenerInterfaz
     * (NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
     * tiempoParaNuevaMonitorizacion =
     * config.getEntornoEjecucionGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES).
     * getIntervaloMonitorizacion().intValue();
     */

    }

public void verificarExistenciaEntidadesDescripcion () {

    // Se verifican las rutas donde deben encontrarse las entidades de descripcion:
    // El esquema de descripcion de la organizacion, el fichero de descripcion y el paquete jaxb
        ComprobadorRutasEntidades comprobadorRutas = new ComprobadorRutasEntidades();
        Boolean SeHapodidoLocalizarEsquema = true;
        Boolean SeHapodidoLocalizarFicheroDescripcion = true;

       if ( !comprobadorRutas.existeSchemaDescOrganizacion()){
           ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo encontrar fichero que define el esquema para interpretar la descripcion de la Organizacion",
                        InfoTraza.NivelTraza.error));
                        SeHapodidoLocalizarEsquema = false;
                }
         if ( !comprobadorRutas.existeDescOrganizacion(NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO)){
           ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo encontrar fichero de descripcion de la Organizacion",
                        InfoTraza.NivelTraza.error));
                        SeHapodidoLocalizarFicheroDescripcion = false;
       }
    try {

        if (SeHapodidoLocalizarEsquema && SeHapodidoLocalizarFicheroDescripcion){
          this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "existenEntidadesDescripcion",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
         }
         else {
           this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "errorLocalizacionFicherosDescripcion",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
               }
        }
        catch (Exception e2) {
            e2.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se ha enviar un evento a si mismo notificando la  validacion de las rutas de las entidades de descripcion . ", InfoTraza.NivelTraza.error));

         }
         }

public void crearRecursosNucleoOrganizacion () throws Exception {

    // Intento crear los recursos en orden pero si se producen errores se capturan, se visualizan y
    // se pude intentar dialogar con el usuario para que los corrija
    try {
   // Se crea el recurso de configuración y posteriormente se debería dar orden al recurso
    // de trazas para pintar el proceso.
  
    //    ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces.instance(RepositorioInterfaces.IMP_LOCAL);


        ItfUsoConfiguracion   configuracionExterna = ClaseGeneradoraConfiguracion.instance();
                

                // registro la configuraci�n 

                        itfUsoRepositorio.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION,configuracionExterna);
               // Ahora que la configuracion es correcta , interpreto las propiedades globales
              //  y pongo la configuracion de trazas definida por el usuario
                       Boolean ConfiguracionTrazas = false;
                       if (NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG.startsWith("t")){
                           ConfiguracionTrazas = true;
                       }
                       else
                           ItfGestionRecTrazas.termina();
                       ItfUsoRecTrazas.visualizacionDeTrazas(ConfiguracionTrazas);
                        // Se crea el gestor de Organizacion
                // DescInstanciaAgente descGestor = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    // creo el agente gestor de organizaci�n
               
                    // arranco la organizacion

                    this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "recursosNucleoCreados",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
                //    ItfgestGestorOrg.arranca();
            } catch (IllegalArgumentException ie) {
            	System.err.println("Error. La organizacion no se ha compilado correctamente. Compruebe que los ficheros xml de los automatas se encuentren en el classpath.");
            	ie.printStackTrace();
            	System.exit(1);
                
            }
                catch (ExcepcionEnComponente ie) {
            	 ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo crear el comportamiento del Gestor de Organizacion",
                        InfoTraza.NivelTraza.error));
                this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "error_InterpretacionDescripcionOrganizacion",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            }
            catch (Exception e) {
                System.err.println("Error. No se ha podido interpretar o registrar la descripcion.");
                e.printStackTrace();
                        }
        }
    
   
    public void crearGestorOrganizacion () throws Exception {
        try {

            
            FactoriaAgenteReactivo.instancia().crearAgenteReactivo(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION ,NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_GESTOR_ORGANIZACION);
            ItfgestGestorOrg = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

              this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "GestorOrganizacionCreado",
                        NombresPredefinidos.NOMBRE_INICIADOR,
                        NombresPredefinidos.NOMBRE_INICIADOR));
                
            }
                catch (ExcepcionEnComponente ie) {
            	 ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo crear el comportamiento del Gestor de Organizacion",
                        InfoTraza.NivelTraza.error));
                 this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "error_al_crearGestorOrganizacion",
                        NombresPredefinidos.NOMBRE_INICIADOR,
                        NombresPredefinidos.NOMBRE_INICIADOR));
                
            }
            catch (Exception e) {
                System.err.println("Error. No se ha podido crear o registrar  el Gestor de Organizacion.");
                e.printStackTrace();
                        }
    }

    /**
     * arranca los agentes que se especifiquen en la config.
     */
    public void arrancarGestorOrganizacionyTerminar() {
        logger.debug("GestorAgentes: Arrancando agentes.");

        try {
            
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "Arrancando el Gestor de Agentes . ", InfoTraza.NivelTraza.debug));
            
            if  (ItfgestGestorOrg != null ){
                 // arranca  el Gestor de  Organizacion y Termina el iniciador
                       this. ItfgestGestorOrg.arranca();
                       this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "GestorOrganizacion_arrancado_ok",
                        NombresPredefinidos.NOMBRE_INICIADOR,
                        NombresPredefinidos.NOMBRE_INICIADOR));

                        terminarPorPropiaVoluntad();
                        
                                            }
            else {
              ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "La interfaz del GO es nula . ", InfoTraza.NivelTraza.error));
             // genero un evento interno de error
              this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "error_al_arrancarGestorOrganizacion",
                        NombresPredefinidos.NOMBRE_INICIADOR,
                        NombresPredefinidos.NOMBRE_INICIADOR));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se ha podido  arrancar  el Gestor de Organizacion . ", InfoTraza.NivelTraza.error));
            
        }
        
    }

    /**
     * Devuelve cierto si es necesario arrancar el agente
     * 
     * @param nombreAgente
     * @return
     */
    /*
     * private boolean esNecesarioArrancar(String nombreAgente) { Enumeration
     * enume = configEspecifica.getListaAgentes().enumerateAgente(); while
     * (enume.hasMoreElements()) { Agente item = (Agente)enume.nextElement(); if
     * (nombreAgente.equals(item.getNombre())) return
     * item.getHayQueArrancarlo(); } logger.error("GestorAgentes: No se encontr�
     * ning�n agente con nombre "+nombreAgente+" dentro de la lista de objetos
     * gestionados."); throw new NullPointerException(); }
     */
    /**
     * Decide qu� hacer en caso de fallos en los agentes.
     */
    public void decidirTratamientoErrorIrrecuperable() {
        // el tratamiento ser� siempre cerrar todo el chiringuito
        logger.debug("GestorAgentes: Se decide cerrar el sistema ante un error cr�tico irrecuperable.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    "Se decide cerrar el sistema ante un error critico irrecuperable.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    "Terminado proceso de arranque automatico de agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        try {
            this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                    "tratamiento_terminar_agentes_y_gestor_agentes",
                    NombresPredefinidos.NOMBRE_INICIADOR,
                    NombresPredefinidos.NOMBRE_INICIADOR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * intenta arrancar los agentes que especifique la config y hayan dado
     * problemas.
     */
    public void recuperarErrorArranqueAgentes() {
        // por defecto no se implementan pol�ticas de recuperaci�n
        //logger.debug("GestorAgentes: Fue imposible recuperar el error en el arranque de los agentes.");
        try {
             trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    "Fue imposible recuperar el error en el arranque de los agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                    "imposible_recuperar_arranque",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elabora un informe del estado en el que se encuentran los agentes y lo
     * env�a al sistema de trazas.
     */
    public void generarInformeErrorIrrecuperable() {
        // Producimos traza de un error
        logger.error("GestorIniciador: Se ha producido un   error irrecuperable.");
        try {
            
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    " Iniciador a la espera de terminacion por parte del usuario debido a un error irrecuperable.",
                    InfoTraza.NivelTraza.error));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
     /*
        try {
            this.itfUsoGestorAReportar.aceptaEvento(new EventoRecAgte(
                    "error_en_arranque_gestores",
                    NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
                    NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
            this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("informe_generado",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
      */
    }

    
    /**
     * Crea y arranca un agente. Es necesario pasar las caracter�sticas del
     * agente a crear por par�metro.
     */
    public void crearAgente() {
        // esto hay que recuperarlo de los par�metros
        logger.debug("GestorAgentes: crearAgente():Este metodo no esta implementado");
        try {
             trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    "crearAgente():Este metodo no esta implementado",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Monitoriza secuencialmente todos los agentes activos que est�n definidos
     * como necesarios en la configuraci�n.
     */
    public void monitorizarAgentes() {
        // if(DEBUG) System.out.println("GestorAgentes:Comienza ciclo
        // monitorizaci�n.");

        boolean errorEncontrado = false;
        // recuperar todos los interfaces de gesti�n del repositorio que estamos
        // gestionando
        Enumeration enume = nombresAgentesGestionados.elements();
        while (enume.hasMoreElements() && !errorEncontrado) {
            String nombre = (String) enume.nextElement();
            // if(DEBUG) System.out.println("GestorAgentes:Monitorizando agente
            // "+nombre+".");
            // recupero el interfaz de gestion del repositorio
            try {
                InterfazGestion itfGes = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
                int monitoriz = itfGes.obtenerEstado();
                if (monitoriz == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE || monitoriz == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE || monitoriz == InterfazGestion.ESTADO_TERMINADO || monitoriz == InterfazGestion.ESTADO_TERMINANDO) {
                    errorEncontrado = true;
                    logger.debug("GestorAgentes:Agente " + nombre + " est� en estado err�neo o terminado.");
                    try {
                        ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                        trazas.aceptaNuevaTraza(new InfoTraza(
                                "GestorAgentes",
                                "Agente " + nombre + " est� en estado err�neo o terminado.",
                                InfoTraza.NivelTraza.debug));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            /*
             * else if(DEBUG) System.out.println("GestorAgentes:Agente
             * "+nombre+" est� ok.");
             */
            } catch (Exception ex) {
                errorEncontrado = true;
                logger.error("GestorAgentes: No se pudo acceder al repositorio.");
                ex.printStackTrace();
            }
        }

        if (errorEncontrado) {
            try {
                this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte(
                        "error_al_monitorizar",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.itfUsoPropiadeEsteAgente.aceptaEvento(new EventoRecAgte("agentes_ok",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    /**
     * Intenta recuperar los errores detectados en la monitorizaci�n siguiendo
     * la pol�tica definida para cada agente.
     */
    

    /**
     * destruye los recursos que se crearon a lo largo del ciclo de vida de este
     * agente.
     */
    public void terminarPorPropiaVoluntad() {
        // termina el gestor.
        // puede esperarse a que terminen los agentes
        //logger.debug("GestorAgentes: Terminando gestor de agentes.");
        logger.debug("GestorOrganizacion: Terminando gestor de la organizaci�n y los recursos de la infraestructura.");
		ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
				"Terminando el Iniciador.",
				InfoTraza.NivelTraza.debug));
		try {
			// se obtiene la propia interfaz de gestion para terminar
			
  
			((ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ this.nombreAgente))
					.termina();
   
 //        this.itfUsoPropiadeEsteAgente.aceptaEvento(new
//         EventoRecAgte ("termina",this.nombreAgente,this.nombreAgente));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.debug("Iniciador: Terminando.");
		ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
				"Terminando.",
				InfoTraza.NivelTraza.debug));
		if (this.hebra != null)
			this.hebra.finalizar();
}
    public void procesarPeticionTerminacion() {
		logger.debug("Iniciador: Procesando la peticion de terminacion");
		ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
				"Procesando la peticion de terminacion",
				InfoTraza.NivelTraza.debug));
		
		ItfUsoRecTrazas.pedirConfirmacionTerminacionAlUsuario();
		
		/*try {
			// this.itfUsoAgente.aceptaEvento(new
			// EventoRecAgte("termina",null,null));
			
		
			ItfGestionAgenteReactivo gestion = (ItfGestionAgenteReactivo) this.itfUsoRepositorio
					.obtenerInterfaz(NombresPredefinidos.ITF_GESTION
							+ NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
			gestion.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void comenzarTerminacionConfirmada() {
		logger.debug("Iniciador: Terminando recursos...");
		ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
				"Comenzando la terminacion de los recursos...",
				InfoTraza.NivelTraza.info));
		
            ItfGestionRecTrazas.termina();
			// y a continuaci�n se termina el gestor 
            terminarPorPropiaVoluntad();
		
	
	}

    public void clasificaError() {
    }
}
