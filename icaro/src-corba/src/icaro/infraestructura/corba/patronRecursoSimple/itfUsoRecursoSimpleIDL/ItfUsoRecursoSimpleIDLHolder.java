package icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL;

/**
* organizacion/infraestructura/corba/patronRecursoSimple/itfUsoRecursoSimpleIDL/ItfUsoRecursoSimpleIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoRecursoSimpleIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfUsoRecursoSimpleIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDL value = null;

  public ItfUsoRecursoSimpleIDLHolder ()
  {
  }

  public ItfUsoRecursoSimpleIDLHolder (icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDLHelper.type ();
  }

}
