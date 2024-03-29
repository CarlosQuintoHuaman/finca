package icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;


/**
* organizacion/infraestructura/corba/patronAgenteReactivo/factoriaAgenteReactivoIDL/_FactoriaAgenteReactivoIDLStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/FactoriaAgenteReactivoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public class _FactoriaAgenteReactivoIDLStub extends org.omg.CORBA.portable.ObjectImpl implements icaro.infraestructura.corba.patronAgenteReactivo.factoriaAgenteReactivoIDL.FactoriaAgenteReactivoIDL
{

  public void crearAgenteReactivo (DescInstanciaAgente nombreAgente)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("crearAgenteReactivo", true);
                
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                crearAgenteReactivo (nombreAgente        );
            } finally {
                _releaseReply ($in);
            }
  } // crearAgenteReactivo

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:organizacion/infraestructura/corba/patronAgenteReactivo/factoriaAgenteReactivoIDL/FactoriaAgenteReactivoIDL:1.0"};

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
} // class _FactoriaAgenteReactivoIDLStub
