package icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL;

import organizacion.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLStub;


/**
* organizacion/infraestructura/corba/patronAgenteReactivo/factoriaAgenteReactivoIDL/FactoriaAgenteReactivoIDLHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/FactoriaAgenteReactivoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

abstract public class FactoriaAgenteReactivoIDLHelper
{
  private static String  _id = "IDL:organizacion/infraestructura/corba/patronAgenteReactivo/factoriaAgenteReactivoIDL/FactoriaAgenteReactivoIDL:1.0";

  public static void insert (org.omg.CORBA.Any a, icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDLHelper.id (), "FactoriaAgenteReactivoIDL");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_FactoriaAgenteReactivoIDLStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL)
      return (icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLStub stub = new icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL)
      return (icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLStub stub = new icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL._FactoriaAgenteReactivoIDLStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
