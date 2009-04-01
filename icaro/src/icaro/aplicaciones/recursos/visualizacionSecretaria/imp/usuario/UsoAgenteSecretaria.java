package icaro.aplicaciones.recursos.visualizacionSecretaria.imp.usuario;

import icaro.aplicaciones.informacion.dominioClases.aplicacionFicha.DatosFichaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosAgenda;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosCitaSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.DatosLlamada;
import icaro.aplicaciones.informacion.dominioClases.aplicacionSecretaria.HorasCita;
import icaro.aplicaciones.recursos.visualizacionFicha.imp.*;
import icaro.aplicaciones.recursos.visualizacionSecretaria.imp.ClaseGeneradoraVisualizacionSecretaria;
import icaro.herramientas.descripcionorganizacion.asistentecreacion.evento.Evento;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteCognitivo.ItfUsoAgenteCognitivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 *@author     
 *@created    
 */
public class UsoAgenteSecretaria {

    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionSecretaria visualizador;
    private String nombreAgenteSecretaria;
    private String tipoAgenteSecretaria;
    private ItfUsoAgenteReactivo usoAgenteControlador;

    public UsoAgenteSecretaria(ClaseGeneradoraVisualizacionSecretaria vis) {

        visualizador = vis;
        
    }

    private void getInformacionAgente() {
        nombreAgenteSecretaria = visualizador.getNombreAgenteControlador();
        tipoAgenteSecretaria = visualizador.getTipoAgenteControlador();

        if (nombreAgenteSecretaria.equals(NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaEsclavo")) {
            nombreAgenteSecretaria = NombresPredefinidos.NOMBRE_AGENTE_APLICACION + "FichaMaestro";
        }
    }

    public ClaseGeneradoraVisualizacionSecretaria getVisualizador() {
        return visualizador;

    }

    public void mostrarVentanaFicha(DatosAgenda datos){
    	getInformacionAgente();
    	
        try {
        if (itfUsoRepositorioInterfaces == null) {
            itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
        }
    	ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
        
        itfUsoFicha.aceptaEvento(new EventoInput("mostrarVentanaFicha", datos, "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));
    } catch (Exception e) {
        System.out.println("Ha habido un error al activar el agente Ficha desde el agente Secretaria");
        e.printStackTrace();
    }
    }
    public void notificacionCierreSistema() {
         try {
             nombreAgenteSecretaria = visualizador.getNombreAgenteControlador();
            usoAgenteControlador = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance().obtenerInterfaz(nombreAgenteSecretaria);
        } catch (Exception ex) {
            Logger.getLogger(UsoAgenteSecretaria.class.getName()).log(Level.SEVERE, null, ex);
        }       
        //cierre de ventanas que genera cierre del sistema

        try {
            getInformacionAgente();
            //me aseguro de crear las interfaces si han sido registradas ya
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("peticion_terminacion_usuario", "VisualizacionFicha", nombreAgenteSecretaria));
                }
            }
        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el evento termina al agente");
            e.printStackTrace();
        }
        //usoAgenteControlador.aceptaEvento(new Evento("peticion_terminacion_usuario"));
    }

    public void mostrarVentanaCita(String nombre, String apellido, String Telf, String hora){
    	getInformacionAgente();
    	DatosCitaSinValidar datos= new DatosCitaSinValidar(nombre, apellido, Telf, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
    
    public void mostrarVentanaProximasCitas(){
    	getInformacionAgente();
    	//DatosCitaSinValidar datos= new DatosCitaSinValidar(nombre, apellido, Telf, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("consultarPCitas", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al consultarPCitas ");
            e.printStackTrace();
        }
    }
    
    public void mostrarVentanaCita(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
    
    public void mostrarVentanaLlamadas(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("anadirLlamada", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
    
    public void mostrarVentanaLlamada(String nombre, String mensaje, String Telf, Boolean paciente, String hora){
    	getInformacionAgente();
    	DatosLlamada datos= new DatosLlamada(nombre, mensaje, Telf, paciente, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("anadirLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
    
       public void mostrarVentanaExtra(){
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("anadirExtra", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el añadir extra ");
            e.printStackTrace();
        }
    }
    
    public void mostrarVentanaExtra(String nombre, String mensaje, String Telf, Boolean paciente, String hora){
    	getInformacionAgente();
    	DatosLlamada datos= new DatosLlamada(nombre, mensaje, Telf, paciente, hora);
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("anadirExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el extra ");
            e.printStackTrace();
        }
    }

    public void cerrarVentanaCita(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorCita();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("cancelarCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarcita al agente secretaria");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaLlamada(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorLlamada();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("cancelarLlamada", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarLlamada al agente secretaria");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaExtra(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorExtra();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("cancelarExtra", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }
            

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarExtra al agente secretaria");
            e.printStackTrace();
        }
    }
    
    public void cerrarVentanaProximasCitas(){
    	getInformacionAgente();
    	
    	try {
    		visualizador.cerrarVisualizadorProximasCita();
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("cancelarPCitas", "VisualizacionSecretaria1", nombreAgenteSecretaria));
               
                }
            }
            

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar evento cancelarExtra al agente secretaria");
            e.printStackTrace();
        }
    }
    public void mostrarMensajeError(String mensaje, String titulo){
    	visualizador.mostrarMensajeError(titulo, mensaje);
    }
    
    public void validaCita(DatosCitaSinValidar datos) {
      
	   getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	
         

        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("infoCita", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
        
    }
    
    public void mostrarAgendaSecretaria(String fecha,String usuEste){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	String[] datos={fecha,usuEste};
        try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("agenda", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de la cita ");
            e.printStackTrace();
        }
    }
    
    public boolean estaLibre(HorasCita hora) {
        
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
     	
          

         try {
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
             }
           return visualizador.estaLibre(hora);

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos de la hora de la cita bMas ");
             e.printStackTrace();
             return false;
         }
         
     }
    
    public void BuscarCitas(String nombre,String telf){
    	getInformacionAgente();
        //provoca la peticiï¿½n de autentificaciï¿½n
    	String[] datos={nombre,telf};
    	try {
       	 
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }

            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("buscarPaciente", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar los datos de buscarPaciente ");
            e.printStackTrace();
        }
       
    }
    
    public void anadeLlamada(DatosLlamada datos) {
        
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
     	
          

         try {
        	 
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
             }

             if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                 //AgenteAplicacionSecretaria
                 ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                 if (itfUsoAgente != null) {
                     itfUsoAgente.aceptaEvento(new EventoInput("infoLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                 }
             }

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos de la llamada ");
             e.printStackTrace();
         }
         
     }
    
    public void modificaLlamada(DatosLlamada datAnt, DatosLlamada datPost) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
      	
           DatosLlamada[] datos={datAnt,datPost};
  	   //DatosLlamada datos=datAnt;

          try {
         	 
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoInput("modLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al enviar los datos de la llamada ");
              e.printStackTrace();
          }
          
      }
    public void borraLlamada(DatosLlamada datos) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
      	
           

          try {
        	  visualizador.cerrarVisualizadorLlamada();
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoInput("borrarLlamada", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al enviar los datos de la cita ");
              e.printStackTrace();
          }
          
      }
      
          public void anadeExtra(DatosLlamada datos) {
        
 	   getInformacionAgente();
         //provoca la peticiï¿½n de autentificaciï¿½n
     	
          

         try {
        	 
             if (itfUsoRepositorioInterfaces == null) {
                 itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
             }

             if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                 //AgenteAplicacionSecretaria
                 ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                 if (itfUsoAgente != null) {
                     itfUsoAgente.aceptaEvento(new EventoInput("infoExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                 }
             }

         } catch (Exception e) {
             System.out.println("Ha habido un error al enviar los datos del extra ");
             e.printStackTrace();
         }
         
     }
          
          public void modificaExtra(DatosLlamada datAnt, DatosLlamada datPost) {
              
         	   getInformacionAgente();
                 //provoca la peticiï¿½n de autentificaciï¿½n
             	
                  DatosLlamada[] datos={datAnt,datPost};
         	   

                 try {
                	 
                     if (itfUsoRepositorioInterfaces == null) {
                         itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
                     }

                     if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                         //AgenteAplicacionSecretaria
                         ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                         if (itfUsoAgente != null) {
                             itfUsoAgente.aceptaEvento(new EventoInput("modExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                         }
                     }

                 } catch (Exception e) {
                     System.out.println("Ha habido un error al enviar los datos del extra ");
                     e.printStackTrace();
                 }
                 
             }
    
    public void borraExtra(DatosLlamada datos) {
        
  	   getInformacionAgente();
          //provoca la peticiï¿½n de autentificaciï¿½n
      	
           

          try {
        	  visualizador.cerrarVisualizadorExtra();
              if (itfUsoRepositorioInterfaces == null) {
                  itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
              }

              if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                  //AgenteAplicacionSecretaria
                  ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                  if (itfUsoAgente != null) {
                      itfUsoAgente.aceptaEvento(new EventoInput("borrarExtra", datos, "VisualizacionSecretaria1", nombreAgenteSecretaria));
                  }
              }

          } catch (Exception e) {
              System.out.println("Ha habido un error al enviar los datos del extra ");
              e.printStackTrace();
          }
          
      }
     
     
    
    public void crearAgenteFicha() {
    	getInformacionAgente();
    	
    	try {
            if (itfUsoRepositorioInterfaces == null) {
                itfUsoRepositorioInterfaces = RepositorioInterfaces.instance();
            }
            
            ItfUsoAgenteReactivo itfUsoFicha = (ItfUsoAgenteReactivo)itfUsoRepositorioInterfaces.obtenerInterfaz("Itf_Uso_AgenteAplicacionFicha1");
            
            itfUsoFicha.aceptaEvento(new EventoInput("comenzar", "VisualizacionSecretaria1", "AgenteAplicacionFicha1"));

/*            if (tipoAgenteSecretaria.equals(NombresPredefinidos.TIPO_REACTIVO)) {
                //AgenteAplicacionSecretaria
                ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) itfUsoRepositorioInterfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO + nombreAgenteSecretaria);
                if (itfUsoAgente != null) {
                    itfUsoAgente.aceptaEvento(new EventoInput("darCita", "VisualizacionSecretaria1", nombreAgenteSecretaria));
                }
            }*/

        } catch (Exception e) {
            System.out.println("Ha habido un error al enviar el usuario y el password al agente de Ficha ");
            e.printStackTrace();
        }
    }
}

