package icaro.infraestructura.clasesGeneradorasOrganizacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescDefOrganizacion;
import icaro.infraestructura.entidadesBasicas.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ArranqueSistemaConCrtlGestorO {

    private static final long serialVersionUID = 1L;

    /**
     * M�todo de arranque principal de la organizaci�n
     * 
     * @param args
     *            Entrada: ruta completa hasta el fichero de configuraci�n
     */
    public static void main(String args[]) {

        boolean herramientaArrancada = false;

        // creamos los recursos de la organizaci�n

        ItfUsoConfiguracion configuracionExterna = null;
        ItfUsoRecursoTrazas recursoTrazas = null;

        if (args.length == 0) {
            System.err.println("Error. Ningun argumento recibido.\n Causa: Es necesario pasar como argumento la ruta del fichero de descripcion.\n Ejemplo: ./config/descripcionAcceso.xml");
            int opcion = JOptionPane.showConfirmDialog(new JFrame(), "Descripción de Organizacion no encontrado. ¿Desea arrancar el asistente de creación de Descripción de Organización?", "Confirmación", JOptionPane.YES_NO_OPTION);
           // if (opcion == JOptionPane.YES_OPTION) {
           //     arrancarHerramienta();
          //      herramientaArrancada = true;
          //  } else {
          //      System.exit(1);
          //  }
        } else {
            try {
            // Se crea el repositorio de interfaces y el recurso de trazas

                ItfUsoRepositorioInterfaces repositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
                recursoTrazas = ClaseGeneradoraRecursoTrazas.instance();
             
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);

            //       NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = NombresPredefinidos.RUTA_DESCRIPCIONES+args[0];
                    NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = args[0];
                } catch (Exception e) {
                    System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
                    e.printStackTrace();
                //no es error cr�tico
               }
            // Se crea el iniciador que se encargara de crear el resto de componentes

            ItfGestionAgenteReactivo ItfGestIniciador = null;
             ItfUsoAgenteReactivo ItfUsoIniciador = null;
                try {
    //                DescInstanciaAgente descGestor = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    // creo el agente gestor de organizaci�n
                    FactoriaAgenteReactivo.instancia().crearAgenteReactivo( NombresPredefinidos.NOMBRE_INICIADOR, NombresPredefinidos.PAQUETE_INICIADOR);
                    ItfGestIniciador = (ItfGestionAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_INICIADOR);
                     ItfUsoIniciador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_INICIADOR);
                    // arranco la organizaci�n
                  if ((ItfGestIniciador != null)&& (ItfUsoIniciador!= null)) {
                    ItfGestIniciador.arranca();
           //     DescDefOrganizacion descOrganizacionaCrear = new DescDefOrganizacion();
           //     descOrganizacionaCrear.setIdentFicheroDefOrganizacion(args[0]);
           //         ItfUsoIniciador.aceptaEvento( new EventoRecAgte("crearOrganizacion",descOrganizacionaCrear, "main", "iniciador" ));
                // args[0] contiene el identificador del fichero que contiene la definicion de la organizacion a crear
            //        ItfUsoIniciador.aceptaEvento( new EventoRecAgte("crearOrganizacion",args[0], "main", "iniciador" ));
                        }
                } catch (ExcepcionEnComponente e) {
                    System.err.println("Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    System.exit(1);
                }
                 catch (Exception e) {
                    System.err.println("Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    System.exit(1);
                }
            }
     }
    }
    /*

                configuracionExterna = Configuracion.instance(args[0]);
                

                // registro la configuraci�n 

                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION,
                        configuracionExterna);
            } catch (IllegalArgumentException ie) {
            	System.err.println("Error. La organizacion no se ha compilado correctamente. Compruebe que los ficheros xml de los automatas se encuentren en el classpath.");
            	ie.printStackTrace();
            	System.exit(1);
            }
            catch (Exception e) {
                System.err.println("Error. No se ha podido interpretar o registrar la descripcion.");
                e.printStackTrace();
                int opcion = JOptionPane.showConfirmDialog(new JFrame(), 
                        "Descripci�n de Organizacion inv�lido. " +
                        "�Desea arrancar el asistente de creaci�n de Descripci�n de Organizaci�n?",
                        "Confirmaci�n", JOptionPane.YES_NO_OPTION);
         //       if (opcion == JOptionPane.YES_OPTION) {
          //          arrancarHerramienta();
          //          herramientaArrancada = true;
          //      } else {
          //          System.exit(1);
          //      }
            }
            if (!herramientaArrancada) {
                try {
                    recursoTrazas = RecursoTrazas.instance();
                    ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces.instance();
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);

                } catch (Exception e) {
                    System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
                    e.printStackTrace();
                //no es error cr�tico
                }
            }
            ItfGestionAgenteReactivo gestorOrg = null;
            if (!herramientaArrancada) {
                try {
                    DescInstanciaAgente descGestor = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    // creo el agente gestor de organizaci�n
                    FactoriaAgenteReactivo.instancia().crearAgenteReactivo(descGestor);
                    gestorOrg = (ItfGestionAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

                    // arranco la organizaci�n
                    gestorOrg.arranca();

                } catch (ExcepcionEnComponente e) {
                    System.err.println("Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    System.exit(1);
                }
                 catch (Exception e) {
                    System.err.println("Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    System.exit(1);
                }
        }}

    }

   // private static void arrancarHerramienta() {
  //      ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces.instance(RepositorioInterfaces.IMP_LOCAL);
   //     repositorioInterfaces.registrarInterfaz(
  //                      NombresPredefinidos.ITF_USO + "AsistenteDescOrganizacion",
  //                      AsistenteDescOrganizacion.instancia());
  //      repositorioInterfaces.registrarInterfaz(
  //                      NombresPredefinidos.ITF_GESTION + "AsistenteDescOrganizacion",
  //                      AsistenteDescOrganizacion.instancia());
   //     AsistenteDescOrganizacion.instancia().arranca();
   // }
}*/
