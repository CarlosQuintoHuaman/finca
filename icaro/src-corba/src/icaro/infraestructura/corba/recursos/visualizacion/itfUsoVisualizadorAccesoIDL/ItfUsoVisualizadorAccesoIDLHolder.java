package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL;

/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorAccesoIDL/ItfUsoVisualizadorAccesoIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorAccesoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfUsoVisualizadorAccesoIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDL value = null;

  public ItfUsoVisualizadorAccesoIDLHolder ()
  {
  }

  public ItfUsoVisualizadorAccesoIDLHolder (icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDLHelper.type ();
  }

}
