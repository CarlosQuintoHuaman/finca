package icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL;

/**
* organizacion/infraestructura/corba/patronAgenteCognitivo/iPercepcionAgenteCognitivoIDL/IPercepcionAgenteCognitivoIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/IPercepcionAgenteCognitivo.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class IPercepcionAgenteCognitivoIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDL value = null;

  public IPercepcionAgenteCognitivoIDLHolder ()
  {
  }

  public IPercepcionAgenteCognitivoIDLHolder (icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.patronAgenteCognitivo.iPercepcionAgenteCognitivoIDL.IPercepcionAgenteCognitivoIDLHelper.type ();
  }

}