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
 * <p>Java class for DescripcionComponentes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescripcionComponentes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DescComportamientoAgentes" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescComportamientoAgentes"/>
 *         &lt;element name="DescRecursosAplicacion" type="{urn:icaro:aplicaciones:descripcionOrganizaciones}DescRecursosAplicacion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescripcionComponentes", propOrder = {
    "descComportamientoAgentes",
    "descRecursosAplicacion"
})
public class DescripcionComponentes {

    @XmlElement(name = "DescComportamientoAgentes", required = true)
    protected DescComportamientoAgentes descComportamientoAgentes;
    @XmlElement(name = "DescRecursosAplicacion", required = true)
    protected DescRecursosAplicacion descRecursosAplicacion;

    /**
     * Gets the value of the descComportamientoAgentes property.
     * 
     * @return
     *     possible object is
     *     {@link DescComportamientoAgentes }
     *     
     */
    public DescComportamientoAgentes getDescComportamientoAgentes() {
        return descComportamientoAgentes;
    }

    /**
     * Sets the value of the descComportamientoAgentes property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescComportamientoAgentes }
     *     
     */
    public void setDescComportamientoAgentes(DescComportamientoAgentes value) {
        this.descComportamientoAgentes = value;
    }

    /**
     * Gets the value of the descRecursosAplicacion property.
     * 
     * @return
     *     possible object is
     *     {@link DescRecursosAplicacion }
     *     
     */
    public DescRecursosAplicacion getDescRecursosAplicacion() {
        return descRecursosAplicacion;
    }

    /**
     * Sets the value of the descRecursosAplicacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DescRecursosAplicacion }
     *     
     */
    public void setDescRecursosAplicacion(DescRecursosAplicacion value) {
        this.descRecursosAplicacion = value;
    }

}
