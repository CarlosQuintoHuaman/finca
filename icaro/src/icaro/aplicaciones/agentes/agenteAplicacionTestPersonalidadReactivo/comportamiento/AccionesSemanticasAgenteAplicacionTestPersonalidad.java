package icaro.aplicaciones.agentes.agenteAplicacionTestPersonalidadReactivo.comportamiento;

import icaro.aplicaciones.recursos.recInformacionUsuario.ItfUsoRecInformacionUsuario;
import icaro.aplicaciones.recursos.recTestPersonalidad.ItfUsoRecTestPersonalidad;
import icaro.aplicaciones.recursos.visualizacionRecTestPersonalidad.ItfUsoVisualizadorTestPersonalidad;
import icaro.infraestructura.entidadesBasicas.EventoInput;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.RepositorioInterfaces;




public class AccionesSemanticasAgenteAplicacionTestPersonalidad extends AccionesSemanticasAgenteReactivo{
	
	/**
     * Marca el número de preguntas que tiene el test.
     */
    public final static int NUMERO_PREGUNTAS= 30;
    public final static int PREGUNTAS_POR_PANEL= 6;
    
    private ItfUsoVisualizadorTestPersonalidad visualizadorTestPersonalidad;
	private ItfUsoRecTestPersonalidad recTestPersonalidad;
	private ItfUsoRecInformacionUsuario recInfoUsuario;
	//private ItfUsoPersistenciaYD persistencia;
	private String[] listaPreguntas;
	private String[] listaAyudas;
	private String[] listaPreguntasEnvio;
	private String[] textosTest;
	private int[] resultadosPrevios;
	private int panel = 1;
	//private RespuestasTest respuestasBD;
    
	public void arranque(){
		try {
			this.listaPreguntas = new String[NUMERO_PREGUNTAS];
			this.listaAyudas = new String[NUMERO_PREGUNTAS];
			this.listaPreguntasEnvio = new String[PREGUNTAS_POR_PANEL];
			this.textosTest = new String[23];
			String nombre = "pregunta";
			String ayuda = "ayuda";
			recInfoUsuario = (ItfUsoRecInformacionUsuario)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"InfoUsuario");
			
			for(int i=1; i<=NUMERO_PREGUNTAS; i++){
				listaPreguntas[(i-1)] = recInfoUsuario.obtenerMensaje((nombre+i));
				listaAyudas[(i-1)] = recInfoUsuario.obtenerMensaje((ayuda+i));
			}
			
			for(int j=16; j<=38; j++){
				textosTest[(j-16)] = recInfoUsuario.obtenerMensaje("texto"+j);
			}
			
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			resultadosPrevios = recTestPersonalidad.cargaPreguntas(listaPreguntas);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			if(listaPreguntas[0]!= null)
				itfUsoAgente.aceptaEvento(new EventoInput("preguntas_ok",null,null));
			else
				itfUsoAgente.aceptaEvento(new EventoInput("preguntas_error", new Integer(0),null,null));
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de preguntas_ok al agente del test de personalidad");
			e.printStackTrace();
		}
	}
	
	public void comprobarTestRealizado(){
		boolean completo = false;
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			completo = recTestPersonalidad.todasRespondidas();
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			if(completo)
				itfUsoAgente.aceptaEvento(new EventoInput("test_realizado",null,null));
			else
				itfUsoAgente.aceptaEvento(new EventoInput("test_no_realizado",null,null));
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de preguntas_ok al agente del test de personalidad");
			e.printStackTrace();
		}
	}
	
	public void mostrarConfirmacionTest(){
		try {
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			visualizadorTestPersonalidad.mostrarConfirmacion(this.textosTest);
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mostrarInstrucciones(){
		try {
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			visualizadorTestPersonalidad.mostrarVentanaPrincipal(this.listaAyudas);
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mostrarPanel(Integer panelAnt){
		int[] resultadosPreviosEnvio = new int[PREGUNTAS_POR_PANEL];
		/*
		 * Se cargan las primeras preguntas
		 */
		this.panel = panelAnt.intValue() + 1;
			
		for(int i=0; i < this.listaPreguntasEnvio.length; i++){
			listaPreguntasEnvio[i] = listaPreguntas[(i + (panelAnt.intValue()*6))];
			resultadosPreviosEnvio[i] = resultadosPrevios[(i + (panelAnt.intValue()*6))];
		}
		try {
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			visualizadorTestPersonalidad.variaVentanaPrincipal(this.panel, listaPreguntasEnvio, resultadosPreviosEnvio);
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void guardarRespuestas(int[] respuestas){
		boolean completo = false;
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			recTestPersonalidad.anotaRespuestas(listaPreguntasEnvio, respuestas);
		}
		catch (Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(this.panel < 5){//no es el ultimo panel
			try {
				ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
				.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
				itfUsoAgente.aceptaEvento(new EventoInput("info_ok", new Integer(this.panel),null,null));	
			}
			catch (Exception e) {
				System.out.println("Ha habido un error al enviar el evento de info_ok al agente del test de personalidad");
				e.printStackTrace();
			}
		}
		else {//es el ultimo panel

			try {
				ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
				.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
				
				itfUsoAgente.aceptaEvento(new EventoInput("fin_info",null,null));
			}
			catch (Exception e) {
				System.out.println("Ha habido un error al enviar el evento de info al agente del test de personalidad");
				e.printStackTrace();
			}
		}
	}
	
	public void mostrarResultado(){
		String[] textos = new String[15];
		String textoResultado = null;
		int[] resultados = null;
		try {
			recInfoUsuario = (ItfUsoRecInformacionUsuario)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"InfoUsuario");
			
			//Obtenemos los textos del resultado del test
			for(int i = 1; i < 16; i++){
				textos[i-1] = recInfoUsuario.obtenerMensaje("texto"+i); 
			}
			
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			textoResultado = recTestPersonalidad.textoResultado(textos);
			resultados = recTestPersonalidad.getResultados();
			
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			visualizadorTestPersonalidad.mostrarResultado(textoResultado, resultados, this.textosTest);
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void accionTerminar(){
		try {
			/*ItfUsoAgenteReactivo itfUsoGestorOrgan = (ItfUsoAgenteReactivo)itfUsoRepositorio.obtenerInterfaz
			("Itf_Uso_Gestor_Organizacion");
			itfUsoGestorOrgan.aceptaEvento(new EventoInput("terminar_gestores_y_gestor_organizacion","AgenteAccesoUso","AgenteAccesoUso"));*/
			this.itfUsoGestorAReportar.aceptaEvento(new EventoInput("peticion_terminar_todo",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad",NombresPredefinidos.NOMBRE_GESTOR_AGENTES));
		} catch (Exception e) {
			logger.error("Error al mandar el evento de terminar_todo",e);
			try {
				ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
						NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
						trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionTestPersonalidad", 
															  "Error al mandar el evento de terminar_todo", 
															  InfoTraza.NivelTraza.error));
			}catch(Exception e2){e2.printStackTrace();}
			try{
				ItfUsoAgenteReactivo agenteAcceso = (ItfUsoAgenteReactivo) itfUsoRepositorio.obtenerInterfaz
				(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
				agenteAcceso.aceptaEvento(new EventoInput("error",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad",NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad"));
			}
			catch(Exception exc){
				try {
					ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas)RepositorioInterfaces.instance().obtenerInterfaz(
							NombresPredefinidos.ITF_USO+NombresPredefinidos.RECURSO_TRAZAS);
							trazas.aceptaNuevaTraza(new InfoTraza("AgenteAplicacionAcceso", 
																  "Fallo al enviar un evento error.", 
																  InfoTraza.NivelTraza.error));
				}catch(Exception e2){e2.printStackTrace();}
				logger.error("Fallo al enviar un evento error.",exc);
			}
		}
	}
	
	public void guardarRespuestas(){
		boolean completo = false;
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			recTestPersonalidad.guardarProgresoFichero();
			completo = recTestPersonalidad.todasRespondidas();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			if(completo)
				itfUsoAgente.aceptaEvento(new EventoInput("info_completa",null,null)); //TODO hay que tener en cuenta que puede no estar completo
			else
				itfUsoAgente.aceptaEvento(new EventoInput("info_incompleta",null,null));
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de terminar al agente del test de personalidad");
			e.printStackTrace();
		}
	}
	
	/*public void almacenarRespuestas(){
		RespuestasTest resultado = null;
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			resultado = recTestPersonalidad.guardarProgresoBD();
		}
		catch (Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			/*persistencia = (ItfUsoPersistenciaYD) itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"PersistenciaTest");
			persistencia.guardarTestPersonalidad(resultado);*/
		//}
		/*catch (Exception e) {
			System.out.println("Ha habido un error al guardar las respuestas en la base de datos.");
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput("info_almacenada",null,null));
				
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de terminar al agente del test de personalidad");
			e.printStackTrace();
		}
	}*/
	
	public void guardarProgreso(){
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			recTestPersonalidad.guardarProgresoFichero();
		}
		catch (Exception e){
			//TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput("terminar",null,null));	
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de terminar al agente del test de personalidad");
			e.printStackTrace();
		}
	}
	
	public void mostrarIncompletitud(){
		try {
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)RepositorioInterfaces.instance().obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			
			visualizadorTestPersonalidad.mostrarIncompletitud();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void error(Integer tipo){
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			visualizadorTestPersonalidad = (ItfUsoVisualizadorTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"VisualizacionTestPersonalidad");
			
			switch (tipo.intValue()){
				case 0:{
					//error de carga de las preguntas
					visualizadorTestPersonalidad.mostrarMensajeError("Ha ocurrido un error al obtener las preguntas. Se cerrará el Test de Personalidad.");
					itfUsoAgente.aceptaEvento(new EventoInput("terminar",null,null));
					break;
				}
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public void reiniciar(){
		this.panel = 1;
		for(int i=0; i<this.resultadosPrevios.length; i++)
			resultadosPrevios[i]=-1;
		try {
			recTestPersonalidad = (ItfUsoRecTestPersonalidad)itfUsoRepositorio.obtenerInterfaz
			(NombresPredefinidos.ITF_USO+"RecTestPersonalidad");
			recTestPersonalidad.reinicia();
		}catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ItfUsoAgenteReactivo itfUsoAgente = (ItfUsoAgenteReactivo) RepositorioInterfaces.instance()
			.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_AGENTE_APLICACION+"TestPersonalidad");
			itfUsoAgente.aceptaEvento(new EventoInput("reinicio_ok",null,null));	
		}
		catch (Exception e) {
			System.out.println("Ha habido un error al enviar el evento de terminar al agente del test de personalidad");
			e.printStackTrace();
		}
	}
	
	public void clasificaError(){
		
	}
}