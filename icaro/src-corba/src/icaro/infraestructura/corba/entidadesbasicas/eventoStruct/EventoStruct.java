package icaro.infraestructura.corba.entidadesbasicas.eventoStruct;


/**
* organizacion/infraestructura/corba/entidadesbasicas/eventoStruct/EventoStruct.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/EntidadesBasicas.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public final class EventoStruct implements org.omg.CORBA.portable.IDLEntity
{
  public String clase = null;
  public String contenido = null;

  public EventoStruct ()
  {
  } // ctor

  public EventoStruct (String _clase, String _contenido)
  {
    clase = _clase;
    contenido = _contenido;
  } // ctor

} // class EventoStruct
