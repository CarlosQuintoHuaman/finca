package icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL;


/**
* organizacion/infraestructura/corba/patronAgenteCognitivo/factoriaAgenteCognitivoIDL/_FactoriaAgenteCognitivoIDLImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/FactoriaAgenteCognitivoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public abstract class _FactoriaAgenteCognitivoIDLImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements icaro.infraestructura.corba.patronAgenteCognitivo.factoriaAgenteCognitivoIDL.FactoriaAgenteCognitivoIDL, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _FactoriaAgenteCognitivoIDLImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("crearAgenteCognitivo", new java.lang.Integer (0));
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
       case 0:  // organizacion/infraestructura/corba/patronAgenteCognitivo/factoriaAgenteCognitivoIDL/FactoriaAgenteCognitivoIDL/crearAgenteCognitivo
       {
         String nombreAgente = in.read_string ();
         this.crearAgenteCognitivo (nombreAgente);
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
    "IDL:organizacion/infraestructura/corba/patronAgenteCognitivo/factoriaAgenteCognitivoIDL/FactoriaAgenteCognitivoIDL:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _FactoriaAgenteCognitivoIDLImplBase