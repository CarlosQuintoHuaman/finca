package icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL;

import organizacion.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLStub;


/**
* organizacion/infraestructura/corba/recursos/itfUsoRecInformacionUsuarioIDL/ItfUsoRecInformacionUsuarioIDLHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoRecInformacionUsuarioIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

abstract public class ItfUsoRecInformacionUsuarioIDLHelper
{
  private static String  _id = "IDL:organizacion/infraestructura/corba/recursos/itfUsoRecInformacionUsuarioIDL/ItfUsoRecInformacionUsuarioIDL:1.0";

  public static void insert (org.omg.CORBA.Any a, icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDLHelper.id (), "ItfUsoRecInformacionUsuarioIDL");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ItfUsoRecInformacionUsuarioIDLStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL)
      return (icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLStub stub = new icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL)
      return (icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL.ItfUsoRecInformacionUsuarioIDL)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLStub stub = new icaro.infraestructura.corba.recursos.itfUsoRecInformacionUsuarioIDL._ItfUsoRecInformacionUsuarioIDLStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
