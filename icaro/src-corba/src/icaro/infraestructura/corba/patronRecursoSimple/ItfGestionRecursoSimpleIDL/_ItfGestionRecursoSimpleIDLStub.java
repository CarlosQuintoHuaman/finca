package icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL;


/**
* organizacion/infraestructura/corba/patronRecursoSimple/ItfGestionRecursoSimpleIDL/_ItfGestionRecursoSimpleIDLStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfGestionRecursoSimpleIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public class _ItfGestionRecursoSimpleIDLStub extends org.omg.CORBA.portable.ObjectImpl implements icaro.infraestructura.corba.patronRecursoSimple.ItfGestionRecursoSimpleIDL.ItfGestionRecursoSimpleIDL
{

  public void arranca ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("arranca", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                arranca (        );
            } finally {
                _releaseReply ($in);
            }
  } // arranca

  public void continua ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("continua", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                continua (        );
            } finally {
                _releaseReply ($in);
            }
  } // continua

  public int obtenerEstado ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("obtenerEstado", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return obtenerEstado (        );
            } finally {
                _releaseReply ($in);
            }
  } // obtenerEstado

  public void para ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("para", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                para (        );
            } finally {
                _releaseReply ($in);
            }
  } // para

  public void termina ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("termina", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                termina (        );
            } finally {
                _releaseReply ($in);
            }
  } // termina

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:organizacion/infraestructura/corba/patronRecursoSimple/ItfGestionRecursoSimpleIDL/ItfGestionRecursoSimpleIDL:1.0", 
    "IDL:organizacion/infraestructura/corba/interfazGestionIDL/InterfazGestionIDL:1.0"};

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
} // class _ItfGestionRecursoSimpleIDLStub
