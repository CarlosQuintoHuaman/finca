package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL;

/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorAltaIDL/ItfUsoVisualizadorAltaIDLHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorAccesoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class ItfUsoVisualizadorAltaIDLHolder implements org.omg.CORBA.portable.Streamable
{
  public icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDL value = null;

  public ItfUsoVisualizadorAltaIDLHolder ()
  {
  }

  public ItfUsoVisualizadorAltaIDLHolder (icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDL initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDLHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDLHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAltaIDL.ItfUsoVisualizadorAltaIDLHelper.type ();
  }

}
