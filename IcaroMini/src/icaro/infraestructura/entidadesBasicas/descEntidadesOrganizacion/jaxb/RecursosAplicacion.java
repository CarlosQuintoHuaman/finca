//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.26 at 01:54:43 AM CET 
//


package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecursosAplicacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecursosAplicacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nodoComun" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Nodo" minOccurs="0"/>
 *         &lt;element name="Instancia" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Instancia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecursosAplicacion", propOrder = {
    "nodoComun",
    "instancia"
})
public class RecursosAplicacion {

    protected Nodo nodoComun;
    @XmlElement(name = "Instancia")
    protected List<Instancia> instancia;

    /**
     * Gets the value of the nodoComun property.
     * 
     * @return
     *     possible object is
     *     {@link Nodo }
     *     
     */
    public Nodo getNodoComun() {
        return nodoComun;
    }

    /**
     * Sets the value of the nodoComun property.
     * 
     * @param value
     *     allowed object is
     *     {@link Nodo }
     *     
     */
    public void setNodoComun(Nodo value) {
        this.nodoComun = value;
    }

    /**
     * Gets the value of the instancia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instancia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstancia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Instancia }
     * 
     * 
     */
    public List<Instancia> getInstancia() {
        if (instancia == null) {
            instancia = new ArrayList<Instancia>();
        }
        return this.instancia;
    }

}
