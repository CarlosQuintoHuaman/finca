package icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct;

/**
* organizacion/infraestructura/corba/recursosOrganizacion/itfUsoRecursoTrazasIDL/infoTrazaStruct/NivelTrazaIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoRecursoTrazas.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class NivelTrazaIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDL value = null;

  public NivelTrazaIDLHolder ()
  {
  }

  public NivelTrazaIDLHolder (icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.NivelTrazaIDLHelper.type ();
  }

}
