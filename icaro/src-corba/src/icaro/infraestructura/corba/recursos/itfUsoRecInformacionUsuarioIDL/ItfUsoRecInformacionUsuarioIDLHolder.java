package icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL;

/**
* organizacion/infraestructura/corba/recursos/itfUsoRecInformacionUsuarioIDL/ItfUsoRecInformacionUsuarioIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoRecInformacionUsuarioIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfUsoRecInformacionUsuarioIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL value = null;

  public ItfUsoRecInformacionUsuarioIDLHolder ()
  {
  }

  public ItfUsoRecInformacionUsuarioIDLHolder (icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLHelper.type ();
  }

}
