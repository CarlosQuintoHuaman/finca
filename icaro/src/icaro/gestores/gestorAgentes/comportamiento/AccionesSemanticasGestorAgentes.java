package icaro.gestores.gestorAgentes.comportamiento;

import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgenteAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteCognitivo.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.patronAgenteReactivoSCXML.AgenteReactivo;
import icaro.infraestructura.patronAgenteReactivoSCXML.FactoriaAgenteReactivoSCXML;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/*
 * Implementaci�n por defecto de las acciones que se ejecutar�n por parte del gestor de agentes.
 * @author     Felipe Polo
 */
public class AccionesSemanticasGestorAgentes extends AccionesSemanticasAgenteReactivo {

    /**
     * Almac�n de los nombres de los agentes que este gestor debe gestionar
     */
    protected Vector<String> nombresAgentesGestionados = new Vector<String>();
    /**
     * Depuraci�n para pruebas
     */
    protected static boolean DEBUG = true;
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
    public AccionesSemanticasGestorAgentes() {
        super();
        this.itfUsoRepositorio = RepositorioInterfaces.instance();
    /*
     * ItfUsoConfiguracion config =
     * (ItfUsoConfiguracion)RepositorioInterfaces.instance().obtenerInterfaz
     * (NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
     * tiempoParaNuevaMonitorizacion =
     * config.getEntornoEjecucionGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES).
     * getIntervaloMonitorizacion().intValue();
     */

    }

    public void configurarGestor() {
        try {
            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            tiempoParaNuevaMonitorizacion = Integer.parseInt(config.getValorPropiedadGlobal("gestores.comun.intervaloMonitorizacion"));
            
            this.itfUsoAgente.aceptaEvento(new EventoInput(
                    "gestor_configurado",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Hubo problemas al configurar el gestor de agentes.",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * Crea los agentes que se especifiquen en la configuraci�n o los localiza
     * si se encuentran remotos
     * 
     */
    public void crearAgentes() {
        // Si se produce excepci�n al crear alguno de los agentes, se aborta el
        // proceso.
        try {
            logger.debug("GestorAgentes: Creando los agentes definidos en la configuraci�n.");

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Creando los agentes definidos en la configuraci�n.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            // se examina la configuraci�n y se obtiene la lista de agentes a
            // crear

            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

            List<DescInstancia> lista = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES).getComponentesGestionados();

            Iterator<DescInstancia> iterador = null;

            if (lista != null) {
                iterador = lista.iterator();            // se recorren todos los agentes de la configuraci�n y se crean
            }
            while (iterador.hasNext()) {

            	DescInstanciaAgenteAplicacion instanciaActual = (DescInstanciaAgenteAplicacion)iterador.next();
                String nombreAgente = instanciaActual.getId();

                logger.debug("GestorAgentes: Creando agente " + nombreAgente +
                        ".");

                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                            "Creando agente " + nombreAgente + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                // crearlos uno a uno dependiendo de su tipo
                if (instanciaActual.getDescComportamiento().getTipo() == TipoAgente.COGNITIVO) {
                    crearYRegistrarUnAgenteCognitivo(nombreAgente);
                } else if (instanciaActual.getDescComportamiento().getTipo() == TipoAgente.REACTIVO) {
                          crearUnAgenteReactivo(nombreAgente);
                    } else if (instanciaActual.getDescComportamiento().getTipo() == TipoAgente.REACTIVO_SCXML) { {
                        crearUnAgenteReactivoSCXML(nombreAgente);
                    }
                } else {
                    try {
                        ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                        trazas.aceptaNuevaTraza(new InfoTraza(
                                "GestorAgentes",
                                "El subtipo del agente " + nombreAgente + " definido en la configuraci�n no es correcto.",
                                InfoTraza.NivelTraza.error));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }

                    logger.error("El subtipo del agente " + nombreAgente + " definido en la configuraci�n no es correcto.");

                    throw new Exception("El subtipo del agente " + nombreAgente + " definido en la configuraci�n no es correcto.");
                }

                // si todo ha ido bien, debemos a�adirlo a la lista de objetos
                // gestionados por el gestor

                logger.debug("GestorAgentes: A�adiendo agente " +
                        nombreAgente + " a la lista de objetos gestionados.");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                            "A�adiendo agente " + nombreAgente + " a la lista de objetos gestionados.",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                this.nombresAgentesGestionados.add(nombreAgente);
            }
            // todo ha ido bien -> seguimos
            this.itfUsoAgente.aceptaEvento(new EventoInput("agentes_creados",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            logger.debug("GestorAgentes: Terminado el proceso de creaci�n de agentes definidos en la configuraci�n.");
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza(
                        "GestorAgentes",
                        "Terminado el proceso de creaci�n de agentes definidos en la configuraci�n.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception ex) {
            logger.error("GestorAgentes: Hubo problemas al crear los agentes desde la configuraci�n. Recuperando errores de creaci�n.");
            ex.printStackTrace();
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza(
                        "GestorAgentes",
                        "Hubo problemas al crear los agentes desde la configuraci�n. Recuperando errores de creaci�n.",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            try {
                this.itfUsoAgente.aceptaEvento(new EventoInput(
                        "error_en_creacion_agentes",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Localiza un agente cognitivo que est� creado remotamente v�a RMI y lo
     * registra en el repositorio de interfaces.
     * 
     * @param agente
     */
    // private void localizarYRegistrarUnAgenteCognitivoRemoto(Agente agente)
    // throws Exception
    // {
	/*
     * try { // intento recuperar los objetos que se han registrado en RMI para
     * colocarlos en el repositorio InterfazGestion gestionAgCognitivo =
     * (InterfazGestion)this.obtenerItfRemoto(agente.getDireccionAccesoRMIGestion());
     * ItfPercepcionAgenteCognitivo usoAgCognitivo =
     * (ItfPercepcionAgenteCognitivo)this.obtenerItfRemoto(agente.getDireccionAccesoRMIUso());
     * 
     * if(DEBUG) System.out.println("GestorAgentes: Registrando agente
     * "+agente.getNombre()+" en el repositorio."); // una vez recuperados, los
     * almaceno en el repositorio
     * this.itfUsoRepositorio.registrarInterfaz(agente.getNombre()+this.SUFIJO_GESTION,gestionAgCognitivo);
     * this.itfUsoRepositorio.registrarInterfaz(agente.getNombre()+this.SUFIJO_USO,usoAgCognitivo); }
     * catch (Exception ex) { System.err.println("GestorAgentes: Fue imposible
     * localizar el agente "+agente.getNombre()); throw ex; }
     */
    // }
    /**
     * Crea un agente reactivo y lo registra en el repositorio
     * 
     * @param nombreAgente
     */
    private void crearUnAgenteReactivo(String nombreAgente) throws Exception {
        try {

            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            logger.debug("GestorAgentes: Construyendo agente reactivo " + nombreAgente + ".");
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            // creamos el reactivo y lo registramos en el repositorio

            // Agentes de aplicaci�n: local o remoto?
			DescInstanciaGestor descGestorAgentes = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String esteNodo = descGestorAgentes.getNodo().getNombreUso();

			DescInstanciaAgenteAplicacion instancia = config.getDescInstanciaAgenteAplicacion(nombreAgente);
			String nodoDestino = instancia.getNodo().getNombreUso();

            boolean ok = false;
            int intentos = 0;
            if (nodoDestino.equals(esteNodo)) {
                FactoriaAgenteReactivo.instancia().crearAgenteReactivo(instancia);
            } else {
                while (!ok) {
                    ++intentos;
                    try {
                        ((FactoriaAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.FACTORIA_AGENTE_REACTIVO + nodoDestino)).crearAgenteReactivo(instancia);
                        ok = true;
                    } catch (Exception e) {
                        try {
                            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorAgentes",
                                    "Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + esteNodo + "\t nodo destino: " + nodoDestino,
                                    InfoTraza.NivelTraza.error));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        logger.error("Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " +
                                intentos + " segundos...\n nodo origen: " + esteNodo +
                                "\t nodo destino: " + nodoDestino);

                        Thread.sleep(1000 * intentos);
                        ok = false;
                    }
                }
            }

            /*
             * logger.debug("GestorAgentes: Agente reactivo " + agente + "
             * creado.");
             */

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Agente reactivo " + nombreAgente + " creado.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            Set<Object> conjuntoEventos = new HashSet<Object>();
            conjuntoEventos.add(EventoInput.class);

            // indico a qui�n debe reportar
            ((ItfGestionAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombreAgente)).setGestorAReportar(
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES, conjuntoEventos);

        } catch (Exception ex) {

            logger.error("GestorAgentes: Error al crear el agente reactivo " +
                    nombreAgente + ".", ex);

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Error al crear el agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            throw ex;
        }
    }

    private void crearUnAgenteReactivoSCXML(String nombreAgente) throws Exception {
        //Pillar el recurso de configuracion 
        //-- Pillar el recurso de trazas e informar de la creacion
        //Detectar si esta en remoto o en local.
        //Si esta en local crearlo con la factoria y punto
        //Si esta en remoto localizar la factoria en remoto y ordenar creacion de agente.
        //-- Indicar por trazas que se ha creado correctamente o no el agente reactivo.

        try {

            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);
            logger.debug("GestorAgentes: Construyendo agente reactivo " + nombreAgente + ".");
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Construyendo agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            // creamos el reactivo y lo registramos en el repositorio

            // Agentes de aplicaci�n: local o remoto?
			DescInstanciaGestor descGestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String esteNodo = descGestorRecursos.getNodo().getNombreUso();

			DescInstanciaAgenteAplicacion instancia = config.getDescInstanciaAgenteAplicacion(nombreAgente);
			String nodoDestino = instancia.getNodo().getNombreUso();
			
            boolean ok = false;
            int intentos = 0;
            if (nodoDestino.equals(esteNodo)) {
                FactoriaAgenteReactivoSCXML.instancia().crearAgenteReactivo(instancia);
            } else {
                while (!ok) {
                    ++intentos;
                    try {
                        ((FactoriaAgenteReactivoSCXML) RepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.FACTORIA_AGENTE_REACTIVO + nodoDestino)).crearAgenteReactivo(instancia);
                        ok = true;
                    } catch (Exception e) {
                        try {
                            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorAgentes",
                                    "Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + esteNodo + "\t nodo destino: " + nodoDestino,
                                    InfoTraza.NivelTraza.error));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        logger.error("Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " +
                                intentos + " segundos...\n nodo origen: " + esteNodo +
                                "\t nodo destino: " + nodoDestino);

                        Thread.sleep(1000 * intentos);
                        ok = false;
                    }
                }
            }

            /*
             * logger.debug("GestorAgentes: Agente reactivo " + agente + "
             * creado.");
             */

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Agente reactivo " + nombreAgente + " creado.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        
            AgenteReactivo ref = (AgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgente);
            ref.setGestorAReportar(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
         
            
        } catch (Exception ex) {

            logger.error("GestorAgentes: Error al crear el agente reactivo " +
                    nombreAgente + ".", ex);

            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Error al crear el agente reactivo " + nombreAgente + ".",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            throw ex;
        }


    }

    /**
     * Crea un nuevo agente y lo registra en el repositorio de interfaces y en
     * RMI.
     * 
     * @param agente
     */
    private void crearYRegistrarUnAgenteCognitivo(String nombreAgente)
            throws Exception {
        try {

            ItfUsoConfiguracion config = (ItfUsoConfiguracion) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION);

			
			DescInstanciaGestor descGestorRecursos = config.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			String esteNodo = descGestorRecursos.getNodo().getNombreUso();

			DescInstanciaRecursoAplicacion instancia = config.getDescInstanciaRecursoAplicacion(nombreAgente);
			String nodoDestino = instancia.getNodo().getNombreUso();

            boolean ok = false;
            int intentos = 0;
            if (nodoDestino.equals(esteNodo)) {
                FactoriaAgenteCognitivo.instancia().crearAgenteCognitivo(nombreAgente);
            } else {
                while (!ok) {
                    ++intentos;
                    try {
                        ((FactoriaAgenteCognitivo) RepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.FACTORIA_AGENTE_COGNITIVO + nodoDestino)).crearAgenteCognitivo(nombreAgente);
                        ok = true;
                    } catch (Exception e) {
                        try {
                            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                            trazas.aceptaNuevaTraza(new InfoTraza(
                                    "GestorAgentes",
                                    "Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " + intentos + " segundos...\n nodo origen: " + esteNodo + "\t nodo destino: " + nodoDestino + e,
                                    InfoTraza.NivelTraza.error));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        logger.error("Error al crear el agente " + nombreAgente + " en un nodo remoto. Se volver� a intentar en " +
                                intentos + " segundos...\n nodo origen: " + esteNodo +
                                "\t nodo destino: " + nodoDestino);

                        Thread.sleep(1000 * intentos);
                        ok = false;
                    }
                }
            }


            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                        "Agente cognitivo " + nombreAgente + " creado.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        } catch (Exception e) {
            trazas.aceptaNuevaTraza(new InfoTraza("Error", "Error al crear el agente cognitivo " + nombreAgente + "\nExcepci�n: " + e.getMessage(), NivelTraza.error));
            logger.error("GestorAgentes: No se pudo terminar la creaci�n o el registro del agente cognitivo " + nombreAgente + ".", e);
            ItfUsoAgenteReactivo gestorAgentes = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
            gestorAgentes.aceptaEvento(new EventoInput("error_en_creacion_agentes", NombresPredefinidos.NOMBRE_GESTOR_AGENTES, NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            throw e;
        }
    /*
     * try { if(DEBUG) System.out.println("GestorAgentes: Construyendo
     * agente cognitivo "+agente.getNombre()+"."); // obtengo la clase que
     * implementa este agente cognitivo Class claseGeneradoraAgente=
     * Class.forName(agente.getClaseGeneradora()); // recupero el
     * constructor adecuado, el que tiene la signatura con 2 strings Class[]
     * signatura = new
     * Class[]{Class.forName("java.lang.String"),Class.forName("java.lang.String"),Class.forName("java.lang.String"),Class.forName("java.lang.String")};
     * Constructor constructorGestorAgCognitivoLocal =
     * claseGeneradoraAgente.getConstructor(signatura); // defino los
     * par�metros para el constructor String dirRMIGes =
     * agente.getDireccionAccesoRMIGestion(); String dirRMIUso =
     * agente.getDireccionAccesoRMIUso(); String ficheroReglas =
     * agente.getRutaFicheroReglas(); String nombreAgente =
     * agente.getNombre(); // creamos el agente cognitivo Object
     * gestionAgenteCognitivo =
     * constructorGestorAgCognitivoLocal.newInstance(new Object[]
     * {dirRMIGes, dirRMIUso, ficheroReglas, nombreAgente});
     * 
     * if(DEBUG) System.out.println("GestorAgentes: Terminada construcci�n
     * del agente cognitivo "+agente.getNombre()+"."); // obtenemos itf
     * gestion remoto InterfazGestion itfGes =
     * (InterfazGestion)this.obtenerItfRemoto(dirRMIGes); // obtenemos itf
     * gestion remoto ItfPercepcionAgenteCognitivo itfUso =
     * (ItfPercepcionAgenteCognitivo)this.obtenerItfRemoto(dirRMIUso); //
     * registramos ambos objetos en el repositorio if(DEBUG)
     * System.out.println("GestorAgentes: Registrando el agente
     * "+agente.getNombre()+" en el repositorio de interfaces.");
     * this.itfUsoRepositorio.registrarInterfaz(nombreAgente+SUFIJO_GESTION,itfGes);
     * this.itfUsoRepositorio.registrarInterfaz(nombreAgente+SUFIJO_USO,itfUso); }
     * catch (Exception ex){ System.err.println("GestorAgentes: No se pudo
     * terminar la creaci�n o el registro del agente cognitivo
     * "+agente.getNombre()+"."); throw ex; }
     */

    }

    /**
     * intenta crear de nuevo los agentes que especifique la config y hayan dado
     * problemas.
     */
    public void recuperarErrorCreacionAgentes() {
        // por defecto no se implementan pol�ticas de recuperaci�n
        logger.debug("GestorAgentes: No es posible recuperar error en creaci�n de los agentes.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "No es posible recuperar error en creaci�n de los agentes. ",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoAgente.aceptaEvento(new EventoInput(
                    "imposible_recuperar_creacion",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * arranca los agentes que se especifiquen en la config.
     */
    public void arrancarAgentes() {
        logger.debug("GestorAgentes: Arrancando agentes.");

        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                    "Arrancando agentes. ", InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        // centinela
        boolean errorEnArranque = false;

        // recorremos todos los agentes que se han creado
        Enumeration enumAgentes = this.nombresAgentesGestionados.elements();
        while (enumAgentes.hasMoreElements()) {
            try {
                String nombre = (String) enumAgentes.nextElement();
                // para cada agente, recuperamos su itf de gestion

                logger.debug("GestorAgentes: Es necesario arrancar el agente " +
                        nombre + ", recuperando interfaz de gesti�n.");

                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                            "Es necesario arrancar el agente " + nombre + ", recuperando interfaz de gesti�n. ",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
                // arrancamos el agente
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza("GestorAgentes",
                            "Arrancando el agente " + nombre + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                logger.debug("GestorAgentes: Arrancando el agente " + nombre +
                        ".");

                itfGesAg.arranca();
                logger.debug("GestorAgentes: Orden de arranque ha sido dada al agente " + nombre + ".");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Orden de arranque ha sido dada al agente " + nombre + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception ex) {
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Hubo un problema al acceder a un interfaz remoto mientras se arrancaban los agentes en el gestor de agentes.",
                            InfoTraza.NivelTraza.error));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

                logger.error("GestorAgentes: Hubo un problema al acceder a un interfaz remoto mientras se arrancaban los agentes en el gestor de agentes.");
                ex.printStackTrace();
                errorEnArranque = true;
            }
        }

        // creo hebra de monitorizaci�n
        hebra = new HebraMonitorizacion(tiempoParaNuevaMonitorizacion,
                this.itfUsoAgente, "monitorizar");
        hebra.start();

        if (errorEnArranque) {
            logger.error("GestorAgente: Se produjo un error en el arranque de los agentes.");
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza(
                        "GestorAgentes",
                        "Se produjo un error en el arranque de los agentes.",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                this.itfUsoAgente.aceptaEvento(new EventoInput(
                        "error_en_arranque_agentes",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.debug("GestorAgentes: Terminado proceso de arranque autom�tico de agentes.");
            try {
                ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                trazas.aceptaNuevaTraza(new InfoTraza(
                        "GestorAgentes",
                        "Terminado proceso de arranque autom�tico de agentes.",
                        InfoTraza.NivelTraza.debug));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                this.itfUsoAgente.aceptaEvento(new EventoInput(
                        "agentes_arrancados_ok",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
                this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
                        "gestor_agentes_arrancado_ok",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Se decide cerrar el sistema ante un error cr�tico irrecuperable.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Terminado proceso de arranque autom�tico de agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        try {
            this.itfUsoAgente.aceptaEvento(new EventoInput(
                    "tratamiento_terminar_agentes_y_gestor_agentes",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
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
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Fue imposible recuperar el error en el arranque de los agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoAgente.aceptaEvento(new EventoInput(
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
        logger.error("GestorAgentes: Finalizando gestor de agentes debido a un error irrecuperable.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Finalizando gestor de agentes debido a un error irrecuperable.",
                    InfoTraza.NivelTraza.error));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
                    "error_en_arranque_gestores",
                    NombresPredefinidos.NOMBRE_GESTOR_RECURSOS,
                    NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
            this.itfUsoAgente.aceptaEvento(new EventoInput("informe_generado",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * da orden de terminar a un agente
     */
    public void finalizarAgente() {
        // esto hay que recuperarlo de los par�metros
        logger.debug("GestorAgentes: finalizarAgente():Este m�todo no est� implementado");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "finalizarAgente():Este m�todo no est� implementado",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Crea y arranca un agente. Es necesario pasar las caracter�sticas del
     * agente a crear por par�metro.
     */
    public void crearAgente() {
        // esto hay que recuperarlo de los par�metros
        logger.debug("GestorAgentes: crearAgente():Este m�todo no est� implementado");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "crearAgente():Este m�todo no est� implementado",
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
                        ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
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
                this.itfUsoAgente.aceptaEvento(new EventoInput(
                        "error_al_monitorizar",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.itfUsoAgente.aceptaEvento(new EventoInput("agentes_ok",
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                        NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Da orden de terminaci�n a todos los agentes que se encuentran
     * activos/arrancando
     */
    public void terminarAgentesActivos() {
        logger.debug("GestorAgentes: Terminando los agentes que est�n activos.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Terminando los agentes que est�n activos.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        // recorremos todos los agentes gestionados
        Enumeration enumAgentes = this.nombresAgentesGestionados.elements();
        String nombre = "";
        while (enumAgentes.hasMoreElements()) {
            try {
                nombre = (String) enumAgentes.nextElement();
                // para cada agente, recuperamos su itf de gestion
                logger.debug("GestorAgentes: Recuperando Itf Gesti�n del agente " + nombre + ".");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Recuperando Itf Gesti�n del agente " + nombre + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                InterfazGestion itfGesAg = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
                // finalizamos el agente
                logger.debug("GestorAgentes: Terminando el agente " + nombre + ".");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Terminando el agente " + nombre + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                itfGesAg.termina();
                logger.debug("GestorAgentes: Orden de terminaci�n ha sido dada al agente " + nombre + ".");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Orden de terminaci�n ha sido dada al agente " + nombre + ".",
                            InfoTraza.NivelTraza.debug));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception ex) {
                logger.error("GestorAgentes: Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminaci�n al agente " + nombre + ".");
                try {
                    ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                    trazas.aceptaNuevaTraza(new InfoTraza(
                            "GestorAgentes",
                            "Hubo un problema al acceder a un interfaz remoto mientras se daba orden de terminaci�n al agente " + nombre + ".",
                            InfoTraza.NivelTraza.error));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                ex.printStackTrace();
            }
        }
        logger.debug("GestorAgentes: Finalizado proceso de terminaci�n de todos los agentes.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Finalizado proceso de terminaci�n de todos los agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoAgente.aceptaEvento(new EventoInput(
                    "agentes_terminados",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Intenta recuperar los errores detectados en la monitorizaci�n siguiendo
     * la pol�tica definida para cada agente.
     */
    public void recuperarErrorAlMonitorizarAgentes() {
        // por defecto no se implementan pol�ticas de recuperaci�n
        logger.debug("GestorAgentes: No se pudo recuperar el error de monitorizaci�n.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "No se pudo recuperar el error de monitorizaci�n.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoAgente.aceptaEvento(new EventoInput(
                    "imposible_recuperar_error_monitorizacion",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * destruye los recursos que se crearon a lo largo del ciclo de vida de este
     * agente.
     */
    public void terminarGestorAgentes() {
        // termina el gestor.
        // puede esperarse a que terminen los agentes
        //logger.debug("GestorAgentes: Terminando gestor de agentes.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Terminando gestor de agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.hebra != null) {
            this.hebra.finalizar();
        }
        try {
            ((InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_AGENTES)).termina();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
                    "gestor_agentes_terminado",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pedirTerminacionAGestorOrg() {
        logger.debug("GestorAgentes: Pidiento terminaci�n al gestor de organizaci�n.");
        try {
            ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) RepositorioInterfaces.instance().obtenerInterfaz(
                    NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "GestorAgentes",
                    "Pidiento terminaci�n al gestor de organizaci�n.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.itfUsoGestorAReportar.aceptaEvento(new EventoInput(
                    "peticion_terminar_todo",
                    NombresPredefinidos.NOMBRE_GESTOR_AGENTES,
                    NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void clasificaError() {
    }
}
