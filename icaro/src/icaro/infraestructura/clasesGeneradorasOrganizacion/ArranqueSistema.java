package icaro.infraestructura.clasesGeneradorasOrganizacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.asistenteDescOrganizacion.AsistenteDescOrganizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.Configuracion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.RecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ArranqueSistema {

    private static final long serialVersionUID = 1L;

    /**
     * Mï¿½todo de arranque principal de la organizaciï¿½n
     * 
     * @param args
     *            Entrada: ruta completa hasta el fichero de configuraciï¿½n
     */
    public static void main(String args[]) {

        boolean herramientaArrancada = false;

        // creamos los recursos de la organizaciï¿½n

        ItfUsoConfiguracion configuracionExterna = null;
        ItfUsoRecursoTrazas recursoTrazas = null;

        if (args.length == 0) {
            System.err.println("Error. Ningun argumento recibido.\n Causa: Es necesario pasar como argumento la ruta del fichero de descripcion.\n Ejemplo: ./config/descripcionAcceso.xml");
            int opcion = JOptionPane.showConfirmDialog(new JFrame(), "DescripciÃ³n de Organizacion no encontrado. Â¿Desea arrancar el asistente de creaciÃ³n de DescripciÃ³n de OrganizaciÃ³n?", "ConfirmaciÃ³n", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                arrancarHerramienta();
                herramientaArrancada = true;
            } else {
                System.exit(1);
            }
        } else {
            try {
                configuracionExterna = Configuracion.instance(args[0]);
                ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces.instance(RepositorioInterfaces.IMP_LOCAL);

                // registro la configuraciï¿½n 

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
                        "Descripción de Organizacion inválido. " +
                        "¿Desea arrancar el asistente de creación de Descripción de Organización?",
                        "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    arrancarHerramienta();
                    herramientaArrancada = true;
                } else {
                    System.exit(1);
                }
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
                //no es error crï¿½tico
                }
            }
            ItfGestionAgenteReactivo gestorOrg = null;
            if (!herramientaArrancada) {
                try {
                    DescInstanciaAgente descGestor = configuracionExterna.getDescInstanciaGestor(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    // creo el agente gestor de organizaciï¿½n
                    FactoriaAgenteReactivo.instancia().crearAgenteReactivo(descGestor);
                    gestorOrg = (ItfGestionAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);

                    // arranco la organizaciï¿½n
                    gestorOrg.arranca();

                } catch (Exception e) {
                    System.err.println("Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
                    System.exit(1);
                }
            }
        }

    }

    private static void arrancarHerramienta() {
        ItfUsoRepositorioInterfaces repositorioInterfaces = RepositorioInterfaces.instance(RepositorioInterfaces.IMP_LOCAL);
        repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + "AsistenteDescOrganizacion",
                        AsistenteDescOrganizacion.instancia());
        repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + "AsistenteDescOrganizacion",
                        AsistenteDescOrganizacion.instancia());
        AsistenteDescOrganizacion.instancia().arranca();
    }
}
