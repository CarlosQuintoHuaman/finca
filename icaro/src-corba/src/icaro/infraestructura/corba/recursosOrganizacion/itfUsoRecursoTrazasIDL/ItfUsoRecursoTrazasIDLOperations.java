package icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL;


/**
* organizacion/infraestructura/corba/recursosOrganizacion/itfUsoRecursoTrazasIDL/ItfUsoRecursoTrazasIDLOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoRecursoTrazas.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public interface ItfUsoRecursoTrazasIDLOperations  extends icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDLOperations
{
  void aceptaNuevaTraza (icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.InfoTrazaStruct traza);
  void visualizaNuevaTraza (icaro.infraestructura.corba.recursosOrganizacion.itfUsoRecursoTrazasIDL.infoTrazaStruct.InfoTrazaStruct traza);
  void pedirConfirmacionTerminacionAlUsuario ();
} // interface ItfUsoRecursoTrazasIDLOperations