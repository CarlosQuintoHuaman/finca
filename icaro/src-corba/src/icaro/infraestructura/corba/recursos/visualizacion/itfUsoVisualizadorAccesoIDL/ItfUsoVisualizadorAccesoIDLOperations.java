package icaro.infraestructura.corba.recursos.visualizacion.itfUsoVisualizadorAccesoIDL;


/**
* organizacion/infraestructura/corba/recursos/visualizacion/itfUsoVisualizadorAccesoIDL/ItfUsoVisualizadorAccesoIDLOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Documents and Settings/damiano/Escritorio/workspace/OrganizacionCognitivo-CORBA/idls/ItfUsoVisualizadorAccesoIDL.idl
* viernes 13 de junio de 2008 11H12' CEST
*/

public interface ItfUsoVisualizadorAccesoIDLOperations  extends icaro.infraestructura.corba.patronRecursoSimple.itfUsoRecursoSimpleIDL.ItfUsoRecursoSimpleIDLOperations
{
  void mostrarVisualizadorAcceso (String nombreAgente, String tipoAgente);
  void cerrarVisualizadorAcceso ();
  void mostrarMensajeInformacion (String titulo, String mensaje);
  void mostrarMensajeAviso (String titulo, String mensaje);
  void mostrarMensajeError (String titulo, String mensaje);
} // interface ItfUsoVisualizadorAccesoIDLOperations
