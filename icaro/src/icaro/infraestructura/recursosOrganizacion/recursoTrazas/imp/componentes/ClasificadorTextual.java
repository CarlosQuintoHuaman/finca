package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;





/*
 * Esta clase se encarga de clasificar los mensajes de traza,
 * escribiéndolos correctamente en los ficheros correspondientes
 * */

public class ClasificadorTextual {
	
	private static final String rutaLogs = "./log/";
	private List<String> agentesSesion;

	public ClasificadorTextual(){
		agentesSesion = new LinkedList<String>();
	}
	
	public void clasificaTraza(InfoTraza traza){
		
		//genero el mensaje a escribir en la persistencia
		String mensajeAEscribir =traza.getNivel().toString()+" : "+traza.getNombre()+" : "+traza.getMensaje(); 
		
		//genero el archivo donde se debe escribir
		String rutaValida = getRuta(traza.getNombre().toString());
			
		escribeFichero(mensajeAEscribir,rutaValida);

	}
	
	private String getRuta(String nombreAgente){
		/* genero el fichero en el que se debe escribir.
		*/
		
		String rutaFichero = "";
		
		if (agentesSesion.contains(nombreAgente)){
		/*no es inicio de sesión -> se añade el mensaje al último archivo
		  del agente especificado*/
		  int versionArchivo = getVersion(nombreAgente);
		  rutaFichero = rutaLogs + nombreAgente + versionArchivo + ".log";
			
		}
		
		else{
		//inicio de sesión -> se devuelve la ruta de un fichero nuevo siguiendo la numeración
			agentesSesion.add(nombreAgente);
			int versionArchivo = getVersion(nombreAgente);
			if(versionArchivo == -1) {
			//no existe ningún fichero del agente -> se devuelve la ruta para el primer archivo
			//y se actualiza el fichero de registro
				rutaFichero = rutaLogs+nombreAgente+"0.log";
				actualizaRegistro(nombreAgente,0);
			}
			else{
			//enviamos la versión siguiente y actualizamos el fichero de registro
				versionArchivo++;
				rutaFichero = rutaLogs+nombreAgente+versionArchivo+".log";
				actualizaRegistro(nombreAgente,versionArchivo);
			}
				
		
		}
		return rutaFichero;
	}
	
	private int getNumeroDigitos(int numero){
		int numDigitos = 1;
		while(numero>9){
			numero = numero/10;
			numDigitos++;
		}
		
		return numDigitos;
	}
	private void actualizaRegistro(String nombreAgente, int versionArchivo){
		String rutaFichero = rutaLogs + "registroLog.log";
		File archivo = new File(rutaFichero);
		boolean encontrado = false;
		
		try{
			if (!archivo.exists()){
				PrintWriter writer = new PrintWriter(archivo); //se crea
			}
				
			//leo el archivo y actualizo la versión del agente
			BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
			String cadenaActual = "";
			String cadenaNueva = "";
			if(versionArchivo == 0){ //primera versión del agente
				cadenaNueva += "0"+nombreAgente+"\n";
			}
			else{
				//genero la cadena con el valor actualizado
				//en función de la longitud de este valor
				while (((cadenaActual = bf.readLine())!=null)&&(!encontrado)) {
					if (cadenaActual.contains(nombreAgente)){
						encontrado = true;
						cadenaNueva += versionArchivo + cadenaActual.substring
												(getNumeroDigitos(versionArchivo-1),cadenaActual.length())+"\n";
						
					}
				}
			}
			
			//escribo en el fichero la nueva cadena,sustituyendo a la otra, junto al resto del mensaje
			BufferedReader bf2= new BufferedReader(new FileReader(rutaFichero));
			String contenidoFichero = cadenaNueva;
			
			while ((cadenaActual = bf2.readLine())!=null) {
				if (!cadenaActual.contains(nombreAgente)){
					contenidoFichero += cadenaActual+"\n";
				}
					
			}
			PrintWriter writer = new PrintWriter(archivo);
			writer.write(contenidoFichero);
			writer.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private int getVersion(String nombreAgente){
	/* Busca en el archivo "registroLog.log" el registro del agente especificado.
	 * Devuelve el número que está antes del nombre del agente (última versión)
	 * Si no está registrado el agente, se devuelve -1
	 */
		String rutaFichero = rutaLogs + "registroLog.log";
		File archivo = new File(rutaFichero);
		int version = -1;
		boolean encontrado = false;
		
		try{
			if (archivo.exists()){
				//leo el archivo y tomo todo su contenido para comprobar si está el agente
				BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
				String cadenaActual = "";
				String caracterActual = "";
				int numeroConstruido = 0;
				
				while (((cadenaActual = bf.readLine())!=null)&&(!encontrado)) {
					if (cadenaActual.contains(nombreAgente)){
						encontrado = true;
						//recorro caracteres mientras sean dígitos para ir formando el número completo
						int indice = 0;
						caracterActual = cadenaActual.substring(indice, indice + 1);
						do{	
							numeroConstruido = numeroConstruido*10 + (new Integer(caracterActual).intValue());
							indice++;
							caracterActual = cadenaActual.substring(indice, indice + 1);
						}while(caracterActual.equals("0")||caracterActual.equals("1")||caracterActual.equals("2")||caracterActual.equals("3")
								||caracterActual.equals("4")||caracterActual.equals("5")||caracterActual.equals("6")||caracterActual.equals("7")
								||caracterActual.equals("8")||caracterActual.equals("9"));
						
						version = numeroConstruido;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return version;
		
	}
	
	private void escribeFichero(String mensaje,String rutaFichero){
	/*escribe el mensaje dado en la ruta especificada. Si el fichero contenía
	 * algo anteriormente, lo concatena
	 */ 
		
		File archivo = new File(rutaFichero);
		String contenidoAnterior = "";
		try{
			if (archivo.exists()){
				//leo el archivo y tomo todo su contenido
				BufferedReader bf = new BufferedReader(new FileReader(rutaFichero));
				String cadenaActual = "";

				while ((cadenaActual = bf.readLine())!=null) {
					contenidoAnterior += cadenaActual;
					contenidoAnterior += "\n";
				}
			}
			
			PrintWriter writer = new PrintWriter(archivo);
			//Escribimos la nueva traza 
			writer.print(contenidoAnterior+mensaje);	
			writer.close();
		}	
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
