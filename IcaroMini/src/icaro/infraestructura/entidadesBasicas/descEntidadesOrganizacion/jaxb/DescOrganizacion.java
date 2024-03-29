//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.26 at 01:54:43 AM CET 
//


package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DescOrganizacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescOrganizacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PropiedadesGlobales" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}PropiedadesGlobales"/>
 *         &lt;element name="DescripcionComponentes" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescripcionComponentes"/>
 *         &lt;element name="DescInstancias" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}Instancias"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescOrganizacion", propOrder = {
    "propiedadesGlobales",
    "descripcionComponentes",
    "instancias"
})
public class DescOrganizacion {

    @XmlElement(name = "PropiedadesGlobales", required = true)
    protected PropiedadesGlobales propiedadesGlobales;
    @XmlElement(name = "DescripcionComponentes", required = true)
    protected DescripcionComponentes descripcionComponentes;
    @XmlElement(name = "DescInstancias", required = true)
    protected DescInstancias instancias;

    /**
     * Gets the value of the propiedadesGlobales property.
     * 
     * @return
     *     possible object is
     *     {@link PropiedadesGlobales }
     *     
     */
    public PropiedadesGlobales getPropiedadesGlobales() {
        return propiedadesGlobales;
    }

    /**
     * Sets the value of the propiedadesGlobales property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropiedadesGlobales }
     *     
     */
    public void setPropiedadesGlobales(PropiedadesGlobales value) {
        this.propiedadesGlobales = value;
    }

    /**
     * Gets the value of the descripcionComponentes property.
     * 
     * @return
     *     possible object is
     *     {@link DescripcionComponentes }
     *     
     */
    public DescripcionComponentes getDescripcionComponentes() {
        return descripcionComponentes;
    }

    /**
     * Sets the value of the descripcionComponentes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescripcionComponentes }
     *     
     */
    public void setDescripcionComponentes(DescripcionComponentes value) {
        this.descripcionComponentes = value;
    }

    /**
     * Gets the value of the instancias property.
     * 
     * @return
     *     possible object is
     *     {@link Instancias }
     *     
     */
    public DescInstancias getInstancias() {
        return instancias;
    }

    /**
     * Sets the value of the instancias property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeskcInstancias }
     *     
     */
    public void setInstancias(DescInstancias value) {
        this.instancias = value;
    }

}
