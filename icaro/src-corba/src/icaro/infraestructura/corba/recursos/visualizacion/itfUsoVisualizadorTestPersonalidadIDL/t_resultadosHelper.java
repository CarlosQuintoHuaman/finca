package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL;


/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/t_resultadosHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorTestPersonalidadIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

abstract public class t_resultadosHelper
{
  private static String  _id = "IDL:organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorTestPersonalidadIDL/t_resultados:1.0";

  public static void insert (org.omg.CORBA.Any a, int[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static int[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorTestPersonalidadIDL.t_resultadosHelper.id (), "t_resultados", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static int[] read (org.omg.CORBA.portable.InputStream istream)
  {
    int value[] = null;
    int _len0 = istream.read_long ();
    value = new int[_len0];
    istream.read_long_array (value, 0, _len0);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, int[] value)
  {
    ostream.write_long (value.length);
    ostream.write_long_array (value, 0, value.length);
  }

}
