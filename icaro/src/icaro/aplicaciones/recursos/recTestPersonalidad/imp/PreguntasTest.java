package icaro.aplicaciones.recursos.recTestPersonalidad.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionTestPersonalidad.PreguntaTest;
import icaro.aplicaciones.recursos.recTestPersonalidad.ExcepcionPreguntas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class PreguntasTest{
	
	protected PreguntaTest[] preguntas;
	protected int[] resultados;
	protected int[] resultadosPrevios;
	protected String[] textosRespuestas;
	
	public PreguntasTest(int numPreguntas){
		this.preguntas = new PreguntaTest[numPreguntas];
		this.resultadosPrevios = new int[numPreguntas];
		this.textosRespuestas = new String[15]; //5 factores con tres resultados cada uno
	}
	
	/*
	 * Método que carga las preguntas del test del recurso de información
	 * de usuario y devuelve las respuestas previas.
	 */
	public int[] cargaPreguntas(String[] preguntas){
		PreguntaTest pregunta;
		for(int i=0; i<preguntas.length; i++){
			pregunta = new PreguntaTest(preguntas[i]);
			this.preguntas[i] = pregunta;
		}
		try{
			cargaRespuestasGuardadas();
		}catch (ExcepcionPreguntas e) {
			e.printStackTrace();
		}
		return this.resultadosPrevios;
		/* Para depurar 
		System.out.println("Cargo las preguntas");
		for(int j=0; j<this.preguntas.length; j++){
			System.out.println("Leo la pregunta: "+(j+1));
			System.out.println("	el nombre es: "+(this.preguntas[j].pregunta));
			System.out.println("	la respuesta es: "+(this.preguntas[j].respuesta));
			if(this.preguntas[j].respondida){
				System.out.println("	la respuesta SI ha sido respondida");
			}
			else
				System.out.println("	la respuesta NO ha sido respondida");
		}
		Fin depuracion*/
	}
	public void anotaRespuestas(String[] conjuntoPreguntas, int[] respuestas){
		for(int i=0; i<conjuntoPreguntas.length; i++){
			for(int j=0; j<this.preguntas.length; j++){
				if(preguntas[j].pregunta.equals(conjuntoPreguntas[i])){
					if(respuestas[i] != -1){
						if(j!=19)
							preguntas[j].respuesta = respuestas[i];
						else
							preguntas[j].respuesta = opuesto(respuestas[i]);
						preguntas[j].respondida = true;
					}
				break;
				}
			}
		}
		/* Para depurar
		System.out.println("Despues de cargar respuestas");
		for(int k=0; k<this.preguntas.length; k++){
			System.out.println("Leo la pregunta: "+(k+1));
			System.out.println("	el nombre es: "+(this.preguntas[k].pregunta));
			System.out.println("	la respuesta es: "+(this.preguntas[k].respuesta));
			if(this.preguntas[k].respondida){
				System.out.println("	la respuesta SI ha sido respondida");
			}
			else
				System.out.println("	la respuesta NO ha sido respondida");
		}
		Fin depuracion*/
	}
	public void guardarProgresoFichero() throws ExcepcionPreguntas{
		BufferedWriter out= null;
		try{
			out= new BufferedWriter(new FileWriter("./config/preguntas.txt",false));
			for(int i=0; i<preguntas.length; i++){
				out.write(i + "\n");
				out.write(preguntas[i].respuesta + "\n");
				out.write(preguntas[i].respondida + "\n");
				out.write("\n");
			}
			out.close();
		}
		catch (FileNotFoundException fn) {
    	    fn.printStackTrace();
    	     throw new ExcepcionPreguntas("No se pudo abrir el fichero ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean todasRespondidas(){
		boolean resultado = true;
		for(int i=0; i<preguntas.length; i++){
			if(!preguntas[i].respondida){
				resultado = false;
				break;
			}
		}
		return resultado;
	}
	public String textoResultado(String[] textos){
		obtenerResultados();
		String texto = "EXTROVERSION: "+resultados[1]+"\n";
		texto += "CORDIALIDAD: "+resultados[3]+"\n";
		texto += "RESPONSABILIDAD: "+resultados[4]+"\n";
		texto += "ABERTURA MENTAL: "+resultados[2]+"\n";
		texto += "ESTABILIDAD EMOCIONAL: "+resultados[0];
		generarInforme(textos);
		return texto;
	}
	
	public int[] getResultados(){
		return resultados;
	}
	private void obtenerResultados(){
		this.resultados = new int[5];
		//Extroversion
		resultados[1] = ((preguntas[0].respuesta + preguntas[5].respuesta + preguntas[10].respuesta + preguntas[15].respuesta + preguntas[20].respuesta + preguntas[25].respuesta)*100)/60;
		//Cordialidad
		resultados[3] = ((preguntas[3].respuesta + preguntas[8].respuesta + preguntas[13].respuesta + preguntas[18].respuesta + preguntas[23].respuesta + preguntas[28].respuesta)*100)/60;
		//Responsabilidad
		resultados[4] = ((preguntas[2].respuesta + preguntas[7].respuesta + preguntas[12].respuesta + preguntas[17].respuesta + preguntas[22].respuesta + preguntas[27].respuesta)*100)/60;
		//Apertura Mental
		resultados[2] = ((preguntas[1].respuesta + preguntas[6].respuesta + preguntas[11].respuesta + preguntas[16].respuesta + preguntas[21].respuesta + preguntas[26].respuesta)*100)/60;
		//Estabilidad emocional
		resultados[0] = ((preguntas[4].respuesta + preguntas[9].respuesta + preguntas[14].respuesta + preguntas[19].respuesta + preguntas[24].respuesta + preguntas[29].respuesta)*100)/60;
	}
	
	public void reinicia(){
		for(int i=0; i<preguntas.length; i++){
			preguntas[i].respondida = false;
			preguntas[i].respuesta = -1;
		}
	}
	
	private void cargaRespuestasGuardadas() throws ExcepcionPreguntas{
		BufferedReader in= null;
		try {
		    in= new BufferedReader(new FileReader("./config/preguntas.txt"));
		} catch (FileNotFoundException fn) {
		    fn.printStackTrace();
		    for(int j=0; j<resultadosPrevios.length; j++){
		    	resultadosPrevios[j] = -1;
		    }
		    throw new ExcepcionPreguntas("No se pudo abrir el fichero preguntas.txt");
		}
		int numLinea= 0;
		try {
		    while (in.ready()) {

				// Salta líneas en blanco
				String linea= "";
			    
			    while (in.ready() && (linea.length() < 1) || (linea.startsWith("#"))) {
				    linea= in.readLine();
				    numLinea++;
				}
	
				if (!in.ready())
				    break;
				// Lee indice de la pregunta
				String indice= new String(linea);
				// Lee respuesta
				String respuesta= in.readLine();
				numLinea++;
				//Lee si respondida
				String respondida= in.readLine();
				numLinea++;
				//Guardamos el resultado 
				resultadosPrevios[(new Integer(indice)).intValue()] = (new Integer(respuesta)).intValue();
				if((new Integer(respuesta)).intValue()!=-1){
					this.preguntas[(new Integer(indice)).intValue()].respondida = true;
					this.preguntas[(new Integer(indice)).intValue()].respuesta = (new Integer(respuesta)).intValue();
				}
		    }
		    in.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
		/*Para depurar
		for(int i=0; i<resultadosPrevios.length; i++){
			System.out.println("el resultado "+i+" es "+resultadosPrevios[i]);
		}
		Fin depuracion*/
	}
	
	private void generarInforme(String[] textos){
		//Vamos a generar el informe html con los resultados
		BufferedWriter out= null;
		try{
			out= new BufferedWriter(new FileWriter("informe_personalidad.html",false));
			out.write("<html>\n<head><title>Informe del Test de Personalidad</title></head>\n<body>\n");
			out.write("<h1>Informe del test de personalidad</h1>");
			for(int i=0; i<this.resultados.length; i++){
				out.write("<div class=\"apartado\">\n");
				switch(i){
					case 0:{ //Estabilidad emocional
						out.write("<h2>Estabilidad Emocional</h2>\n");
						break;
					}
					case 1:{ //Extroversion
						out.write("<h2>Extroversión</h2>\n");
						break;
					}
					case 2:{ //Apertura Mental
						out.write("<h2>Apertura Mental</h2>\n");
						break;
					}
					case 3:{ //Cordialidad
						out.write("<h2>Cordialidad</h2>\n");
						break;
					}
					case 4:{ //Responsabilidad
						out.write("<h2>Responsabilidad</h2>\n");
						break;
					}
				}
				out.write("<p>\n");
				if(resultados[i] < 30){ //nivel Bajo
					out.write(textos[(i*3)]);
				}
				else if(resultados[i] > 70){ //nivel alto
					out.write(textos[(i*3)+2]);
				}
				else{ //nivel medio
					out.write(textos[(i*3)+1]);
				}
				out.write("\n</p>\n");
				out.write("</div>\n");
			}
			out.write("</body></html>");
			out.close();
		}
		catch (FileNotFoundException fn) {
    	    fn.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int opuesto(int numero){
		int resultado = 0;
		switch(numero){
			case 0:{
				resultado = 10;
				break;
			}
			case 1:{
				resultado = 9;
				break;
			}
			case 2:{
				resultado = 8;
				break;
			}
			case 3:{
				resultado = 7;
				break;
			}
			case 4:{
				resultado = 6;
				break;
			}
			case 5:{
				resultado = 5;
				break;
			}
			case 6:{
				resultado = 4;
				break;
			}
			case 7:{
				resultado = 3;
				break;
			}
			case 8:{
				resultado = 2;
				break;
			}
			case 9:{
				resultado = 1;
				break;
			}
			case 10:{
				resultado = 0;
				break;
			}
		}
		return resultado;
	}
}