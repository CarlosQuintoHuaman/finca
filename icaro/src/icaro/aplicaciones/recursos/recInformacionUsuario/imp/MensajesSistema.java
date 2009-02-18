package icaro.aplicaciones.recursos.recInformacionUsuario.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad.recInfoUsuario.MensajeUsuario;
import icaro.aplicaciones.recursos.recInformacionUsuario.ExcepcionMensajes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;


/**
 *  Clase que lee y obtiene los mensajes a presentar al usuario del fichero de
 *  texto que contiene todos los mensajes del sistema
 *
 * @author     Carlos Delgado
 * @created    23 de enero de 2002
 */
public class MensajesSistema{

    /**
     * Properties que contienen los mensajes del sistema
     */
    protected  Hashtable mensajes;

    /**
     * Números aleatorios
     */
    protected  Random generador;
    
    private String fichero;

       /**
     * mensajes (alternativas totales)
     */
	private int numFilasTotales;

	/**
	 * Constructor
	 * @param fichero donde se guardan los mensajes
	 */
    public MensajesSistema(String fichero) {
		super();
		mensajes= new Hashtable();
		// Crea el generador de números aleatorios
		generador= new Random();
	   
		try {
			this.fichero=fichero;
			this.cargaMensajes(fichero);
		} catch (ExcepcionMensajes e) {
			
			e.printStackTrace();
		}
	}


	/**
     *  Carga los mensajes del sistema contenidos en el fichero de mensajes, y los añade a los
     *      que ya había cargado
     *
     * @param fichMensajes  Fichero en el que se encuentran los mensajes, en formato
     *     ID_MENSAJE (Cadena de caracteres que identifica el mensaje)
     *     TIPO_MENSAJE (puede ser ALEATORIO, PRIMERO o SECUENCIAL).
     *     Alternativa 1 (Cadena de texto con la 1ª alternativa del mensaje)
     *     Alternativa 2
     *     ...
     *     Alternativa n
     *                  (Al menos una línea en blanco, puede haber comentarios precedidos por #)
     *
     */
    private  void cargaMensajes(String ficheroMens) throws ExcepcionMensajes {

	// Lectura del fichero de mensajes
	BufferedReader in= null;
	try {
	    in= new BufferedReader(new FileReader(ficheroMens));
	} catch (FileNotFoundException fn) {
	    fn.printStackTrace();
	    throw new ExcepcionMensajes("No se pudo abrir el fichero "+ficheroMens);
	}
	int numLinea= 0;

	try {
	    while (in.ready()) {

		// Salta líneas en blanco
		String linea= "";
		while (in.ready() && (linea.length() <= 1) || (linea.startsWith("#"))) {
		    linea= in.readLine();
		    numLinea++;
		}

		if (!in.ready())
		    break;

		// Lee id mensaje
		String id= new String(linea);

		// Lee tipo
		String tipo= in.readLine();
		numLinea++;
		int tipoMens= -1;
		if (tipo.equalsIgnoreCase("ALEATORIO"))
		    tipoMens= MensajeUsuario.PRESENTACION_ALEATORIA;
		else if (tipo.equalsIgnoreCase("SECUENCIAL"))
		    tipoMens= MensajeUsuario.PRESENTACION_SECUENCIAL;
		else if (tipo.equalsIgnoreCase("PRIMERO"))
		    tipoMens= MensajeUsuario.PRESENTACION_PRIMERO;
		else
		    throw new ExcepcionMensajes("El tipo de mensaje "+tipo+" no se reconoce en la línea "+numLinea+ " del fichero de mensajes " +ficheroMens);

		// Lee las alternativas
		Vector alternativas= new Vector();
		while (in.ready() && (linea.length() > 0)) {

		    do {
			linea= in.readLine();
			numLinea++;
		    } while (linea != null && linea.startsWith("#") && in.ready());

		    if (linea != null && linea.length() > 0)
			alternativas.add(linea);
		}

		// Crea el mensaje y lo inserta en la tabla hash, comprobando que no existía antes
		MensajeUsuario mens= new MensajeUsuario(id, tipoMens, alternativas);
		if (mensajes.containsKey(id)) {
		    throw new ExcepcionMensajes("Ya existe el mensaje con identificador "+id+" en los mensajes del sistema");
		}
		mensajes.put(id, mens);
	    }

	    in.close();

	} catch (IOException io) {
	    io.printStackTrace();
	}

    }


    /**
     *  Obtiene un mensaje dado su identificador
     *
     * @param  idMensaje              Identificador del mensaje
     * @return                        Cadena de caracteres con el mensaje
     *      correspondiente
     * @exception  ExcepcionMensajes  si el mensaje no existía
     */
    public String obtenerMensaje(String idMensaje) throws ExcepcionMensajes {

	MensajeUsuario mensaje= (MensajeUsuario)mensajes.get(idMensaje);
	if (mensaje == null)
	    throw new ExcepcionMensajes("Identificador de mensaje no válido: "+idMensaje);

	// En función del tipo de mensaje, sacamos una u otra alternativa
	String mens= null;
	try {
	    switch(mensaje.tipoPresentacion) {

		case MensajeUsuario.PRESENTACION_ALEATORIA:
		    mens= mensaje.alternativas[generador.nextInt(mensaje.alternativas.length)];
		    break;

		case MensajeUsuario.PRESENTACION_PRIMERO:
		    mens= mensaje.alternativas[0];
		    break;

		case MensajeUsuario.PRESENTACION_SECUENCIAL:
		    mensaje.mensajeAnterior= (mensaje.mensajeAnterior + 1) % mensaje.alternativas.length;
		    mens= mensaje.alternativas[mensaje.mensajeAnterior];
		    break;

		default:
		    throw new ExcepcionMensajes("No existe el tipo de mensaje de "+idMensaje);

	    }
	} catch (ArrayIndexOutOfBoundsException ai) {
	    throw new ExcepcionMensajes("No existía la alternativa correspondiente para el mensaje "+idMensaje);
	}

	return mens;

    }

    /**
     *  Obtiene un mensaje dado su identificador
     *
     * @param  idMensaje              Identificador del mensaje
     * @param numAlternativa           Número de alternativa a presentar. La primera alternativa es 1
     * @return                        Cadena de caracteres con el mensaje
     *      correspondiente
     * @exception  ExcepcionMensajes  si el mensaje no existía
     */
    public String obtenerMensaje(String idMensaje, int numAlternativa) throws ExcepcionMensajes {

	MensajeUsuario mensaje= (MensajeUsuario)mensajes.get(idMensaje);
	if (mensaje == null)
	    throw new ExcepcionMensajes("Identificador de mensaje no válido: "+idMensaje);

	try {
	    return mensaje.alternativas[numAlternativa-1];
	} catch (ArrayIndexOutOfBoundsException ai) {
	    throw new ExcepcionMensajes("No existe la alternativa "+numAlternativa+" para el mensaje "+idMensaje);
	}

    }

    /**
     *  Sustituye los parámetros de un mensaje por los suministrados. Los
     *  parámetros de un mensaje vienen especificados de la forma %1% para el
     *  primer parámetro, %2% para el segundo, etc.
     *
     * @param  idMensaje              id de mensaje en el que sustituir los
     *      parámetros
     * @param  parametros             vector de objetos en el que se especifican
     *      los parámetros en orden; el 1er elemento del vector será el 1er
     *      parámetro, etc. Todos los objetos incluidos como parámetro deben
     *      tener toString() definido
     * @return                        String con la cadena resultado de
     *      sustituir los parámetros indicados en el mensaje
     * @exception  ExcepcionMensajes  si no se pudo realizar la sustitución, o
     *      el número de parámetros era diferente, o el mensaje no aceptaba
     *      parámetros
     */
    public  String obtenerMensajeParametrizado(String idMensaje, Vector parametros) throws ExcepcionMensajes {

	// Obtiene mensaje
	String mens = obtenerMensaje(idMensaje);
	String nuevoMens;

	// Comienza a sustituir parámetros
	for (int i = 0; i < parametros.size(); i++) {

	    nuevoMens = MensajesSistema.replace(mens, "%" + (i + 1) + "%", parametros.elementAt(i).toString());

	    // Si las cadenas son iguales, lanza excepción puesto que no existía ese parámetro
	    if (nuevoMens.equals(mens))
		throw new ExcepcionMensajes("No existe el parámetro " + (i + 1) + " en el mensaje " + idMensaje);

	    mens = nuevoMens;

	}

	return mens;
    }

    /**
     *  Sustituye los parámetros de un mensaje por los suministrados. Los
     *  parámetros de un mensaje vienen especificados de la forma %1% para el
     *  primer parámetro, %2% para el segundo, etc.
     *
     * @param  idMensaje              id de mensaje en el que sustituir los
     *      parámetros
     * @param  parametros             vector de objetos en el que se especifican
     *      los parámetros en orden; el 1er elemento del vector será el 1er
     *      parámetro, etc. Todos los objetos incluidos como parámetro deben
     *      tener toString() definido
     * @param numAlternativa        Especifica el número de alternativa del mensaje que debe presentar
     * @return                        String con la cadena resultado de
     *      sustituir los parámetros indicados en el mensaje
     * @exception  ExcepcionMensajes  si no se pudo realizar la sustitución, o
     *      el número de parámetros era diferente, o el mensaje no aceptaba
     *      parámetros
     */
    public String obtenerMensajeParametrizado(String idMensaje, Vector parametros, int numAlternativa) throws ExcepcionMensajes {

	// Obtiene mensaje
	String mens = obtenerMensaje(idMensaje, numAlternativa);
	String nuevoMens;

	// Comienza a sustituir parámetros
	for (int i = 0; i < parametros.size(); i++) {

	    nuevoMens = MensajesSistema.replace(mens, "%" + (i + 1) + "%", parametros.elementAt(i).toString());

	    // Si las cadenas son iguales, lanza excepción puesto que no existía ese parámetro
	    if (nuevoMens.equals(mens))
		throw new ExcepcionMensajes("No existe el parámetro " + (i + 1) + " en el mensaje " + idMensaje);

	    mens = nuevoMens;

	}

	return mens;
    }


    /**
     * Empieza a presentar desde el primero los mensajes secuenciales. Se invoca este método antes
     *  de obtener una alternativa de un mensaje secuencial para asegurarse de que al obtener
     *  el mensaje se obtenga la primera alternativa
     *
     * @param idMensaje Identificador del mensaje
     * @exception   ExcepcionMensajes si el mensaje no existe o no es de tipo secuencial
     */
    public void resetMensaje(String idMensaje) throws ExcepcionMensajes {

	MensajeUsuario mensaje= (MensajeUsuario)mensajes.get(idMensaje);
	if (mensaje == null)
	    throw new ExcepcionMensajes("Identificador de mensaje no válido: "+idMensaje);

	if (mensaje.tipoPresentacion != MensajeUsuario.PRESENTACION_SECUENCIAL)
	    throw new ExcepcionMensajes("El mensaje no es secuencial: "+idMensaje);

	mensaje.mensajeAnterior= -1;

    }



    /**
     *  Reemplaza una subcadena por otra dentro de una cadena de caracteres
     *
     * @param  original       cadena original
     * @param  cadReemplazar  Subcadena a reemplazar
     * @param  cadNueva       Subcadena que reemplaza a la anterior
     * @return                cadena resultado del reemplazo
     */
    protected static String replace(String original, String cadReemplazar, String cadNueva) {
	// target is the original string
	// from   is the string to be replaced
	// to     is the string which will used to replace
	int start = original.indexOf(cadReemplazar);
	if (start == -1) {
	    return original;
	}
	int lf = cadReemplazar.length();
	char[] targetChars = original.toCharArray();
	StringBuffer buffer = new StringBuffer();
	int copyFrom = 0;
	while (start != -1) {
	    buffer.append(targetChars, copyFrom, start - copyFrom);
	    buffer.append(cadNueva);
	    copyFrom = start + lf;
	    start = original.indexOf(cadReemplazar, copyFrom);
	}
	buffer.append(targetChars, copyFrom, targetChars.length - copyFrom);
	return buffer.toString();
    }
    /**
     * Comprueba si el mensaje existe y si no pues lo guarda
     * @param mensaje
     * @throws ExcepcionMensajes
     */
    public void guardarMensaje(MensajeUsuario mensaje) throws ExcepcionMensajes{
    	if (mensajes.containsKey(mensaje.id)) {
		    //throw new ExcepcionMensajes("Ya existe el mensaje con identificador "+mensaje.id+" en los mensajes del sistema");
    		//System.out.println("\n\n\n\n\n\nHas añadido una alternativa\n\n\n\n");
    		anyadirAlternativaAlMensaje(mensaje.id, mensaje.alternativas[mensaje.alternativas.length-1]);
		}
    	else{
    	guardarMensajeenFichero(mensaje);
    	}
    }
    /**
     * Guarda el mensaje en el fichero de texto pero no comprueba si ya existe.
     * @param mensaje
     * @throws ExcepcionMensajes
     */
    private void guardarMensajeenFichero(MensajeUsuario mensaje) throws ExcepcionMensajes{
//    	 Escritura del fichero de mensajes
    	BufferedWriter out= null;
    	try {
    	
			out= new BufferedWriter(new FileWriter(fichero,true));
    		mensajes.put(mensaje.id,mensaje);
    		out.write("\n");
			out.write(mensaje.id+"\n");
       		switch(mensaje.tipoPresentacion){
       			case	MensajeUsuario.PRESENTACION_ALEATORIA:{
       				out.write("ALEATORIO\n");
       				break;
       			}
       			case	MensajeUsuario.PRESENTACION_PRIMERO:{
       				out.write("PRIMERO\n");
       				break;
       			}
       			case	MensajeUsuario.PRESENTACION_SECUENCIAL:{
       				out.write("SECUENCIAL\n");
       				break;
       			}
       		}
    		for (int i=0;i<mensaje.alternativas.length&&mensaje.alternativas[i]!=null;i++)
    				out.write(mensaje.alternativas[i]+"\n");
    		
    		out.close();
    		
       	}catch (FileNotFoundException fn) {
    	    fn.printStackTrace();
    	     throw new ExcepcionMensajes("No se pudo abrir el fichero "+fichero);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
    	
    	  
    	   
    }
    /**
     * Borra el contenido del fichero dejandolo en blanco
     * @throws ExcepcionMensajes
     */
    private void limpiarFichero() throws ExcepcionMensajes{
    		BufferedWriter out= null;
    	try {	
    		out= new BufferedWriter(new FileWriter(fichero,false));
    		out.close();
    	}catch (FileNotFoundException fn) {
    		fn.printStackTrace();
    		throw new ExcepcionMensajes("No se pudo limpiar el fichero "+fichero+" porque no encuentra");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    /**
     * Elimina el mensaje con el identificador pasado
     * @param id
     * @throws ExcepcionMensajes
     */
    public void eliminarMensaje(String id) throws ExcepcionMensajes{
  		if (!mensajes.containsKey(id)){
   			throw new ExcepcionMensajes("No existe el mensaje con identificador "+id+" en los mensajes del sistema");
   		}
   		else{
   			mensajes.remove(id);
   		}
   		
   		actualizarMensajesFichero();
		
   	  
   	   
   }
    /**
     * Elimina el mensaje con el identificador pasado
     * @param id
     * @throws ExcepcionMensajes
     */
    public void eliminarAlternativaDeMensaje(String id,int alternativa) throws ExcepcionMensajes{
  		if (!mensajes.containsKey(id)){
   			throw new ExcepcionMensajes("No existe el mensaje con identificador "+id+" en los mensajes del sistema");
   		}
   		else{
   			
   			if ((alternativa>=0)&&(alternativa<((MensajeUsuario)mensajes.get(id)).alternativas.length)){
   				MensajeUsuario mensaje=(MensajeUsuario)mensajes.get(id);
   				if (mensaje.alternativas.length==1){
   					mensajes.remove(id);
   				}
   				else{
   					String[] aux=new String[mensaje.alternativas.length-1];
   					if (alternativa==0){
   						System.arraycopy(mensaje.alternativas, 1, aux, 0, aux.length);
   					}
   					else{
   						System.arraycopy(mensaje.alternativas, 0, aux, 0, alternativa-1);
   						System.arraycopy(mensaje.alternativas, alternativa, aux, alternativa-1, aux.length-alternativa+1);
   					}
   					mensaje.alternativas=aux;
   				}
   				
   			}
   			else{
   				throw new ExcepcionMensajes("No existe la alternativa "+alternativa+" en el mensaje con identificador "+id+" en los mensajes del sistema");
   			}
   		}
   					
   		   		
   		actualizarMensajesFichero();
		
   	  
   	   
   }
    /**
     * Añado la alternativa al mensaje con identificador pasado por parametro
     * @param id
     * @throws ExcepcionMensajes
     */
    public void anyadirAlternativaAlMensaje(String id,String alternativa) throws ExcepcionMensajes{
  		if (!mensajes.containsKey(id)){
   			throw new ExcepcionMensajes("Al añadir la alternativa no se ha encontrado el mensaje con identificador "+id+" en los mensajes del sistema");
   		}
   		else{
   			MensajeUsuario mensaje=(MensajeUsuario)mensajes.get(id);
   			String[] aux=new String[mensaje.alternativas.length+1];
   			
   			System.arraycopy(mensaje.alternativas, 0, aux, 0, mensaje.alternativas.length);
   			aux[aux.length-1]=alternativa;
   			mensaje.alternativas=aux;
   		}
   					   		
   		actualizarMensajesFichero();
		
   	  
   	   
   }
    /**
     * Limpia el fichero y escribe en él los mensajes q hay en el atributo mensajes
     *
     */
   public void actualizarMensajesFichero(){
	  Object[]  vect=this.mensajes.values().toArray();
	  try {
		  limpiarFichero();
		  for (int i=0;i<vect.length;i++){
			  guardarMensajeenFichero((MensajeUsuario)vect[i]);
			  
		  }
		} catch (ExcepcionMensajes e) {
	
			e.printStackTrace();
		}
	  
   }
   /**
    * Devuelve todos los mensajes del sistema
    * @return
    */
   public MensajeUsuario[] obtenerTodosLosMensajes(){
	   Object[]  vect=this.mensajes.values().toArray();
	   MensajeUsuario[] resultado=new MensajeUsuario[vect.length];
	   this.numFilasTotales=0;
	   for (int i=0;i<vect.length;i++){
		   resultado[i]=(MensajeUsuario)vect[i];
		   numFilasTotales+=((MensajeUsuario)vect[i]).alternativas.length;
	   }
	   return resultado;
   }
   /**
    * Devuelve la tabla a mostrar
    * @return
    */
   public Object [][] dameTablaDatosMostrar(){
	   MensajeUsuario[] listaMensajes=this.obtenerTodosLosMensajes();
	   Object [][] resultado=new Object[numFilasTotales][4];
	   int contador=0;
	   for (int i=0;i<listaMensajes.length;i++){
		   for (int j=0;j<listaMensajes[i].alternativas.length;j++){
			   resultado[contador][0]=listaMensajes[i].id;
			   resultado[contador][1]=listaMensajes[i].tipoPresentacion;
			   resultado[contador][2]=j;
			   resultado[contador][3]=listaMensajes[i].alternativas[j];
			   contador++;
			   
		   }
	   }
	   
	   return resultado;
   }
   
    /**
     *  Description of the Method
     *
     * @param  args  Description of the Parameter
     */
    public static void main(String args[]) {
    	MensajesSistema info=null;
	try {
		info=new MensajesSistema("mensajesInfo.txt");
	  
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	Vector vect=new Vector();
	vect.addElement("Hola mi nombre es pepe1");
	vect.addElement("Hola mi nombre es juan2");
	vect.addElement("Hola mi nombre es javi3");
	MensajeUsuario mens=new MensajeUsuario("Mensg1",MensajeUsuario.PRESENTACION_SECUENCIAL,vect);
	Vector vect2=new Vector();
	vect.addElement("Hola2 mi nombre es pepe1");
	vect.addElement("Hola2 mi nombre es juan2");
	vect.addElement("Hola2 mi nombre es javi3");
	MensajeUsuario mens2=new MensajeUsuario("Mensg2",MensajeUsuario.PRESENTACION_SECUENCIAL,vect);
	
	
	
	try {
		/*
		info.guardarMensaje(mens);
		info.guardarMensaje(mens2);
		info.eliminarMensaje(mens.id);
		*/
		info.eliminarAlternativaDeMensaje("mensa4", 1);
		/*
		System.out.println(info.obtenerMensaje(mens.id));
		System.out.println(info.obtenerMensaje(mens.id));
		System.out.println(info.obtenerMensaje(mens.id));
		System.out.println(info.obtenerMensaje(mens.id));
		System.out.println(info.obtenerMensaje(mens.id));
		*/
	} catch (ExcepcionMensajes e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
/*
	try {
	    for (int i= 1; i < 5; i++)
			System.out.println(info.obtenerMensaje("mens"+i));

	    Vector param= new Vector();
	    param.add("Pepe1");
	    param.add("Pepe2");
	    param.add("Pepe3");
	    param.add("Pepe4");
	    param.add("Pepe5");
	
	    for (int i= 0; i < 9; i++)
		System.out.println(info.obtenerMensajeParametrizado("mensa4", param));




	    for (int i= 0; i < 10; i++)
		System.out.println(MensajesSistema.obtenerMensaje("MSG_PRIMERO"));

	    for (int i= 0; i < 10; i++) {
		MensajesSistema.resetMensaje("MSG_PRUEBA_2");
		System.out.println(MensajesSistema.obtenerMensaje("MSG_PRUEBA_2"));
	    }

	    Vector param= new Vector();
	    param.add("Hola");
	    for (int i= 0; i < 10; i++)
		System.out.println(MensajesSistema.obtenerMensajeParametrizado("MSG_PRUEBA_4", param));

		System.out.println(MensajesSistema.obtenerMensajeParametrizado("MSG_PRUEBA_4", param, 3));
		System.out.println(MensajesSistema.obtenerMensajeParametrizado("MSG_PRUEBA_4", param, 2));
		System.out.println(MensajesSistema.obtenerMensajeParametrizado("MSG_PRUEBA_4", param, 1));

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	*/
    }


	public int getNumFilasTotales() {
		return numFilasTotales;
	}


	public void setNumFilasTotales(int numFilasTotales) {
		this.numFilasTotales = numFilasTotales;
	}
    
    

}
