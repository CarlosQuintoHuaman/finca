/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package icaro.infraestructura.patronAgenteReactivoSCXML.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.scxml.model.CustomAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Carlos Rodr&iacute;guez Fern&aacute;ndez
 */
public class ListaAcciones {
    private List acciones;
    private Log log = LogFactory.getLog(ListaAcciones.class);

    public void cargar(InputStream is) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element accionesElement = document.getDocumentElement();
        String cualificador = accionesElement.getAttribute("cualificador");
        NodeList listaAcciones = accionesElement.getElementsByTagName("accion");
        int tamano = listaAcciones.getLength();
        log.debug("Cualificador:"+cualificador);
        acciones = new ArrayList();
        Element accion = null;
        String nombre = null;
        String clase = null;
        for(int i=0;i<tamano;i++){
            accion = (Element)listaAcciones.item(i);
            nombre = accion.getAttribute("nombre");
            clase = accion.getAttribute("clase");
            log.debug("Agregado: ("+nombre+","+accion+","+clase+")");
            acciones.add(new CustomAction(cualificador,nombre,Class.forName(clase)));
        }
        
    }
    public List getAcciones() {
        return acciones;
    }
    
}
