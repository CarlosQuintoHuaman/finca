package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL;


/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorAccesoIDL/_ItfUsoVisualizadorAccesoIDLStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorAccesoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public class _ItfUsoVisualizadorAccesoIDLStub extends org.omg.CORBA.portable.ObjectImpl implements icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL.ItfUsoVisualizadorAccesoIDL
{

  public void mostrarVisualizadorAcceso (String nombreAgente, String tipoAgente)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("mostrarVisualizadorAcceso", true);
                $out.write_string (nombreAgente);
                $out.write_string (tipoAgente);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                mostrarVisualizadorAcceso (nombreAgente, tipoAgente        );
            } finally {
                _releaseReply ($in);
            }
  } // mostrarVisualizadorAcceso

  public void cerrarVisualizadorAcceso ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("cerrarVisualizadorAcceso", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                cerrarVisualizadorAcceso (        );
            } finally {
                _releaseReply ($in);
            }
  } // cerrarVisualizadorAcceso

  public void mostrarMensajeInformacion (String titulo, String mensaje)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("mostrarMensajeInformacion", true);
                $out.write_string (titulo);
                $out.write_string (mensaje);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                mostrarMensajeInformacion (titulo, mensaje        );
            } finally {
                _releaseReply ($in);
            }
  } // mostrarMensajeInformacion

  public void mostrarMensajeAviso (String titulo, String mensaje)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("mostrarMensajeAviso", true);
                $out.write_string (titulo);
                $out.write_string (mensaje);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                mostrarMensajeAviso (titulo, mensaje        );
            } finally {
                _releaseReply ($in);
            }
  } // mostrarMensajeAviso

  public void mostrarMensajeError (String titulo, String mensaje)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("mostrarMensajeError", true);
                $out.write_string (titulo);
                $out.write_string (mensaje);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                mostrarMensajeError (titulo, mensaje        );
            } finally {
                _releaseReply ($in);
            }
  } // mostrarMensajeError

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorAccesoIDL/ItfUsoVisualizadorAccesoIDL:1.0", 
    "IDL:organizacion/infraestructura/corba/patronRecursoSimple/itfUsoRecursoSimpleIDL/ItfUsoRecursoSimpleIDL:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _ItfUsoVisualizadorAccesoIDLStub
