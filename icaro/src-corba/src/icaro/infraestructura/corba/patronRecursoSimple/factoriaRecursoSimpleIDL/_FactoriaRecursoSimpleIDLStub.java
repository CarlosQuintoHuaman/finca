package icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL;


/**
* organizacion/infraestructura/corba/patronRecursoSimple/factoriaRecursoSimpleIDL/_FactoriaRecursoSimpleIDLStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/FactoriaRecursoSimpleIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public class _FactoriaRecursoSimpleIDLStub extends org.omg.CORBA.portable.ObjectImpl implements icaro.infraestructura.corba.patronRecursoSimple.factoriaRecursoSimpleIDL.FactoriaRecursoSimpleIDL
{

  public void crearRecursoSimple (String nombreRecurso)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("crearRecursoSimple", true);
                $out.write_string (nombreRecurso);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                crearRecursoSimple (nombreRecurso        );
            } finally {
                _releaseReply ($in);
            }
  } // crearRecursoSimple

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:organizacion/infraestructura/corba/patronRecursoSimple/factoriaRecursoSimpleIDL/FactoriaRecursoSimpleIDL:1.0"};

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
} // class _FactoriaRecursoSimpleIDLStub
