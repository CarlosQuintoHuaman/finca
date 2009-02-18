setlocal
rem set NODO_GESTOR_ORGANIZACION="C:\Documents and Settings\Elisa\Escritorio\workspace\NodoGestorOrganizacion2\src"
rem set NODO_RESTO="C:\Documents and Settings\Elisa\Escritorio\workspace\NodoResto2\src"
rem set IDLs="C:\Documents and Settings\Elisa\Escritorio\workspace\factorias\src"
rem cd %IDLs%
rem set NODO_GESTOR_ORGANIZACION=../NodoGestorOrganizacion/src
rem set NODO_RESTO=../NodoResto/src

idlj -fall -OldImplBase -td ../tmp InterfazGestion.idl

idlj -fall -OldImplBase -td ../tmp ItfGestionAgenteReactivoIDL.idl
idlj -fall -OldImplBase -td ../tmp ItfUsoAgenteReactivoIDL.idl
idlj -fall -OldImplBase -td ../tmp FactoriaAgenteReactivoIDL.idl
  
idlj -fall -OldImplBase -td ../tmp ItfGestionRecursoSimpleIDL.idl
idlj -fall -OldImplBase -td ../tmp ItfUsoRecursoSimpleIDL.idl

idlj -fall -OldImplBase -td ../tmp ItfUsoPersistenciaIDL.idl
idlj -fall -OldImplBase -td ../tmp ItfUsoVisualizadorAccesoOrigIDL.idl


endlocal
