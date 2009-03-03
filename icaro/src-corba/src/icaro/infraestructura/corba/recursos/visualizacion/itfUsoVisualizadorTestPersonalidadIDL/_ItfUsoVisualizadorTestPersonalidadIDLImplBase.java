package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL;


/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/_ItfUsoVisualizadorTestPersonalidadIDLImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorTestPersonalidadIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public abstract class _ItfUsoVisualizadorTestPersonalidadIDLImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.ItfUsoVisualizadorTestPersonalidadIDL, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ItfUsoVisualizadorTestPersonalidadIDLImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("accionSalir", new java.lang.Integer (0));
    _methods.put ("mostrarMensajeError", new java.lang.Integer (1));
    _methods.put ("mostrarConfirmacion", new java.lang.Integer (2));
    _methods.put ("mostrarVentanaPrincipal", new java.lang.Integer (3));
    _methods.put ("variaVentanaPrincipal", new java.lang.Integer (4));
    _methods.put ("mostrarResultado", new java.lang.Integer (5));
    _methods.put ("mostrarIncompletitud", new java.lang.Integer (6));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/accionSalir
       {
         this.accionSalir ();
         out = $rh.createReply();
         break;
       }

       case 1:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/mostrarMensajeError
       {
         String descripcion = in.read_string ();
         this.mostrarMensajeError (descripcion);
         out = $rh.createReply();
         break;
       }

       case 2:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/mostrarConfirmacion
       {
         String textosTest[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_textosHelper.read (in);
         this.mostrarConfirmacion (textosTest);
         out = $rh.createReply();
         break;
       }

       case 3:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/mostrarVentanaPrincipal
       {
         String listaAyudas[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_textosHelper.read (in);
         this.mostrarVentanaPrincipal (listaAyudas);
         out = $rh.createReply();
         break;
       }

       case 4:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/variaVentanaPrincipal
       {
         int panel = in.read_long ();
         String listaPreguntas[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_textosHelper.read (in);
         int resultadosPrevios[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_resultadosHelper.read (in);
         this.variaVentanaPrincipal (panel, listaPreguntas, resultadosPrevios);
         out = $rh.createReply();
         break;
       }

       case 5:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/mostrarResultado
       {
         String descripcion = in.read_string ();
         int resultados[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_resultadosHelper.read (in);
         String textosTest[] = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_textosHelper.read (in);
         this.mostrarResultado (descripcion, resultados, textosTest);
         out = $rh.createReply();
         break;
       }

       case 6:  // organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL/mostrarIncompletitud
       {
         this.mostrarIncompletitud ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/ItfUsoVisualizadorTestPersonalidadIDL:1.0", 
    "IDL:organizacion/infraestructura/corba/patronRecursoSimple/itfUsoRecursoSimpleIDL/ItfUsoRecursoSimpleIDL:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ItfUsoVisualizadorTestPersonalidadIDLImplBase