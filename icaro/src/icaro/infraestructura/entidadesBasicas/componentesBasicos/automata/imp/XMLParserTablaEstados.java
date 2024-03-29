/*
 *  Copyright 2001 Telef�nica I+D. All rights reserved
 */
package icaro.infraestructura.entidadesBasicas.componentesBasicos.automata.imp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Clase que convierte un fichero XML en una tabla de estados v�lida para un
 * aut�mata
 * 
 * @author Jorge Gonz�lez
 * @created 5 de septiembre de 2001
 */
public class XMLParserTablaEstados {

	Document document;

	/**
	 * Constructor
	 */
	public XMLParserTablaEstados(String nombreFich) {
		// Esta parte es gen�rica para cualquier parsing XML
		// parsing XML

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		// factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(this.getClass().getResourceAsStream(nombreFich));
		} catch (SAXException sxe) {
			// Error generated during parsing
			Exception x = sxe;
			if (sxe.getException() != null) {
				x = sxe.getException();
			}
			System.out
					.println("Se ha producido un error al procesar el fichero XML: "
							+ nombreFich);
			//x.printStackTrace();

		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			System.out
					.println("No se pudo construir un analizador XML con las opciones especificadas referido al fichero XML: "
							+ nombreFich);
			;
			//pce.printStackTrace();
		} catch (IOException ioe) {
			System.out
					.println("Error de lectura en el fichero XML. �Est� usted seguro de que el fichero '"
							+ nombreFich + "' est� ah�?");
			//ioe.printStackTrace();
		}

	}

	public String extraerDescripcionTablaEstados() {
		return document.getElementsByTagName("tablaEstados").item(0)
				.getAttributes().item(0).getNodeValue();
	}

	/**
	 * Convierte el fichero dado en una tablaEstadosControl
	 * 
	 * @param nombreFich
	 *            Nombre del fichero a convertir
	 * @return Tabla del aut�mata extra�do de ese fichero
	 */
	public TablaEstadosControl extraeTablaEstados() throws Exception {
		TablaEstadosControl tabla = new TablaEstadosControl();

		// Esta parte es dependiente del tipo del documento que hemos creado

		// ahora tengo el documento XML
		// referencias de ayuda en los recorridos
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;
		org.w3c.dom.NodeList listaNodos;
		String id;

		// capturo el estado inicial (SE QUE SOLO HAY UNO, LAS COMPROBACIONES SE
		// HACEN DESDE EL EXTERIOR)
		org.w3c.dom.NodeList nlInicial = document
				.getElementsByTagName("estadoInicial");

		nodo = nlInicial.item(0);
		mapaNombreNodo = nodo.getAttributes();
		// este es el identificador del nodo inicial
		id = mapaNombreNodo.getNamedItem("idInicial").getNodeValue();
		tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INICIAL);

		// ahora obtenemos las transiciones desde el estado inicial
		// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
		// transiciones
		listaNodos = nodo.getChildNodes();
		procesarListaNodosTransicion(id, listaNodos, tabla);

		// capturo subestados
		org.w3c.dom.NodeList nlIntermedios = document
				.getElementsByTagName("estado");

		// bucle que recorre estados intermedios
		for (int i = 0; i < nlIntermedios.getLength(); i++) {
			nodo = nlIntermedios.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idIntermedio").getNodeValue();
			tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INTERMEDIO);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
			// transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

		// capturo estados finales
		org.w3c.dom.NodeList nlFinales = document
				.getElementsByTagName("estadoFinal");
		// bucle que recorre estados finales
		for (int i = 0; i < nlFinales.getLength(); i++) {
			nodo = nlFinales.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// este es el identificador del nodo inicial
			id = mapaNombreNodo.getNamedItem("idFinal").getNodeValue();
			tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_FINAL);

			// SE QUE TIENE AL MENOS UNA TRANSICION y que todos los hijos seran
			// transiciones
			listaNodos = nodo.getChildNodes();
			procesarListaNodosTransicion(id, listaNodos, tabla);
		}

		// capturo ahora transiciones universales
		org.w3c.dom.NodeList nlUniversal = document
				.getElementsByTagName("transicionUniversal");
		// bucle que recorre transiciones universales
		for (int i = 0; i < nlUniversal.getLength(); i++) {
			nodo = nlUniversal.item(i);
			mapaNombreNodo = nodo.getAttributes();
			// extraemos los atributos
			String input = mapaNombreNodo.getNamedItem("input").getNodeValue();
			String accion = mapaNombreNodo.getNamedItem("accion")
					.getNodeValue();
			String estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente")
					.getNodeValue();
			String modo = mapaNombreNodo.getNamedItem("modoDeTransicion")
					.getNodeValue();

			tabla.putTransicionUniversal(input, accion, estadoSig, modo);
		}

		return tabla;
	}

	/**
	 * Crea un conjunto con los inputs v�lidos para el aut�mata le�do a partir
	 * del fichero de tabla de estados
	 * 
	 * @param nombreFich
	 *            Nombre del fichero que contiene la tabla de estados
	 * @return Conjunto de inputs v�lidos del aut�mata extra�dos de ese fichero
	 */
	public Set<String> extraeConjuntoInputs() {

		Set<String> conjuntoInputs = new HashSet<String>();

		// A�adir todos los inputs de los elementos "tranisicion"
		NodeList listaTransiciones = document
				.getElementsByTagName("transicion");

		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			org.w3c.dom.Node transicion = listaTransiciones.item(i);
			Node input = transicion.getAttributes().getNamedItem("input");

			conjuntoInputs.add(input.getNodeValue());

		}

		// A�adir todos los inputs de los elementos "tranisicionUniversal"
		listaTransiciones = document
				.getElementsByTagName("transicionUniversal");

		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			org.w3c.dom.Node transicion = listaTransiciones.item(i);
			Node input = transicion.getAttributes().getNamedItem("input");

			conjuntoInputs.add(input.getNodeValue());

		}

		return conjuntoInputs;

	}

	/**
	 * M�todo auxiliar que procesa las transiciones de un estado determinado
	 * 
	 * @param idEstado
	 *            Estado al que pertenecen las transiciones
	 * @param listaTransiciones
	 *            Lista con las transiciones de ese estado
	 * @param tablaEstados
	 *            Tabla de estados / transiciones para a�adir las nuevas
	 *            transiciones que se detectan
	 */
	private void procesarListaNodosTransicion(String idEstado,
			org.w3c.dom.NodeList listaTransiciones,
			TablaEstadosControl tablaEstados) {
		org.w3c.dom.Node nodo;
		org.w3c.dom.NamedNodeMap mapaNombreNodo;

		for (int i = 0; i < listaTransiciones.getLength(); i++) {
			nodo = listaTransiciones.item(i);
			if (nodo.getNodeName().equalsIgnoreCase("transicion")) {
				mapaNombreNodo = nodo.getAttributes();
				String input = mapaNombreNodo.getNamedItem("input")
						.getNodeValue();
				String accion = mapaNombreNodo.getNamedItem("accion")
						.getNodeValue();
				String estadoSig = mapaNombreNodo.getNamedItem(
						"estadoSiguiente").getNodeValue();
				String modo = mapaNombreNodo.getNamedItem("modoDeTransicion")
						.getNodeValue();
				tablaEstados.putTransicion(idEstado, input, accion, estadoSig,
						modo);
			}
		}

	}
}
