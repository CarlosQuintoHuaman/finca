package icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL;

/**
* organizacion/infraestructura/corba/recursos/ItfUsoPersistenciaIDL/ItfUsoPersistenciaIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoPersistenciaIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfUsoPersistenciaIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDL value = null;

  public ItfUsoPersistenciaIDLHolder ()
  {
  }

  public ItfUsoPersistenciaIDLHolder (icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.recursos.ItfUsoPersistenciaIDL.ItfUsoPersistenciaIDLHelper.type ();
  }

}
