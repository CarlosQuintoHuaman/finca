package icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL;

/**
* organizacion/infraestructura/corba/patronAgenteReactivo/itfGestionAgenteReactivoIDL/ItfGestionAgenteReactivoIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfGestionAgenteReactivoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfGestionAgenteReactivoIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDL value = null;

  public ItfGestionAgenteReactivoIDLHolder ()
  {
  }

  public ItfGestionAgenteReactivoIDLHolder (icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.patronAgenteReactivo.itfGestionAgenteReactivoIDL.ItfGestionAgenteReactivoIDLHelper.type ();
  }

}
